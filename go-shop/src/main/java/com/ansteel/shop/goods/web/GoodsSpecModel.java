package com.ansteel.shop.goods.web;

import java.util.List;

/**
 * Created by Administrator on 2015/12/4.
 */
public class GoodsSpecModel {

    /**
     * 规格值列表
     */
    List<GoodsSpecValueSelectListModel> gsvslList;

    /**
     * 库存配置列表
     */
    List<GoodsSpecValueStockModel> stockList;

    /**
     * 库存配置列表
     */
    List<GoodsAttrModel> attrList;

    /**
     * 店铺分类列表
     */
    String[] sgcateIdist;

    public String[] getSgcateIdist() {
        return sgcateIdist;
    }

    public void setSgcateIdist(String[] sgcateIdist) {
        this.sgcateIdist = sgcateIdist;
    }

    public List<GoodsAttrModel> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<GoodsAttrModel> attrList) {
        this.attrList = attrList;
    }

    public List<GoodsSpecValueStockModel> getStockList() {
        return stockList;
    }

    public void setStockList(List<GoodsSpecValueStockModel> stockList) {
        this.stockList = stockList;
    }

    public List<GoodsSpecValueSelectListModel> getGsvslList() {
        return gsvslList;
    }

    public void setGsvslList(List<GoodsSpecValueSelectListModel> gsvslList) {
        this.gsvslList = gsvslList;
    }
}
