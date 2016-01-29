package com.ansteel.common.springsecurity.service;

import com.ansteel.core.context.ValidationCodeServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：自定义用户名密码过滤器，主要在这个地方可以判断验证码。  
 */
public class CustomUsernamePasswordAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{

    @Value("${go_pro.is_login_captcha}")
    private String is_login_captcha;

    public Authentication attemptAuthentication(HttpServletRequest request,
                                            HttpServletResponse response) throws AuthenticationException {
        if (is_login_captcha.equals("1")) {
            if (request.getParameterMap().containsKey(ValidationCodeServlet.VALIDATION_NAME)) {
                String captcha = request.getParameter(ValidationCodeServlet.VALIDATION_NAME);
                HttpSession session = request.getSession(true);
                String validationCode = (String) session.getAttribute(ValidationCodeServlet.VALIDATION_CODE);
                if (!captcha.equals(validationCode)) {
                    throw new AuthenticationServiceException("验证码错误");
                }
            } else {
                throw new AuthenticationServiceException("验证码参数异常");
            }
        }
        return super.attemptAuthentication(request, response);
    }
}
