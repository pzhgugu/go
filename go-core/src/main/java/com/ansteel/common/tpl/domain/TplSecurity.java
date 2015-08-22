package com.ansteel.common.tpl.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ansteel.common.view.domain.View;
import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-16
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板安全实体。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "tpl_security")
public class TplSecurity extends OperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 981784498043428618L;
	
	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name = "tid", nullable = true)
	@JsonIgnore
	private Tpl tpl;
	
	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name = "vid", nullable = true)
	@JsonIgnore
	private View view;
	
	@Column(length=4000)
	private String scription;
	
	private String varValue;
	
	private String permissionValue;
	
	private Integer isDefault;
	
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public String getVarValue() {
		return varValue;
	}
	public void setVarValue(String varValue) {
		this.varValue = varValue;
	}
	public String getPermissionValue() {
		return permissionValue;
	}
	public void setPermissionValue(String permissionValue) {
		this.permissionValue = permissionValue;
	}
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}	
		
	public View getView() {
		return view;
	}
	public void setView(View view) {
		this.view = view;
	}
	
	public Tpl getTpl() {
		return tpl;
	}
	public void setTpl(Tpl tpl) {
		this.tpl = tpl;
	}

	public String getScription() {
		return scription;
	}
	public void setScription(String scription) {
		this.scription = scription;
	}
	
	
}
