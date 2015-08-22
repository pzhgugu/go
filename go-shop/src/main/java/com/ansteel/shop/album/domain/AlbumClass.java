package com.ansteel.shop.album.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：相册列表。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "album_class")
public class AlbumClass extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1986918480163854697L;

	/**
	 * 相册名称
	 */
	@NotNull(message="相册名称不能为空")
	@Pattern(regexp = "^.{1,20}$",message="相册名称不能超过20个字符")
	private String aclassName;
	/**
	 * 所属店铺id
	 */
	//@NotNull(message="所属店铺不能为空")
	private String storeId;
	/**
	 * 相册描述
	 */
	@Column(length=4000)
	private String aclassDes;
	/**
	 * 排序
	 */
	//@Pattern(regexp="^[0-9]*$",message="排序只能填写数字")
	private Integer aclassSort;
	/**
	 * 相册封面
	 */
	private String aclassCover;
	/**
	 * 图片上传时间
	 */
	private Date uploadTime;
	/**
	 * 是否为默认相册,1代表默认
	 */
	private Integer isDefault;
	
	/**
	 * 此相册下的图片数量，虚拟字段
	 */
	@Transient
	private Long picNumber;
	
	public Long getPicNumber() {
		return picNumber;
	}
	public void setPicNumber(Long picNumber) {
		this.picNumber = picNumber;
	}
	public String getAclassName() {
		return aclassName;
	}
	public void setAclassName(String aclassName) {
		this.aclassName = aclassName;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getAclassDes() {
		return aclassDes;
	}
	public void setAclassDes(String aclassDes) {
		this.aclassDes = aclassDes;
	}
	public Integer getAclassSort() {
		return aclassSort;
	}
	public void setAclassSort(Integer aclassSort) {
		this.aclassSort = aclassSort;
	}
	public String getAclassCover() {
		return aclassCover;
	}
	public void setAclassCover(String aclassCover) {
		this.aclassCover = aclassCover;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	
}
