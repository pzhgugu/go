package com.ansteel.report.excel.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.TreeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel报表树实体。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX +  "ExcelReport_Tree")
public class ExcelReportTree extends TreeEntity<ExcelReportTree> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1628830489465313897L;
	/**
	 * 描述
	 */
	@Column(length=4000) 
	private String scription;
	
	@OneToMany(mappedBy = "excelReportTree",  cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<ExcelReport> excelReportList = new ArrayList<ExcelReport>();
	
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

	public Collection<ExcelReport> getExcelReportList() {
		return excelReportList;
	}

	public void setExcelReportList(Collection<ExcelReport> excelReportList) {
		this.excelReportList = excelReportList;
	}
	
	

}
