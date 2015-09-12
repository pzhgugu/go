package com.ansteel.report.excel.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.report.excel.domain.ExcelReport;
import com.ansteel.report.excel.domain.ExcelReportSQL;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel报表服务。
 */
public interface ExcelService {
	/**
	 * 保存附件,返回附件id
	 * @param file
	 * @return
	 */
	String saveAttachment(MultipartFile file,ExcelReport excelReport);
	/**
	 * 根据id删除报表数据及附件
	 * @param id
	 */
	void delectExcelReport(String id);
	/**
	 * 设置附件下载路径
	 * @param dataSet
	 * @param request
	 * @return
	 */
	UDataSet setAttPath(UDataSet dataSet, HttpServletRequest request);
	/**
	 * 设置测试路径
	 * @param dataSet
	 * @param request
	 * @return
	 */
	UDataSet setTestPath(UDataSet dataSet, HttpServletRequest request);
	/**
	 * 通过名称获取ExcelReport
	 * @param name
	 * @return
	 */
	ExcelReport getExcelReportToName(String name);
	/**
	 * 获取报表数据，及报表ExcelReportSQL对象
	 * @return
	 */
	Map<ExcelReportSQL, List> getExcelReportSqlData(ExcelReport excelReport, HttpServletRequest request, Map<String, Object> parameterMap);
	/**
	 * 获取附件路径
	 * @param attachmentId
	 * @return
	 */
	String getFilePathById(String attachmentId);

	/**
	 * 查询报表下的sql语句
	 *
	 * @param reportName 报表编码
	 * @param sqlName    sql编码
	 * @return
	 */
	String findByReportAndSql(String reportName, String sqlName);
}
