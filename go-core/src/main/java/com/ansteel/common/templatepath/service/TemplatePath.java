package com.ansteel.common.templatepath.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板路径接口。  
 */
public interface TemplatePath {
	/**
	 * 获取模板路径
	 * @param modelAndView
	 * @param handler
	 * @param request
	 * @return
	 */
	String getTemplatePath(ModelAndView modelAndView, Object handler,
			HttpServletRequest request);

}
