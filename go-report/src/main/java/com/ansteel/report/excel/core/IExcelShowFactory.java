package com.ansteel.report.excel.core;

import javax.servlet.http.HttpServletResponse;

import com.ansteel.report.poi.utils.Excel;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：excel报表展示服务工厂。
 */
public interface IExcelShowFactory {

	/**
	 * Excel报表展示
	 * @param excel
	 * @param rType
	 * @param inline
	 * @param outPath
	 * @param response
	 * @return
	 */
	String show(Excel excel, String rType, String inline,
			String outPath,HttpServletResponse response,String fileName);
}
