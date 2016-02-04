package com.ansteel.shop.goods.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 订单支付表
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_order_pay")
public class GoodsOrderPay extends BaseEntity {
    /**
     * 支付单号
     */
    private String paySn;
    /**
     * 买家ID
     */
    private String buyerId;
    /**
     * 0默认未支付1已支付(只有第三方支付接口通知到时才会更改此状态)
     */
    private Integer apiPayState;

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getApiPayState() {
        return apiPayState;
    }

    public void setApiPayState(Integer apiPayState) {
        this.apiPayState = apiPayState;
    }
}
