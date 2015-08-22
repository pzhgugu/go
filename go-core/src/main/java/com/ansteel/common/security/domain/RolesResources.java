package com.ansteel.common.security.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：角色资源实体类。  
 */
@Entity
@Table(name=Constants.G_TABLE_PREFIX + "roles_resources")
public class RolesResources extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4242958484976449938L;
	
	@ManyToOne
	@JoinColumn(name = "roles_id")
	private Roles roles=new Roles();
	@ManyToOne
	@JoinColumn(name = "resources_id")
	private Resources resources=new Resources();
	
	private Integer permission;

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}
	
	
	
}
