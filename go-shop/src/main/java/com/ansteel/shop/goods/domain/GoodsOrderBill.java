package com.ansteel.shop.goods.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算表
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_bill")
public class GoodsOrderBill extends BaseEntity {
    /**
     * 结算单编号(年月店铺ID)
     */
    private String obNo;
    /**
     * 开始日期
     */
    private Date obStartDate;
    /**
     * 结束日期
     */
    private Date obEndDate;
    /**
     * 订单金额
     */
    private BigDecimal obOrderTotals;
    /**
     * 运费
     */
    private BigDecimal obShippingTotals;
    /**
     * 退单金额
     */
    private BigDecimal obOrderReturnTotals;
    /**
     * 佣金金额
     */
    private BigDecimal obCommisTotals;
    /**
     * 退还佣金
     */
    private BigDecimal obCommisReturnTotals;
    /**
     * 店铺促销活动费用
     */
    private BigDecimal obStoreCostTotals;
    /**
     * 应结金额
     */
    private BigDecimal obResultTotals;
    /**
     * 生成结算单日期
     */
    private Date obCreateDate;
    /**
     * 结算单年月份
     */
    private String osMonth;
    /**
     * 1默认2店家已确认3平台已审核4结算完成
     */
    private Integer obState;
    /**
     * 付款日期
     */
    private Date obPayDate;
    /**
     * 支付备注
     */
    @Column(length = 4000)
    private String obPayContent;
    /**
     * 店铺ID
     */
    private String obStoreId;
    /**
     * 店铺名称
     */
    private String obStoreName;

    public String getObNo() {
        return obNo;
    }

    public void setObNo(String obNo) {
        this.obNo = obNo;
    }

    public Date getObStartDate() {
        return obStartDate;
    }

    public void setObStartDate(Date obStartDate) {
        this.obStartDate = obStartDate;
    }

    public Date getObEndDate() {
        return obEndDate;
    }

    public void setObEndDate(Date obEndDate) {
        this.obEndDate = obEndDate;
    }

    public BigDecimal getObOrderTotals() {
        return obOrderTotals;
    }

    public void setObOrderTotals(BigDecimal obOrderTotals) {
        this.obOrderTotals = obOrderTotals;
    }

    public BigDecimal getObShippingTotals() {
        return obShippingTotals;
    }

    public void setObShippingTotals(BigDecimal obShippingTotals) {
        this.obShippingTotals = obShippingTotals;
    }

    public BigDecimal getObOrderReturnTotals() {
        return obOrderReturnTotals;
    }

    public void setObOrderReturnTotals(BigDecimal obOrderReturnTotals) {
        this.obOrderReturnTotals = obOrderReturnTotals;
    }

    public BigDecimal getObCommisTotals() {
        return obCommisTotals;
    }

    public void setObCommisTotals(BigDecimal obCommisTotals) {
        this.obCommisTotals = obCommisTotals;
    }

    public BigDecimal getObCommisReturnTotals() {
        return obCommisReturnTotals;
    }

    public void setObCommisReturnTotals(BigDecimal obCommisReturnTotals) {
        this.obCommisReturnTotals = obCommisReturnTotals;
    }

    public BigDecimal getObStoreCostTotals() {
        return obStoreCostTotals;
    }

    public void setObStoreCostTotals(BigDecimal obStoreCostTotals) {
        this.obStoreCostTotals = obStoreCostTotals;
    }

    public BigDecimal getObResultTotals() {
        return obResultTotals;
    }

    public void setObResultTotals(BigDecimal obResultTotals) {
        this.obResultTotals = obResultTotals;
    }

    public Date getObCreateDate() {
        return obCreateDate;
    }

    public void setObCreateDate(Date obCreateDate) {
        this.obCreateDate = obCreateDate;
    }

    public String getOsMonth() {
        return osMonth;
    }

    public void setOsMonth(String osMonth) {
        this.osMonth = osMonth;
    }

    public Integer getObState() {
        return obState;
    }

    public void setObState(Integer obState) {
        this.obState = obState;
    }

    public Date getObPayDate() {
        return obPayDate;
    }

    public void setObPayDate(Date obPayDate) {
        this.obPayDate = obPayDate;
    }

    public String getObPayContent() {
        return obPayContent;
    }

    public void setObPayContent(String obPayContent) {
        this.obPayContent = obPayContent;
    }

    public String getObStoreId() {
        return obStoreId;
    }

    public void setObStoreId(String obStoreId) {
        this.obStoreId = obStoreId;
    }

    public String getObStoreName() {
        return obStoreName;
    }

    public void setObStoreName(String obStoreName) {
        this.obStoreName = obStoreName;
    }
}
