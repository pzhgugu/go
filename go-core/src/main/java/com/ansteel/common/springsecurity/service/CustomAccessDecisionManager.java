package com.ansteel.common.springsecurity.service;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.ansteel.common.security.domain.Permission;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源。  
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
		Iterator<ConfigAttribute> cons = configAttributes.iterator();

		while (cons.hasNext()) {
			ConfigAttribute ca = cons.next();
			if (this.isGrantedAuthority(authentication, ca)) {
				CustomSecurityConfig securityConfig = (CustomSecurityConfig) ca;
				// 判断当前角色是否通过认证
				if(this.isSecurityConfig(securityConfig)){
					return;
				}
			}
		}
		// 该用户没有权限访问该资源
		throw new AccessDeniedException("Access Denied");
	}
	
	/**
	 * 判断当前角色是否通过认证 通过认证加入到configAttributeList认证许可列表中
	 * 
	 * @param securityConfig
	 * @param configAttributeList
	 */
	private boolean isSecurityConfig(CustomSecurityConfig securityConfig) {
		Integer permission = securityConfig.getPermission();
		Integer urlPermissionBit = securityConfig.getUrlPermissionBit();
		if (permission != null) {
			if (Permission.isPermission(permission, urlPermissionBit)) {
				return true;
			}
		}
		return false;
	}

	
	/**
	 * 当前角色集合中是否有此角色
	 * 
	 * @param authentication
	 * @param ca
	 * @return
	 */
	private boolean isGrantedAuthority(Authentication authentication,
			ConfigAttribute ca) {
		String needRole = ((SecurityConfig) ca).getAttribute();
		for (GrantedAuthority ga : authentication.getAuthorities()) {
			if (needRole.equals(ga.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 此方法返回真
	 */
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	/**
	 * 此方法返回真
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
