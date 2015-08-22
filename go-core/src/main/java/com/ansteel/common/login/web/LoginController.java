package com.ansteel.common.login.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ansteel.core.utils.FisUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：登录控制器。  
 */
@Controller
public class LoginController {
	
	static Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String login(Model model,HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(true); 
		Object errorObject = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if(errorObject instanceof AuthenticationException){
			AuthenticationException e =(AuthenticationException) errorObject;
			logger.info(e.getMessage());
			String message = e.getMessage();
			if(message.equals("Bad credentials")){
				model.addAttribute("errorMessage", "密码错误！");
			}else{
				model.addAttribute("errorMessage", e.getMessage());
			}
		}
		return FisUtils.page("core:pages/login/login.html");
	}
}
