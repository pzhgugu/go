package com.ansteel.common.adminhome.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ansteel.core.constant.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ansteel.common.menu.domain.Menu;
import com.ansteel.common.menu.service.MenuService;
import com.ansteel.common.security.domain.Users;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.common.security.service.UserService;
/**
 * 创 建 人：yxb
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：后台管理控制器。  
 */
@Controller
@RequestMapping(value = Public.ADMIN)
public class AdminHomeController {

	private static final String P_MENUS = "P_MENUS";
	@Autowired
	MenuService menuService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/home")
	public String home(Model model,HttpServletRequest request) {
		
		List<Menu> menus=menuService.getMenu(request);
		model.addAttribute(P_MENUS, menus);
		return FisUtils.page("core:pages/framework/framework.html");
	}


	@RequestMapping(method = RequestMethod.GET, value = "/main")
	public String homePage(Model model,HttpServletRequest request) {
		String name = request.getUserPrincipal().getName();
		Users user = userService.findOneByName(name);
        String strAlias = user.getAlias();
		model.addAttribute("ALIAS",strAlias);
		return FisUtils.page("core:pages/home/mainPage.html");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/changePassword")
	public String changePage(Model model,HttpServletRequest request) {
		
		return FisUtils.page("core:pages/changePassword/changePassword.html");
	}

}
