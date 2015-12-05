package com.ansteel.shop.goods.web;

import com.ansteel.shop.goods.domain.GoodsSpecValue;

import java.util.List;

/**
 * Created by Administrator on 2015/11/25.
 */
public class GoodsSpecValueModel {

    /**
     * 商品分类id
     */
    private String gcId;

    /**
     * 规格Id
     */
    private String spId;

    List<GoodsSpecValue> specValues;

    public List<GoodsSpecValue> getSpecValues() {
        return specValues;
    }

    public void setSpecValues(List<GoodsSpecValue> specValues) {
        this.specValues = specValues;
    }

    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }
}
