package com.ansteel.core.domain;

import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：业务信息实体，包含编码、中文名称、排序。 
 */

@MappedSuperclass
public class OperEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9118708867159019457L;


	public OperEntity() {
		super();
	}
	/**
	 * 机器名（英文）
	 */

	@NotEmpty
	private String name;
	/**
	 * 中文名称
	 */
	private String alias;
	/**
	 *  排序
	 */
	private Integer displayOrder;
	
	
    public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
