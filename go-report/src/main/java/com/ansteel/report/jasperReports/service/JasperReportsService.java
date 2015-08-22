package com.ansteel.report.jasperReports.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.report.jasperReports.domain.JasperReport;
import com.ansteel.report.reportlist.domain.ReportMapped;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：Jasper报表服务。
 */
public interface JasperReportsService {

	/**
	 * 保存附件
	 * @param file
	 * @param report
	 * @return
	 */
	String saveAttachment(MultipartFile file, JasperReport report);

	/**
	 * 设置附件路径
	 * @param dataSet
	 * @param request
	 * @return
	 */
	UDataSet setAttPath(UDataSet dataSet, HttpServletRequest request);
	
	/**
	 * 展示报表
	 * @param name
	 * @param rType
	 * @param inline
	 * @param outPath
	 * @param parameters
	 * @param response
	 * @return
	 */
	String show(String name, String rType, String inline, String outPath,
			Map parameters, HttpServletResponse response);

	/**
	 * 根据名称查询报表
	 * @param name
	 * @return
	 */
	JasperReport findOneByName(String name);

	/**
	 * 保存报表
	 * @param jasperReport
	 * @param rType
	 * @param parameters
	 * @return
	 */
	String saveReport(JasperReport jasperReport, String rType,
			Map<String, Object> parameters);

	/**
	 * 生成报表
	 * @param reportMapped
	 * @param type
	 * @param parameters
	 * @param response
	 * @return
	 */
	String makeReport(ReportMapped reportMapped, String type, Map parameters,
			HttpServletResponse response);


}
