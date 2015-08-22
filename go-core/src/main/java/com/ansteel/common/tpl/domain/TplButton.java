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
 * 创建日期：2015-05-17
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板按钮实体。
 */
//@Entity
//@Table(name = Constants.G_TABLE_PREFIX + "tpl_button")
public class TplButton extends OperEntity {

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
	/**
	 * 是否停用
	 */
	private Integer isStop;
	/**
	 * 代码
	 */
	@Column(length=4000)
	private String code;
	/**
	 * 描述
	 */
	@Column(length=4000)
	private String scription;
	
	/**
	 * 元素图标
	 * @return
	 */
	private String icon;
	
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
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Tpl getTpl() {
		return tpl;
	}
	public void setTpl(Tpl tpl) {
		this.tpl = tpl;
	}
	public Integer getIsStop() {
		return isStop;
	}
	public void setIsStop(Integer isStop) {
		this.isStop = isStop;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getScription() {
		return scription;
	}
	public void setScription(String scription) {
		this.scription = scription;
	}
	
	
}
