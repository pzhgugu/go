package com.ansteel.common.dynamicmodel.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.common.dynamicmodel.domain.DynamicFields;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsCategory;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsForm;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsGrid;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsQuery;
import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.common.dynamicmodel.service.DynamicModelsService;
import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.constant.Public;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.controller.SaveBefore;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.MainSubInfo;
import com.ansteel.core.domain.TreeInfo;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.service.CheckService;
import com.ansteel.core.utils.StringUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态建模控制器。  
 */
@Controller
@RequestMapping(value = Public.ADMIN+"/dymodel")
public class DynamicModelsController extends BaseController implements SaveBefore{
	
	private static final int DYNAMIC_TYPE = 1;

	
	@Autowired
	DynamicModelsService dynamicModelsService;
	
	@Autowired
	CheckService checkService;

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		
		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
		
		TreeInfo treeInfo=new TreeInfo();
		treeInfo.setTree(DynamicModels.class);
		Collection<Class> tables=new ArrayList<Class>();
		tables.add(DynamicFields.class);
		treeInfo.setTables(tables);
		
		TreeInfo typeInfo=new TreeInfo();
		typeInfo.setTree(DynamicModels.class);
		Collection<Class> typeTables=new ArrayList<Class>();
		typeTables.add(DynamicFieldsCategory.class);
		typeInfo.setTables(typeTables);
		
		EntityInfo treeEntityInfo = new EntityInfo();
		treeEntityInfo.setClazz(DynamicModels.class);
		treeEntityInfo.setTree(treeInfo);
		entityInfos.add(treeEntityInfo);
		
		EntityInfo tableEntityInfo = new EntityInfo();
		tableEntityInfo.setClazz(DynamicFields.class);
		tableEntityInfo.setTree(treeInfo);
		entityInfos.add(tableEntityInfo);
		
		
		MainSubInfo msi=new MainSubInfo();
		Collection<Class> subs=new ArrayList<Class>();
		msi.setPrincipal(DynamicFieldsCategory.class);
		subs.add(DynamicFieldsGrid.class);
		subs.add(DynamicFieldsForm.class);
		subs.add(DynamicFieldsQuery.class);
		msi.setSubordinate(subs);
		

		
		EntityInfo fieldsCategoryEntityInfo = new EntityInfo();
		fieldsCategoryEntityInfo.setClazz(DynamicFieldsCategory.class);
		fieldsCategoryEntityInfo.setTree(typeInfo);
		fieldsCategoryEntityInfo.setMainSub(msi);
		entityInfos.add(fieldsCategoryEntityInfo);
		

		EntityInfo fieldsGrid = new EntityInfo();
		fieldsGrid.setClazz(DynamicFieldsGrid.class);
		fieldsGrid.setMainSub(msi);
		entityInfos.add(fieldsGrid);
		
		EntityInfo fieldsForm = new EntityInfo();
		fieldsForm.setClazz(DynamicFieldsForm.class);
		fieldsForm.setMainSub(msi);
		entityInfos.add(fieldsForm);
		
		EntityInfo fieldsQuery = new EntityInfo();
		fieldsQuery.setClazz(DynamicFieldsQuery.class);
		fieldsQuery.setMainSub(msi);
		entityInfos.add(fieldsQuery);
		
		return entityInfos;
	}
	
	public void delectAjax(@PathClass("className")Class clazz,
			@RequestParam("id")String id,
			HttpServletRequest request,
			HttpServletResponse response){
		if(clazz==DynamicFields.class){
			DynamicFields dynamicFields=dynamicModelsService.findOne(id);
			DynamicModels models = dynamicFields.getModels();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("name", dynamicFields.getName());
			Collection<DynamicFieldsCategory> fieldsCategoryList = models.getFieldsCategory();
			for(DynamicFieldsCategory fieldsCategory: fieldsCategoryList ){
				map.put("fieldsCategory", fieldsCategory);
				Collection<DynamicFieldsForm> formList = dynamicModelsService.findDynamicFieldsForm(map);
				dynamicModelsService.delect(formList);
				Collection<DynamicFieldsGrid> gridList =  dynamicModelsService.findDynamicFieldsGrid(map);
				dynamicModelsService.delect(gridList);
				Collection<DynamicFieldsQuery> queryList =  dynamicModelsService.findDynamicFieldsQuery(map);
				dynamicModelsService.delect(queryList);
			}

			dynamicModelsService.delect(dynamicFields);
			return ;
		}
		super.delectAjax(clazz, id, request, response);
	}

	@RequestMapping("/createTable")
	public @ResponseBody int createTable(@RequestParam("id")String id,HttpServletRequest request,
			HttpServletResponse response){
		dynamicModelsService.createTable(id);
		return 0;
	}
	
	@RequestMapping("/createTable/all")
	public @ResponseBody int createTableAll(HttpServletRequest request,
			HttpServletResponse response){
		dynamicModelsService.createTable();
		return 0;
	}

	@Override
	public <T extends BaseEntity> void SaveCheck(T entity) {
		if(entity instanceof DynamicFields){
			DynamicFields dynamicFields = (DynamicFields) entity;
			String name = dynamicFields.getName().toUpperCase();
			if(name.indexOf(Public.FIELD_PREFIX)!=0){
				dynamicFields.setName(Public.FIELD_PREFIX+name);
			}else{
				dynamicFields.setName(name);
			}
			DynamicModels model = dynamicFields.getModels();
			if(model!=null){
				DynamicModels dynamicModels=dynamicModels = dynamicModelsService.getDynamicModelsById(model.getId(),DYNAMIC_TYPE);
				Assert.notNull(dynamicModels, model.getAlias()+",模型没有找到,请检查模型类型是否为动态模型！");				
				Assert.isTrue(!checkService.isNodeNameRepeat(dynamicFields, "models", model.getId()), "名称重复请重新设置");
				if(!StringUtils.hasText(dynamicFields.getId())){
					dynamicModelsService.setModelInfo(dynamicFields, dynamicModels);
				}
			}else{
				throw new PageException("请选择节点！");
			}
			
		}
	}
}
