package com.ansteel.report.sqlmodel.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.ansteel.core.constant.Public;
import com.ansteel.report.sqlmodel.domain.*;
import com.ansteel.report.sqlmodel.service.SqlModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.controller.SaveBefore;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.MainSubInfo;
import com.ansteel.core.domain.TreeInfo;
import com.ansteel.core.service.CheckService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql模型控制器。
 */
@Controller
@RequestMapping(value = Public.ADMIN+ "/sqlmodel")
public class SqlModelsController extends BaseController implements SaveBefore {

    @Autowired
    SqlModelsService sqlModelsService;


    @Override
    public Collection<EntityInfo> getEntityInfos() {

        Collection<EntityInfo> entityInfos = new ArrayList<EntityInfo>();

        TreeInfo treeInfo = new TreeInfo();
        treeInfo.setTree(SqlModels.class);
        Collection<Class> tables = new ArrayList<Class>();
        tables.add(SqlFields.class);
        treeInfo.setTables(tables);

        TreeInfo typeInfo = new TreeInfo();
        typeInfo.setTree(SqlModels.class);
        Collection<Class> typeTables = new ArrayList<Class>();
        typeTables.add(SqlFieldsCategory.class);
        typeInfo.setTables(typeTables);

        EntityInfo treeEntityInfo = new EntityInfo();
        treeEntityInfo.setClazz(SqlModels.class);
        treeEntityInfo.setTree(treeInfo);
        entityInfos.add(treeEntityInfo);

        EntityInfo tableEntityInfo = new EntityInfo();
        tableEntityInfo.setClazz(SqlFields.class);
        tableEntityInfo.setTree(treeInfo);
        entityInfos.add(tableEntityInfo);


        MainSubInfo msi = new MainSubInfo();
        Collection<Class> subs = new ArrayList<Class>();
        msi.setPrincipal(SqlFieldsCategory.class);
        subs.add(SqlFieldsGrid.class);
        subs.add(SqlFieldsForm.class);
        subs.add(SqlFieldsQuery.class);
        msi.setSubordinate(subs);


        EntityInfo fieldsCategoryEntityInfo = new EntityInfo();
        fieldsCategoryEntityInfo.setClazz(SqlFieldsCategory.class);
        fieldsCategoryEntityInfo.setTree(typeInfo);
        fieldsCategoryEntityInfo.setMainSub(msi);
        entityInfos.add(fieldsCategoryEntityInfo);


        EntityInfo fieldsGrid = new EntityInfo();
        fieldsGrid.setClazz(SqlFieldsGrid.class);
        fieldsGrid.setMainSub(msi);
        entityInfos.add(fieldsGrid);

        EntityInfo fieldsForm = new EntityInfo();
        fieldsForm.setClazz(SqlFieldsForm.class);
        fieldsForm.setMainSub(msi);
        entityInfos.add(fieldsForm);

        EntityInfo fieldsQuery = new EntityInfo();
        fieldsQuery.setClazz(SqlFieldsQuery.class);
        fieldsQuery.setMainSub(msi);
        entityInfos.add(fieldsQuery);

        return entityInfos;
    }

    @RequestMapping("/scan")
    @ResponseBody
    public void scan(@RequestParam(value = "id") String id,
                     HttpServletRequest request) {
        sqlModelsService.scanSqlModels(id, request);
    }

    @Autowired
    CheckService checkService;

    @Override
    public <T extends BaseEntity> void SaveCheck(T entity) {
        if (entity instanceof SqlModels) {
            SqlModels o = (SqlModels) entity;
            Assert.isTrue(!checkService.isNameRepeat(o), o.getName() + "名称重复请重新设置！");
        } else if (entity instanceof SqlFieldsCategory) {
            SqlFieldsCategory o = (SqlFieldsCategory) entity;
            SqlModels m = o.getModels();
            Assert.notNull(m, m.getAlias() + ",模型没有找到,请检查！");
            Assert.isTrue(!checkService.isNodeNameRepeat(o, "models", m.getId()), o.getName() + "名称重复请重新设置！");
        }
        if (entity instanceof SqlFields) {
            SqlFields o = (SqlFields) entity;
            SqlModels m = o.getModels();
            Assert.notNull(m, m.getAlias() + ",模型没有找到,请检查！");
            Assert.isTrue(!checkService.isNodeNameRepeat(o, "models", m.getId()), o.getName() + "名称重复请重新设置！");
        }
    }


}
