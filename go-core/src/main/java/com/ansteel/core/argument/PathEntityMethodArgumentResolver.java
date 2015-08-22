package com.ansteel.core.argument;


import java.lang.annotation.Annotation;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import com.ansteel.common.entityinfo.service.EntityInfoService;
import com.ansteel.common.entityinfo.service.EntityInfoServiceBean;
import com.ansteel.core.annotation.PathEntity;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：路径映射到实体类参数绑定，用于绑定请求实体到路径参数。 
 */
public class PathEntityMethodArgumentResolver implements HandlerMethodArgumentResolver{


	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (!parameter.hasParameterAnnotation(PathEntity.class)) {
			return false;
		}
		if (Map.class.isAssignableFrom(parameter.getParameterType())) {
			String paramName = parameter.getParameterAnnotation(PathEntity.class).value();
			return StringUtils.hasText(paramName);
		}
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest request,
			WebDataBinderFactory binderFactory) throws Exception {
		String nameEntity = parameter.getParameterAnnotation(PathEntity.class).value();
		String name = ModelFactory.getNameForParameter(parameter);
		Class declaringClass = parameter.getContainingClass();
		Map<String, String> uriTemplateVars =
				(Map<String, String>) request.getAttribute(
						HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		if(uriTemplateVars != null&&uriTemplateVars.containsKey(nameEntity)){
			EntityInfoService entityInfoService = new EntityInfoServiceBean();
			Object attribute=entityInfoService.getBaseEntity(declaringClass, uriTemplateVars.get(nameEntity));
			//String name = attribute.getClass().getSimpleName();
			WebDataBinder binder = binderFactory.createBinder(request, attribute, name);
			if (binder.getTarget() != null) {
				bindRequestParameters(binder, request);
				validateIfApplicable(binder, parameter);
				if (binder.getBindingResult().hasErrors()) {
					if (isBindExceptionRequired(binder, parameter)) {
						throw new BindException(binder.getBindingResult());
					}
				}
			}
	
			// Add resolved attribute and BindingResult at the end of the model
	
			Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
			mavContainer.removeAttributes(bindingResultModel);
			mavContainer.addAllAttributes(bindingResultModel);
	
			return binder.getTarget();
		}
		return null;
	}
	
	protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
		ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
		ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
		servletBinder.bind(servletRequest);
	}
	
	/**
	 * Validate the model attribute if applicable.
	 * <p>The default implementation checks for {@code @javax.validation.Valid}.
	 * @param binder the DataBinder to be used
	 * @param parameter the method parameter
	 */
	protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
		Annotation[] annotations = parameter.getParameterAnnotations();
		for (Annotation annot : annotations) {
			if (annot.annotationType().getSimpleName().startsWith("Valid")) {
				Object hints = AnnotationUtils.getValue(annot);
				binder.validate(hints instanceof Object[] ? (Object[]) hints : new Object[] {hints});
				break;
			}
		}
	}
	
	protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
		int i = parameter.getParameterIndex();
		Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
		boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));

		return !hasBindingResult;
	}

}
