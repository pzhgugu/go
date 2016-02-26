package com.ansteel.shop.core.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Constants.G_TABLE_PREFIX + "address")
public class Address extends BaseEntity {

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 会员姓名
     */
    private String trueName;

    /**
     * 地区ID
     */
    private String areaId;

    /**
     * 市级ID
     */
    private String cityId;

    /**
     * 地区内容
     */
    private String areaInfo;

    /**
     * 地址
     */
    private String address;

    /**
     * 座机电话
     */
    private String telPhone;

    /**
     * 手机电话
     */
    private String mobPhone;

    /**
     * 1默认收货地址
     */
    private Integer isDefault;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaInfo() {
        return areaInfo;
    }

    public void setAreaInfo(String areaInfo) {
        this.areaInfo = areaInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getMobPhone() {
        return mobPhone;
    }

    public void setMobPhone(String mobPhone) {
        this.mobPhone = mobPhone;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}
