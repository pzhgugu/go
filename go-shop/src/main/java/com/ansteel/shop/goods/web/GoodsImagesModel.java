package com.ansteel.shop.goods.web;

import com.ansteel.shop.goods.domain.GoodsImages;

import java.util.List;

/**
 * Created by Administrator on 2015/12/7.
 */
public class GoodsImagesModel {

    private String colorId;

    private List<GoodsImages> imgList;

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public List<GoodsImages> getImgList() {
        return imgList;
    }

    public void setImgList(List<GoodsImages> imgList) {
        this.imgList = imgList;
    }
}
