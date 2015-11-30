package com.ansteel.shop.goods.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonGoodsClassData {

    private String type;

    private Object data;

    private Integer deep;

    private String gcid;

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }
}
