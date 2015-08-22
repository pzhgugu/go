package com.ansteel.shop.album.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：相册图片表
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "album_pic")
public class AlbumPic extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8079783788433373116L;
	
	/**
	 * 图片名称
	 */
	private String apicName;
	/**
	 * 图片标签
	 */
	private String apicTag;
	/**
	 * 相册id
	 */
	private String aclassId;
	/**
	 * 图片路径
	 */
	private String apicCover;
	/**
	 * 图片大小
	 */
	private Long apicSize;
	/**
	 * 图片规格
	 */
	private String apicSpec;
	/**
	 * 所属店铺id
	 */
	private String storeId;
	public String getApicName() {
		return apicName;
	}
	public void setApicName(String apicName) {
		this.apicName = apicName;
	}
	public String getApicTag() {
		return apicTag;
	}
	public void setApicTag(String apicTag) {
		this.apicTag = apicTag;
	}
	public String getAclassId() {
		return aclassId;
	}
	public void setAclassId(String aclassId) {
		this.aclassId = aclassId;
	}
	public String getApicCover() {
		return apicCover;
	}
	public void setApicCover(String apicCover) {
		this.apicCover = apicCover;
	}
	public Long getApicSize() {
		return apicSize;
	}
	public void setApicSize(Long apicSize) {
		this.apicSize = apicSize;
	}
	public String getApicSpec() {
		return apicSpec;
	}
	public void setApicSpec(String apicSpec) {
		this.apicSpec = apicSpec;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	

}
