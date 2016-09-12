package com.ansteel.common.springsecurity.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：登录后跳转到不同页面
 *
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler  {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/admin/home").forward(request, response);
	}

}
