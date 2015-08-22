package com.ansteel.common.dynamicmodel.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.common.prentmodel.domain.Fields;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态建模模型字段。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX +  "dynamic_fields")
public class DynamicFields extends Fields{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6044835479715148989L;
	
	
	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, 
			fetch=FetchType.LAZY)
	@JoinColumn(name = "mid")
	@JsonIgnore
	private DynamicModels models = new DynamicModels();
	

	public DynamicModels getModels() {
		return models;
	}
	
	public void setModels(DynamicModels models) {
		this.models = models;
	}

}
