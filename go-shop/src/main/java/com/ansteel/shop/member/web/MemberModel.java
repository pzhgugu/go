package com.ansteel.shop.member.web;

import java.util.Date;

/**
 * Created by Administrator on 2016/2/23.
 */
public class MemberModel {

    /**
     * 真实姓名
     */
    private String memberTrueName;
    /**
     * 会员性别
     */
    private Integer memberSex;
    /**
     * 生日
     */
    private Date memberBirthday;
    /**
     * QQ     *
     */
    private String memberQq;
    /**
     * 阿里旺旺
     */
    private String memberWw;
    /**
     * 地区ID
     */
    private String memberAreaId;
    /**
     * 城市ID
     */
    private String memberCityId;
    /**
     * 省份ID
     */
    private String memberProvinceId;
    /**
     * 地区内容
     */
    private String memberAreaInfo;
    /**
     *  隐私设置
     */
    private PrivacyModel privacyModel;

    public String getMemberTrueName() {
        return memberTrueName;
    }

    public void setMemberTrueName(String memberTrueName) {
        this.memberTrueName = memberTrueName;
    }

    public Integer getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(Integer memberSex) {
        this.memberSex = memberSex;
    }

    public Date getMemberBirthday() {
        return memberBirthday;
    }

    public void setMemberBirthday(Date memberBirthday) {
        this.memberBirthday = memberBirthday;
    }

    public String getMemberQq() {
        return memberQq;
    }

    public void setMemberQq(String memberQq) {
        this.memberQq = memberQq;
    }

    public String getMemberWw() {
        return memberWw;
    }

    public void setMemberWw(String memberWw) {
        this.memberWw = memberWw;
    }

    public String getMemberAreaId() {
        return memberAreaId;
    }

    public void setMemberAreaId(String memberAreaId) {
        this.memberAreaId = memberAreaId;
    }

    public String getMemberCityId() {
        return memberCityId;
    }

    public void setMemberCityId(String memberCityId) {
        this.memberCityId = memberCityId;
    }

    public String getMemberProvinceId() {
        return memberProvinceId;
    }

    public void setMemberProvinceId(String memberProvinceId) {
        this.memberProvinceId = memberProvinceId;
    }

    public String getMemberAreaInfo() {
        return memberAreaInfo;
    }

    public void setMemberAreaInfo(String memberAreaInfo) {
        this.memberAreaInfo = memberAreaInfo;
    }

    public PrivacyModel getPrivacyModel() {
        return privacyModel;
    }

    public void setPrivacyModel(PrivacyModel privacyModel) {
        this.privacyModel = privacyModel;
    }
}
