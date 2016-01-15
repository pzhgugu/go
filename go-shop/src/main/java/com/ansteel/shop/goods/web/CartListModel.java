package com.ansteel.shop.goods.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.List;
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CartListModel {

    private List<CartObjectModel> list;

    private BigDecimal cart_all_price;

    private Integer cart_goods_num;

    public List<CartObjectModel> getList() {
        return list;
    }

    public void setList(List<CartObjectModel> list) {
        this.list = list;
    }

    public BigDecimal getCart_all_price() {
        return cart_all_price;
    }

    public void setCart_all_price(BigDecimal cart_all_price) {
        this.cart_all_price = cart_all_price;
    }

    public Integer getCart_goods_num() {
        return cart_goods_num;
    }

    public void setCart_goods_num(Integer cart_goods_num) {
        this.cart_goods_num = cart_goods_num;
    }
}
