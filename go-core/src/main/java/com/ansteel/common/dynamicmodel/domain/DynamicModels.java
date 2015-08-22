package com.ansteel.common.dynamicmodel.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.TreeEntity;
import com.ansteel.common.prentmodel.domain.Fields;
import com.ansteel.common.prentmodel.domain.FieldsCategory;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态建模模型。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX +  "dynamic_models")
public class DynamicModels extends TreeEntity<DynamicModels>{

	private static final long serialVersionUID = -8349403529456317667L;
	/**
	 * 模型描述
	 */
	@Column(length=4000) 
	private String scription;
	/**
	 * 模式类型（-1为目录，0为Javabean，2为动态建模模型，3SQL类型）
	 */
	private Integer modelType;
	
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}	
	
	@OneToMany(mappedBy = "models",  cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<DynamicFieldsCategory> fieldsCategory= new ArrayList<DynamicFieldsCategory>();	
	
	public Collection<DynamicFieldsCategory> getFieldsCategory() {
		return fieldsCategory;
	}
	public void setFieldsCategory(Collection<DynamicFieldsCategory> fieldsCategory) {
		this.fieldsCategory = fieldsCategory;
	}
	@OneToMany(mappedBy = "models", cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<DynamicFields> fields= new ArrayList<DynamicFields>();	
	
	public Collection<DynamicFields> getFields() {
		return fields;
	}
	public void setFields(Collection<DynamicFields> fields) {
		this.fields = fields;
	}

	public String getScription() {
		return scription;
	}
	public void setScription(String scription) {
		this.scription = scription;
	}
	public Integer getModelType() {
		return modelType;
	}
	public void setModelType(Integer modelType) {
		this.modelType = modelType;
	}	
}
