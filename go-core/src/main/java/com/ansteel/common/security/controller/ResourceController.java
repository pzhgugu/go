package com.ansteel.common.security.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ansteel.core.constant.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.common.security.domain.Permission;
import com.ansteel.common.security.domain.PermissionURL;
import com.ansteel.common.security.domain.Resources;
import com.ansteel.common.security.domain.Roles;
import com.ansteel.common.security.service.ResourcesService;
import com.ansteel.common.security.service.SecurityService;
import com.ansteel.common.tpl.core.IDhtmlxWidget;
import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.annotation.PathDatabaseEntity;
import com.ansteel.core.annotation.PathGridData;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.core.constant.TplViewConstant;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.MainSubInfo;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.dhtmlx.xml.Data;
import com.ansteel.common.viewelement.service.ViewElement;
import com.ansteel.common.viewelement.service.ViewElementService;

@Controller
@RequestMapping(value = Public.ADMIN+ "/resource")
public class ResourceController extends BaseController {

	@Autowired
	ViewElementService viewElementService;
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	ResourcesService resourcesService;

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> entityInfos = new ArrayList<EntityInfo>();
		
		MainSubInfo mainSubInfo = new MainSubInfo();
		mainSubInfo.setPrincipal(Resources.class);
		Collection<Class> subs = new ArrayList<Class>();
		subs.add(Permission.class);
		subs.add(PermissionURL.class);
		subs.add(Roles.class);
		mainSubInfo.setSubordinate(subs);
		
		EntityInfo resEntityInfo = new EntityInfo();
		resEntityInfo.setClazz(Resources.class);
		resEntityInfo.setMainSub(mainSubInfo);
		entityInfos.add(resEntityInfo);
		
		EntityInfo perEntityInfo = new EntityInfo();
		perEntityInfo.setClazz(Permission.class);
		perEntityInfo.setMainSub(mainSubInfo);
		entityInfos.add(perEntityInfo);
		
		EntityInfo urlEntityInfo = new EntityInfo();
		urlEntityInfo.setClazz(PermissionURL.class);
		urlEntityInfo.setMainSub(mainSubInfo);
		entityInfos.add(urlEntityInfo);
		
		EntityInfo rolesEntityInfo = new EntityInfo();
		rolesEntityInfo.setClazz(Roles.class);
		rolesEntityInfo.setMainSub(mainSubInfo);
		entityInfos.add(rolesEntityInfo);
		
		return entityInfos;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String resource(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		// 自定义字段页面元素
		String prefix = "TOP";
		ViewElement viewElement = viewElementService.getViewElement(request,
				response, Resources.class, TplViewConstant.DEFAULT_SHORT,
				this.getEntityInfos(), this.getClass());
		viewElement.setViewPrefix(prefix);

		// 构建简单查询
		IDhtmlxWidget query = ContextHolder
				.getSpringBean(ServiceConstants.DTHMLX_SIMPLE_QUERY);
		model.addAllAttributes(query.structure(viewElement));

		// 构建字段表格元素
		IDhtmlxWidget grid = ContextHolder
				.getSpringBean(ServiceConstants.DTHMLX_GRID);
		model.addAllAttributes(grid.structure(viewElement));

		// 构建字段表单元素
		IDhtmlxWidget form = ContextHolder
				.getSpringBean(ServiceConstants.DTHMLX_FORM);
		model.addAllAttributes(form.structure(viewElement));

		return FisUtils.page("core:pages/resource/resource.html");
	}
	
	@RequestMapping("/resourceRole")
	public String resourceRole(@RequestParam(value = "_value", required = false) String value,
			HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Resources resources = resourcesService.findOne(value);		
		model.addAttribute("P_PERMISSION",securityService.getPermissionDefaultStr(resources));
		return FisUtils.page("core:pages/resource/resourceRole.html");
	}


	@RequestMapping("/a/resLoadRole")
	public @ResponseBody
	UDataSet resLoadRole(@RequestParam(value = "_value", required = false) String value,// 过滤字段值（一般用于主从表）
			@RequestParam(value = "posStart", required = false) String posStart,// 分页当前记录行
			@RequestParam(value = "count", required = false) String count,// 分页记录行
			HttpServletRequest request, HttpServletResponse response) {
		Page result = securityService.resLoadRole(value, posStart, count);
		return new UDataSet(request, DHtmlxConstants.UI_ROWS, result);
		
	}
	
	@RequestMapping("/a/resQueryRole")
	public @ResponseBody
	UDataSet resQueryRole(@RequestParam(value = "_value", required = false) String value,// 过滤字段值（一般用于主从表）
			@RequestParam(value = "posStart", required = false) String posStart,// 分页当前记录行
			@RequestParam(value = "count", required = false) String count,// 分页记录行
			@RequestParam(value = "where", required = false) String where,// 分页记录行
			@RequestParam(value = "whereValue", required = false) String whereValue,// 分页记录行
			HttpServletRequest request, HttpServletResponse response) {
		Page result = securityService.resQueryRole(value,where,whereValue,posStart, count);
		return new UDataSet(request, DHtmlxConstants.UI_ROWS, result);
		
	}

	@RequestMapping("/a/updateResourceRole/{className}")
	public @ResponseBody
	Data updateResourceRole(
			@PathGridData("className") List<BaseEntity> baseEntityLsit,
			@RequestParam(value = "_key", required = false) String key,
			@RequestParam(value = "_value", required = false) String value,
			HttpServletRequest request) {		
		return securityService.updateResourceRole(value, baseEntityLsit);		
	}
	
	public void saveAjax(@Valid @PathDatabaseEntity("className")BaseEntity entity,
			BindingResult result,
			@RequestParam(value="_key",required=false)String key,
			@RequestParam(value="_value",required=false)String value,
			HttpServletRequest request,
			HttpServletResponse response
			){
		if (entity instanceof Permission) {  			
			securityService.savePermission((Permission)entity,value);
	    }else if (entity instanceof PermissionURL) {  			
	    	super.saveAjax(entity, result, key, value, request,response);
	    	securityService.refresh();
	    }  else{
	    	super.saveAjax(entity, result, key, value, request,response);
	    }
	}
	
	public void delectAjax(@PathClass("className")Class clazz,
			@RequestParam("id")String id,
			HttpServletRequest request,
			HttpServletResponse response){
		if(clazz==Resources.class){
			resourcesService.delect(id);
		}else{
			super.delectAjax(clazz, id, request, response);
		}
		securityService.refresh();
	}
		
	public Data updateAllAjax(@PathGridData("className")List<BaseEntity> baseEntityLsit,
			@RequestParam(value="_key",required=false)String key,
			@RequestParam(value="_value",required=false)String value,
			HttpServletRequest request){ 
		Data data = super.updateAllAjax(baseEntityLsit, key, value, request);
		securityService.refresh();
		return data;
	}
}
