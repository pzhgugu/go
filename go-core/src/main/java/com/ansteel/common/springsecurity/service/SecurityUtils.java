package com.ansteel.common.springsecurity.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：权限工具类。  
 */
public class SecurityUtils {

	public static UserDetails getUserDetail() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		Object principal = auth.getPrincipal();
		if(principal instanceof String){
			return null;
		}
		return (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}
}
