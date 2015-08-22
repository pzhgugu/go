package com.ansteel.modules.poi.utils;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.RegexUtil;
import com.ansteel.report.poi.utils.Excel;

public class ExcelTest {

	@Test
	public void testExcel() {
		try {
			Excel excel = new Excel("d:/test/poi/Book1.xlsx");
			Sheet sheet = excel.getSheet("Sheet1");
			Cell c =excel.getCell(sheet,1,0);

			excel.setCellValue(c,"设置xlsx成功！");

			excel.write("d:/test/poi/Book2"+"."+excel.getType());
	
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testExcelFooter() {
		try {
			Excel excel = new Excel("d:/test/poi/Book1.xlsx");
			Sheet sheet = excel.getSheet("Sheet1");
			Footer footer = sheet.getFooter();
			Header header = sheet.getHeader();
			System.out.println(footer.getLeft());
			System.out.println(footer.getCenter());
			System.out.println(footer.getRight());
			
			System.out.println(header.getLeft());
			System.out.println(header.getCenter());
			System.out.println(header.getRight());

			footer.setRight( "Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages() );

				
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testExcel2() {
		try {
			Excel excel = new Excel("d:/test/poi/w1.xls");
			
			Sheet sheet = excel.getSheet("Sheet1");
			Cell c =excel.getCell(sheet,1,0);

			excel.setCellValue(c,"设置xls成功！");
			
			Cell c1 =excel.getCell(sheet,1,2);

			excel.setCellValue(c1,"102");
			
			excel.write("d:/test/poi/w2"+"."+excel.getType());
	
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testgetCellFigure() {
		Integer[] iArray=Excel.getCellFigure("A2");
		assertThat(iArray[0], is(1));
		assertThat(iArray[1], is(0));
	}
	
	@Test
	public void testgetCellFigure1() {
		System.out.println(ClassLoader.getSystemResource("").getPath());
		
		System.out.println(Excel.getCellFigure(35, 2));
	}

}
