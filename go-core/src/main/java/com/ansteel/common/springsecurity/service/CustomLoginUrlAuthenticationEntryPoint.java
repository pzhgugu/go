package com.ansteel.common.springsecurity.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.ansteel.core.exception.ErrorMessage;
import com.ansteel.core.utils.RequestUtils;
import com.ansteel.core.utils.ResponseUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：自定义登录超时过滤器，主要负责超时ajax返回错误信息。  
 */
public class CustomLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint{
	
	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        if (RequestUtils.isAjaxRequest(request)){
        	//ResponseUtils.returnAjaxMessage(response, "与服务器的会话已经超时",-2);
            ResponseUtils.returnAjaxMessage(response,authException.getMessage(),-2);
        } else {
            super.commence(request, response, authException);
        }
    }
}
