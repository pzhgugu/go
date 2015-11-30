package com.ansteel.shop.store.domain;


import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.*;

/**
 * 店铺数据表
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "store_joinin")
public class StoreJoinin extends BaseEntity {

    /**
     * 用户编号
     */
    @Column(name = "MEMBER_ID", length = 64)
    private String memberId;

    /**
     * 店主用户名
     */
    @Column(name = "MEMBER_NAME", length = 255)
    private String memberName;

    /**
     * 公司名称
     */
    @Column(name = "COMPANY_NAME", length = 255)
    private String companyName;

    /**
     * 公司地址
     */
    @Column(name = "COMPANY_ADDRESS")
    @Lob
    private String companyAddress;

    /**
     * 公司详细地址
     */
    @Column(name = "COMPANY_ADDRESS_DETAIL", length = 512)
    private String companyAddressDsetail;

    /**
     * 公司电话
     */
    @Column(name = "COMPANY_PHONE", length = 32)
    private String companyPhone;

    /**
     * 员工总数
     */
    @Column(name = "COMPANY_EMPLOYEE_COUNT", length = 10)
    private Integer companyEmployeeCount;

    /**
     * 注册资金
     */
    @Column(name = "COMPANY_REGISTERED_CAPITAL", length = 10)
    private String companyRegisteredCapital;
    /**
     * 联系人姓名
     */
    @Column(name = "CONTACTS_NAME", length = 32)
    private String contactsName;
    /**
     * 联系人电话
     */
    @Column(name = "CONTACTS_PHONE", length = 32)
    private String contactsPhone;
    /**
     * 联系人邮箱
     */
    @Column(name = "CONTACTS_EMAIL", length = 64)
    private String contactsEmail;
    /**
     * 营业执照号
     */
    @Column(name = "BUSINESS_LICENCE_NUMBER", length = 64)
    private String businessLicenceNumber;
    /**
     * 营业执所在地
     */
    @Column(name = "BUSINESS_LICENCE_ADDRESS", length = 512)
    private String businessLicenceAddress;
    /**
     * 营业执照有效期开始
     */
    @Column(name = "BUSINESS_LICENCE_START", length = 16)
    private String businessLicenceStart;
    /**
     * 营业执照有效期结束
     */
    @Column(name = "BUSINESS_LICENCE_END", length = 16)
    private String businessLicenceEnd;
    /**
     * 法定经营范围
     */
    @Column(name = "BUSINESS_SPHERE", length = 1024)
    private String businessSphere;
    /**
     * 营业执照电子版
     */
    @Column(name = "BUSINESS_LICENCE_NUMBER_ELECTRONIC", length = 64)
    private String businessLicenceNumberElectronic;
    /**
     * 组织机构代码
     */
    @Column(name = "ORGANIZATION_CODE", length = 64)
    private String organizationCode;
    /**
     * 组织机构代码电子版
     */
    @Column(name = "ORGANIZATION_CODE_ELECTRONIC", length = 64)
    private String organizationCodeElectronic;
    /**
     * 一般纳税人证明
     */
    @Column(name = "GENERAL_TAXPAYER", length = 64)
    private String generalTaxpayer;
    /**
     * 银行开户名
     */
    @Column(name = "BANK_ACCOUNT_NAME", length = 255)
    private String bankAccountName;
    /**
     * 公司银行账号
     */
    @Column(name = "BANK_ACCOUNT_NUMBER", length = 32)
    private String bankAccountNumber;
    /**
     * 开户银行支行名称
     */
    @Column(name = "BANK_NAME", length = 255)
    private String bankName;
    /**
     * 支行联行号
     */
    @Column(name = "BANK_CODE", length = 32)
    private String bankCode;
    /**
     * 开户银行所在地
     */
    @Column(name = "BANK_ADDRESS", length = 512)
    private String bankAddress;
    /**
     * 开户银行许可证电子版
     */
    @Column(name = "BANK_LICENCE_ELECTRONIC", length = 64)
    private String bankLicenceElectronic;
    /**
     * 开户行账号是否为结算账号 1-开户行就是结算账号 2-独立的计算账号
     */
    @Column(name = "IS_SETTLEMENT_ACCOUNT", length = 2)
    private String isSettlementAccount;
    /**
     * 结算银行开户名
     */
    @Column(name = "SETTLEMENT_BANK_ACCOUNT_NAME", length = 512)
    private String settlementBankAccountName;
    /**
     * 结算公司银行账号
     */
    @Column(name = "SETTLEMENT_BANK_ACCOUNT_NUMBER", length = 32)
    private String settlementBankAccountNumber;
    /**
     * 结算开户银行支行名称
     */
    @Column(name = "SETTLEMENT_BANK_NAME", length = 512)
    private String settlementBankName;
    /**
     * 结算支行联行号
     */
    @Column(name = "SETTLEMENT_BANK_CODE", length = 512)
    private String settlementBankCode;
    /**
     * 结算开户银行所在地
     */
    @Column(name = "SETTLEMENT_BANK_ADDRESS", length = 512)
    private String settlementBankAddress;
    /**
     * 税务登记证号
     */
    @Column(name = "TAX_REGISTRATION_CERTIFICATE", length = 64)
    private String taxRegistrationCertificate;
    /**
     * 纳税人识别号
     */
    @Column(name = "TAXPAYER_ID", length = 64)
    private String taxpayerId;
    /**
     * 税务登记证号电子版
     */
    @Column(name = "TAX_REGISTRATION_CERTIFICATE_ELECTRONIC", length = 64)
    private String taxRegistrationCertificateElectronic;
    /**
     * 卖家帐号
     */
    @Column(name = "SELLER_NAME", length = 255)
    private String sellerName;
    /**
     * 店铺名称
     */
    @Column(name = "STORE_NAME", length = 255)
    private String storeName;
    /**
     * 店铺分类编号集合
     */
    @Column(name = "STORE_CLASS_IDS")
    @Lob
    private String storeClassIds;
    /**
     * 店铺分类名称集合
     */
    @Column(name = "STORE_CLASS_NAMES")
    @Lob
    private String storeClassNames;
    /**
     * 申请状态 10-已提交申请 11-缴费完成 20-审核成功 30-审核失败 31-缴费审核失败 40-审核通过开店
     */
    @Column(name = "JOININ_STATE", length = 2)
    private String joininState;
    /**
     * 管理员审核信息
     */
    @Column(name = "JOININ_MESSAGE", length = 1024)
    private String joininMessage;
    /**
     * 店铺等级名称
     */
    @Column(name = "SG_NAME", length = 255)
    private String sgName;
    /**
     * 店铺等级编号
     */
    @Column(name = "SG_ID", length = 64)
    private String sgId;
    /**
     * 店铺分类名称
     */
    @Column(name = "SC_NAME", length = 255)
    private String scName;
    /**
     * 店铺分类编号
     */
    @Column(name = "SC_ID", length = 64)
    private String scId;
    /**
     * 分类佣金比例
     */
    @Column(name = "STORE_CLASS_COMMIS_RATES", length = 32)
    private String storeClassCommisRates;
    /**
     * 付款凭证
     */
    @Column(name = "PAYING_MONEY_CERTIFICATE", length = 64)
    private String payingMoneyCertificate;
    /**
     * 付款凭证说明
     */
    @Column(name = "PAYING_MONEY_CERTIFICATE_EXPLAIN", length = 1024)
    private String payingMoneyCertificateExplain;


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyAddressDsetail() {
        return companyAddressDsetail;
    }

    public void setCompanyAddressDsetail(String companyAddressDsetail) {
        this.companyAddressDsetail = companyAddressDsetail;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public Integer getCompanyEmployeeCount() {
        return companyEmployeeCount;
    }

    public void setCompanyEmployeeCount(Integer companyEmployeeCount) {
        this.companyEmployeeCount = companyEmployeeCount;
    }

    public String getCompanyRegisteredCapital() {
        return companyRegisteredCapital;
    }

    public void setCompanyRegisteredCapital(String companyRegisteredCapital) {
        this.companyRegisteredCapital = companyRegisteredCapital;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getContactsEmail() {
        return contactsEmail;
    }

    public void setContactsEmail(String contactsEmail) {
        this.contactsEmail = contactsEmail;
    }

    public String getBusinessLicenceNumber() {
        return businessLicenceNumber;
    }

    public void setBusinessLicenceNumber(String businessLicenceNumber) {
        this.businessLicenceNumber = businessLicenceNumber;
    }

    public String getBusinessLicenceAddress() {
        return businessLicenceAddress;
    }

    public void setBusinessLicenceAddress(String businessLicenceAddress) {
        this.businessLicenceAddress = businessLicenceAddress;
    }

    public String getBusinessLicenceStart() {
        return businessLicenceStart;
    }

    public void setBusinessLicenceStart(String businessLicenceStart) {
        this.businessLicenceStart = businessLicenceStart;
    }

    public String getBusinessLicenceEnd() {
        return businessLicenceEnd;
    }

    public void setBusinessLicenceEnd(String businessLicenceEnd) {
        this.businessLicenceEnd = businessLicenceEnd;
    }

    public String getBusinessSphere() {
        return businessSphere;
    }

    public void setBusinessSphere(String businessSphere) {
        this.businessSphere = businessSphere;
    }

    public String getBusinessLicenceNumberElectronic() {
        return businessLicenceNumberElectronic;
    }

    public void setBusinessLicenceNumberElectronic(String businessLicenceNumberElectronic) {
        this.businessLicenceNumberElectronic = businessLicenceNumberElectronic;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationCodeElectronic() {
        return organizationCodeElectronic;
    }

    public void setOrganizationCodeElectronic(String organizationCodeElectronic) {
        this.organizationCodeElectronic = organizationCodeElectronic;
    }

    public String getGeneralTaxpayer() {
        return generalTaxpayer;
    }

    public void setGeneralTaxpayer(String generalTaxpayer) {
        this.generalTaxpayer = generalTaxpayer;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankLicenceElectronic() {
        return bankLicenceElectronic;
    }

    public void setBankLicenceElectronic(String bankLicenceElectronic) {
        this.bankLicenceElectronic = bankLicenceElectronic;
    }

    public String getIsSettlementAccount() {
        return isSettlementAccount;
    }

    public void setIsSettlementAccount(String isSettlementAccount) {
        this.isSettlementAccount = isSettlementAccount;
    }

    public String getSettlementBankAccountName() {
        return settlementBankAccountName;
    }

    public void setSettlementBankAccountName(String settlementBankAccountName) {
        this.settlementBankAccountName = settlementBankAccountName;
    }

    public String getSettlementBankAccountNumber() {
        return settlementBankAccountNumber;
    }

    public void setSettlementBankAccountNumber(String settlementBankAccountNumber) {
        this.settlementBankAccountNumber = settlementBankAccountNumber;
    }

    public String getSettlementBankName() {
        return settlementBankName;
    }

    public void setSettlementBankName(String settlementBankName) {
        this.settlementBankName = settlementBankName;
    }

    public String getSettlementBankCode() {
        return settlementBankCode;
    }

    public void setSettlementBankCode(String settlementBankCode) {
        this.settlementBankCode = settlementBankCode;
    }

    public String getSettlementBankAddress() {
        return settlementBankAddress;
    }

    public void setSettlementBankAddress(String settlementBankAddress) {
        this.settlementBankAddress = settlementBankAddress;
    }

    public String getTaxRegistrationCertificate() {
        return taxRegistrationCertificate;
    }

    public void setTaxRegistrationCertificate(String taxRegistrationCertificate) {
        this.taxRegistrationCertificate = taxRegistrationCertificate;
    }

    public String getTaxpayerId() {
        return taxpayerId;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }

    public String getTaxRegistrationCertificateElectronic() {
        return taxRegistrationCertificateElectronic;
    }

    public void setTaxRegistrationCertificateElectronic(String taxRegistrationCertificateElectronic) {
        this.taxRegistrationCertificateElectronic = taxRegistrationCertificateElectronic;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreClassIds() {
        return storeClassIds;
    }

    public void setStoreClassIds(String storeClassIds) {
        this.storeClassIds = storeClassIds;
    }

    public String getStoreClassNames() {
        return storeClassNames;
    }

    public void setStoreClassNames(String storeClassNames) {
        this.storeClassNames = storeClassNames;
    }

    public String getJoininState() {
        return joininState;
    }

    public void setJoininState(String joininState) {
        this.joininState = joininState;
    }

    public String getJoininMessage() {
        return joininMessage;
    }

    public void setJoininMessage(String joininMessage) {
        this.joininMessage = joininMessage;
    }

    public String getSgName() {
        return sgName;
    }

    public void setSgName(String sgName) {
        this.sgName = sgName;
    }

    public String getSgId() {
        return sgId;
    }

    public void setSgId(String sgId) {
        this.sgId = sgId;
    }

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public String getStoreClassCommisRates() {
        return storeClassCommisRates;
    }

    public void setStoreClassCommisRates(String storeClassCommisRates) {
        this.storeClassCommisRates = storeClassCommisRates;
    }

    public String getPayingMoneyCertificate() {
        return payingMoneyCertificate;
    }

    public void setPayingMoneyCertificate(String payingMoneyCertificate) {
        this.payingMoneyCertificate = payingMoneyCertificate;
    }

    public String getPayingMoneyCertificateExplain() {
        return payingMoneyCertificateExplain;
    }

    public void setPayingMoneyCertificateExplain(String payingMoneyCertificateExplain) {
        this.payingMoneyCertificateExplain = payingMoneyCertificateExplain;
    }

}
