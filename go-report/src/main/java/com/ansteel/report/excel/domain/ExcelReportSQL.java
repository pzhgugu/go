package com.ansteel.report.excel.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel报表SQL实体。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "ExcelReport_SQL")
public class ExcelReportSQL extends OperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5993819751153475897L;

	/**
	 * sql语句
	 */
	@Lob
	private String sqlContent;
	/**
	 * 是否循环
	 */
	@Column( columnDefinition = "int default 1") 
	private Integer isLoop;
	/**
	 * 循环最大条数
	 */
	private Integer loopMaxNumber;
	/**
	 * Excle编码
	 */
	//@Lob
	private String recode;
	/**
	 * 不循环编码
	 */
	//@Lob
	private String fixedRecode;
	/**
	 * sheet名称
	 */
	private String sheetName;
	/**
	 * 循环结束单元格
	 */
	private Integer cellEnd;
	/**
	 * 循环开始单元格
	 */
	private Integer cellStart;
	/**
	 * 页眉编码
	 */
	private String headerRecode;	
	/**
	 * 页脚编码
	 */
	private String footerRecode;
	/**
	 * 是否生成连续打印报表
	 * 1为是
	 */
	private Integer isSeries;
	/**
	 * 连续打印sheet名称设置
	 * 此内容为SQL语句中的其中字段
	 */
	private String seriesSheetRecode;
	/**
	 * 合并单元格编码
	 */
	private String mergerRegionRecode;

    /**
     * 合并单元格固定编码
     */
    private String mergerRegionFixed;

    /**
     * 合并单元格跟随编码
     */
    private String mergerRegionFollow;

    public String getMergerRegionFixed() {
        return mergerRegionFixed;
    }

    public void setMergerRegionFixed(String mergerRegionFixed) {
        this.mergerRegionFixed = mergerRegionFixed;
    }

    public String getMergerRegionFollow() {
        return mergerRegionFollow;
    }

    public void setMergerRegionFollow(String mergerRegionFollow) {
        this.mergerRegionFollow = mergerRegionFollow;
    }

    @Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}	
		

	public String getMergerRegionRecode() {
		return mergerRegionRecode;
	}

	public void setMergerRegionRecode(String mergerRegionRecode) {
		this.mergerRegionRecode = mergerRegionRecode;
	}

	public Integer getIsSeries() {
		return isSeries;
	}

	public void setIsSeries(Integer isSeries) {
		this.isSeries = isSeries;
	}

	public String getSeriesSheetRecode() {
		return seriesSheetRecode;
	}

	public void setSeriesSheetRecode(String seriesSheetRecode) {
		this.seriesSheetRecode = seriesSheetRecode;
	}

	public String getHeaderRecode() {
		return headerRecode;
	}

	public void setHeaderRecode(String headerRecode) {
		this.headerRecode = headerRecode;
	}

	public String getFooterRecode() {
		return footerRecode;
	}

	public void setFooterRecode(String footerRecode) {
		this.footerRecode = footerRecode;
	}

	public String getSqlContent() {
		return sqlContent;
	}

	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}

	public Integer getIsLoop() {
		return isLoop;
	}

	public void setIsLoop(Integer isLoop) {
		this.isLoop = isLoop;
	}

	public Integer getLoopMaxNumber() {
		return loopMaxNumber;
	}

	public void setLoopMaxNumber(Integer loopMaxNumber) {
		this.loopMaxNumber = loopMaxNumber;
	}

	public String getRecode() {
		return recode;
	}

	public void setRecode(String recode) {
		this.recode = recode;
	}

	public String getFixedRecode() {
		return fixedRecode;
	}

	public void setFixedRecode(String fixedRecode) {
		this.fixedRecode = fixedRecode;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Integer getCellEnd() {
		return cellEnd;
	}

	public void setCellEnd(Integer cellEnd) {
		this.cellEnd = cellEnd;
	}

	public Integer getCellStart() {
		return cellStart;
	}

	public void setCellStart(Integer cellStart) {
		this.cellStart = cellStart;
	}

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true, fetch = FetchType.LAZY)
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
