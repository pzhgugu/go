package com.ansteel.cms.news.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.TreeEntity;

@Entity
@Table(name = Constants.G_TABLE_PREFIX + "NewsCategory")
public class NewsCategory extends TreeEntity<NewsCategory>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6896495639496850914L;
	
	/**
	 * 分类跳转 URL 
	 */
	private String url;
	
	/**
	 * 是否有封面图片
	 */
	private Integer haveAttach;
	
	/**
	 * 分类的封面图片 
	 */
	private String image;
	
	/**
	 * 分类的封面图片缩略图 
	 */
	private String thumb;
	
	/**
	 * 列表模板
	 */
	private String listTpl;
	
	/**
	 * 查看分类模板文件 
	 */
	private String viewTpl;
	
	/**
	 * 模型描述
	 */
	@Column(length=4000) 
	private String scription;
	
	/**
	 * 分类是否为专题 
	 */
	private Integer isChannel;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getHaveAttach() {
		return haveAttach;
	}

	public void setHaveAttach(Integer haveAttach) {
		this.haveAttach = haveAttach;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getListTpl() {
		return listTpl;
	}

	public void setListTpl(String listTpl) {
		this.listTpl = listTpl;
	}

	public String getViewTpl() {
		return viewTpl;
	}

	public void setViewTpl(String viewTpl) {
		this.viewTpl = viewTpl;
	}

	public String getScription() {
		return scription;
	}

	public void setScription(String scription) {
		this.scription = scription;
	}

	public Integer getIsChannel() {
		return isChannel;
	}

	public void setIsChannel(Integer isChannel) {
		this.isChannel = isChannel;
	}

}
