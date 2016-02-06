package com.ansteel.shop.goods.web;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/2/3.
 */
public class ShopOrderModel {

    private List<ShopOrderStoreModel> storeList;
    /**
     * 订单总金额
     */
    private BigDecimal amount;
    /**
     * 发票ID
     */
    private String invoice_id;
    /**
     * 收货地址ID
     */
    private String address_id;
    /**
     * 城市ID
     */
    private String buy_city_id;
    /**
     * 返回地址
     */
    private String ref_url;
    /**
     * 支付方式（online 在线，
     */
    private String pay_name;
    /**
     * 来源于购物车标志 1 购物车
     */
    private Integer ifcart;



    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<ShopOrderStoreModel> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<ShopOrderStoreModel> storeList) {
        this.storeList = storeList;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getBuy_city_id() {
        return buy_city_id;
    }

    public void setBuy_city_id(String buy_city_id) {
        this.buy_city_id = buy_city_id;
    }

    public String getRef_url() {
        return ref_url;
    }

    public void setRef_url(String ref_url) {
        this.ref_url = ref_url;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public Integer getIfcart() {
        return ifcart;
    }

    public void setIfcart(Integer ifcart) {
        this.ifcart = ifcart;
    }
}
