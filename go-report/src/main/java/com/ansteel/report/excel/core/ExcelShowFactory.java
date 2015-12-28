package com.ansteel.report.excel.core;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.report.poi.utils.Excel;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：excel展现服务工厂。
 */
@Service
public class ExcelShowFactory implements IExcelShowFactory{

	private static final String PDF = "PDF";
	private static final String HTML = "HTML";
	private static final String SWF = "SWF";
	

	/**
	 * 显示excel
	 * @param excel
	 * @param rType 显示类型（excel，pdf，html）
	 * @param inline （1、为在线方式）
	 * @param outPath （输出保存路径，不输出文件这位null）
	 * @param response
	 */
	public String show(Excel excel, String rType, String inline,
			String outPath,HttpServletResponse response,String fileName) {
		IExcelShow excelShow = null;
		switch (rType) {
			case PDF:
				excelShow = ContextHolder.getSpringBean("pdfShow");
				break;
			case HTML:
				excelShow = ContextHolder.getSpringBean("htmlShow");
				break;
			case SWF:
				excelShow = ContextHolder.getSpringBean("swfShow");	
				break;
			default:
				excelShow = ContextHolder.getSpringBean("excelShow");
				break;
			}
		return excelShow.show(excel,rType,inline,outPath,response,fileName);
	}

}
