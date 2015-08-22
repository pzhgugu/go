package com.ansteel.core.context;


import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
/**
 * 创 建 人：gugu
 * 创建日期：2015-03-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：系统返回值拦截器，未使用。 
 */
public class UIReturnValueHandler implements HandlerMethodReturnValueHandler {
	

	/**
	 * 方法用来确定此实现类是否支持对应返回类型。 
	 */
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.getMethodAnnotation(ResponseBody.class) != null;
	}

	@Override
	public void handleReturnValue(Object returnValue,
			MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		returnValue=null;
		mavContainer.setRequestHandled(false);
	}
}
