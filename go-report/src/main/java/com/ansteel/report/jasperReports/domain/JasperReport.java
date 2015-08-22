package com.ansteel.report.jasperReports.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：Jasper报表实体类。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "JasperReport")
public class JasperReport extends OperEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2870192384176490976L;

	/**
	 * 附件id
	 */
	private String attachmentId;
	
	private String attachmentPath;
	
	/**
	 * 测试参数
	 */
	private String testParameter;
	
	/**
	 * 测试路径
	 */
	@Transient
	private String testPath;
	
	/**
	 * 生成报表类型（1=EXCEL;2=PDF;3=SWF;4=HTML;）
	 */
	private Integer type;
	/**
	 * 打开模式（1=在线;2=附件;）
	 */
	private Integer openType;		

	/**
	 * day=日报;month=月报;year=年报;region=区间报表;
	 */
	private String dateType;
	
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getOpenType() {
		return openType;
	}
	public void setOpenType(Integer openType) {
		this.openType = openType;
	}
	public String getTestPath() {
		return testPath;
	}
	public void setTestPath(String testPath) {
		this.testPath = testPath;
	}
	public String getTestParameter() {
		return testParameter;
	}
	public void setTestParameter(String testParameter) {
		this.testParameter = testParameter;
	}
	/**
	 * 附件web路径
	 */
	@Transient
	private String attPath;
	public String getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	public String getAttPath() {
		return attPath;
	}
	public void setAttPath(String attPath) {
		this.attPath = attPath;
	}
	
	
}
