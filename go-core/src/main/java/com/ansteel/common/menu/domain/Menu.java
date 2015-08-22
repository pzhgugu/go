package com.ansteel.common.menu.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.TreeEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：菜单实体类。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX +  "menu")
public class Menu extends TreeEntity<Menu>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 258920242292748135L;
	
	/**
	 * 菜单URL
	 */
	private String url;

	/**
	 * 是否弹出页面
	 */
	private Integer isPopup;
	/**
	 * 图片
	 */
	private String image;
	/**
	 * 备注
	 */
	@Column(length=4000)
	private String scription;
	/**
	 * 是否目录 1、菜单 2、分隔符 3
	 */
	@Column(columnDefinition="INT default 1")
	private Integer isCatalogue;
	/**
	 * 是否停用
	 */
	@Column(columnDefinition="INT default 0")
	private Integer isStop;
	/**
	 * 资源名称
	 */
	private String resourcesName;
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}
	public Integer getIsStop() {
		return isStop;
	}
	public void setIsStop(Integer isStop) {
		this.isStop = isStop;
	}
	public String getResourcesName() {
		return resourcesName;
	}
	public void setResourcesName(String resourcesName) {
		this.resourcesName = resourcesName;
	}
	public Integer getIsCatalogue() {
		return isCatalogue;
	}
	public void setIsCatalogue(Integer isCatalogue) {
		this.isCatalogue = isCatalogue;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getIsPopup() {
		return isPopup;
	}
	public void setIsPopup(Integer isPopup) {
		this.isPopup = isPopup;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getScription() {
		return scription;
	}
	public void setScription(String scription) {
		this.scription = scription;
	}
	
	/*public Collection<Menu> queryRoot(){
		return this.find("from Menu where parent.id is null");
	}*/
}
