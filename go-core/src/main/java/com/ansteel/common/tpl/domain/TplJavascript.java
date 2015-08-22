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
 * 描   述：模板脚本实体。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX   + "tpl_javascript")
public class TplJavascript extends OperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2499712201097077357L;

	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name = "tid", nullable = true)
	@JsonIgnore
	private Tpl tpl;
	
	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name = "vid", nullable = true)
	@JsonIgnore
	private View view;
	
	public View getView() {
		return view;
	}
	public void setView(View view) {
		this.view = view;
	}
	/**
	 * url路径
	 */
	private String urlPath;
	/**
	 * 是否停用
	 */
	private Integer isStop;
	/**
	 * 描述
	 */
	@Column(length=4000)
	private String scription;
	/**
	 * 页面位置(1=页面头部;2=页面底部;)
	 */
	private Integer pagePlace;
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}	
	
	public Integer getPagePlace() {
		return pagePlace;
	}
	public void setPagePlace(Integer pagePlace) {
		this.pagePlace = pagePlace;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public Integer getIsStop() {
		return isStop;
	}
	public void setIsStop(Integer isStop) {
		this.isStop = isStop;
	}
	public String getScription() {
		return scription;
	}
	public void setScription(String scription) {
		this.scription = scription;
	}
	public Tpl getTpl() {
		return tpl;
	}
	public void setTpl(Tpl tpl) {
		this.tpl = tpl;
	}
	
}
