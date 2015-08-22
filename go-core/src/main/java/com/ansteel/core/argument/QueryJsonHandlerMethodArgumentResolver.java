package com.ansteel.core.argument;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：json查询注解绑定参数，用于json解析然后绑定到实体。 
 */
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ansteel.core.annotation.QueryJson;
import com.ansteel.core.utils.JsonUtils;
import com.ansteel.core.utils.QueryMapping;
public class QueryJsonHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver   {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		 if (parameter.hasParameterAnnotation(QueryJson.class)) {  
	            return true;  
	        }  
		 return false;  
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest request,
			WebDataBinderFactory binderFactory) throws Exception {
		
		if(request.getParameterMap().containsKey("_json")){
			String json = request.getParameter("_json");
			JsonUtils jsonUtils = new JsonUtils();
			List<QueryMapping> QueryMappingList = jsonUtils.readValue(json, QueryMapping.class);
			return QueryMappingList;
		}
		return null;
	}

}