package com.ansteel.common.springsecurity.service;

import com.ansteel.core.utils.JavaScriptUtils;
import com.ansteel.core.utils.RequestUtils;
import com.ansteel.core.utils.ResponseUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/1/6.
 */
public class AjaxAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String refUrl="";
        if(request.getParameterMap().containsKey("ref_url")){
            refUrl=request.getParameter("ref_url");
            super.setDefaultTargetUrl(refUrl);
        }
        if (RequestUtils.isAjaxRequest(request)){
            if(request.getParameterMap().containsKey("for")){
                if(request.getParameter("for").equals("xml")){
                    ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog("登录成功", refUrl));
                }
            }
        }else{
            //super.onAuthenticationSuccess(request,response,authentication);
            String cp = request.getContextPath();
            response.sendRedirect(cp+"/admin/home");
        }
    }
}
