package com.ansteel.shop.goods.web;

/**
 * Created by Administrator on 2016/2/3.
 */
public class ShopOrderCartModel {

    /**
     * 数量
     */
    private Integer number;
    /**
     * 商品Id
     */
    private String goodsId;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
