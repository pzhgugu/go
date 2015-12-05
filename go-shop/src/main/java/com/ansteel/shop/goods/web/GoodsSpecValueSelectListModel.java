package com.ansteel.shop.goods.web;

import com.ansteel.shop.goods.domain.GoodsSpecValue;

import java.util.List;

/**
 * Created by Administrator on 2015/12/5.
 */
public class GoodsSpecValueSelectListModel {


    /**
     * 规格Id
     */
    private String spId;

    private String spName;

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    private String[] spvId;

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String[] getSpvId() {
        return spvId;
    }

    public void setSpvId(String[] spvId) {
        this.spvId = spvId;
    }
}
