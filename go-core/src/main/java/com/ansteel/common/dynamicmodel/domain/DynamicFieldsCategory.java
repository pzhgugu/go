package com.ansteel.common.dynamicmodel.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.common.prentmodel.domain.FieldsCategory;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态建模模型字段分类。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX +  "dynamic_fields_category")
public class DynamicFieldsCategory extends FieldsCategory{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7401564153040472481L;

	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, 
			fetch=FetchType.LAZY)
	@JoinColumn(name = "mid")
	@JsonIgnore
	private DynamicModels models = new DynamicModels();
	
	@OneToMany(cascade = CascadeType.ALL, fetch =  FetchType.EAGER, mappedBy = "fieldsCategory")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<DynamicFieldsQuery> fieldsQuery=new ArrayList<DynamicFieldsQuery>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch =  FetchType.EAGER, mappedBy = "fieldsCategory")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<DynamicFieldsGrid> fieldsGrid=new ArrayList<DynamicFieldsGrid>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch =  FetchType.EAGER, mappedBy = "fieldsCategory")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<DynamicFieldsForm> fieldsForm=new ArrayList<DynamicFieldsForm>();


	public Collection<DynamicFieldsQuery> getFieldsQuery() {
		return fieldsQuery;
	}

	public void setFieldsQuery(Collection<DynamicFieldsQuery> fieldsQuery) {
		this.fieldsQuery = fieldsQuery;
	}

	public Collection<DynamicFieldsGrid> getFieldsGrid() {
		return fieldsGrid;
	}

	public void setFieldsGrid(Collection<DynamicFieldsGrid> fieldsGrid) {
		this.fieldsGrid = fieldsGrid;
	}

	public Collection<DynamicFieldsForm> getFieldsForm() {
		return fieldsForm;
	}

	public void setFieldsForm(Collection<DynamicFieldsForm> fieldsForm) {
		this.fieldsForm = fieldsForm;
	}

	public DynamicModels getModels() {
		return models;
	}

	public void setModels(DynamicModels models) {
		this.models = models;
	}

	
}
