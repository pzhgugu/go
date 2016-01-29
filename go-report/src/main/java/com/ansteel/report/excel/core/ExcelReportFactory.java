package com.ansteel.report.excel.core;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.report.poi.utils.Excel;
import com.ansteel.report.excel.domain.ExcelReportSQL;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：excel报表生成工厂。
 */
public class ExcelReportFactory {
		

	public Excel getExcel(String tplPath,Map<ExcelReportSQL,List> mapExcel){
		Excel excel = ExcelMakeUtils.getExcel(tplPath);
		return getExcel(excel, mapExcel);
	}
	
	public Excel getExcel(Map<ExcelReportSQL,List> mapExcel){
		return getExcel(new Excel(), mapExcel);
	}
	
	public Excel getExcel(Excel excel,Map<ExcelReportSQL,List> mapExcel){
		SeriesExcel seriesExcel=null;
		//先执行固定列数据填充
		for(Entry<ExcelReportSQL,List> entry:mapExcel.entrySet()){	
			List list = entry.getValue();
			if(list.size()>0){
				ExcelReportSQL excelReportSQL=entry.getKey();
				String sheetName = ExcelMakeUtils.getSheetName(excelReportSQL.getSheetName());
				Sheet sheet=excel.getSheet(sheetName);
				if(excelReportSQL.getIsSeries()!=null&&excelReportSQL.getIsSeries()==1){
					if(seriesExcel==null){
						seriesExcel = new SeriesExcel();
						seriesExcel.setExcelReportSQL(excelReportSQL);
						seriesExcel.setList(list);
						seriesExcel.setSheet(sheet);
						seriesExcel.setExcel(excel);
					}
				}
				ExcelMakeUtils.setFixedSQLData(sheet,excelReportSQL,list);
				ExcelMakeUtils.setHeader(sheet,excelReportSQL,list);
				ExcelMakeUtils.setFooter(sheet,excelReportSQL,list);
			}
		}
		
		if(seriesExcel==null){
			BaseExcelReport excelReport = new BaseExcelReport();
			Excel exc = excelReport.getExcel(excel, mapExcel);
			this.evaluateAll(exc);
			return exc;
		}else{			
			SeriesExcelReport seriesExcelReport = new SeriesExcelReport();
			Excel exc =  seriesExcelReport.getExcel(seriesExcel);
			this.evaluateAll(exc);
			return exc;
		}
		
		
	}

	/**
	 * 执行公式
	 * @param exc
	 */
	private void evaluateAll(Excel exc) {
		try {
			CreationHelper helper = exc.getWorkbook().getCreationHelper();
			FormulaEvaluator evaluator = helper.createFormulaEvaluator();
			evaluator.evaluateAll();
		}catch (Exception e){

		}
	}
}
