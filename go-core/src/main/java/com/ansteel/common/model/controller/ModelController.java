package com.ansteel.common.model.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.core.constant.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.common.model.domain.EntityFields;
import com.ansteel.common.model.domain.EntityFieldsCategory;
import com.ansteel.common.model.domain.EntityFieldsForm;
import com.ansteel.common.model.domain.EntityFieldsGrid;
import com.ansteel.common.model.domain.EntityFieldsQuery;
import com.ansteel.common.model.domain.Models;
import com.ansteel.common.model.service.ModelService;
import com.ansteel.common.tpl.core.IDhtmlxWidget;
import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.core.constant.TplViewConstant;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.controller.SaveBefore;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.MainSubInfo;
import com.ansteel.core.domain.TreeInfo;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.service.CheckService;
import com.ansteel.common.viewelement.service.ViewElement;
import com.ansteel.common.viewelement.service.ViewElementService;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体模型控制器。  
 */
@Controller
@RequestMapping(value = Public.ADMIN+"/model")
public class ModelController  extends BaseController implements SaveBefore{
	
	@Autowired
	ViewElementService viewElementService;
	
	@Autowired
	ModelService modelService;

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		
		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
		
		TreeInfo treeInfo=new TreeInfo();
		treeInfo.setTree(Models.class);
		Collection<Class> tables=new ArrayList<Class>();
		tables.add(EntityFields.class);
		treeInfo.setTables(tables);
		
		TreeInfo typeInfo=new TreeInfo();
		typeInfo.setTree(Models.class);
		Collection<Class> typeTables=new ArrayList<Class>();
		typeTables.add(EntityFieldsCategory.class);
		typeInfo.setTables(typeTables);
		
		EntityInfo treeEntityInfo = new EntityInfo();
		treeEntityInfo.setClazz(Models.class);
		treeEntityInfo.setTree(treeInfo);
		entityInfos.add(treeEntityInfo);
		
		EntityInfo tableEntityInfo = new EntityInfo();
		tableEntityInfo.setClazz(EntityFields.class);
		tableEntityInfo.setTree(treeInfo);
		entityInfos.add(tableEntityInfo);
		
		
		MainSubInfo msi=new MainSubInfo();
		Collection<Class> subs=new ArrayList<Class>();
		msi.setPrincipal(EntityFieldsCategory.class);
		subs.add(EntityFieldsGrid.class);
		subs.add(EntityFieldsForm.class);
		subs.add(EntityFieldsQuery.class);
		msi.setSubordinate(subs);
		

		
		EntityInfo fieldsCategoryEntityInfo = new EntityInfo();
		fieldsCategoryEntityInfo.setClazz(EntityFieldsCategory.class);
		fieldsCategoryEntityInfo.setTree(typeInfo);
		fieldsCategoryEntityInfo.setMainSub(msi);
		entityInfos.add(fieldsCategoryEntityInfo);
		

		EntityInfo fieldsGrid = new EntityInfo();
		fieldsGrid.setClazz(EntityFieldsGrid.class);
		fieldsGrid.setMainSub(msi);
		entityInfos.add(fieldsGrid);
		
		EntityInfo fieldsForm = new EntityInfo();
		fieldsForm.setClazz(EntityFieldsForm.class);
		fieldsForm.setMainSub(msi);
		entityInfos.add(fieldsForm);
		
		EntityInfo fieldsQuery = new EntityInfo();
		fieldsQuery.setClazz(EntityFieldsQuery.class);
		fieldsQuery.setMainSub(msi);
		entityInfos.add(fieldsQuery);
		
		return entityInfos;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String model(HttpServletRequest request,
			HttpServletResponse response,Model model){
		
		this.addTypeModel(request, response, model);
		
		this.addFieldModel(request, response, model);
		
		
		return "admin/model.html";		
	}
	

	private void addFieldModel(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		//自定义字段页面元素
		String prefix = "FIELD";
		ViewElement viewElement = viewElementService.getViewElement(request, response, EntityFields.class, 
				TplViewConstant.DEFAULT_SHORT, this.getEntityInfos(), this.getClass());
		viewElement.setViewPrefix(prefix);
		
		//构建字段表格元素
		IDhtmlxWidget grid=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_GRID);
		model.addAllAttributes(grid.structure(viewElement));
		
		//构建字段表单元素
		IDhtmlxWidget form=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_FORM);
		model.addAllAttributes(form.structure(viewElement));
		
		//构建简单查询
		IDhtmlxWidget query=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_SIMPLE_QUERY);
		model.addAllAttributes(query.structure(viewElement));
		
	}

	private void addTypeModel(HttpServletRequest request,
			HttpServletResponse response,Model model) {
		//自定义分类页面元素
		String prefix = "TYPE";
		ViewElement viewElement = viewElementService.getViewElement(request, response, EntityFieldsCategory.class, 
				TplViewConstant.DEFAULT_SHORT, this.getEntityInfos(), this.getClass());
		viewElement.setViewPrefix(prefix);
		
		//构建分类表格元素
		IDhtmlxWidget grid=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_GRID);
		model.addAllAttributes(grid.structure(viewElement));
		//构建分类表单元素
		IDhtmlxWidget form=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_FORM);
		Tpl tpl =new Tpl();
		tpl.setColumnNumber(1);
		tpl.setPosition(DHtmlxConstants.LABEL_LEFT_DEFAUTL);
		tpl.setLabelWidth(DHtmlxConstants.LABEL_WIDTH_DEFAUTL);
		tpl.setInputWidth(DHtmlxConstants.INPUT_WIDTH_DEFAUTL);
		tpl.setBlockWidth(340);
		viewElement.setTpl(tpl);
		model.addAllAttributes(form.structure(viewElement));
	}

	@RequestMapping("/scan")
	@ResponseBody
	public void scan(@RequestParam(value="id") String id){
		Models models = modelService.findOne(id);
		String name = models.getRequestMappingName();
		String clazzName = models.getClazz();
		
		try {
			Class<?> clazz = Class.forName(clazzName);
			modelScanService.scanModel(clazz,name);
		} catch (ClassNotFoundException e) {
			throw new PageException(e.getMessage());
		}
	}

	@Autowired
	CheckService checkService;
	
	@Override
	public <T extends BaseEntity> void SaveCheck(T entity) {
		if(entity instanceof Models){
			Models o=(Models) entity;
			Assert.isTrue(!checkService.isNameRepeat(o),o.getName()+"名称重复请重新设置！");
		}else if(entity instanceof EntityFields){
			EntityFields o=(EntityFields) entity;
			Models m = o.getModels();
			Assert.notNull(m, m.getAlias()+",模型没有找到,请检查！");
			Assert.isTrue(!checkService.isNodeNameRepeat(o,"models",m.getId()),o.getName()+"名称重复请重新设置！");
		}if(entity instanceof EntityFieldsCategory){
			EntityFieldsCategory o=(EntityFieldsCategory) entity;
			Models m = o.getModels();
			Assert.notNull(m, m.getAlias()+",模型没有找到,请检查！");
			Assert.isTrue(!checkService.isNodeNameRepeat(o,"models",m.getId()),o.getName()+"名称重复请重新设置！");
		}
	}
}
