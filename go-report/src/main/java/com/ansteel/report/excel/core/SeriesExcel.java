package com.ansteel.report.excel.core;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

import com.ansteel.report.poi.utils.Excel;
import com.ansteel.report.excel.domain.ExcelReportSQL;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-09
 * 修 改 人：
 * 修改日 期：
 * 描   述：连续打印报表类。
 */
public class SeriesExcel {
	
	private ExcelReportSQL excelReportSQL;
	
	private Sheet sheet;
	
	private List list;
	
	private Excel excel;
	

	public Excel getExcel() {
		return excel;
	}

	public void setExcel(Excel excel) {
		this.excel = excel;
	}

	public ExcelReportSQL getExcelReportSQL() {
		return excelReportSQL;
	}

	public void setExcelReportSQL(ExcelReportSQL excelReportSQL) {
		this.excelReportSQL = excelReportSQL;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	
	
}
