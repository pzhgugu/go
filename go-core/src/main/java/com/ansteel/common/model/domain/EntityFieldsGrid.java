package com.ansteel.common.model.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.common.prentmodel.domain.FieldsGrid;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体模型字段表格实体类。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX +  "fields_grid")
public class EntityFieldsGrid extends FieldsGrid{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1574609000346150703L;
	
	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, 
			fetch=FetchType.EAGER)
	@JoinColumn(name = "fcid", nullable = true)
	@JsonIgnore
	EntityFieldsCategory fieldsCategory=new EntityFieldsCategory();


	public EntityFieldsCategory getFieldsCategory() {
		return fieldsCategory;
	}
	public void setFieldsCategory(EntityFieldsCategory fieldsCategory) {
		this.fieldsCategory = fieldsCategory;
	}
}
