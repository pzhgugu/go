package com.ansteel.common.view.domain;


import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.TreeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-14
 * 修 改 人：
 * 修改日 期：
 * 描   述：视图树实体。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "view_tree")
public class ViewTree extends TreeEntity<ViewTree> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6077207147973444694L;
	
	/**
	 * 描述
	 */
	@Column(length=4000)
	private String scription;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "viewTree",fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<View> viewList=new ArrayList<View>();
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}	

	public String getScription() {
		return scription;
	}

	public void setScription(String scription) {
		this.scription = scription;
	}

	public Collection<View> getViewList() {
		return viewList;
	}

	public void setViewList(Collection<View> viewList) {
		this.viewList = viewList;
	}

	

	
}
