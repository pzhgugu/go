package com.ansteel.common.attachment.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：附件管理树。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "attachment_tree")
public class AttachmentTree extends TreeEntity<AttachmentTree>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5623821561396700893L;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentTree")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<Attachment> attachments = new ArrayList<Attachment>();
	/**
	 * 指定目录
	 */
	private String catalogue;
	/**
	 * 目录是否添加日期
	 */
	private Integer isDate;
	
	public String getCatalogue() {
		return catalogue;
	}

	public void setCatalogue(String catalogue) {
		this.catalogue = catalogue;
	}

	public Integer getIsDate() {
		return isDate;
	}

	public void setIsDate(Integer isDate) {
		this.isDate = isDate;
	}

	/**
	 * 描述
	 */
	@Column(length=4000)
	private String scription;

	public Collection<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Collection<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getScription() {
		return scription;
	}

	public void setScription(String scription) {
		this.scription = scription;
	}
	
	

}
