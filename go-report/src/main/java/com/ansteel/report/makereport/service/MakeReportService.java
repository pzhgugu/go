package com.ansteel.report.makereport.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.report.poi.utils.Excel;
import com.ansteel.report.reportlist.domain.ReportMapped;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-12
 * 修 改 人：
 * 修改日 期：
 * 描   述：报表生成服务。
 */
public interface MakeReportService {

	/**
	 * 获取Excel
	 * @param name
	 * @param request
	 * @return
	 */
	Excel getExcel(String name, HttpServletRequest request, Map<String, Object> parameterMap);
	
	/**
	 * 获取Excel
	 * @param listMap
	 * @param modelName
	 * @return
	 */
	Excel getExcel(List listMap, String modelName);

	/**
	 * 报表展示
	 * @param name
	 * @param rType
	 * @param inline
	 * @param outPath
	 * @param request
	 * @param response
	 * @return
	 */
	String show(String name, String rType, String inline, String outPath,
			HttpServletRequest request, HttpServletResponse response,String fileName);

	/**
	 * 报表展示
	 *
	 * @param name
	 * @param rType
	 * @param inline
	 * @param outPath
	 * @param request
	 * @param response
	 * @return
	 */
	String show(String name, String rType, String inline, String outPath, Map<String, Object> parameterMap,
				HttpServletRequest request, HttpServletResponse response,String fileName);

	/**
	 * 保存报表
	 * @param type
	 * @param rType
	 * @param request
	 * @return
	 */
	String saveReport(String type,String rType, HttpServletRequest request);

	/**
	 * 报表展示
	 * @param list
	 * @param nameMap
	 * @param rType
	 * @param inline
	 * @param request
	 * @param response
	 * @return
	 */
	String show(List<Map> list, Map<String, String> nameMap, String rType,
			String inline, HttpServletRequest request,
			HttpServletResponse response,String fileName);

	/**
	 * 获取Excel
	 * @param list
	 * @param nameMap
	 * @return
	 */
	Excel getExcel(List<Map> list, Map<String, String> nameMap);

	/**
	 * 报表展示
	 * @param modelName
	 * @param listMap
	 * @param type
	 * @param inline
	 * @param request
	 * @param response
	 * @return
	 */
	String show(String modelName, List listMap, String type, String inline,
			HttpServletRequest request, HttpServletResponse response,String fileName);

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
