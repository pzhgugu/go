package com.ansteel.common.springsecurity.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import com.ansteel.core.utils.RequestUtils;
import com.ansteel.core.utils.ResponseUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：默认的AccessDeniedHandler中只有对页面请求的处理，而没有对Ajax的处理
 */
public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {

	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		if (RequestUtils.isAjaxRequest(request)){
        	ResponseUtils.returnAjaxMessage(response, "你没有此操作权限！路径："+request.getServletPath(),-3); 
        } else
        	super.handle(request, response, accessDeniedException);
	}

}
