package com.ansteel.report.dc.service;

import com.ansteel.common.sql.service.SqlService;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.report.dc.domain.DataCollection;
import com.ansteel.report.dc.domain.DataCollectionSQL;
import com.ansteel.report.dc.domain.DataCollectionTest;
import com.ansteel.report.dc.repository.DataCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly=true)
public class DataCollectionServiceImpl implements DataCollectionService {

    @Autowired
    DataCollectionRepository dataCollectionRepository;

    @Autowired
    SqlService sqlService;

    @Override
    public Boolean run(String name,HttpServletRequest request, Map requestMap) {
        DataCollection dc = dataCollectionRepository.findOneByName(name);
        Collection<DataCollectionSQL> dcSqlList = dc.getDataCollectionSQLList();
        for(DataCollectionSQL dcSql :dcSqlList){
            this.runSQL(dcSql,request,requestMap);
        }
        return true;
    }

    @Override
    public UDataSet setTestPath(UDataSet dataSet, HttpServletRequest request) {
        Page result = (Page) dataSet.getResult();
        List<DataCollectionTest> ers=(List<DataCollectionTest>) result.getContent();
        String url = (String) request
                .getAttribute(ViewModelConstant.S_URL);
        for(DataCollectionTest er:ers){
            String testParam = er.getTestParam();
            StringBuffer sbLink = new StringBuffer();
            sbLink.append(url);
            sbLink.append("/admin/dc/a/run/");
            sbLink.append(er.getDataCollection().getName());
            sbLink.append("?_type=1");
            if(StringUtils.hasText(testParam)){
                sbLink.append("&");
                sbLink.append(testParam);
            }
            String webUrl = sbLink.toString();
            er.setTestLink(webUrl+"^"+webUrl);
        }
        return dataSet;
    }

    private void runSQL(DataCollectionSQL dcSql, HttpServletRequest request, Map<String, Object> parameterMap) {
        String sql = dcSql.getSqlContent();
        Assert.hasText(sql, dcSql.getName()+":SQL不能为空！");
        sqlService.executeUpdate(sql, request, parameterMap);
    }
}
