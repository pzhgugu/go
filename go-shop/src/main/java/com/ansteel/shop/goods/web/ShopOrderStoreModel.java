package com.ansteel.shop.goods.web;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/2/3.
 */
public class ShopOrderStoreModel {
    /**
     * 店铺ID
     */
    private String storeId;

    private String storeName;

    private List<ShopOrderCartModel> cartList;
    /**
     * 买家留言
     */
    private String message;
    /**
     * 店铺运费
     */
    private BigDecimal freight;
    /**
     * 店铺商品金额
     */
    private BigDecimal storeGoodsPrice;
    /**
     * 店铺金额
     */
    private BigDecimal storePrice;

    public BigDecimal getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(BigDecimal storePrice) {
        this.storePrice = storePrice;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getStoreGoodsPrice() {
        return storeGoodsPrice;
    }

    public void setStoreGoodsPrice(BigDecimal storeGoodsPrice) {
        this.storeGoodsPrice = storeGoodsPrice;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<ShopOrderCartModel> getCartList() {
        return cartList;
    }

    public void setCartList(List<ShopOrderCartModel> cartList) {
        this.cartList = cartList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
