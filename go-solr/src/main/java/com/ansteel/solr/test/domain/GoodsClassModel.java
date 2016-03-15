package com.ansteel.solr.test.domain;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by Administrator on 2016/3/3.
 */
public class GoodsClassModel {

    @Field
    private String id;
    @Field
    private String goodsClassName;
    @Field
    private String goodsClassTypeName;
    @Field
    private Integer goodsClassSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsClassName() {
        return goodsClassName;
    }

    public void setGoodsClassName(String goodsClassName) {
        this.goodsClassName = goodsClassName;
    }

    public String getGoodsClassTypeName() {
        return goodsClassTypeName;
    }

    public void setGoodsClassTypeName(String goodsClassTypeName) {
        this.goodsClassTypeName = goodsClassTypeName;
    }

    public Integer getGoodsClassSort() {
        return goodsClassSort;
    }

    public void setGoodsClassSort(Integer goodsClassSort) {
        this.goodsClassSort = goodsClassSort;
    }
}
