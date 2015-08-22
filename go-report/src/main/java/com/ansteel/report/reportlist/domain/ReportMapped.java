package com.ansteel.report.reportlist.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-12
 * 修 改 人：
 * 修改日 期：
 * 描   述：报表映射实体类。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "report_mapped")
public class ReportMapped extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1412939507620558047L;
	/**
	 * 报表附件ID
	 */
	private String attPath;
	/**
	 * 报表类型
	 */
	private String type;
	/**
	 * 报表日期
	 */
	private String rDate;
	
	/**
	 *  描述
	 */
	private String scription;
	
	@Transient
	private String pdf;
	
	@Transient
	private String excel;
	
	@Transient
	private String html;
	
	@Transient
	private String swf;
	
	
	
	public String getPdf() {
		return pdf;
	}
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	public String getExcel() {
		return excel;
	}
	public void setExcel(String excel) {
		this.excel = excel;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getSwf() {
		return swf;
	}
	public void setSwf(String swf) {
		this.swf = swf;
	}
	public String getScription() {
		return scription;
	}
	public void setScription(String scription) {
		this.scription = scription;
	}
	public String getAttPath() {
		return attPath;
	}
	public void setAttPath(String attPath) {
		this.attPath = attPath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getrDate() {
		return rDate;
	}
	public void setrDate(String rDate) {
		this.rDate = rDate;
	}
	
	
}
