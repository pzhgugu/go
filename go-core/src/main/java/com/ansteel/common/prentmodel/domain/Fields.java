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
import com.ansteel.core.domain.OperEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模型字段父类。  
 */
@MappedSuperclass
public class Fields extends OperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3620762794585507346L;
	/**
	 *  字段描述
	 */
	@Column(length = 4000)
	private String scription;
	/**
	 * 字段类型（此为Java数据类型）
	 */
	private String fieldType;
	/**
	 * 字段长度
	 */
	private Integer fieldLength;

	/**
	 * precision属性和scale属性表示精度
	 *  数值的总长度
	 */
	private Integer fieldPrecision;
	/**
	 * 小数点所占的位数
	 */
	private Integer fieldScale;
	/**
	 * 字段是否唯一
	 */
	private Integer fieldUnique;
	/**
	 * 字段默认值
	 */
	private String fieldDefault;
	/**
	 * 字段是否可为空
	 */
	private Integer fieldNullable;

	/**
	 *  类型全路径class
	 */
	private String typeClass;
	/**
	 * 关联关系
	 */
	private String relation;
	/**
	 * 
	 */
	private String relationFetch;
	/**
	 * 
	 */
	private String relationMappedBy;
	/**
	 * 
	 */
	private String relationCascade;
	/**
	 * 
	 */
	private String relationOrderBy;
	/**
	 * 是否虚拟字段
	 */
	private Integer isInvented;
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}
	
	public String getScription() {
		return scription;
	}
	public void setScription(String scription) {
		this.scription = scription;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public Integer getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(Integer fieldLength) {
		this.fieldLength = fieldLength;
	}
	public Integer getFieldPrecision() {
		return fieldPrecision;
	}
	public void setFieldPrecision(Integer fieldPrecision) {
		this.fieldPrecision = fieldPrecision;
	}
	public Integer getFieldScale() {
		return fieldScale;
	}
	public void setFieldScale(Integer fieldScale) {
		this.fieldScale = fieldScale;
	}
	public Integer getFieldUnique() {
		return fieldUnique;
	}
	public void setFieldUnique(Integer fieldUnique) {
		this.fieldUnique = fieldUnique;
	}
	public String getFieldDefault() {
		return fieldDefault;
	}
	public void setFieldDefault(String fieldDefault) {
		this.fieldDefault = fieldDefault;
	}
	public Integer getFieldNullable() {
		return fieldNullable;
	}
	public void setFieldNullable(Integer fieldNullable) {
		this.fieldNullable = fieldNullable;
	}

	public String getTypeClass() {
		return typeClass;
	}
	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getRelationFetch() {
		return relationFetch;
	}
	public void setRelationFetch(String relationFetch) {
		this.relationFetch = relationFetch;
	}
	public String getRelationMappedBy() {
		return relationMappedBy;
	}
	public void setRelationMappedBy(String relationMappedBy) {
		this.relationMappedBy = relationMappedBy;
	}
	public String getRelationCascade() {
		return relationCascade;
	}
	public void setRelationCascade(String relationCascade) {
		this.relationCascade = relationCascade;
	}
	public String getRelationOrderBy() {
		return relationOrderBy;
	}
	public void setRelationOrderBy(String relationOrderBy) {
		this.relationOrderBy = relationOrderBy;
	}
	public Integer getIsInvented() {
		return isInvented;
	}
	public void setIsInvented(Integer isInvented) {
		this.isInvented = isInvented;
	}
	
	

}
