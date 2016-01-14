package com.ansteel.shop.core.domain;


import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 买家发票信息表
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "invoice")
public class Invoice extends BaseEntity {

    /**
     * 会员ID
     */
    private String memberId;
    /**
     * 1普通发票2增值税发票
     */
    private Integer invState;
    /**
     * 发票抬头[普通发票]
     */
    private String invTitle;
    /**
     * 发票内容[普通发票]
     */
    private String invContent;
    /**
     * 单位名称
     */
    private String invCompany;
    /**
     * 纳税人识别号
     */
    private String invCode;
    /**
     * 注册地址
     */
    private String invRegAddr;
    /**
     * 注册电话
     */
    private String invRegPhone;
    /**
     * 开户银行
     */
    private String invRegBankName;
    /**
     * 银行帐户
     */
    private String invRegBaccount;
    /**
     * 收票人姓名
     */
    private String invRecName;
    /**
     * 收票人手机号
     */
    private String invRecMobphone;
    /**
     * 收票人省份
     */
    private String invRecProvince;
    /**
     * 送票地址
     */
    private String invGotoAddr;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getInvState() {
        return invState;
    }

    public void setInvState(Integer invState) {
        this.invState = invState;
    }

    public String getInvTitle() {
        return invTitle;
    }

    public void setInvTitle(String invTitle) {
        this.invTitle = invTitle;
    }

    public String getInvContent() {
        return invContent;
    }

    public void setInvContent(String invContent) {
        this.invContent = invContent;
    }

    public String getInvCompany() {
        return invCompany;
    }

    public void setInvCompany(String invCompany) {
        this.invCompany = invCompany;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getInvRegAddr() {
        return invRegAddr;
    }

    public void setInvRegAddr(String invRegAddr) {
        this.invRegAddr = invRegAddr;
    }

    public String getInvRegPhone() {
        return invRegPhone;
    }

    public void setInvRegPhone(String invRegPhone) {
        this.invRegPhone = invRegPhone;
    }

    public String getInvRegBankName() {
        return invRegBankName;
    }

    public void setInvRegBankName(String invRegBankName) {
        this.invRegBankName = invRegBankName;
    }

    public String getInvRegBaccount() {
        return invRegBaccount;
    }

    public void setInvRegBaccount(String invRegBaccount) {
        this.invRegBaccount = invRegBaccount;
    }

    public String getInvRecName() {
        return invRecName;
    }

    public void setInvRecName(String invRecName) {
        this.invRecName = invRecName;
    }

    public String getInvRecMobphone() {
        return invRecMobphone;
    }

    public void setInvRecMobphone(String invRecMobphone) {
        this.invRecMobphone = invRecMobphone;
    }

    public String getInvRecProvince() {
        return invRecProvince;
    }

    public void setInvRecProvince(String invRecProvince) {
        this.invRecProvince = invRecProvince;
    }

    public String getInvGotoAddr() {
        return invGotoAddr;
    }

    public void setInvGotoAddr(String invGotoAddr) {
        this.invGotoAddr = invGotoAddr;
    }
}
