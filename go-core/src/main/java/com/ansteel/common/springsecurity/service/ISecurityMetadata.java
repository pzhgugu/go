package com.ansteel.common.springsecurity.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：认证判断接口。  
 */
public interface ISecurityMetadata {
	/**
	 *根据用户访问的url，加载该url所需要角色列表　
	 * @param object:uri地址
	 * @return
	 */
	Collection<ConfigAttribute> getAttributes(Object object);
	/**
	 *根据用户访问的url，加载该url所需要角色列表　
	 * @param object:uri地址
	 * @return
	 */
	Collection<ConfigAttribute> getAttributes(String url);
	/**
	 * 加载所有url对应的角色
	 * @return
	 */
	Map<String, Collection<ConfigAttribute>> loadResourceDefine();
}
