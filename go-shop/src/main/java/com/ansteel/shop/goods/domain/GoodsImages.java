package com.ansteel.shop.goods.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

/**
 * 创 建 人：gugu 
 * 创建日期：2015-08-17 
 * 修 改 人： 修改日 期： 
 * 描 述：商品图片。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_images")
public class GoodsImages  extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6556753349020757274L;
	/**
	 * 商品id
	 */
	private String goodsId;
	/**
	 * 店铺ID
	 */
	private String storeId;
	/**
	 * 颜色规格ID
	 */
	private String colorId;
	/**
	 * 商品图片
	 */
	private String goodsImage;
	/**
	 * 商品图片排序
	 */
	private Integer goodsImageSort;
	/**
	 * 是否默认
	 */
	private Integer isDefault;
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getGoodsImageSort() {
		return goodsImageSort;
	}
	public void setGoodsImageSort(Integer goodsImageSort) {
		this.goodsImageSort = goodsImageSort;
	}	
}
