package com.ansteel.common.security.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：权限认证url实体类。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "permission_url")
public class PermissionURL extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3959758546501267647L;
    /**
     * url路徑
     */
	private String path;
	/**
	 * url類型
	 * 0：增加，1：查询，2修改，3：删除，其他为Permission
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getPermissionBit() {
		return permissionBit;
	}
	public void setPermissionBit(Integer permissionBit) {
		this.permissionBit = permissionBit;
	}
	
	
}
