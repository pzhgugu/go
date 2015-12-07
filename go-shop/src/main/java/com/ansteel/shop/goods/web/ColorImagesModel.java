package com.ansteel.shop.goods.web;

import java.util.List;

/**
 * Created by Administrator on 2015/12/7.
 */
public class ColorImagesModel {

    private  String goodsId;

    private List<GoodsImagesModel> gimList;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public List<GoodsImagesModel> getGimList() {
        return gimList;
    }

    public void setGimList(List<GoodsImagesModel> gimList) {
        this.gimList = gimList;
    }
}
