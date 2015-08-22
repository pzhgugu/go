package com.ansteel.cms.news.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;


import com.ansteel.core.constant.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = Constants.G_TABLE_PREFIX + "NewsItems")
public class NewsItems extends News  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4907196920096793364L;
	

	/**
	 * 咨询内容
	 */
	@Lob
	private String message;


	/**
	 * 最后回复时间
	 */
	private Date lastPost;
	/**
	 * 信息查看数
	 */
	private Integer viewNum;
	/**
	 * 信息回复数
	 */
	private Integer replyNum;
	/**
	 * 正的平方
	 */
	private Integer goodRate;
	/**
	 * 差的评分
	 */
	private Integer badRate;
	/**
	 * 精华等级
	 */
	private Integer digest;
	/**
	 * 置顶等级 
	 */
	private Integer top;
	/**
	 * 是否允许回复 
	 */
	private Integer allowreply;
	/**
	 * 附件组
	 */
	private String groupId;
	/**
	 * 审核等级 
	 */
	private Integer grade;
	/**
	 * 信息属于圈子的id 
	 */
	private String gid;
	/**
	 * 信息在圈子中的精华等级 
	 */
	private Integer gdigest;
	/**
	 * 信息密码 
	 */
	private String password;
	/**
	 * 资讯作者 
	 */
	private String newsAuthor;
	/**
	 * 资讯来源 
	 */
	private String newsFrom;
	/**
	 * 资讯来源网址
	 */
	private String newsFromUrl ;
	/**
	 * 资讯链接 
	 */
	private String newsUrl ;
	/**
	 * 标题样式
	 */

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private String subjectStyle;

	public String getSubjectStyle() {
		return subjectStyle;
	}
	public void setSubjectStyle(String subjectStyle) {
		this.subjectStyle = subjectStyle;
	}
	public Date getLastPost() {
		return lastPost;
	}
	public void setLastPost(Date lastPost) {
		this.lastPost = lastPost;
	}
	public Integer getViewNum() {
		return viewNum;
	}
	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}
	public Integer getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}
	public Integer getGoodRate() {
		return goodRate;
	}
	public void setGoodRate(Integer goodRate) {
		this.goodRate = goodRate;
	}
	public Integer getBadRate() {
		return badRate;
	}
	public void setBadRate(Integer badRate) {
		this.badRate = badRate;
	}
	public Integer getDigest() {
		return digest;
	}
	public void setDigest(Integer digest) {
		this.digest = digest;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getAllowreply() {
		return allowreply;
	}
	public void setAllowreply(Integer allowreply) {
		this.allowreply = allowreply;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public Integer getGdigest() {
		return gdigest;
	}
	public void setGdigest(Integer gdigest) {
		this.gdigest = gdigest;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewsAuthor() {
		return newsAuthor;
	}
	public void setNewsAuthor(String newsAuthor) {
		this.newsAuthor = newsAuthor;
	}
	public String getNewsFrom() {
		return newsFrom;
	}
	public void setNewsFrom(String newsFrom) {
		this.newsFrom = newsFrom;
	}
	public String getNewsFromUrl() {
		return newsFromUrl;
	}
	public void setNewsFromUrl(String newsFromUrl) {
		this.newsFromUrl = newsFromUrl;
	}
	public String getNewsUrl() {
		return newsUrl;
	}
	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}
	
	
}
