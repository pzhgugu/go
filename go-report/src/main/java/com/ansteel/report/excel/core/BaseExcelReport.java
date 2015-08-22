package com.ansteel.report.excel.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.report.poi.utils.Excel;
import com.ansteel.report.excel.domain.ExcelReportSQL;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-07
 * 修 改 人：
 * 修改日 期：
 * 描   述：普通Excel报表。 
 */
public class BaseExcelReport {

	Logger logger=Logger.getLogger(BaseExcelReport.class);
			

	public Excel getExcel(Excel excel, Map<ExcelReportSQL, List> mapExcel) {
		for(Entry<ExcelReportSQL,List> entry:mapExcel.entrySet()){
			List list = entry.getValue();
			if(list.size()>0){
				ExcelReportSQL excelReportSQL=entry.getKey();
				String sheetName = ExcelMakeUtils.getSheetName(excelReportSQL.getSheetName());
				Sheet sheet=excel.getSheet(sheetName);
				this.setSQLData(sheet,excelReportSQL,list);
			}else{
				logger.error(entry.getKey().getAlias()+",没有记录！");
			}
		}
		
		return excel;
	}

	/**
	 * 数据填充
	 * @param excel
	 * @param excelReportSQL
	 * @param list
	 */
	public void setSQLData(Sheet sheet,ExcelReportSQL excelReportSQL, List list) {
			/**
			 * 最大行数
			 */
			int maxNumber = excelReportSQL.getLoopMaxNumber()== null ? 0 : excelReportSQL.getLoopMaxNumber();
			/**
			 * 当前记录行数
			 */
			int currentNum=list.size();
			if(maxNumber>0){
				if(list.size()>maxNumber){
					currentNum=maxNumber;
				}
			}
			
			/**
			 * 是否循环
			 */
			Integer isLoop=excelReportSQL.getIsLoop() == null ? 0 : excelReportSQL.getIsLoop() ;
			
			if(isLoop==1){
				Integer cellend = excelReportSQL.getCellEnd() == null ? 0 : excelReportSQL
						.getCellEnd();
				Integer cellStatr = excelReportSQL.getCellStart() == null ? 0
						: excelReportSQL.getCellStart();
				if(cellend>0){
					Integer num = cellend - cellStatr+1;
			
					// excel模板的结束换到开始行之间插入行
					if (currentNum > num) {
						Excel.insertRow(sheet,cellStatr, currentNum - num);
					}
				}
			}
			

			
			// 字段对照excel单元格
			Map<String, String> mapping = StringUtils
							.getOptionsSplit(excelReportSQL.getRecode());
			
			String mergerRegionRecode = excelReportSQL.getMergerRegionRecode();
			
			Map<String,MergerRegion> mergerMap = new HashMap<String, MergerRegion>();
			
			if(StringUtils.hasText(mergerRegionRecode)){
				String[] mrArray = mergerRegionRecode.split(";");
				for(String s:mrArray){
					if(mapping.containsKey(s)){
						MergerRegion mergerRegion = new MergerRegion();
						mergerRegion.setMapping(mapping.get(s));
						mergerMap.put(s, mergerRegion);
					}else{
						throw new PageException("合并单元格映射错误！");
					}
				}
			}
			
			for (int i = 0; i < currentNum; i++) {
				Map dataMap = (Map) list.get(i);
				ExcelMakeUtils.mappingCellValue(i,mapping,dataMap,sheet);
				if(mergerMap.size()>0){
					ExcelMakeUtils.mergerRegion(i,currentNum-1,dataMap,mergerMap,sheet);
				}
			}
			sheet.setForceFormulaRecalculation(true);
		
	}
}
