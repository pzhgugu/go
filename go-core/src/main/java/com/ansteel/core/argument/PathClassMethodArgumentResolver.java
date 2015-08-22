package com.ansteel.core.argument;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;

import com.ansteel.common.entityinfo.service.EntityInfoService;
import com.ansteel.common.entityinfo.service.EntityInfoServiceBean;
import com.ansteel.core.annotation.PathClass;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：路径映射class参数绑定，用于绑定路径参数。 
 */
public class PathClassMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver  {
	
	

	public PathClassMethodArgumentResolver() {
		super(null);
	}
	/**
	 * 判断是否有PathClass的参数
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (!parameter.hasParameterAnnotation(PathClass.class)) {
			return false;
		}
		if (Map.class.isAssignableFrom(parameter.getParameterType())) {
			String paramName = parameter.getParameterAnnotation(PathClass.class).value();
			return StringUtils.hasText(paramName);
		}
		return true;
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		PathClass annotation = parameter.getParameterAnnotation(PathClass.class);
		return new PathVariableNamedValueInfo(annotation);
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter param)
			throws ServletException {
		String paramType = param.getParameterType().getName();
		throw new ServletRequestBindingException(
				"Missing URI template variable '" + name + "' for method parameter type [" + paramType + "]");
	}
	/**
	 * 从request中将路径名参数取出
	 */
	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		Map<String, String> uriTemplateVars =
				(Map<String, String>) request.getAttribute(
						HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		Class declaringClass = parameter.getContainingClass();
		if(uriTemplateVars != null&&uriTemplateVars.containsKey(name)){
				EntityInfoService entityInfoService = new EntityInfoServiceBean();
				Class clazz=entityInfoService.getBaseEntityClass(declaringClass, uriTemplateVars.get(name));
				return clazz;			
		}		
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void handleResolvedValue(Object arg, String name, MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest request) {

		String key = PathClass.class.getName();
		int scope = RequestAttributes.SCOPE_REQUEST;
		Map<String, Object> pathVars = (Map<String, Object>) request.getAttribute(key, scope);
		if (pathVars == null) {
			pathVars = new HashMap<String, Object>();
			request.setAttribute(key, pathVars, scope);
		}
		pathVars.put(name, arg);
	}
	
	private static class PathVariableNamedValueInfo extends NamedValueInfo {

		private PathVariableNamedValueInfo(PathClass annotation) {
			super(annotation.value(), true, ValueConstants.DEFAULT_NONE);
		}
	}

}
