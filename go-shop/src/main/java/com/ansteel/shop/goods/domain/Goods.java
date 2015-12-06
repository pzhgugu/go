package com.ansteel.shop.goods.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

/**
 * 创 建 人：gugu 创建日期：2015-06-11 修 改 人： 修改日 期：
 * 描 述：商品信息。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods")
public class Goods extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4633737721060132586L;

	/**
	 * 商品主表Id
	 * 不做关联关系
	 */
	private String goodsCommonId;

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
	 * 商品分类id
	 */
	@NotEmpty(message = "商品分类不能为空")
	private String gcId;
	/**
	 * 商品品牌id
	 */
	private String brandId;
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
	 * 商品货号
	 */
	private String goodsSerial;
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
	 * 商品规格序列化
	 */
	@Lob
	private String goodsSpec;

	/**
	 * 商品库存
	 */
	@Min(value = 1)
	@Max(value = 999999999)
	private Integer goodsStorage;
	/**
	 * 商品默认封面图片
	 */
	private String goodsImage;

	/**
	 * 商品状态 0下架，1正常，10违规（禁售）
	 */
	private Integer goodsState;
	/**
	 * 商品审核 1通过，0未通过，10审核中
	 */
	private Integer goodsVerify;
	/**
	 * 商品所在地(省)
	 */
	private String provinceId;
	/**
	 * 商品所在地(市)
	 */
	private String cityId;
	/**
	 * 颜色规格id
	 */
	private String colorId;
	/**
	 * 运费模板ID，不使用运费模板值为0
	 */
	private String transportId;
	/**
	 * 运费 0为免运费
	 */
	private BigDecimal goodsFreight;

	/**
	 * 是否开具增值税发票 1是，0否
	 */
	private Integer goodsVat;
	/**
	 * 商品推荐
	 * 商品推荐 1是，0否 默认0
	 */
	private Integer goodsCommend;

	/**
	 * 店铺分类id 首尾用,隔开
	 */
	private String goodsStcids;
	/**
	 * 好评星级
	 */
	private Integer evaluationGoodStar;
	/**
	 * 评论次数
	 */
	private Integer goodsCommentNum;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getGoodsCommonId() {
		return goodsCommonId;
	}

	public void setGoodsCommonId(String goodsCommonId) {
		this.goodsCommonId = goodsCommonId;
	}

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

	public String getGcId() {
		return gcId;
	}

	public void setGcId(String gcId) {
		this.gcId = gcId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
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

	public String getGoodsSerial() {
		return goodsSerial;
	}

	public void setGoodsSerial(String goodsSerial) {
		this.goodsSerial = goodsSerial;
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

	public String getGoodsSpec() {
		return goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	public Integer getGoodsStorage() {
		return goodsStorage;
	}

	public void setGoodsStorage(Integer goodsStorage) {
		this.goodsStorage = goodsStorage;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public Integer getGoodsState() {
		return goodsState;
	}

	public void setGoodsState(Integer goodsState) {
		this.goodsState = goodsState;
	}

	public Integer getGoodsVerify() {
		return goodsVerify;
	}

	public void setGoodsVerify(Integer goodsVerify) {
		this.goodsVerify = goodsVerify;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getColorId() {
		return colorId;
	}

	public void setColorId(String colorId) {
		this.colorId = colorId;
	}

	public String getTransportId() {
		return transportId;
	}

	public void setTransportId(String transportId) {
		this.transportId = transportId;
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

	public Integer getGoodsCommend() {
		return goodsCommend;
	}

	public void setGoodsCommend(Integer goodsCommend) {
		this.goodsCommend = goodsCommend;
	}

	public String getGoodsStcids() {
		return goodsStcids;
	}

	public void setGoodsStcids(String goodsStcids) {
		this.goodsStcids = goodsStcids;
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
