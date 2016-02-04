package com.ansteel.shop.goods.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息扩展表
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_order_common")
public class GoodsOrderCommon  extends BaseEntity {

    /**
     * 订单索引id
     */
    private String orderId;
    /**
     * 店铺ID
     */
    private String storeId;
    /**
     * 配送时间
     */
    private Date shippingTime;
    /**
     * 配送公司ID
     */
    private String shippingExpressId;
    /**
     * 评价时间
     */
    private Date evaluationTime;
    /**
     * 卖家是否已评价买家
     */
    private Integer evalsellerState;
    /**
     * 卖家评价买家的时间
     */
    private Date evalsellerTime;
    /**
     * 订单留言
     */
    @Column(length = 4000)
    private String orderMessage;
    /**
     * 订单赠送积分
     */
    private Integer orderPointscount;
    /**
     * 代金券面额
     */
    private BigDecimal voucherPrice;
    /**
     * 代金券编码
     */
    private String voucherCode;
    /**
     * 发货备注
     */
    @Column(length = 4000)
    private String deliverExplain;
    /**
     * 发货地址ID
     */
    private String daddressId;
    /**
     * 收货人姓名
     */
    private String reciverName;
    /**
     * 收货人其它信息
     */
    private String reciverInfo;
    /**
     * 收货人省级ID
     */
    private String reciverProvinceId;
    /**
     * 发票信息
     */
    private String invoiceInfo;
    /**
     * 促销信息备注
     */
    private String promotionInfo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getShippingExpressId() {
        return shippingExpressId;
    }

    public void setShippingExpressId(String shippingExpressId) {
        this.shippingExpressId = shippingExpressId;
    }

    public Date getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(Date evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public Integer getEvalsellerState() {
        return evalsellerState;
    }

    public void setEvalsellerState(Integer evalsellerState) {
        this.evalsellerState = evalsellerState;
    }

    public Date getEvalsellerTime() {
        return evalsellerTime;
    }

    public void setEvalsellerTime(Date evalsellerTime) {
        this.evalsellerTime = evalsellerTime;
    }

    public String getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }

    public Integer getOrderPointscount() {
        return orderPointscount;
    }

    public void setOrderPointscount(Integer orderPointscount) {
        this.orderPointscount = orderPointscount;
    }

    public BigDecimal getVoucherPrice() {
        return voucherPrice;
    }

    public void setVoucherPrice(BigDecimal voucherPrice) {
        this.voucherPrice = voucherPrice;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getDeliverExplain() {
        return deliverExplain;
    }

    public void setDeliverExplain(String deliverExplain) {
        this.deliverExplain = deliverExplain;
    }

    public String getDaddressId() {
        return daddressId;
    }

    public void setDaddressId(String daddressId) {
        this.daddressId = daddressId;
    }

    public String getReciverName() {
        return reciverName;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }

    public String getReciverInfo() {
        return reciverInfo;
    }

    public void setReciverInfo(String reciverInfo) {
        this.reciverInfo = reciverInfo;
    }

    public String getReciverProvinceId() {
        return reciverProvinceId;
    }

    public void setReciverProvinceId(String reciverProvinceId) {
        this.reciverProvinceId = reciverProvinceId;
    }

    public String getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(String invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public String getPromotionInfo() {
        return promotionInfo;
    }

    public void setPromotionInfo(String promotionInfo) {
        this.promotionInfo = promotionInfo;
    }
}
