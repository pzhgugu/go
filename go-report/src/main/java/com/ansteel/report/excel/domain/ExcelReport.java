package com.ansteel.report.excel.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel报表实体。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "ExcelReport")
public class ExcelReport extends OperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2088917057418650317L;
	
	/**
	 * 是否启用
	 */
	@Column( columnDefinition = "int default 1") 
	private Integer isStart;
	
	/**
	 * 附件id
	 */
	private String attachmentId;
	
	private String attachmentPath;
	/**
	 * 附件web路径
	 */
	@Transient
	private String attPath;
	
	/**
	 * 报表类型 day=日报;month=月报;year=年报;region=区间报表;
	 */
	private String type;
	/**
	 * 类型web路径
	 */
	@Transient
	private String typePath;
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypePath() {
		return typePath;
	}

	public void setTypePath(String typePath) {
		this.typePath = typePath;
	}

	public String getAttPath() {
		return attPath;
	}

	public void setAttPath(String attPath) {
		this.attPath = attPath;
	}

	public Integer getIsStart() {
		return isStart;
	}

	public void setIsStart(Integer isStart) {
		this.isStart = isStart;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "tree_id")
	@JsonIgnore
	private ExcelReportTree excelReportTree = new ExcelReportTree();

	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "excelReport")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<ExcelReportSQL> excelReportSQLList = new ArrayList<ExcelReportSQL>();

	public ExcelReportTree getExcelReportTree() {
		return excelReportTree;
	}

	public void setExcelReportTree(ExcelReportTree excelReportTree) {
		this.excelReportTree = excelReportTree;
	}

	public Collection<ExcelReportSQL> getExcelReportSQLList() {
		return excelReportSQLList;
	}

	public void setExcelReportSQLList(Collection<ExcelReportSQL> excelReportSQLList) {
		this.excelReportSQLList = excelReportSQLList;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "excelReport")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<ExcelReportTest> excelReportTestList = new ArrayList<ExcelReportTest>();
	
	public Collection<ExcelReportTest> getExcelReportTestList() {
		return excelReportTestList;
	}

	public void setExcelReportTestList(
			Collection<ExcelReportTest> excelReportTestList) {
		this.excelReportTestList = excelReportTestList;
	}
	
	

}
