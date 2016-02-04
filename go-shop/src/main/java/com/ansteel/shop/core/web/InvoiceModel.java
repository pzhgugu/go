package com.ansteel.shop.core.web;

/**
 * 发票返回数据模型
 */

public class InvoiceModel {

    private String state;

    private String id;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
