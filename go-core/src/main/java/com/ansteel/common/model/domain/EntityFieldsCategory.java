package com.ansteel.common.model.domain;

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
import com.ansteel.core.domain.OperEntity;
import com.ansteel.common.prentmodel.domain.FieldsCategory;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体模型字段分类实体类。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX +  "fields_category")
public class EntityFieldsCategory extends FieldsCategory{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6211561189394776618L;
	

	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, 
			fetch=FetchType.LAZY)
	@JoinColumn(name = "mid")
	@JsonIgnore
	private Models models = new Models();
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "fieldsCategory")
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<EntityFieldsQuery> fieldsQuery=new ArrayList<EntityFieldsQuery>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch =  FetchType.LAZY, mappedBy = "fieldsCategory")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<EntityFieldsGrid> fieldsGrid=new ArrayList<EntityFieldsGrid>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch =  FetchType.LAZY, mappedBy = "fieldsCategory")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<EntityFieldsForm> fieldsForm=new ArrayList<EntityFieldsForm>();


	public Collection<EntityFieldsQuery> getFieldsQuery() {
		return fieldsQuery;
	}

	public void setFieldsQuery(Collection<EntityFieldsQuery> fieldsQuery) {
		this.fieldsQuery = fieldsQuery;
	}

	public Collection<EntityFieldsGrid> getFieldsGrid() {
		return fieldsGrid;
	}

	public void setFieldsGrid(Collection<EntityFieldsGrid> fieldsGrid) {
		this.fieldsGrid = fieldsGrid;
	}

	public Collection<EntityFieldsForm> getFieldsForm() {
		return fieldsForm;
	}

	public void setFieldsForm(Collection<EntityFieldsForm> fieldsForm) {
		this.fieldsForm = fieldsForm;
	}

	public Models getModels() {
		return models;
	}

	public void setModels(Models models) {
		this.models = models;
	}
	
}
