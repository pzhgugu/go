package com.ansteel.common.springsecurity.service;

import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.ansteel.core.context.ContextHolder;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色访问。  
 */
public class CustomFilterInvocationSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	Logger logger = Logger
			.getLogger(CustomFilterInvocationSecurityMetadataSource.class);

	public static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	@Autowired
	ISecurityMetadata securityMetadata;

	/**
	 * 
	 * 自定义方法，这个类放入到Spring容器后， 指定init为初始化方法，从数据库中读取资源
	 */
	@PostConstruct 
	public void init() {
		resourceMap=securityMetadata.loadResourceDefine();
	}
	
	/**
	 * 刷新权限
	 */
	public  void refresh(){
		if(securityMetadata==null){
			securityMetadata=ContextHolder.getSpringBean("securityMetadata");
		}
		resourceMap=securityMetadata.loadResourceDefine();
	}

	
	/**
	 * 根据用户访问的url，加载该url所需要角色列表　
	 * Object object:uri地址
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		return securityMetadata.getAttributes(object);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
