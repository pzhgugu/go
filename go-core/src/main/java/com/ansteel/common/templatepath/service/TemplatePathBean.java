package com.ansteel.common.templatepath.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板路径接口实现。  
 */
@Service
public class TemplatePathBean implements TemplatePath {

	@Override
	public String getTemplatePath(ModelAndView modelAndView, Object handler,
			HttpServletRequest request) {
		String viewName = modelAndView.getViewName();
		if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class handlerClass = handlerMethod.getBeanType();
            // 1、是否开启多模板
    		// 2、从参数中取出模板
    		// 3、从数据库中取出模板
    		// 4、匹配模板路径
            viewName = "default/"+viewName;
		}
		return viewName;
	}

}
