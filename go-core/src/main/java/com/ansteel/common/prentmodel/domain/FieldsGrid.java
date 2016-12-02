package com.ansteel.common.prentmodel.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模型字段表格父类。  
 */
@MappedSuperclass
public class FieldsGrid extends BaseEntity{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1574609000346150703L;
	/**
	 * 行显示宽度
	 */
	private Integer initWidths;
	/**
	 * 行对齐方式
	 */
	private String colAlign;
	/**
	 * 行类型"tree,ro,ro"
	 */
	private String colTypes;
	/**
	 * 行字段类型("str,str,str")
	 */
	private String colSorting;
	
	/**
	 * 是否显示
	 */
	@Column( columnDefinition = "int default 1") 
	private Integer isShow;
	

	/**
	 * 机器名（英文）
	 */

	@NotEmpty(message = "{Valid.NotEmpty}")
	private String name;
	/**
	 *  排序
	 */
	private Integer displayOrder;
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;

	//是否选计
	private Boolean isSelectSum;
	//是否合计
	private Boolean isTotal;
	//是否总计
	private Boolean isGrandTotal;

	public Boolean getIsSelectSum() {
		return isSelectSum;
	}

	public void setIsSelectSum(Boolean selectSum) {
		isSelectSum = selectSum;
	}

	public Boolean getIsTotal() {
		return isTotal;
	}

	public void setIsTotal(Boolean total) {
		isTotal = total;
	}

	public Boolean getIsGrandTotal() {
		return isGrandTotal;
	}

	public void setIsGrandTotal(Boolean grandTotal) {
		isGrandTotal = grandTotal;
	}
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
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
	
	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getInitWidths() {
		return initWidths;
	}

	public void setInitWidths(Integer initWidths) {
		this.initWidths = initWidths;
	}

	public String getColAlign() {
		return colAlign;
	}

	public void setColAlign(String colAlign) {
		this.colAlign = colAlign;
	}

	public String getColTypes() {
		return colTypes;
	}

	public void setColTypes(String colTypes) {
		this.colTypes = colTypes;
	}

	public String getColSorting() {
		return colSorting;
	}

	public void setColSorting(String colSorting) {
		this.colSorting = colSorting;
	}
	
	
}
