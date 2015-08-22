package com.ansteel.core.argument;


import java.lang.annotation.Annotation;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.util.WebUtils;

import com.ansteel.common.entityinfo.service.EntityInfoService;
import com.ansteel.common.entityinfo.service.EntityInfoServiceBean;
import com.ansteel.core.annotation.PathDatabaseEntity;
import com.ansteel.core.constant.Public;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.service.BaseService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：通过id到数据库查询实体，并将人Request有的值写入实体
 * 如果更新需要版本，请在前台设置version字段
 * 通常用在Update中
 * 如果数据库查询不到则，按照参数装配。 
 */
public class PathDatabaseEntityMethodArgumentResolver implements HandlerMethodArgumentResolver{
	
	@Autowired
	protected BaseService baseService;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (!parameter.hasParameterAnnotation(PathDatabaseEntity.class)) {
			return false;
		}
		if (Map.class.isAssignableFrom(parameter.getParameterType())) {
			String paramName = parameter.getParameterAnnotation(PathDatabaseEntity.class).value();
			return StringUtils.hasText(paramName);
		}
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest request,
			WebDataBinderFactory binderFactory) throws Exception {
		String nameEntity = parameter.getParameterAnnotation(PathDatabaseEntity.class).value();
		String name = ModelFactory.getNameForParameter(parameter);
		Class declaringClass = parameter.getContainingClass();
		Map<String, String> uriTemplateVars =
				(Map<String, String>) request.getAttribute(
						HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		if(uriTemplateVars != null&&uriTemplateVars.containsKey(nameEntity)){
			EntityInfoService entityInfoService = new EntityInfoServiceBean();
			BaseEntity attribute=entityInfoService.getBaseEntity(declaringClass, uriTemplateVars.get(nameEntity));
			String id = getId(request,attribute);
			BaseEntity dataBaseEntity = null;
			if(StringUtils.hasText(id)){
				dataBaseEntity =baseService.findOne(attribute.getClass(),id);
				if(dataBaseEntity!=null){
					attribute=dataBaseEntity;
				}
			}
			WebDataBinder binder = binderFactory.createBinder(request, attribute, name);
			if (binder.getTarget() != null) {
				bindRequestParameters(binder, request);
				validateIfApplicable(binder, parameter);
				/*if (binder.getBindingResult().hasErrors()) {
					if (isBindExceptionRequired(binder, parameter)) {
						throw new BindException(binder.getBindingResult());
					}
				}*/
			}
	
			Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
			mavContainer.removeAttributes(bindingResultModel);
			mavContainer.addAllAttributes(bindingResultModel);

			BaseEntity rEntity = (BaseEntity) binder.getTarget();
			if(dataBaseEntity==null){
				rEntity.setId(null);
				return rEntity;
			}else{
				if(!rEntity.getId().equals(id)){
					rEntity.setId(id);
				}
				return rEntity;
			}
		}
		return null;
	}
	
	private String getId(NativeWebRequest request, Object attribute) {
		String className=attribute.getClass().getSimpleName();
		String idName=className+"."+Public.PK_NAME;
		String[] ids=null;
		if(request.getParameterMap().containsKey(idName)){
			ids = request.getParameterValues(idName);
		}else if(request.getParameterMap().containsKey(Public.PK_NAME)){
			ids = request.getParameterValues(Public.PK_NAME);
		}
		if(ids!=null&&ids.length>0){
			return ids[ids.length-1];
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
