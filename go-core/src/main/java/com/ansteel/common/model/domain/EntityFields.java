package com.ansteel.common.model.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;
import com.ansteel.common.prentmodel.domain.Fields;


/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体模型字段实体类。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX +  "fields")
public class EntityFields extends Fields{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3620762794585507346L;
	
	
	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, 
			fetch=FetchType.LAZY)
	@JoinColumn(name = "mid")
	@JsonIgnore
	private Models models = new Models();
	

	public Models getModels() {
		return models;
	}
	
	public void setModels(Models models) {
		this.models = models;
	}
}
