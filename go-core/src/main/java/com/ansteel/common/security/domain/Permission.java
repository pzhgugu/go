package com.ansteel.common.security.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

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
 * 描   述：权限认证实体类。  
 */
@Entity
@Table(name=Constants.G_TABLE_PREFIX + "permission")
public class Permission extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8959467411526518216L;
	
	public static final int CREATE = 0;
	public static final int READ = 1;
	public static final int UPDATE = 2;
	public static final int DELETE = 3;
	
	/**
	 * 加国际化？？
	 */
	public static final String CREATE_ALIAS = "新增";
	public static final String READ_ALIAS = "查看";
	public static final String UPDATE_ALIAS = "修改";
	public static final String DELETE_ALIAS = "删除";
	
	/**
	 * 中文名称
	 */

	@NotEmpty
	private String alias;
	
	/**
	 * 除crud以外的权限
	 */
	private Integer permissionBit;
	
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}
	
	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name = "res_id", nullable = true)
	@JsonIgnore
	private Resources resources=new Resources();
	
	public Resources getResources() {
		return resources;
	}
	public void setResources(Resources resources) {
		this.resources = resources;
	}

	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Integer getPermissionBit() {
		return permissionBit;
	}
	public void setPermissionBit(Integer permissionBit) {
		this.permissionBit = permissionBit;
	}
	
	/** 
	 * @param p 授权集合，移位后的编码，比如(1新增，2查看，4修改，8删除)
	 * @param b 授权码 ，Permission.CREATE(0新增，1查看，2修改，3删除)
	 * @return
	 */
	public static boolean isPermission(Integer permission, Integer urlPermissionBit) {
		int temp = 1;
		temp <<= urlPermissionBit;
		temp &= permission;
		if (temp > 0) {
			return true;
		}
		return false;
	}
	
}
