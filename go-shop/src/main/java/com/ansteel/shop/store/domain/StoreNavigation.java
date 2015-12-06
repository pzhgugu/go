package com.ansteel.shop.store.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 店铺导航
 */
@Entity
@Table(name =  Constants.G_TABLE_PREFIX + "STORE_NAV")
public class StoreNavigation extends BaseEntity {

    /**
     * 导航名称
     */
    private String snTitle;

    /**
     * 卖家店铺ID
     */
    private String storeId;

    /**
     * 导航内容
     */
    private String snContent;

    /**
     * 导航排序
     */
    private String snSort;

    /**
     * 导航是否显示
     */
    private String snIfShow;

    /**
     * 店铺导航的外链URL
     */
    private String snUrl;

    /**
     * 店铺导航外链是否在新窗口打开：0不开新窗口1开新窗口，默认是0
     */
    private String snNewOpen;

    public String getSnTitle() {
        return snTitle;
    }

    public void setSnTitle(String snTitle) {
        this.snTitle = snTitle;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSnContent() {
        return snContent;
    }

    public void setSnContent(String snContent) {
        this.snContent = snContent;
    }

    public String getSnSort() {
        return snSort;
    }

    public void setSnSort(String snSort) {
        this.snSort = snSort;
    }

    public String getSnIfShow() {
        return snIfShow;
    }

    public void setSnIfShow(String snIfShow) {
        this.snIfShow = snIfShow;
    }

    public String getSnUrl() {
        return snUrl;
    }

    public void setSnUrl(String snUrl) {
        this.snUrl = snUrl;
    }

    public String getSnNewOpen() {
        return snNewOpen;
    }

    public void setSnNewOpen(String snNewOpen) {
        this.snNewOpen = snNewOpen;
    }

}
