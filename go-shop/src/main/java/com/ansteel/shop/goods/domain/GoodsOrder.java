package com.ansteel.shop.goods.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_order")
public class GoodsOrder extends BaseEntity {

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 支付单号
     */
    private String paySn;

    /**
     * 卖家店铺id
     */
    private String storeId;

    /**
     * 卖家店铺名称
     */
    private String storeName;

    /**
     * 买家id
     */
    private String buyerId;

    /**
     * 买家姓名
     */
    private String buyerName;

    /**
     * 买家电子邮箱
     */
    private String buyerEmail;

    /**
     * 支付方式名称代码
     */
    private String paymentCode;

    /**
     * 支付(付款)时间
     */
    private Date paymentTime;

    /**
     * 订单完成时间
     */
    private Date finnshedTime;

    /**
     * 商品总价格
     */
    private BigDecimal goodsAmount;

    /**
     * 订单总价格
     */
    private BigDecimal orderAmount;

    /**
     * 预存款支付金额
     */
    private BigDecimal pdAmount;

    /**
     * 运费
     */
    private BigDecimal shippingFee;

    /**
     * 评价状态 0未评价，1已评价
     */
    private Integer evaluationState;

    /**
     * 订单状态：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;
     */
    private Integer orderState;
    /**
     * 退款状态:0是无退款,1是部分退款,2是全部退款
     */
    private Integer refundState;
    /**
     * 锁定状态:0是正常,大于0是锁定,默认是0
     */
    private Integer lockState;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 延迟时间,默认为0
     */
    private Date delayTime;
    /**
     * 物流单号
     */
    private String shippingCode;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
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

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getFinnshedTime() {
        return finnshedTime;
    }

    public void setFinnshedTime(Date finnshedTime) {
        this.finnshedTime = finnshedTime;
    }

    public BigDecimal getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(BigDecimal goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getPdAmount() {
        return pdAmount;
    }

    public void setPdAmount(BigDecimal pdAmount) {
        this.pdAmount = pdAmount;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Integer getEvaluationState() {
        return evaluationState;
    }

    public void setEvaluationState(Integer evaluationState) {
        this.evaluationState = evaluationState;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getRefundState() {
        return refundState;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }

    public Integer getLockState() {
        return lockState;
    }

    public void setLockState(Integer lockState) {
        this.lockState = lockState;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Date getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Date delayTime) {
        this.delayTime = delayTime;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }
}
