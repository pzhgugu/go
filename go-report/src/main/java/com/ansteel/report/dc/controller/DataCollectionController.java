package com.ansteel.report.dc.controller;


import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.annotation.QueryJson;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.controller.SaveBefore;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.MainSubInfo;
import com.ansteel.core.domain.TreeInfo;
import com.ansteel.core.service.CheckService;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.core.utils.RequestUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.report.dc.domain.DataCollection;
import com.ansteel.report.dc.domain.DataCollectionSQL;
import com.ansteel.report.dc.domain.DataCollectionTest;
import com.ansteel.report.dc.domain.DataCollectionTree;
import com.ansteel.report.dc.service.DataCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：数据收集控制器。
 */
@Controller
@RequestMapping(value = Public.ADMIN+ "/dc")
public class DataCollectionController extends BaseController implements SaveBefore {

   @Autowired
   DataCollectionService dataCollectionService;

    @Autowired
    CheckService checkService;

    @Override
    public Collection<EntityInfo> getEntityInfos() {
        //树设置
        TreeInfo treeInfo=new TreeInfo();
        treeInfo.setTree(DataCollectionTree.class);
        Collection<Class> tables=new ArrayList<Class>();
        tables.add(DataCollection.class);
        treeInfo.setTables(tables);

        MainSubInfo msi=new MainSubInfo();
        msi.setPrincipal(DataCollection.class);
        Collection<Class> subs=new ArrayList<Class>();
        subs.add(DataCollectionSQL.class);
        subs.add(DataCollectionTest.class);
        msi.setSubordinate(subs);

        Collection<EntityInfo> eis = new ArrayList<EntityInfo>();
        EntityInfo tree = new EntityInfo();
        tree.setClazz(DataCollectionTree.class);
        tree.setTree(treeInfo);
        eis.add(tree);

        EntityInfo excelReport = new EntityInfo();
        excelReport.setClazz(DataCollection.class);
        excelReport.setTree(treeInfo);
        excelReport.setMainSub(msi);
        eis.add(excelReport);

        EntityInfo excelReportSQL = new EntityInfo();
        excelReportSQL.setClazz(DataCollectionSQL.class);
        excelReportSQL.setMainSub(msi);
        eis.add(excelReportSQL);

        EntityInfo excelReportTest = new EntityInfo();
        excelReportTest.setClazz(DataCollectionTest.class);
        excelReportTest.setMainSub(msi);
        eis.add(excelReportTest);

        return eis;
    }

    @Override
    public <T extends BaseEntity> void SaveCheck(T entity) {
        if (entity.getClass() == DataCollection.class ) {
            DataCollection dataCollection = (DataCollection) entity;
            Assert.isTrue(!checkService.isNameRepeat(dataCollection), dataCollection.getName() + ",已经存在，请检查！");
        }
    }

    public @ResponseBody UDataSet loadPageAjax(@PathClass("className")Class clazz,
                                               @RequestParam(value="_key",required=false)String key,//过滤字段名（一般用于主从表）
                                               @RequestParam(value="_value",required=false)String value,//过滤字段值（一般用于主从表）
                                               @RequestParam(value="posStart",required=false)String posStart,//分页当前记录行
                                               @RequestParam(value="count",required=false)String count,//分页记录行
                                               @RequestParam(value="_order",required=false)String order,//排序字段名
                                               HttpServletRequest request,
                                               HttpServletResponse response){
        UDataSet dataSet = super.loadPageAjax(clazz, key, value, posStart, count, order, request, response);
        if(clazz==DataCollectionTest.class){
            return dataCollectionService.setTestPath(dataSet,request);
        }
        return dataSet;
    }

    @RequestMapping("/a/run/{name}")
    public @ResponseBody
    void loadAjax(@PathVariable("name")String name,
                      HttpServletRequest request){
        Map requestMap = RequestUtils.getRequestMapUnicode(request);
        boolean is = dataCollectionService.run(name,request,requestMap);
    }
}
