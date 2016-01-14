package com.ansteel.shop.goods.web;

import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.store.domain.Store;

/**
 * 购买商品数据模型.
 */
public class BuyGoodsModel {

    /**
     * 购买商品
     */
    private Goods goods;

    /**
     * 购买数量
     */
    private Integer number;

    /**
     * 店铺di
     */
    private Store store;

    private String cartId;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
