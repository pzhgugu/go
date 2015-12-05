package com.ansteel.modules.openoffice.service;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.SpringBaseTest;
import com.ansteel.report.openoffice.service.OpenOfficeService;

public class OpenOfficeServiceBeanTest extends SpringBaseTest{

	@Autowired
	OpenOfficeService openOfficeService;
	@Test
	public void testPdf() {
		/*File excelFile = new File("d:/test/poi/Book1.xlsx");
		if(excelFile.exists()){
			File pdfFile = new File("d:/test/poi/Book1.pdf");
			if(pdfFile.exists())//pdf文件存在则删除
				pdfFile.delete();
			if(!openOfficeService.convert2PDF(excelFile, pdfFile)){
				throw new PageException("pdf文档转换失败！");
			}			
		}*/
	}

}
