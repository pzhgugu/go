package com.ansteel.cms.news.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = Constants.G_TABLE_PREFIX + "News")
@Inheritance(strategy = InheritanceType.JOINED)
public class News  extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8523042725761359228L;
	/**
	 * 信息标题 
	 */
	@Length(min=2,max=100, message="标题长度在2~100个字符之间。")
	private String subject  ;	
	/**
	 * 发布 ip 
	 */
	private String postip;
	/**
	 * 分类id，不使用一对多映射
	 */
	private String categoryId;
	/**
	 * 作者用户名，允许冗余
	 */
	private String userName;
	/**
	 * 资讯类型（1、发布箱2、待审箱3、垃圾箱）
	 */
	private Integer newType;
	/**
	 * 来源（1、后台发布 2、用户发布)
	 */
	private Integer origin;
	/**
	 * 发布日期
	 */
	private Date publishDate;
	
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Integer getOrigin() {
		return origin;
	}
	public void setOrigin(Integer origin) {
		this.origin = origin;
	}
	public Integer getNewType() {
		return newType;
	}
	public void setNewType(Integer newType) {
		this.newType = newType;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPostip() {
		return postip;
	}
	public void setPostip(String postip) {
		this.postip = postip;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
