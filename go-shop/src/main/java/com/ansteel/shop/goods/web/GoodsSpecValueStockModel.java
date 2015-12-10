package com.ansteel.shop.goods.web;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/12/5.
 */
public class GoodsSpecValueStockModel {

    private String goodsId;
    /**
     * 颜色id
     */
    private String colorId;
    /**
     * 规格名称集合
     */
    private String[] specName;

    /**
     * 规格id集合
     */
    private String[] specId;

    /**
     * 规格价格
     */
    private BigDecimal price;

    /**
     * 规格库存
     */
    private Integer stock;

    /**
     * 商家货号
     */
    private  String sku;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String[] getSpecName() {
        return specName;
    }

    public void setSpecName(String[] specName) {
        this.specName = specName;
    }

    public String[] getSpecId() {
        return specId;
    }

    public void setSpecId(String[] specId) {
        this.specId = specId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }
}
