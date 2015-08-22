package com.ansteel.common.springsecurity.service;

import org.springframework.security.access.SecurityConfig;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：自定义权限配置扩展类。  
 */
public class CustomSecurityConfig extends SecurityConfig {

	public CustomSecurityConfig(String config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8561613118067002987L;
	/**
	 * 许可码
	 */
	private Integer permission;
	/**
	 * 资源名称
	 */
	private String resName;
	/**
	 * url许可位
	 */
	private Integer urlPermissionBit;
	

	public Integer getUrlPermissionBit() {
		return urlPermissionBit;
	}

	public void setUrlPermissionBit(Integer urlPermissionBit) {
		this.urlPermissionBit = urlPermissionBit;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}
	
	

}
