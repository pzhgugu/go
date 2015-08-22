package com.ansteel.report.excel.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel报表测试实体。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX +  "ExcelReport_Test")
public class ExcelReportTest extends OperEntity{ 




	/**
	 * 
	 */
	private static final long serialVersionUID = 1611830489465313897L;

	/**
	 * 测试参数
	 */
	private String testParam;
	
	@Transient
	private String testLink;
	
	/**
	 * 生成报表类型（1=EXCEL;2=PDF;3=SWF;）
	 */
	private Integer type;
	/**
	 * 打开模式（1=在线;2=附件;）
	 */
	private Integer openType;
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}	
	
	public Integer getOpenType() {
		return openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTestLink() {
		return testLink;
	}

	public void setTestLink(String testLink) {
		this.testLink = testLink;
	}

	public String getTestParam() {
		return testParam;
	}

	public void setTestParam(String testParam) {
		this.testParam = testParam;
	}

	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true, 
			fetch=FetchType.LAZY)
	@JoinColumn(name = "pid", nullable = true)
	@JsonIgnore
	ExcelReport excelReport = new ExcelReport();

	public ExcelReport getExcelReport() {
		return excelReport;
	}

	public void setExcelReport(ExcelReport excelReport) {
		this.excelReport = excelReport;
	}

}
