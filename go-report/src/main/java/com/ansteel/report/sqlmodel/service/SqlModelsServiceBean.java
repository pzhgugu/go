package com.ansteel.report.sqlmodel.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.interfaces.service.MakeReport;
import com.ansteel.report.excel.service.ExcelService;
import com.ansteel.report.makereport.service.MakeReportService;
import com.ansteel.report.sqlmodel.domain.*;
import com.ansteel.report.sqlmodel.repository.SqlModelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.exception.PageException;
import com.ansteel.common.sql.service.SqlService;
import org.springframework.util.StringUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql模型服务接口实现。
 */
@Transactional(readOnly = true)
@Service
public class SqlModelsServiceBean implements SqlModelsService {


    @Autowired
    SqlService sqlService;

    @Autowired
    SqlModelsRepository sqlModelsRepository;

    @Autowired
    ExcelService excelService;


    @Autowired
    MakeReportService makeReportService;


    private static final String DEFAULT_FORM = DHtmlxConstants.INPUT;

    @Override
    public Map<String, String> getFieldNames(List mapList) {
        Map<String, String> fieldMap = new HashMap<String, String>();
        if (mapList.size() > 0) {
            for (Object o : mapList) {
                Map<String, Object> map = (Map<String, Object>) o;
                for (Entry<String, Object> entry : map.entrySet()) {
                    Object value = entry.getValue();
                    fieldMap.put(entry.getKey(), value == null ? "" : value.getClass().getSimpleName());
                }
            }
            return fieldMap;
        }
        throw new PageException("sql语句至少应该有一条数据，才可以扫描字段名称！");
    }

    @Override
    @Transactional(readOnly = false)
    public SqlModels scanSqlModels(String id, HttpServletRequest request) {
        SqlModels sqlModels = this.getSqlModelsById(id);
        Assert.notNull(sqlModels, id + ",没有找到");
        String sqlContent = this.getSql(sqlModels);
        List list = sqlService.querySql(sqlContent, request);
        Map<String, String> newFieldMap = this.getFieldNames(list);
        this.setModelInfo(newFieldMap, sqlModels);
        sqlModelsRepository.save(sqlModels);
        return sqlModels;
    }

    @Override
    @Transactional(readOnly = false)
    public String getSql(SqlModels sqlModels) {

        String sqlContent = sqlModels.getSqlContent();
        //增加报表sql读取
        if (sqlModels.getSqlMode() != null && sqlModels.getSqlMode() == 1) {
            sqlContent = getReportSql(sqlContent);
        }
        return sqlContent;
    }

    @Override
    public String findByNameToSql(String modelName) {
        SqlModels sqlModels = this.getSqlModels(modelName);
        Assert.notNull(sqlModels, modelName + ",SQL模型中没有找到，请检查！");
        String sqlContent = this.getSql(sqlModels);
        return sqlContent;
    }

    @Override
    public String showReport(String reportName, SqlModels sqlModels, Map<String, Object> operMap, String type, HttpServletRequest request, HttpServletResponse response,String fileName) {
        String path = "";
        if (sqlModels.getSqlMode() != null && sqlModels.getSqlMode() == 1) {
            String reportContent = sqlModels.getSqlContent();
            Assert.notNull(reportContent, "报表sql配置不能为空！");
            String[] reportParameterArray = reportContent.split(Public.SPLIT_POINT);
            Assert.isTrue(reportParameterArray.length == 2, reportContent + "，报表模式SQL的格式为：报表编码.SQL编码");

            path = makeReportService.show(reportParameterArray[0], type, null, null, operMap, request, response,fileName);
        } else {
            String sqlContent = sqlModels.getSqlContent();
            List listMap = sqlService.querySql(sqlContent, request, operMap);

            if (StringUtils.hasText(reportName)) {
                path = makeReportService.show(reportName, listMap, type, null, request, response,fileName);
            } else {
                Map<String, String> nameMap = new HashMap<String, String>();
                for (SqlFields field : sqlModels.getFields()) {
                    nameMap.put(field.getName(), field.getAlias());
                }
                path = makeReportService.show(listMap, nameMap, type,
                        null, request, response,fileName);
            }
        }
        return path;
    }

    private String getReportSql(String reportParameter) {
        String[] reportParameterArray = reportParameter.split(Public.SPLIT_POINT);
        Assert.isTrue(reportParameterArray.length == 2, reportParameter + "，报表模式SQL的格式为：报表编码.SQL编码");
        return excelService.findByReportAndSql(reportParameterArray[0], reportParameterArray[1]);
    }

    private SqlModels getSqlModelsById(String id) {
        return sqlModelsRepository.findOne(id);
    }

    private SqlFieldsCategory getDefaultFieldsCategory(SqlModels sqlModels) {
        SqlFieldsCategory fieldsCategory = new SqlFieldsCategory();
        fieldsCategory.setName(Public.DEFAULE_CATEGORY_NAME);
        fieldsCategory.setAlias(Public.DEFAULE_CATEGORY_NAME);
        fieldsCategory.setModels(sqlModels);
        return fieldsCategory;
    }

