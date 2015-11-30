package com.ansteel.shop.store.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.OperEntity;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：店铺信息。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "store")
public class Store extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8211394485894559836L;
	/**
	 * 机器名（英文）
	 */

	private String name;

	/**
	 * 店主卖家用户名
	 */
	private String sellerName;
	/**
	 *  排序
	 */
	private Integer displayOrder;
	/**
	 * 店铺认证
	 */
	private Integer storeAuth;
	/**
	 * 店主认证
	 */
	private Integer nameAuth;
	/**
	 * 店铺等级
	 */
	private String gradeId;
	/**
	 * 店铺名称
	 */
	private String gradeName;
	/**
	 * 会员id
	 */
	private String memberId;
	/**
	 * 会员名称
	 */
	private String memberName;
	/**
	 * 身份证
	 */
	private String storeOwnerCard;
	/**
	 * 店铺分类id
	 */
	private String scId;
	/**
	 * 地区id
	 */
	private String areaId;
	/**
	 * 店铺详细地址
	 */
	private String storeAddress;
	/**
	 * 邮政编码
	 */
	private String storeZip;
	/**
	 * 电话号码
	 */
	private String storeTel;
	/**
	 * 证件上传
	 */
	private String credentialsImage;
	/**
	 * 执照上传
	 */
	private String licenseImage;
	/**
	 * 店铺状态
	 */
	private String storeState;
	/**
	 * 店铺关闭原因
	 */
	private String storeCloseInfo;
	/**
	 * 店铺关闭时间
	 */
	private Date storeEndTime;
	/**
	 * 店标
	 */
	private String storeLabel;
	/**
	 * 店铺logo
	 */
	private String storeLogo;
	/**
	 * 店铺横幅
	 */
	private String storeBanner;
	/**
	 * 店铺seo关键字
	 */
	private String storeKeywords;
	/**
	 * 店铺seo描述
	 */
	private String storeDescription;
	/**
	 * QQ
	 */
	private String storeQQ;
	/**
	 * 阿里旺旺
	 */
	private String storeWW;
	/**
	 * MSN
	 */
	private String storeMsn;
	/**
	 * 店铺简介
	 */
	@Column(length=4000)
	private String description;
	/**
	 * 店铺二级域名
	 */
	private String storeDomain;
	/**
	 * 二级域名修改次数
	 */
	private String storeDomainTimes;
	/**
	 * 推荐，0为否，1为是，默认为0
	 */
	private Integer storeRecommend;
	/**
	 * 店铺审核，0为未通过，1为通过，默认为0
	 */
	private Integer storeAudit;
	/**
	 * 店铺当前主题
	 */
	private String storeTheme;
	/**
	 * 店铺信用
	 */
	private Integer storeCredit;
	/**
	 * 店铺好评率
	 */
	private Float praiseRate;
	/**
	 * 描述相符度分数
	 */
	private Float storeDesccredit;
	/**
	 * 服务态度分数
	 */
	private Float storeServicecredit;
	/**
	 * 发货速度分数
	 */
	private Float storeDeliverycredit;
	/**
	 * 店铺二维码
	 */
	private String storeCode;
	/**
	 * 店铺收藏数量
	 */
	private Integer storeCollect;
	/**
	 * 店铺幻灯片
	 */
	private String storeSlide;
	/**
	 * 店铺幻灯片链接
	 */
	private String storeSlideUrl;
	/**
	 * 卖家中心的常用操作快捷链接
	 */
	private String storeCenterQuicklink;
	/**
	 * 店铺公司名称
	 */
	private String storeCompanyName;
	/**
	 * 主营商品
	 */
	@Lob
	private String storeZy;

	public String getStoreZy() {
		return storeZy;
	}

	public void setStoreZy(String storeZy) {
		this.storeZy = storeZy;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getStoreCompanyName() {
		return storeCompanyName;
	}

	public void setStoreCompanyName(String storeCompanyName) {
		this.storeCompanyName = storeCompanyName;
	}

	public Integer getStoreAuth() {
		return storeAuth;
	}
	public void setStoreAuth(Integer storeAuth) {
		this.storeAuth = storeAuth;
	}
	public Integer getNameAuth() {
		return nameAuth;
	}
	public void setNameAuth(Integer nameAuth) {
		this.nameAuth = nameAuth;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
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
	public String getStoreOwnerCard() {
		return storeOwnerCard;
	}
	public void setStoreOwnerCard(String storeOwnerCard) {
		this.storeOwnerCard = storeOwnerCard;
	}
	public String getScId() {
		return scId;
	}
	public void setScId(String scId) {
		this.scId = scId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getStoreAddress() {
		return storeAddress;
	}
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	public String getStoreZip() {
		return storeZip;
	}
	public void setStoreZip(String storeZip) {
		this.storeZip = storeZip;
	}
	public String getStoreTel() {
		return storeTel;
	}
	public void setStoreTel(String storeTel) {
		this.storeTel = storeTel;
	}
	public String getCredentialsImage() {
		return credentialsImage;
	}
	public void setCredentialsImage(String credentialsImage) {
		this.credentialsImage = credentialsImage;
	}
	public String getLicenseImage() {
		return licenseImage;
	}
	public void setLicenseImage(String licenseImage) {
		this.licenseImage = licenseImage;
	}
	public String getStoreState() {
		return storeState;
	}
	public void setStoreState(String storeState) {
		this.storeState = storeState;
	}
	public String getStoreCloseInfo() {
		return storeCloseInfo;
	}
	public void setStoreCloseInfo(String storeCloseInfo) {
		this.storeCloseInfo = storeCloseInfo;
	}
	public Date getStoreEndTime() {
		return storeEndTime;
	}
	public void setStoreEndTime(Date storeEndTime) {
		this.storeEndTime = storeEndTime;
	}
	public String getStoreLabel() {
		return storeLabel;
	}
	public void setStoreLabel(String storeLabel) {
		this.storeLabel = storeLabel;
	}
	public String getStoreLogo() {
		return storeLogo;
	}
	public void setStoreLogo(String storeLogo) {
		this.storeLogo = storeLogo;
	}
	public String getStoreBanner() {
		return storeBanner;
	}
	public void setStoreBanner(String storeBanner) {
		this.storeBanner = storeBanner;
	}
	public String getStoreKeywords() {
		return storeKeywords;
	}
	public void setStoreKeywords(String storeKeywords) {
		this.storeKeywords = storeKeywords;
	}
	public String getStoreDescription() {
		return storeDescription;
	}
	public void setStoreDescription(String storeDescription) {
		this.storeDescription = storeDescription;
	}
	public String getStoreQQ() {
		return storeQQ;
	}
	public void setStoreQQ(String storeQQ) {
		this.storeQQ = storeQQ;
	}
	public String getStoreWW() {
		return storeWW;
	}
	public void setStoreWW(String storeWW) {
		this.storeWW = storeWW;
	}
	public String getStoreMsn() {
		return storeMsn;
	}
	public void setStoreMsn(String storeMsn) {
		this.storeMsn = storeMsn;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStoreDomain() {
		return storeDomain;
	}
	public void setStoreDomain(String storeDomain) {
		this.storeDomain = storeDomain;
	}
	public String getStoreDomainTimes() {
		return storeDomainTimes;
	}
	public void setStoreDomainTimes(String storeDomainTimes) {
		this.storeDomainTimes = storeDomainTimes;
	}
	public Integer getStoreRecommend() {
		return storeRecommend;
	}
	public void setStoreRecommend(Integer storeRecommend) {
		this.storeRecommend = storeRecommend;
	}
	public Integer getStoreAudit() {
		return storeAudit;
	}
	public void setStoreAudit(Integer storeAudit) {
		this.storeAudit = storeAudit;
	}
	public String getStoreTheme() {
		return storeTheme;
	}
	public void setStoreTheme(String storeTheme) {
		this.storeTheme = storeTheme;
	}
	public Integer getStoreCredit() {
		return storeCredit;
	}
	public void setStoreCredit(Integer storeCredit) {
		this.storeCredit = storeCredit;
	}
	public Float getPraiseRate() {
		return praiseRate;
	}
	public void setPraiseRate(Float praiseRate) {
		this.praiseRate = praiseRate;
	}
	public Float getStoreDesccredit() {
		return storeDesccredit;
	}
	public void setStoreDesccredit(Float storeDesccredit) {
		this.storeDesccredit = storeDesccredit;
	}
	public Float getStoreServicecredit() {
		return storeServicecredit;
	}
	public void setStoreServicecredit(Float storeServicecredit) {
		this.storeServicecredit = storeServicecredit;
	}
	public Float getStoreDeliverycredit() {
		return storeDeliverycredit;
	}
	public void setStoreDeliverycredit(Float storeDeliverycredit) {
		this.storeDeliverycredit = storeDeliverycredit;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public Integer getStoreCollect() {
		return storeCollect;
	}
	public void setStoreCollect(Integer storeCollect) {
		this.storeCollect = storeCollect;
	}
	public String getStoreSlide() {
		return storeSlide;
	}
	public void setStoreSlide(String storeSlide) {
		this.storeSlide = storeSlide;
	}
	public String getStoreSlideUrl() {
		return storeSlideUrl;
	}
	public void setStoreSlideUrl(String storeSlideUrl) {
		this.storeSlideUrl = storeSlideUrl;
	}
	public String getStoreCenterQuicklink() {
		return storeCenterQuicklink;
	}
	public void setStoreCenterQuicklink(String storeCenterQuicklink) {
		this.storeCenterQuicklink = storeCenterQuicklink;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
}
