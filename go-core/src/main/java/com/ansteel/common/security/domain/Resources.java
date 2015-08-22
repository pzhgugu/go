package com.ansteel.common.security.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
 * 描   述：资源实体类。  
 */
@Entity
@Table(name=Constants.G_TABLE_PREFIX + "resources")
public class Resources extends OperEntity{
	/**
	 * 菜单资源，这里表示URL
	 */
	public static final String URL="U";
	/**
	 * 组件资源，这里表示Component
	 */
	public static final String COMPONENT="C";
	
	/**
	 * 模块资源，这里表示Module
	 */
	public static final String MODULE="M";
	
	/**
	 * WEBSERVICE资源，这里表示的是Webservice
	 */
	public static final String WEBSERVICE="W";
	
	/**
	 * 用于业务数据权限控制的物理表
	 */
	public static final String BUSINESS_DATA_CONDITION="B";
	/**
	 * 
	 */
	private static final long serialVersionUID = -5656615410401584142L;
	/**
	 * 备注
	 */
	@Column(length=2000)
	private String scription;
	/**
	 * 类型 (MENU、COMPONENT。。。。)
	 */
	private String type;
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "resources")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("permissionBit")
	@JsonIgnore
	private Collection<PermissionURL> permissionUrls = new ArrayList<PermissionURL>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "resources")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("permissionBit")
	@JsonIgnore
	private Collection<Permission> permissions = new ArrayList<Permission>();
	
	@OneToMany(mappedBy = "resources", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Collection<RolesResources> rolesResources=new ArrayList<RolesResources>();
	
	public Collection<RolesResources> getRolesResources() {
		return rolesResources;
	}
	public void setRolesResources(Collection<RolesResources> rolesResources) {
		this.rolesResources = rolesResources;
	}
	public Collection<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Collection<Permission> permissions) {
		this.permissions = permissions;
	}
	public Collection<PermissionURL> getPermissionUrls() {
		return permissionUrls;
	}
	public void setPermissionUrls(Collection<PermissionURL> permissionUrls) {
		this.permissionUrls = permissionUrls;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScription() {
		return scription;
	}
	public void setScription(String scription) {
		this.scription = scription;
	}
	
	/**
	 * 返回最近的type
	 */
	public Integer getLateType(Integer init,List<Permission> ps){
		
		if(ps==null){
			return init;
		}
		return  dg(ps,init);
		
	}
	
	public Integer dg(Collection<Permission> ps,Integer ir ){
		Integer ib=ir;
		for(Permission o :ps){
			if(o.getPermissionBit()==ir){
				ib++;
			}
		}
		if(ir==ib){
			return ir;
		}else{
			return dg(ps,ib);
		}
	}

	public Map<Integer, String> getPermissionsMap() {
		Map<Integer, String> permissionMap = new HashMap<Integer, String>();
		permissionMap.put(Permission.CREATE, Permission.CREATE_ALIAS);
		permissionMap.put(Permission.DELETE, Permission.DELETE_ALIAS);
		permissionMap.put(Permission.READ, Permission.READ_ALIAS);
		permissionMap.put(Permission.UPDATE, Permission.UPDATE_ALIAS);
		
		Collection<Permission> permissions = this.getPermissions();
		for(Permission p:permissions){
			permissionMap.put(p.getPermissionBit(), p.getAlias());
		}
		return permissionMap;
	}
	
	public Map<String,Integer> getPermissionsMap2() {
		Map<String,Integer> permissionMap = new HashMap<String,Integer>();
		permissionMap.put(Permission.CREATE_ALIAS,Permission.CREATE);
		permissionMap.put(Permission.DELETE_ALIAS,Permission.DELETE);
		permissionMap.put(Permission.READ_ALIAS,Permission.READ);
		permissionMap.put(Permission.UPDATE_ALIAS,Permission.UPDATE);
		
		Collection<Permission> permissions = this.getPermissions();
		for(Permission p:permissions){
			permissionMap.put(p.getAlias(),p.getPermissionBit());
		}
		return permissionMap;
	}
}
