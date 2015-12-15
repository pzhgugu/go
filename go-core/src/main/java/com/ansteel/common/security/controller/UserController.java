package com.ansteel.common.security.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ansteel.core.constant.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.common.security.domain.Roles;
import com.ansteel.common.security.domain.Users;
import com.ansteel.common.security.service.SecurityService;
import com.ansteel.common.security.service.UserService;
import com.ansteel.common.tpl.core.IDhtmlxWidget;
import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.annotation.PathGridData;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.core.constant.TplViewConstant;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.controller.SaveBefore;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.MainSubInfo;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.dhtmlx.xml.Data;
import com.ansteel.common.viewelement.service.ViewElement;
import com.ansteel.common.viewelement.service.ViewElementService;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：用户控制器。  
 */
@Controller
@RequestMapping(value = Public.ADMIN+"/user")
public class UserController extends BaseController implements SaveBefore{

	@Autowired
	ViewElementService viewElementService;
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	UserService userService;

	@Override
	public Collection<EntityInfo> getEntityInfos() {

		Collection<EntityInfo> entityInfos = new ArrayList<EntityInfo>();

		MainSubInfo mainSubInfo = new MainSubInfo();
		mainSubInfo.setPrincipal(Users.class);
		Collection<Class> subs = new ArrayList<Class>();
		subs.add(Roles.class);
		mainSubInfo.setSubordinate(subs);

		EntityInfo usersEntityInfo = new EntityInfo();
		usersEntityInfo.setClazz(Users.class);
		usersEntityInfo.setMainSub(mainSubInfo);
		entityInfos.add(usersEntityInfo);

		EntityInfo rolesEntityInfo = new EntityInfo();
		rolesEntityInfo.setClazz(Roles.class);
		rolesEntityInfo.setMainSub(mainSubInfo);
		entityInfos.add(rolesEntityInfo);

		return entityInfos;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String user(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		// 自定义字段页面元素
		String prefix = "TOP";
		ViewElement viewElement = viewElementService.getViewElement(request,
				response, Users.class, TplViewConstant.DEFAULT_SHORT,
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

		return FisUtils.page("core:pages/user/user.html");
	}
	
	@RequestMapping("/roles")
	public String roles(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 自定义字段页面元素

		return FisUtils.page("core:pages/user/userRole.html");
	}

	@RequestMapping("/a/userLoadRole")
	public @ResponseBody
	UDataSet userLoadRole(@RequestParam(value = "_value", required = false) String value,// 过滤字段值（一般用于主从表）
			@RequestParam(value = "posStart", required = false) String posStart,// 分页当前记录行
			@RequestParam(value = "count", required = false) String count,// 分页记录行
			HttpServletRequest request, HttpServletResponse response) {
		Page result = securityService.userLoadRole(value, posStart, count);
		return new UDataSet(request, DHtmlxConstants.UI_ROWS, result);
		
	}
	
	@RequestMapping("/a/userQueryRole")
	public @ResponseBody
	UDataSet userQueryRole(@RequestParam(value = "_value", required = false) String value,// 过滤字段值（一般用于主从表）
			@RequestParam(value = "posStart", required = false) String posStart,// 分页当前记录行
			@RequestParam(value = "count", required = false) String count,// 分页记录行
			@RequestParam(value = "where", required = false) String where,// 分页记录行
			@RequestParam(value = "whereValue", required = false) String whereValue,// 分页记录行
			HttpServletRequest request, HttpServletResponse response) {
		Page result = securityService.userQueryRole(value,where,whereValue, posStart, count);
		return new UDataSet(request, DHtmlxConstants.UI_ROWS, result);
	}

	@RequestMapping("/a/updateUserRoles/{className}")
	@ResponseBody
	public Data updateUserRoles(@PathGridData("className")List<BaseEntity> baseEntityLsit,
			@RequestParam(value="_key",required=false)String key,
			@RequestParam(value="_value",required=false)String value,
			HttpServletRequest request){ 		
		return securityService.updateUserRolesData(value, baseEntityLsit);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/validate")
	public @ResponseBody String changePassword(@RequestParam(value="oldPwd")String oldPwd,
			@RequestParam(value="newPwd")String newPwd,
			@RequestParam(value="newPwdConfirm")String newPwdConfirm,
			HttpServletRequest request) {	
			String name = request.getUserPrincipal().getName();
			return userService.changePassword(oldPwd, newPwd, newPwdConfirm, name);

	}

	@Override
	public <T extends BaseEntity> void SaveCheck(T entity) {		
		if(entity instanceof Users){
			Users user=(Users) entity;
			String password = user.getPassword();
			if(StringUtils.hasText(password)){
				user.setPassword(userService.getPasswordEncoder(password, null));
			}
		}
	}
	
	public void delectAjax(@PathClass("className")Class clazz,
			@RequestParam("id")String id,
			HttpServletRequest request,
			HttpServletResponse response){
		if(clazz==Users.class){
			userService.delect(id);
		}else{
			super.delectAjax(clazz, id, request, response);
		}
	}

}
