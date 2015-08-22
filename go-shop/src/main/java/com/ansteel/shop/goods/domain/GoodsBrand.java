package com.ansteel.shop.goods.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.OperEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：商品品牌。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_brand")
public class GoodsBrand extends OperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4640970260992318311L;
	/**
	 * 类别名称
	 */
	private String className;
	/**
	 * logo
	 */
	private String logoImage;
	/**
	 * 推荐，0为否，1为是，默认为0
	 */
	private Integer recommend;
	/**
	 * 店铺ID
	 */
	private String storeId;
	/**
	 * 品牌申请，0为申请中，1为通过，默认为1，申请功能是会员使用，系统后台默认为1
	 */
	private Integer brandApply;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getLogoImage() {
		return logoImage;
	}
	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}
	public Integer getRecommend() {
		return recommend;
	}
	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public Integer getBrandApply() {
		return brandApply;
	}
	public void setBrandApply(Integer brandApply) {
		this.brandApply = brandApply;
	}
	
	
}
