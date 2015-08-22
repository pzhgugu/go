package com.ansteel.report.excel.core;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.Assert;

import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.report.poi.utils.Excel;
import com.ansteel.report.poi.utils.PoiSheetCopy;
import com.ansteel.report.excel.domain.ExcelReportSQL;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-09
 * 修 改 人：
 * 修改日 期：
 * 描   述：生成连续打印报表。
 */
public class SeriesExcelReport {

	private static final String  DEFAULT_SERIES_NAME = "S_E_";

	public Excel getExcel(SeriesExcel seriesExcel) {
		ExcelReportSQL excelReportSQL = seriesExcel.getExcelReportSQL();
		Sheet sheet = seriesExcel.getSheet();
		List list = seriesExcel.getList();
		Excel excel = seriesExcel.getExcel();
		String seriesSheetName = excelReportSQL.getSeriesSheetRecode();
		/**
		 * 最大行数
		 */
		int maxNumber = excelReportSQL.getLoopMaxNumber() == null ? 0
				: excelReportSQL.getLoopMaxNumber();
		/**
		 * 当前记录行数
		 */
		int currentNum = list.size();
		if (maxNumber > 0) {
			if (list.size() > maxNumber) {
				currentNum = maxNumber;
			}
		}

		// 字段对照excel单元格
		Map<String, String> mapping = StringUtils
				.getOptionsSplitUpper(excelReportSQL.getRecode());

		for (int i = 0; i < currentNum; i++) {
			Map dataMap = (Map) list.get(i);
			String sheetName=this.getSeriesSheetName(seriesSheetName,dataMap,i);
			Sheet sSheet = excel.getSheet(sheetName);
			try {
				PoiSheetCopy.copySheet(sheet, sSheet,excel.getWorkbook(),excel.getWorkbook());
			} catch (Exception e) {
				e.printStackTrace();
				throw new PageException(e.getMessage());
			}
			ExcelMakeUtils.mappingCellValue(mapping,dataMap,sSheet);
			sSheet.setForceFormulaRecalculation(true);
		}
		Workbook wb = excel.getWorkbook();
		wb.removeSheetAt(wb.getSheetIndex(sheet));
		return excel;
	}

	private String getSeriesSheetName(String seriesSheetName, Map dataMap,int i) {
		if(StringUtils.hasText(seriesSheetName)){
			if(dataMap.containsKey(seriesSheetName)){
				return (String)dataMap.get(seriesSheetName);
			}else{
				throw new PageException("连续打印名称映射没有这个参数！");
			}
		}else{
			return DEFAULT_SERIES_NAME+i;
		}
	}

	

}
