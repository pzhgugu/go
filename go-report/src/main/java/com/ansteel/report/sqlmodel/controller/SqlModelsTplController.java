package com.ansteel.report.sqlmodel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.core.annotation.PathClass;
import com.ansteel.report.sqlmodel.domain.SqlFields;
import com.ansteel.report.sqlmodel.domain.SqlModels;
import com.ansteel.report.sqlmodel.service.SqlModelsService;
import com.ansteel.report.viewelement.service.SqlViewElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.core.annotation.QueryJson;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.query.PageUtils;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.interfaces.service.MakeReport;
import com.ansteel.common.sql.service.SqlService;
import com.ansteel.common.tpl.service.TplService;
import com.ansteel.common.viewelement.service.ViewElement;
import com.ansteel.common.viewelement.service.ViewElementService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql模型模板控制器。
 */
@Controller
@RequestMapping(value = "/sqltpl")
public class SqlModelsTplController {

    @Autowired
    SqlViewElementService viewElementService;

    @Autowired
    SqlModelsService sqlModelsService;

    @Autowired
    SqlService sqlService;


    @Autowired
    TplService tplService;

    private int maxRow = 10000;

    /**
     * ajax调用
     * 单条件查询、带分页
     *
     * @return
     */
    @RequestMapping("/a/queryPage/{modelName}")
    public
    @ResponseBody
    UDataSet queryPageAjax(@PathVariable("modelName") String modelName,
                           @RequestParam(value = "posStart", required = false) String posStart,//分页当前记录行
                           @RequestParam(value = "count", required = false) String count,//分页记录行
                           @QueryJson List<QueryMapping> queryList,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        Page result = this.getResult(modelName, posStart, count, queryList, request);
        return new UDataSet(request, DHtmlxConstants.UI_ROWS, result);
    }

    @RequestMapping("/a/queryTotal/{modelName}")
    public
    @ResponseBody
    UDataSet queryTotalAjax(@PathVariable("modelName") String modelName,
                           @QueryJson List<QueryMapping> queryList,
                           @RequestParam("sumArray[]")  String[] sumArray,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        Map result = this.getResult(modelName,queryList, request,sumArray);
        return new UDataSet(request, DHtmlxConstants.UI_ROWS, result);
    }

    @RequestMapping("/toexcel/{modelName}")
    public void toExcel(@PathVariable("modelName") String modelName,
                        @RequestParam(value = "_report", required = false) String reportName,
                        @QueryJson List<QueryMapping> queryList,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        to(reportName, modelName, queryList, "EXCEL", request, response);
    }

    @RequestMapping("/topdf/{modelName}")
    public void toPdf(@PathVariable("modelName") String modelName,
                      @RequestParam(value = "_report", required = false) String reportName,
                      @QueryJson List<QueryMapping> queryList,
                      HttpServletRequest request,
                      HttpServletResponse response) {
        to(reportName, modelName, queryList, "PDF", request, response);
    }

    @RequestMapping("/toswf/{modelName}")
    public String toSwf(@PathVariable("modelName") String modelName,
                        @RequestParam(value = "_report", required = false) String reportName,
                        @QueryJson List<QueryMapping> queryList,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        String path = to(reportName, modelName, queryList, "SWF", request, response);
        return "redirect:/browsen?url=" + path;
    }

    public String to(String reportName, String modelName,
                     @QueryJson List<QueryMapping> queryList,
                     String type,
                     HttpServletRequest request,
                     HttpServletResponse response) {
        Map<String, Object> operMap = new HashMap<String, Object>();
        for (QueryMapping qm : queryList) {
            if (Public.QUERY_BETWEEN.endsWith(qm.getOperator())) {
                operMap.put("_BEGIN_" + qm.getName(), qm.getBegin());
                operMap.put("_END_" + qm.getName(), qm.getEnd());
            } else if (StringUtils.hasText(qm.getValue())) {
                operMap.put(qm.getName(), qm.getValue());
            }
        }

        SqlModels sqlModels = sqlModelsService.getSqlModels(modelName);
        Assert.notNull(sqlModels, modelName + ",SQL模型中没有找到，请检查！");


        String path = sqlModelsService.showReport(reportName, sqlModels, operMap, type, request, response,null);
        return path;

    }

    private Page getResult(String modelName,
                           String posStart, String count,
                           List<QueryMapping> queryList, HttpServletRequest request) {

        String sqlContent = sqlModelsService.findByNameToSql(modelName);
        Map<String, Object> operMap = new HashMap<String, Object>();
        for (QueryMapping qm : queryList) {
            if (Public.QUERY_BETWEEN.endsWith(qm.getOperator())) {
                operMap.put("_BEGIN_" + qm.getName(), qm.getBegin());
                operMap.put("_END_" + qm.getName(), qm.getEnd());
            } else if (StringUtils.hasText(qm.getValue())) {
                operMap.put(qm.getName(), qm.getValue());
            }
        }
        Pageable pageable = new PageRequest(PageUtils.getTotalPages(posStart,maxRow), maxRow);
        return sqlService.querySqlPage(sqlContent, request, operMap, pageable);
    }

    private Map getResult(String modelName,
                           List<QueryMapping> queryList, HttpServletRequest request,String[] sumArray) {

        String sqlContent = sqlModelsService.findByNameToSql(modelName);
        Map<String, Object> operMap = new HashMap<String, Object>();
        for (QueryMapping qm : queryList) {
            if (Public.QUERY_BETWEEN.endsWith(qm.getOperator())) {
                operMap.put("_BEGIN_" + qm.getName(), qm.getBegin());
                operMap.put("_END_" + qm.getName(), qm.getEnd());
            } else if (StringUtils.hasText(qm.getValue())) {
                operMap.put(qm.getName(), qm.getValue());
            }
        }
        if(sumArray.length>0) {
            StringBuffer sumSB = new StringBuffer("select ");
            for(String s:sumArray){
                sumSB.append("sum("+s+") as "+ s+" ,");
            }
            String sumStr = sumSB.substring(0, sumSB.length()-1);
            sumStr+=" from ("+sqlContent+")";
            return sqlService.querySqlTotal(sumStr, request, operMap);
        }else{
            return new HashMap();
        }
    }

    @RequestMapping("/tpl/{tplName}/{modelType}/{fieldsCategory}")
    public String tpl(@PathVariable("tplName") String tplName,
                      @PathVariable("modelType") String modelType,
                      @PathVariable("fieldsCategory") String fieldsCategory,
                      @RequestParam(value = "_view", required = false) String viewName,
                      Model model,
                      HttpServletRequest request,
                      HttpServletResponse response) {

        ViewElement viewElement = viewElementService.getSQLViewElement(
                request, response, modelType,
                fieldsCategory, tplName, viewName);
        viewElement.setRequestMappingName("/sqltpl");

        return tplService.runTpl(viewElement, model);

    }

}
