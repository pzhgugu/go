package com.ansteel.common.security.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：角色实体类。  
 */
@Entity
@Table(name=Constants.G_TABLE_PREFIX + "roles")
public class Roles extends OperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2706792932444786096L;
	/**
	 * 是否停用
	 */
	private Integer enable;

	/**
	 * 角色类型
	 * 0为管理组，1为用户组
	 */
	private Integer type;

	@Transient
	private Integer accredit;

	/**
	 * 认证集合字符串（虚拟字段，前台使用）
	 */
	@Transient
	private String permissionStr;
	
	public String getPermissionStr() {
		return permissionStr;
	}
	public void setPermissionStr(String permissionStr) {
		this.permissionStr = permissionStr;
	}
	public Integer getAccredit() {
		return accredit;
	}
	public void setAccredit(Integer accredit) {
		this.accredit = accredit;
	}
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "roles",  
			fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<Users> users=new ArrayList<Users>();
	
	@OneToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Collection<RolesResources> rolesResources=new ArrayList<RolesResources>();
	
	public Collection<RolesResources> getRolesResources() {
		return rolesResources;
	}
	public void setRolesResources(Collection<RolesResources> rolesResources) {
		this.rolesResources = rolesResources;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public Collection<Users> getUsers() {
		return users;
	}
	public void setUsers(Collection<Users> users) {
		this.users = users;
	}
	
	
}
