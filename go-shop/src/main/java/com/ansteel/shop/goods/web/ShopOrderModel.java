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
}
