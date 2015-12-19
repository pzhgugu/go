package com.ansteel.core.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ansteel.common.pageglobal.service.PageGlobalAttribute;
import com.ansteel.common.templatepath.service.TemplatePath;
/**
 * 创 建 人：gugu
 * 创建日期：2015-03-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：springmvc拦截器，用于处理进入页面的全局变量设置，返回页面的路径设置。 
 */
public class SiteInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	TemplatePath templatePath;
	@Autowired
	PageGlobalAttribute pageGlobalAttribute;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 2、设置全局页面参数
		response.addHeader("P3P", "CP=CAO PSA OUR");
		pageGlobalAttribute.set(request);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		/*if (modelAndView != null) {
			// 1、设置模板路径
			String viewName = modelAndView.getViewName();
			if(viewName.indexOf("fis:")>-1){
				viewName=viewName.substring(4,viewName.length());
				modelAndView.setViewName(viewName);
			}else if(!(viewName.indexOf("redirect")>-1||viewName.indexOf("forward")>-1)){
				viewName = templatePath.getTemplatePath(modelAndView,
					handler, request);
				modelAndView.setViewName("template/"+viewName);
			}
			
		}*/
		response.addHeader("P3P", "CP=CAO PSA OUR");
	}
}
