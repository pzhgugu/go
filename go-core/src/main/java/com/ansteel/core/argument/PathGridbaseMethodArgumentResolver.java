package com.ansteel.core.argument;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.RequestContext;

import com.ansteel.common.entityinfo.service.EntityInfoService;
import com.ansteel.common.entityinfo.service.EntityInfoServiceBean;
import com.ansteel.core.annotation.PathGridData;
import com.ansteel.core.constant.Public;
import com.ansteel.core.context.DefaultEditors;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.service.BaseService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：通过id到数据库查询实体，并将人Request有的值写入实体
 * 如果更新需要版本，请在前台设置version字段
 * 通常用在Update中
 * 如果数据库查询不到则，按照参数装配。 
 */
public class PathGridbaseMethodArgumentResolver implements HandlerMethodArgumentResolver{
	
	@Autowired
	protected BaseService baseService;
	
	private static final String sataus = "!nativeeditor_status";
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (!parameter.hasParameterAnnotation(PathGridData.class)) {
			return false;
		}
		if (Map.class.isAssignableFrom(parameter.getParameterType())) {
			String paramName = parameter.getParameterAnnotation(PathGridData.class).value();
			return StringUtils.hasText(paramName);
		}
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest request,
			WebDataBinderFactory binderFactory) throws Exception {
		List<BaseEntity> baseEntityLsit = new ArrayList<BaseEntity>();
		String nameEntity = parameter.getParameterAnnotation(PathGridData.class).value();
		String name = ModelFactory.getNameForParameter(parameter);
		Class declaringClass = parameter.getContainingClass();
		Map<String, String> uriTemplateVars =
				(Map<String, String>) request.getAttribute(
						HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		if(uriTemplateVars != null&&uriTemplateVars.containsKey(nameEntity)){
			EntityInfoService entityInfoService = new EntityInfoServiceBean();
			String[] ids= getIds(request);
			if(ids!=null){
				for(String id :ids){
					BaseEntity attribute=entityInfoService.getBaseEntity(declaringClass, uriTemplateVars.get(nameEntity));
					BaseEntity baseEntity=this.getBaseEntity(id,attribute,request);
					WebDataBinder binder = binderFactory.createBinder(request, baseEntity, name);
					if (binder.getTarget() != null) {
						binder.validate(new Object[] {null});
					}
					if (binder.getBindingResult().hasErrors()) {
						FieldError fieldError = binder.getBindingResult().getFieldError();
						//RequestContext requestContext = new RequestContext(request.getNativeRequest(HttpServletRequest.class));
						//System.out.println(requestContext.getMessage(fieldError.getDefaultMessage()));
						//throw new PageException(fieldError.getField()+","+fieldError.getDefaultMessage());
					}
					baseEntityLsit.add(baseEntity);
				}
			}
			
		}
		return baseEntityLsit;
	}
	
	private BaseEntity getBaseEntity(String id, BaseEntity attribute,NativeWebRequest request) {
		String gridSataus = "";
		String idSataus=id+"_"+sataus;
		if(request.getParameterMap().containsKey(idSataus)){
			gridSataus=request.getParameter(idSataus);
		}else{
			return null;
		}
		
		switch (gridSataus) {
			case "updated":
				return this.getUpdatedBaseEntity(id,attribute,request);
			case "inserted":
				return this.getAddBaseEntity(id,attribute,request);
			case "deleted":
				
				break;
			case "invalid":
				
				break;
			case "error":
				
				break;
			default:
				
				return this.getUpdatedBaseEntity(id,attribute,request);
		}
		return null;
	}

	private BaseEntity getAddBaseEntity(String id, BaseEntity attribute,
			NativeWebRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		for(Entry<String, String[]> entry:map.entrySet()){
			String key = entry.getKey();
			if(key.indexOf(id)==0&&!key.equals(id+"_"+sataus)&&!key.equals(id+"_total_count")/*&&!key.equals(id+"_gr_id")*/&&!key.equals(id+"_pos")&&!key.equals(id+"_data")){
				String fieldName=key.replace(id+"_", "");
				Field field = null;
				String valueR = request.getParameter(key); 
				if(fieldName.equals("gr_id")){
					valueR="inserted_"+valueR;
					field=ReflectionUtils.findField(attribute.getClass(), "id");
				}else{
					field=ReflectionUtils.findField(attribute.getClass(), fieldName);
				}
				if(field==null){
					continue;
				}
				Object wValue = new DefaultEditors().getValue(field.getType(), valueR);
				field.setAccessible(true);
				try {
					field.set(attribute, wValue);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return attribute;
	}

	private BaseEntity getUpdatedBaseEntity(String id,BaseEntity attribute, NativeWebRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		attribute = baseService.findOne(attribute.getClass(),id);
		if(attribute!=null){
			for(Entry<String, String[]> entry:map.entrySet()){
				String key = entry.getKey();
				if(key.indexOf(id)==0&&!key.equals(id+"_"+sataus)&&!key.equals(id+"_total_count")&&!key.equals(id+"_gr_id")&&!key.equals(id+"_pos")&&!key.equals(id+"_data")){
					String fieldName=key.replace(id+"_", "");
					Field field = ReflectionUtils.findField(attribute.getClass(), fieldName);
					if(field!=null){
						String valueR = request.getParameter(key); 
						Object wValue = new DefaultEditors().getValue(field.getType(), valueR);
						field.setAccessible(true);
						try {
							field.set(attribute, wValue);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return attribute;
	}

	private String[] getIds(NativeWebRequest request) {
		String[] ids=null;
		if(request.getParameterMap().containsKey(Public.GRID_IDS)){
			String sIds = request.getParameter(Public.GRID_IDS);
			if(StringUtils.hasText(sIds)){
				ids=sIds.split(",");
			}
		}
		return ids;
	}

}
