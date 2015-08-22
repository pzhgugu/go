package com.ansteel.modules.excelin.service;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.common.model.domain.Models;
import com.ansteel.core.context.DefaultEditors;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.service.BaseService;
import com.ansteel.core.utils.SpringBaseTest;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.report.poi.utils.Excel;
import com.ansteel.common.dynamicmodel.service.DynamicModelsService;
import com.ansteel.common.model.service.ModelService;
import com.ansteel.report.excelin.domain.ExcelIn;
import com.ansteel.report.excelin.service.ExcelInService;

public class ExcelInControllerTest   extends SpringBaseTest {
	
	@Autowired
	ExcelInService excelInService;
	
	@Autowired
	DynamicModelsService dynamicModelsService;
	
	@Autowired
	ModelService modelService;
	
	@Autowired
	BaseService globalBaseService;
	
	@Test
	@Ignore
	public void testEntityModels() throws InvalidFormatException, IOException {
		InputStream inp = new FileInputStream("d:/test/poi/in2.xls");
		Excel excel = new Excel(inp);
		ExcelIn excelIn = excelInService.getExcelInByName("entityInTest");
		Assert.notNull(excelIn, "没有找到输入excel配置！");
		Class clazz = ExcelIn.class;
		
		//Models model = modelService.getClassToModel(clazz.getName());
		
		Map<String, Integer[]> mapping = getOptionsSplit(excelIn.getRecode());
		Sheet sheet = excel.getSheet(excelIn.getSheetName());
		Integer isLoop = excelIn.getIsLoop();
		if(isLoop!=null&&isLoop==1){
			Integer oneValue=0;
			for(Entry<String, Integer[]> entry:mapping.entrySet()){
				oneValue=entry.getValue()[0];
				break;
			}
			List<BaseEntity> list = new ArrayList<BaseEntity>();
		    int rowEnd = sheet.getLastRowNum();
		    for (int rowNum = oneValue; rowNum<= rowEnd; rowNum++) {
		    	BaseEntity object=null;
				try {
					object = (BaseEntity) clazz.newInstance();
				} catch (Exception e) {
					continue;
				}
		    	for(Entry<String, Integer[]> entry:mapping.entrySet()){
		    		if(entry.getKey().equals("id")||entry.getKey().equals("ID")){
		    			continue;
		    		}
		    		Row row = sheet.getRow(rowNum);
		    		Cell cell = row.getCell(entry.getValue()[1]);
		    		String cellValue=this.getCellValue(cell);
		    		Field field = ReflectionUtils.findField(clazz, entry.getKey());
		    		if(field!=null){
		    			field.setAccessible(true);
						try {
							field.set(object, new DefaultEditors().getValue(field.getType(), cellValue));
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
				}
		    	list.add(object);
		    }
		    globalBaseService.save(list);
		}
	}

	/**
	 * 动态模型导入测试
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@Test
	@Ignore
	public void testDynamicModels() throws InvalidFormatException, IOException {
		InputStream inp = new FileInputStream("d:/test/poi/in.xls");
		Excel excel = new Excel(inp);
		ExcelIn excelIn = excelInService.getExcelInByName("telTest");
		Assert.notNull(excelIn, "没有找到输入excel配置！");
		
		////DynamicModels dynamicModels = dynamicModelsService.getDynamicModels("testTel");
		//Assert.notNull(dynamicModels, "动态模型中没有testTel！");
		
		
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
			//int rowStart = sheet.getFirstRowNum();//Math.min(0, sheet.getFirstRowNum())
		    int rowEnd = sheet.getLastRowNum();//Math.max(1400, sheet.getLastRowNum());
		    for (int rowNum = oneValue; rowNum < rowEnd; rowNum++) {
		    	Map<String, String> valueMap = new HashMap<String, String>();
		    	for(Entry<String, Integer[]> entry:mapping.entrySet()){
		    		if(entry.getKey().equals("id")||entry.getKey().equals("ID")){
		    			continue;
		    		}
		    		Row row = sheet.getRow(rowNum);
		    		Cell cell = row.getCell(entry.getValue()[1]);
		    		String cellValue=this.getCellValue(cell);
		    		valueMap.put(entry.getKey(), cellValue);
				}
		    	valueList.add(valueMap);
		    }

			dynamicModelsService.save("testTel", valueList);
		}

	}
	
	private String getCellValue(Cell cell) {
		Object value=null;
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

		return value+"";
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

}
