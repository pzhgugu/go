package com.ansteel.common.dynamicmodel.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.common.prentmodel.domain.FieldsCategory;
import com.ansteel.common.prentmodel.domain.FieldsQuery;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态建模模型字段查询。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX +  "dynamic_fields_query")
public class DynamicFieldsQuery extends FieldsQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7837235808062882055L;
	
	
	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, 
			fetch=FetchType.LAZY)
	@JoinColumn(name = "fcid", nullable = true)
	@JsonIgnore
	DynamicFieldsCategory fieldsCategory=new DynamicFieldsCategory();


	public FieldsCategory getFieldsCategory() {
		return fieldsCategory;
	}
	public void setFieldsCategory(DynamicFieldsCategory fieldsCategory) {
		this.fieldsCategory = fieldsCategory;
	}
	
}