    public void setModelInfo(Map<String, String> newFieldMap, SqlModels sqlModels) {
        Collection<SqlFieldsCategory> fieldsCategoryList = sqlModels.getFieldsCategory();
        if (!(fieldsCategoryList.size() > 0)) {
            fieldsCategoryList.add(getDefaultFieldsCategory(sqlModels));
        }
        List<SqlFields> fields = (List<SqlFields>) sqlModels.getFields();
        Map<String, String> fieldMap = new HashMap<String, String>();
        for (SqlFields df : fields) {
            fieldMap.put(df.getName(), df.getAlias());
        }
        int i = 0;
        for (Entry<String, String> entry : newFieldMap.entrySet()) {
            if (!fieldMap.containsKey(entry.getKey())) {
                fields.add(this.getField(sqlModels, entry.getKey(), entry.getValue(), i));
            }
            for (SqlFieldsCategory fieldsCategory : fieldsCategoryList) {
                this.setSqlFieldsCategory(fieldsCategory, entry.getKey(), entry.getValue(), i);
            }
            i += 5;
        }
    }

    private void setSqlFieldsCategory(
            SqlFieldsCategory sqlFieldsCategory, String name,
            String type, int i) {
        Collection<SqlFieldsForm> formList = sqlFieldsCategory.getFieldsForm();
        Collection<SqlFieldsGrid> gridList = sqlFieldsCategory.getFieldsGrid();
        Collection<SqlFieldsQuery> queryList = sqlFieldsCategory.getFieldsQuery();
        this.getSqlFieldsForm(sqlFieldsCategory, formList, name, type, i);
        this.getSqlFieldsGrid(sqlFieldsCategory, gridList, name, type, i);
        this.getSqlFieldsQuery(sqlFieldsCategory, queryList, name, type, i);
    }

    private void getSqlFieldsQuery(
            SqlFieldsCategory sqlFieldsCategory,
            Collection<SqlFieldsQuery> queryList, String name,
            String type, int i) {
        boolean is = true;
        for (SqlFieldsQuery query : queryList) {
            if (query.getName().equals(name)) {
                is = false;
            }
        }
        if (is) {
            SqlFieldsQuery fieldsQuery = new SqlFieldsQuery();
            fieldsQuery.setName(name);
            fieldsQuery.setDisplayOrder(i);
            fieldsQuery.setFieldsCategory(sqlFieldsCategory);
            fieldsQuery.setIsLock(0);
            fieldsQuery.setIsShow(1);
            queryList.add(fieldsQuery);
        }
    }

    private void getSqlFieldsGrid(
            SqlFieldsCategory sqlFieldsCategory,
            Collection<SqlFieldsGrid> gridList, String name,
            String type, int i) {
        boolean is = true;
        for (SqlFieldsGrid grid : gridList) {
            if (grid.getName().equals(name)) {
                is = false;
            }
        }
        if (is) {
            SqlFieldsGrid fieldsGrid = new SqlFieldsGrid();
            fieldsGrid.setName(name);
            fieldsGrid.setDisplayOrder(i);
            fieldsGrid.setFieldsCategory(sqlFieldsCategory);
            fieldsGrid.setColAlign(Public.DEFAULE_MODELGRID_ALIGN);
            fieldsGrid.setInitWidths(Public.DEFAULE_MODELGRID_WIDTHS);
            fieldsGrid.setColSorting(Public.DEFAULE_MODELGRID_SORTING);
            fieldsGrid.setColTypes(Public.DEFAULE_MODELGRID_TYPES);
            fieldsGrid.setIsShow(1);
            gridList.add(fieldsGrid);
        }
    }

    private void getSqlFieldsForm(SqlFieldsCategory sqlFieldsCategory,
                                  Collection<SqlFieldsForm> formList, String key, String type,
                                  int i) {
        boolean is = true;
        for (SqlFieldsForm form : formList) {
            if (form.getName().equals(key)) {
                is = false;
            }
        }
        if (is) {
            SqlFieldsForm fieldsForm = new SqlFieldsForm();
            fieldsForm.setName(key);
            fieldsForm.setDisplayOrder(i);
            fieldsForm.setType(DEFAULT_FORM);
            fieldsForm.setIsShow(1);
            fieldsForm.setFieldsCategory(sqlFieldsCategory);
            formList.add(fieldsForm);
        }
    }

    private SqlFields getField(SqlModels sqlModels, String key, String value, int displayOrder) {
        SqlFields sqlFields = new SqlFields();
        sqlFields.setName(key);
        sqlFields.setAlias(key);
        sqlFields.setFieldType(value);
        sqlFields.setDisplayOrder(displayOrder);
        sqlFields.setModels(sqlModels);
        return sqlFields;
    }

    @Override
    public SqlModels getSqlModels(String modelName) {
        return sqlModelsRepository.findOneByName(modelName);
    }


}
