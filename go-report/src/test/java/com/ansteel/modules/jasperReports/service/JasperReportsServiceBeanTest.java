package com.ansteel.modules.jasperReports.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.view.JasperViewer;

import org.junit.Test;
import org.springframework.util.Assert;

import com.ansteel.core.utils.SpringBaseTest;

public class JasperReportsServiceBeanTest {

	@Test
	public void test() throws FileNotFoundException {
		String sourceFileName= "C:\\Users\\Administrator\\Desktop\\report\\1.jrprint";
		try {
			String s=JasperExportManager.exportReportToPdfFile(sourceFileName);
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Test
	public void test2() throws FileNotFoundException {		
		String sourceFileName= "C:\\Users\\Administrator\\Desktop\\report\\report1.jrprint";
		String destFileName="C:\\Users\\Administrator\\Desktop\\report\\a.pdf";
		try {
			JasperExportManager.exportReportToPdfFile(sourceFileName,destFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3() throws FileNotFoundException {		
		String sourceFileName= "C:\\Users\\Administrator\\Desktop\\report\\report1.jrxml";
		
		//将报表的定义文件HelloWorld.jrxml编译成HelloWorld.jasper文件
        
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("1", 1);
		try {
			String jasperFile=JasperCompileManager.compileReportToFile(sourceFileName);
			String jasperPrint = JasperFillManager.fillReportToFile(jasperFile ,new HashMap(), new JREmptyDataSource());
			 //将.jrprint文件转换成HTML格式
	        JasperExportManager.exportReportToHtmlFile(jasperPrint);
	        //将.jrprint文件转换成PDF格式
	        JasperExportManager.exportReportToPdfFile(jasperPrint);
	        //将.jrprint文件转换成XML格式
	        JasperExportManager.exportReportToXmlFile(jasperPrint,false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
