package com.ansteel.common.springsecurity.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：用户扩展类。  
 */
public class UserInfo extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8881162435882119882L;
	/**
	 * 
	 * @param username 登陆名
	 * @param password 密码
	 * @param enabled  是否激活
	 * @param accountNonExpired 账户过期
	 * @param credentialsNonExpired 证书过期
	 * @param accountNonLocked 账户锁定
	 * @param authorities 权限集合
	 */
	public UserInfo(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}

	/**
	 * 中文名
	 */
	private String alias;
	/**
	 * 登录时间
	 */
	private Date loginTime;
	/**
	 * 用户ID
	 */
	private String id;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	
}
