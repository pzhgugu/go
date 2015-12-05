package com.ansteel.shop.goods.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Administrator on 2015/11/24.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JsonGoodsSpec {

    /**
     * 商品类型ID
     * GoodsType
     */
    private String type_id;
    /**
     * 规格id
     * GoodsSpec
     */
    private String sp_id;
    /**
     * 规格名称
     */
    private String sp_name;
    /**
     * 规格排序
     */
    private Integer sp_sort;

    private String class_id;

    private String class_name;

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getSp_id() {
        return sp_id;
    }

    public void setSp_id(String sp_id) {
        this.sp_id = sp_id;
    }

    public String getSp_name() {
        return sp_name;
    }

    public void setSp_name(String sp_name) {
        this.sp_name = sp_name;
    }

    public Integer getSp_sort() {
        return sp_sort;
    }

    public void setSp_sort(Integer sp_sort) {
        this.sp_sort = sp_sort;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
