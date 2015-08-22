package com.ansteel.shop.goods.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：店铺常用分类表。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_class_staple")
public class GoodsClassStaple extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -388234123914247969L;

	/**
	 * 常用分类名称
	 */
	private String stapleName;
	/**
	 * 分类id1
	 */
	private String gcId1;
	
	/**
	 * 分类id2
	 */
	private String gcId2;
	
	/**
	 * 分类id3
	 */
	private String gcId3;
	/**
	 * 类型id
	 */
	private String typeId;
	/**
	 * 所属店铺id
	 */
	private String storeId;
	public String getStapleName() {
		return stapleName;
	}
	public void setStapleName(String stapleName) {
		this.stapleName = stapleName;
	}
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
	public String getGcId1() {
		return gcId1;
	}
	public void setGcId1(String gcId1) {
		this.gcId1 = gcId1;
	}
	public String getGcId2() {
		return gcId2;
	}
	public void setGcId2(String gcId2) {
		this.gcId2 = gcId2;
	}
	public String getGcId3() {
		return gcId3;
	}
	public void setGcId3(String gcId3) {
		this.gcId3 = gcId3;
	}

	
}
