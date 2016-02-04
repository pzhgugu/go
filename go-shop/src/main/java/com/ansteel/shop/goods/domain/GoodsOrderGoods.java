package com.ansteel.shop.goods.domain;


import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 订单商品表
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_order_goods")
public class GoodsOrderGoods extends BaseEntity {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;
    /**
     * 商品数量
     */
    private Integer goodsNum;
    /**
     * 商品图片
     */
    private String goodsImage;
    /**
     * 商品实际成交价
     */
    private BigDecimal goodsPayPrice;
    /**
     * 店铺ID
     */
    private String storeId;
    /**
     * 买家ID
     */
    private String buyerId;
    /**
     * 1默认2团购商品3限时折扣商品4组合套装5赠品
     */
    private Integer goodsType;
    /**
     * 促销活动ID（团购ID/限时折扣ID/优惠套装ID）与goods_type搭配使用
     */
    private String promotionsId;
    /**
     * 佣金比例
     */
    private Integer commisRate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public BigDecimal getGoodsPayPrice() {
        return goodsPayPrice;
    }

    public void setGoodsPayPrice(BigDecimal goodsPayPrice) {
        this.goodsPayPrice = goodsPayPrice;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getPromotionsId() {
        return promotionsId;
    }

    public void setPromotionsId(String promotionsId) {
        this.promotionsId = promotionsId;
    }

    public Integer getCommisRate() {
        return commisRate;
    }

    public void setCommisRate(Integer commisRate) {
        this.commisRate = commisRate;
    }
}
