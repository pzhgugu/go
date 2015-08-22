package com.ansteel.common.prentmodel.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模型字段查询父类。  
 */
@MappedSuperclass
public class FieldsQuery extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3033101258110465361L;
	/**
	 * 操作符编码 =@>@>=@<@<=@like@between
	 */
	private String operatorCode;
	/**
	 * 操作符名称 等于@大于@大于等于@小于@小于等于@包含@介于
	 */
	private String operatorName;
	/**
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 是否显示
	 */
	@Column( columnDefinition = "int default 1") 
	private Integer isShow;
	/**
	 * 是否锁定
	 */
	@Column( columnDefinition = "int default 0") 
	private Integer isLock;
	/**
	 * 默认值
	 */
	private String defalutValue;
	

	/**
	 * 机器名（英文）
	 */
	private String name;
	/**
	 *  排序
	 */
	private Integer displayOrder;
	/**
	 * 是否常用
	 */
	private Integer isOften;
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}

	
	public Integer getIsOften() {
		return isOften;
	}
	public void setIsOften(Integer isOften) {
		this.isOften = isOften;
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

	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Integer getIsLock() {
		return isLock;
	}
	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}
	public String getDefalutValue() {
		return defalutValue;
	}
	public void setDefalutValue(String defalutValue) {
		this.defalutValue = defalutValue;
	}
	
}
