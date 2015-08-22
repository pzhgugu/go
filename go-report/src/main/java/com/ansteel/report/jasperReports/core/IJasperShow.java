package com.ansteel.report.jasperReports.core;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.ansteel.report.poi.utils.Excel;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Jasper报表以显示接口。
 */
public interface IJasperShow {

	/**
	 * 报表展示
	 * @param sourceFileName
	 * @param outPath
	 * @param parameters
	 * @param response
	 * @param connection
	 * @return
	 */
	String show(String sourceFileName, String outPath,
			Map<String, Object> parameters, HttpServletResponse response,Connection connection);

}
