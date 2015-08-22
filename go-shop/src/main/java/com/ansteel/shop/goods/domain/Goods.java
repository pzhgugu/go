package com.ansteel.shop.goods.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

/**
 * 创 建 人：gugu 创建日期：2015-06-11 修 改 人： 修改日 期： 描 述：商品信息。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods")
public class Goods extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4633737721060132586L;

	/**
	 * 机器名（英文）
	 */

	@NotEmpty(message = "商品名称不能为空")
	@Pattern(regexp = "^.{3,100}$", message = "商品标题名称长度至少3个字符，最长50个汉字 ")
	private String name;
	/**
	 * 广告词
	 */
	private String adWord;
	/**
	 * 中文名称
	 */
	private String alias;
	/**
	 * 排序
	 */
	private Integer displayOrder;
	/**
	 * 商品分类id
	 */
	@NotEmpty(message = "商品分类不能为空")
	private String gcId;
	/**
	 * 商品分类名称
	 */
	@NotEmpty(message = "商品名称不能为空")
	private String gcName;
	/**
	 * 店铺id
	 */
	//@NotEmpty(message = "店铺id不能为空")
	private String storeId;
	/**
	 * 店铺名称
	 */
	//@NotEmpty(message = "店铺名称不能为空")
	private String storeName;
	/**
	 * 规格名称
	 */
	private String specName;
	/**
	 * 规格值
	 */
	private String specValue;
	/**
	 * 商品品牌id
	 */
	private String brandId;
	/**
	 * 商品品牌名称
	 */
	private String brandName;
	/**
	 * 类型id
	 */
	private String typeId;
	/**
	 * 商品默认封面图片
	 */
	private String goodsImage;
	/**
	 * 商品属性
	 */
	private String goodsAttr;
	/**
	 * 商品详细内容
	 */
	private String goodsBody;
	/**
	 * 商品状态 0下架，1正常，10违规（禁售）
	 */
	private Integer goodsState;
	/**
	 * 商品违规下架原因
	 */
	private String goodsStateremark;
	/**
	 * 商品审核 1通过，0未通过，10审核中
	 */
	private Integer goodsVerify;
	/**
	 * 审核失败原因
	 */
	private String goodsVerifyremark;
	/**
	 * 商品锁定 0未锁，1已锁
	 */
	private Integer goodsLock;
	/**
	 * 上架时间
	 */
	private Date goodsSelltime;
	/**
	 * 规格名称序列化（下标为规格id）
	 */
	private String goodsSpecName;
	/**
	 * 商品店铺价格
	 */
	//@NotEmpty(message = "商品价格不能为空 ")
	private BigDecimal goodsStorePrice;
	/**
	 * 市场价
	 */
	//@NotEmpty(message = "请填写市场价")
	private BigDecimal goodsMarketprice;
	/**
	 * 成本价
	 */
	private BigDecimal goodsCostprice;
	/**
	 * 折扣
	 */
	private Float goodsDiscount;
	/**
	 * 商品货号
	 */
	private String goodsSerial;

	/**
	 * 运费模板ID，不使用运费模板值为0
	 */
	private String transportId;
	/**
	 * 运费模板名称
	 */
	private String transportName;
	/**
	 * 商品推荐
	 */
	private Integer goodsCommend;
	/**
	 * 运费 0为免运费
	 */
	private BigDecimal goodsFreight;
	/**
	 * 是否开具增值税发票 1是，0否
	 */
	private Integer goodsVat;
	/**
	 * 商品所在地(市)
	 */
	private String cityId;
	/**
	 * 商品所在地(省)
	 */
	private String provinceId;
	/**
	 * 店铺分类id 首尾用,隔开
	 */
	private String goodsStcids;
	/**
	 * 顶部关联板式
	 */
	private String plateidTop;
	/**
	 * 底部关联板式
	 */
	private String plateidBottom;
	
	//-----------------------common------------------

	/**
	 * 商品库存
	 */
	@Min(value = 1)
	@Max(value = 999999999)
	private Integer goodsStorage;
	/**
	 * 商品浏览数
	 */
	private Integer goodsClick;
	/**
	 * 售出数量
	 */
	private Integer goodsSaleNum;
	/**
	 * 商品收藏数量
	 */
	private Integer goodsCollect;
	/**
	 * 颜色规格id
	 */
	private String colorId;
	/**
	 * 好评星级
	 */
	private Integer evaluationGoodStar;
	/**
	 * 评论次数
	 */
	private Integer goodsCommentNum;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdWord() {
		return adWord;
	}
	public void setAdWord(String adWord) {
		this.adWord = adWord;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getGcId() {
		return gcId;
	}
	public void setGcId(String gcId) {
		this.gcId = gcId;
	}
	public String getGcName() {
		return gcName;
	}
	public void setGcName(String gcName) {
		this.gcName = gcName;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public String getSpecValue() {
		return specValue;
	}
	public void setSpecValue(String specValue) {
		this.specValue = specValue;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	public String getGoodsAttr() {
		return goodsAttr;
	}
	public void setGoodsAttr(String goodsAttr) {
		this.goodsAttr = goodsAttr;
	}
	public String getGoodsBody() {
		return goodsBody;
	}
	public void setGoodsBody(String goodsBody) {
		this.goodsBody = goodsBody;
	}
	public Integer getGoodsState() {
		return goodsState;
	}
	public void setGoodsState(Integer goodsState) {
		this.goodsState = goodsState;
	}
	public String getGoodsStateremark() {
		return goodsStateremark;
	}
	public void setGoodsStateremark(String goodsStateremark) {
		this.goodsStateremark = goodsStateremark;
	}
	public Integer getGoodsVerify() {
		return goodsVerify;
	}
	public void setGoodsVerify(Integer goodsVerify) {
		this.goodsVerify = goodsVerify;
	}
	public String getGoodsVerifyremark() {
		return goodsVerifyremark;
	}
	public void setGoodsVerifyremark(String goodsVerifyremark) {
		this.goodsVerifyremark = goodsVerifyremark;
	}
	public Integer getGoodsLock() {
		return goodsLock;
	}
	public void setGoodsLock(Integer goodsLock) {
		this.goodsLock = goodsLock;
	}
	public Date getGoodsSelltime() {
		return goodsSelltime;
	}
	public void setGoodsSelltime(Date goodsSelltime) {
		this.goodsSelltime = goodsSelltime;
	}
	public String getGoodsSpecName() {
		return goodsSpecName;
	}
	public void setGoodsSpecName(String goodsSpecName) {
		this.goodsSpecName = goodsSpecName;
	}
	public BigDecimal getGoodsStorePrice() {
		return goodsStorePrice;
	}
	public void setGoodsStorePrice(BigDecimal goodsStorePrice) {
		this.goodsStorePrice = goodsStorePrice;
	}
	public BigDecimal getGoodsMarketprice() {
		return goodsMarketprice;
	}
	public void setGoodsMarketprice(BigDecimal goodsMarketprice) {
		this.goodsMarketprice = goodsMarketprice;
	}
	public BigDecimal getGoodsCostprice() {
		return goodsCostprice;
	}
	public void setGoodsCostprice(BigDecimal goodsCostprice) {
		this.goodsCostprice = goodsCostprice;
	}
	public Float getGoodsDiscount() {
		return goodsDiscount;
	}
	public void setGoodsDiscount(Float goodsDiscount) {
		this.goodsDiscount = goodsDiscount;
	}
	public String getGoodsSerial() {
		return goodsSerial;
	}
	public void setGoodsSerial(String goodsSerial) {
		this.goodsSerial = goodsSerial;
	}
	public String getTransportId() {
		return transportId;
	}
	public void setTransportId(String transportId) {
		this.transportId = transportId;
	}
	public String getTransportName() {
		return transportName;
	}
	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}
	public Integer getGoodsCommend() {
		return goodsCommend;
	}
	public void setGoodsCommend(Integer goodsCommend) {
		this.goodsCommend = goodsCommend;
	}
	public BigDecimal getGoodsFreight() {
		return goodsFreight;
	}
	public void setGoodsFreight(BigDecimal goodsFreight) {
		this.goodsFreight = goodsFreight;
	}
	public Integer getGoodsVat() {
		return goodsVat;
	}
	public void setGoodsVat(Integer goodsVat) {
		this.goodsVat = goodsVat;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getGoodsStcids() {
		return goodsStcids;
	}
	public void setGoodsStcids(String goodsStcids) {
		this.goodsStcids = goodsStcids;
	}
	public String getPlateidTop() {
		return plateidTop;
	}
	public void setPlateidTop(String plateidTop) {
		this.plateidTop = plateidTop;
	}
	public String getPlateidBottom() {
		return plateidBottom;
	}
	public void setPlateidBottom(String plateidBottom) {
		this.plateidBottom = plateidBottom;
	}
	public Integer getGoodsStorage() {
		return goodsStorage;
	}
	public void setGoodsStorage(Integer goodsStorage) {
		this.goodsStorage = goodsStorage;
	}
	public Integer getGoodsClick() {
		return goodsClick;
	}
	public void setGoodsClick(Integer goodsClick) {
		this.goodsClick = goodsClick;
	}
	public Integer getGoodsSaleNum() {
		return goodsSaleNum;
	}
	public void setGoodsSaleNum(Integer goodsSaleNum) {
		this.goodsSaleNum = goodsSaleNum;
	}
	public Integer getGoodsCollect() {
		return goodsCollect;
	}
	public void setGoodsCollect(Integer goodsCollect) {
		this.goodsCollect = goodsCollect;
	}
	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
	public Integer getEvaluationGoodStar() {
		return evaluationGoodStar;
	}
	public void setEvaluationGoodStar(Integer evaluationGoodStar) {
		this.evaluationGoodStar = evaluationGoodStar;
	}
	public Integer getGoodsCommentNum() {
		return goodsCommentNum;
	}
	public void setGoodsCommentNum(Integer goodsCommentNum) {
		this.goodsCommentNum = goodsCommentNum;
	}

	
	
}
