package com.ansteel.common.springsecurity.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ansteel.common.security.domain.Roles;
import com.ansteel.common.security.domain.Users;
import com.ansteel.core.exception.PageException;
import com.ansteel.common.security.service.RolesService;
import com.ansteel.common.security.service.UserService;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：t-eplat单点登录接口实现类。  
 */
@Service
@Transactional(readOnly=false)
public class TePlatSingleSignOn implements SingleSignOn {
	
	private String sessionIdName;
	
	private String sessionIdSql;
	
	private String  userId;
	
	private String  userName;
	
	private String password;
	
	private String defaultRole;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RolesService rolesService;

	public String getSessionIdName() {
		return sessionIdName;
	}

	public void setDefaultRole(String defaultRole) {
		this.defaultRole = defaultRole;
	}



	public void setSessionIdName(String sessionIdName) {
		this.sessionIdName = sessionIdName;
	}



	public String getSessionIdSql() {
		return sessionIdSql;
	}



	public void setSessionIdSql(String sessionIdSql) {
		this.sessionIdSql = sessionIdSql;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public void on(HttpServletRequest request, HttpServletResponse response) {
		Assert.hasText(sessionIdName, "sessionIdName不能为空！");
		Assert.hasText(sessionIdSql, "sessionIdSql不能为空！");
		Assert.hasText(userId, "userId不能为空！");
		Assert.hasText(userName, "userName不能为空！");
		Assert.hasText(password, "password不能为空！");
		
		String sessionId="";
		if(request.getParameterMap().containsKey(sessionIdName)){
			sessionId=request.getParameter(sessionIdName);
		}else{
			return ;
		}
		if(!StringUtils.hasText(sessionId)){
			return ;
		}
		
		Query query = em.createNativeQuery(sessionIdSql).setParameter(sessionIdName, sessionId);
		SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
		List list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		Map<String,String> userMap;
		if(list.size()>0){
			userMap=(Map<String, String>) list.get(0);
		}else{
			return ;
		}
		
		Users user = this.getUser(userMap);
		
		this.setLocalhostUser(user);
		
		Authentication authentication = SecurityContextHolder
				.getContext().getAuthentication();
		if (authentication == null) {
			this.setAuthentication(user);
			
		} else {
			if (authentication.getPrincipal() == null
					|| "anonymousUser".equals(authentication
							.getPrincipal())) {
				this.setAuthentication(user);
			}
		}
	}



	private void setAuthentication(Users user) {
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				user.getName(), user.getPassword());

		SecurityContextHolder.getContext().setAuthentication(
				authRequest);
	}

	private void setLocalhostUser(Users user) {
		Users localhostUser = userService.findOneByName(user.getName());
		if(localhostUser==null){
			user.setRoles(this.getDefaultRole());
			this.save(user);
		}else{
			if(!localhostUser.getPassword().equals(user.getPassword())){
				localhostUser.setPassword(user.getPassword());
				this.save(localhostUser);
			}
		}
	}



	private Collection<Roles> getDefaultRole() {
		Collection<Roles> list = new ArrayList<>();
		Roles role=rolesService.findOneByName(defaultRole);
		if(role==null){
			return null;
		}
		list.add(role);
		return list;
	}



	private void save(Users user) {
		userService.save(user);
	}



	private Users getUser(Map<String, String> userMap) {
		Users user = new Users();
		if(userMap.containsKey(userId)){
			user.setName(userMap.get(userId));
		}else{
			throw new PageException("请设置userId！");
		}
		if(userMap.containsKey(userName)){
			user.setAlias(userMap.get(userName));
		}else{
			throw new PageException("请设置userName！");
		}
		if(userMap.containsKey(password)){
			user.setPassword(userMap.get(password));
		}else{
			throw new PageException("请设置password！");
		}
		return user;
	}

}
