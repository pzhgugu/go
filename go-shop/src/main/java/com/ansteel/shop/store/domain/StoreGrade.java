package com.ansteel.shop.store.domain;

import javax.persistence.Column;
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
 * 描   述：店铺等级。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "store_grade")
public class StoreGrade extends OperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8495539082267954766L;
	/**
	 * 备注:’允许发布的商品数量',
	 */
	private Integer goodsLimit;	
	/**
	 * 备注:’上传空间大小，单位MB',
	 */
	private Integer spaceLimit;
	/**
	 * 选择店铺模板套数
	 */
	private Integer templateNumber;
	/**
	 * 模板内容
	 */
	private String template;
	/**
	 * 费用
	 */
	private String price;
	/**
	 * ’审核，0为否，1为是，默认为1
	 */
	private Integer confirm;
	/**
	 * 申请说明
	 */
	@Column(length=4000)
	private String description;
	/**
	 * 附加功能
	 */
	private String function;
	/**
	 * 级别，数目越大级别越高
	 */
	private Integer sort;
	public Integer getGoodsLimit() {
		return goodsLimit;
	}
	public void setGoodsLimit(Integer goodsLimit) {
		this.goodsLimit = goodsLimit;
	}
	public Integer getSpaceLimit() {
		return spaceLimit;
	}
	public void setSpaceLimit(Integer spaceLimit) {
		this.spaceLimit = spaceLimit;
	}
	public Integer getTemplateNumber() {
		return templateNumber;
	}
	public void setTemplateNumber(Integer templateNumber) {
		this.templateNumber = templateNumber;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getConfirm() {
		return confirm;
	}
	public void setConfirm(Integer confirm) {
		this.confirm = confirm;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
