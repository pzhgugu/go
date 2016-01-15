package com.ansteel.shop.goods.web;

/**
 * Created by Administrator on 2016/1/15.
 */
public class CartSessionModel {

    public static final String SESSION_NAME = "_SHOW_CART_SESSION_NAME";

    private String goodsId;

    private Integer number;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
