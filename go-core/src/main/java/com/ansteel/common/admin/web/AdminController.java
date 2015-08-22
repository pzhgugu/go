package com.ansteel.common.admin.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：后台管理控制器。  
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController{
	
	@RequestMapping("/index")
	public String index(
			HttpServletRequest request, 
			HttpServletResponse response){
				
		return "/template/default/admin/index";		
	}
	
	@RequestMapping("/json")
	public @ResponseBody Object josn(
			HttpServletRequest request, 
			HttpServletResponse response){
		Map<String,Object> jsonMap = new HashMap<String,Object>();	
		jsonMap.put("em", 123);
		return jsonMap;		
	}

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		// TODO Auto-generated method stub
		return null;
	}

}
