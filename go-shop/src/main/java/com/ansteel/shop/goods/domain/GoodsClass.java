package com.ansteel.shop.goods.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.TreeEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：商品分类。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_class")
public class GoodsClass extends  TreeEntity<GoodsClass>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -665742313428797578L;
	
	/**
	 * 类型id
	 */
	private String typeId;
	/**
	 * 店铺ID，0为系统后台发布
	 */
	private String storeId;
	/**
	 * 前台显示，0为否，1为是，默认为1
	 */
	private Integer isShow;
	/**
	 * 首页显示	1：默认	显示	0：不显示
	 */
	private Integer indexShow;
	/**
	 * 关键词
	 */
	private String keywords;
	/**
	 * 描述
	 */
	@Column(length=4000)
	private String description;
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Integer getIndexShow() {
		return indexShow;
	}
	public void setIndexShow(Integer indexShow) {
		this.indexShow = indexShow;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
