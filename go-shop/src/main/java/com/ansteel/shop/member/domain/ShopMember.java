package com.ansteel.shop.member.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = Constants.G_TABLE_PREFIX + "shop_member")
public class ShopMember extends BaseEntity {

    private String userId;

    private String userName;

    private String alias;
    /**
     * 真实姓名
     */
    private String memberTrueName;
    /**
     * 会员头像
     */
    private String memberAvatar;
    /**
     * 会员性别
     */
    private Integer memberSex;
    /**
     * 生日
     */
    private Date memberBirthday;
    /**
     * 电子邮件
     */
    private String memberEmail;
    /**
     * QQ     *
     */
    private String memberQq;
    /**
     * 阿里旺旺
     */
    private String memberWw;
    /**
     * 会员积分
     */
    private Integer memberPoints;
    /**
     * 预存款可用金额
     */
    private BigDecimal availablePredeposit;
    /**
     * 预存款冻结金额
     */
    private BigDecimal freezePredeposit;
    /**
     * 是否允许举报(1可以/2不可以)
     */
    private Integer informAllow;
    /**
     * 会员是否有购买权限 1为开启 0为关闭
     */
    private Integer isBuy;
    /**
     * 会员是否有咨询和发送站内信的权限 1为开启 0为关闭
     */
    private Integer isAllowtalk;
    /**
     * 会员的开启状态 1为开启 0为关闭
     */
    private Integer memberState;
    /**
     * 会员信用
     */
    private Integer memberCredit;
    /**
     * 隐私设定
     */
    private String memberPrivacy;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberAvatar() {
        return memberAvatar;
    }

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
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

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
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

    public Integer getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(Integer memberPoints) {
        this.memberPoints = memberPoints;
    }

    public BigDecimal getAvailablePredeposit() {
        return availablePredeposit;
    }

    public void setAvailablePredeposit(BigDecimal availablePredeposit) {
        this.availablePredeposit = availablePredeposit;
    }

    public BigDecimal getFreezePredeposit() {
        return freezePredeposit;
    }

    public void setFreezePredeposit(BigDecimal freezePredeposit) {
        this.freezePredeposit = freezePredeposit;
    }

    public Integer getInformAllow() {
        return informAllow;
    }

    public void setInformAllow(Integer informAllow) {
        this.informAllow = informAllow;
    }

    public Integer getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }

    public Integer getIsAllowtalk() {
        return isAllowtalk;
    }

    public void setIsAllowtalk(Integer isAllowtalk) {
        this.isAllowtalk = isAllowtalk;
    }

    public Integer getMemberState() {
        return memberState;
    }

    public void setMemberState(Integer memberState) {
        this.memberState = memberState;
    }

    public Integer getMemberCredit() {
        return memberCredit;
    }

    public void setMemberCredit(Integer memberCredit) {
        this.memberCredit = memberCredit;
    }

    public String getMemberPrivacy() {
        return memberPrivacy;
    }

    public void setMemberPrivacy(String memberPrivacy) {
        this.memberPrivacy = memberPrivacy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMemberTrueName() {
        return memberTrueName;
    }

    public void setMemberTrueName(String memberTrueName) {
        this.memberTrueName = memberTrueName;
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
}
