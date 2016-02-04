package com.ansteel.shop.core.web;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/4.
 */
public class AddressFreightModel {

    private String state;

    private String allow_offpay;

    private String offpay_hash;

    private Map<String,Object> content = new HashMap<>();

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAllow_offpay() {
        return allow_offpay;
    }

    public void setAllow_offpay(String allow_offpay) {
        this.allow_offpay = allow_offpay;
    }

    public String getOffpay_hash() {
        return offpay_hash;
    }

    public void setOffpay_hash(String offpay_hash) {
        this.offpay_hash = offpay_hash;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }
}
