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
 * 描   述：模板路径实体。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "tpl_url")
public class TplUrl extends OperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8003247708256760963L;
	

	/**
	 * 	链接方式(0:域内,1:跨域)
	 */
	private int type;
	
	private String url;	

	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name = "tid", nullable = true)
	@JsonIgnore
	private Tpl tpl;
	
	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name = "vid", nullable = true)
	@JsonIgnore
	private View view;
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}		

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Tpl getTpl() {
		return tpl;
	}

	public void setTpl(Tpl tpl) {
		this.tpl = tpl;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}
	
	

}
