package com.ansteel.common.security.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;


import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.OperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：用户实体类。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "users")
public class Users extends OperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5244180653571068196L;
	/**
	 * 是否停用
	 */
	private Integer enable;
	/**
	 * 密码
	 */
	@NotEmpty
	private String password;
	/**
	 * 用户类型，1为外部用户
	 */
	private Integer type;

	@Transient
	private Integer accredit;
	
	public Integer getAccredit() {
		return accredit;
	}
	public void setAccredit(Integer accredit) {
		this.accredit = accredit;
	}
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
	fetch=FetchType.LAZY)
    @JoinTable(name = Constants.G_TABLE_PREFIX + "user_role", 
    	    joinColumns = {@JoinColumn(name="user_id")},
    	    inverseJoinColumns = { @JoinColumn(name = "role_id") })	
	@JsonIgnore
	private Collection<Roles> roles=new ArrayList<Roles>();
	
	public Collection<Roles> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Roles> roles) {
		this.roles = roles;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
