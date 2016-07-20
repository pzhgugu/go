package com.ansteel.report.excelin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.report.poi.utils.Excel;
import com.ansteel.report.excelin.domain.ExcelIn;
import com.ansteel.report.excelin.repository.ExcelInRepository;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel导入实现。
 */
@Service
@Transactional
public class ExcelInServiceBean implements ExcelInService {
	
	@Autowired
	ExcelInRepository excelInRepository;

	@Override
	public ExcelIn getExcelInByName(String name) {
		return excelInRepository.findOneByName(name);
	}

	@Override
	public List inExcel(String modelName, String inName,
			MultipartFile file) {
		Excel excel =null;
		try {
			excel = new Excel(file.getInputStream());
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
			throw new PageException("excel错误！");
		}
		ExcelIn excelIn = this.getExcelInByName(inName);
		Assert.notNull(excelIn, "没有找到输入excel配置！");
		Map<String, Integer[]> mapping = getOptionsSplit(excelIn.getRecode());
		Sheet sheet = excel.getSheet(excelIn.getSheetName());
		Integer isLoop = excelIn.getIsLoop();
		if(isLoop!=null&&isLoop==1){
			Integer oneValue=0;
			for(Entry<String, Integer[]> entry:mapping.entrySet()){
				oneValue=entry.getValue()[0];
				break;
			}
			
			List<Map<String,String>> valueList = new ArrayList<>();
		    int rowEnd = sheet.getLastRowNum();
		    for (int rowNum = oneValue; rowNum <=rowEnd; rowNum++) {
		    	Map<String, String> valueMap = new HashMap<String, String>();
		    	for(Entry<String, Integer[]> entry:mapping.entrySet()){
		    		if(entry.getKey().equals("id")||entry.getKey().equals("ID")){
		    			continue;
		    		}
		    		Row row = sheet.getRow(rowNum);
		    		Cell cell = row.getCell(entry.getValue()[1]);
		    		if(cell!=null){
			    		String cellValue=this.getCellValue(cell);
			    		valueMap.put(entry.getKey(), cellValue);
		    		}
				}
		    	valueList.add(valueMap);
		    }

			return valueList;
		}else{
			List<Map<String,String>> valueList = new ArrayList<>();
	    	Map<String, String> valueMap = new HashMap<String, String>();
	    	for(Entry<String, Integer[]> entry:mapping.entrySet()){
	    		if(entry.getKey().equals("id")||entry.getKey().equals("ID")){
	    			continue;
	    		}
	    		Row row = sheet.getRow(entry.getValue()[0]);
	    		Cell cell = row.getCell(entry.getValue()[1]);
	    		String cellValue=this.getCellValue(cell);
	    		valueMap.put(entry.getKey(), cellValue);
			}
	    	valueList.add(valueMap);
			return valueList;
		}
	}
	
	public static Map<String, Integer[]> getOptionsSplit(String recode) {
		Map<String, Integer[]> map = new LinkedHashMap<String, Integer[]>();
		if(StringUtils.hasText(recode)){
			String[] values = recode.split(";");
			for(String v:values){
				String[] vs = v.split("=");
				if(vs.length==2){
					String key = vs[0].trim();
					if(StringUtils.hasText(key)){
						map.put(key,Excel.getCellFigure(vs[1].trim()));
					}
				}
			}
		}
		return map;
	}
	
	private String getCellValue(Cell cell) {
		/*Object value=null;
		switch (cell.getCellType()) {
	        case Cell.CELL_TYPE_STRING:
	        	value=cell.getRichStringCellValue().getString();
	            break;
	        case Cell.CELL_TYPE_NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {
	            	value=cell.getDateCellValue();
	            } else {
	            	value=cell.getNumericCellValue();
	            }
	            break;
	        case Cell.CELL_TYPE_BOOLEAN:
	        	value=cell.getBooleanCellValue();
	            break;
	        case Cell.CELL_TYPE_FORMULA:
	        	value=cell.getCellFormula();
	            break;
	        default:
	            System.out.println();
		}

		return value+"";*/
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		return cell.getRichStringCellValue().getString();
	}

}
