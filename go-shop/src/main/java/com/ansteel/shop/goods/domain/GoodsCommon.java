package com.ansteel.shop.goods.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

/**
 *商品表
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "GOODS_COMMON")
public class GoodsCommon extends BaseEntity {

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
     * 商品推荐 1是，0否 默认0
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
     * 商品所在地(省)
     */
    private String provinceId;
    /**
     * 商品所在地(市)
     */
    private String cityId;
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
}
