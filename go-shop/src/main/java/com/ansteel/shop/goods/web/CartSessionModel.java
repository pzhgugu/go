package com.ansteel.shop.goods.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CartSessionModel {

    public static final String SESSION_NAME = "_SHOW_CART_SESSION_NAME";

    private String goodsId;

    private Integer number;

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

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
