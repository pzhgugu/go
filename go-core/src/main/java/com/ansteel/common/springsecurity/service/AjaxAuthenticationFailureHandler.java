package com.ansteel.common.springsecurity.service;

import com.ansteel.core.utils.JavaScriptUtils;
import com.ansteel.core.utils.RequestUtils;
import com.ansteel.core.utils.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/1/6.
 */
public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String refUrl="";
        if(request.getParameterMap().containsKey("ref_url")){
            refUrl=request.getParameter("ref_url");
            super.setDefaultFailureUrl(refUrl);
        }
        if (RequestUtils.isAjaxRequest(request)){
            if(request.getParameterMap().containsKey("for")){
                if(request.getParameter("for").equals("xml")){
                    ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnErrorShowDialog(exception.getMessage()));
                }
            }
        }else{
            super.onAuthenticationFailure(request,response,exception);
        }
    }
}
