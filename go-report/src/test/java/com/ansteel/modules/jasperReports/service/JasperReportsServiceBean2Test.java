package com.ansteel.modules.jasperReports.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.core.utils.SpringBaseTest;


public class JasperReportsServiceBean2Test extends SpringBaseTest {

	
	
	@Test
	public void test() throws SQLException {
		HibernateEntityManagerFactory em=(HibernateEntityManagerFactory) entityManagerFactory;
		SessionFactoryImpl sessionFactory = (SessionFactoryImpl)  em.getSessionFactory();
		Connection connection = sessionFactory.getConnectionProvider().getConnection();

		String sourceFileName = "C:\\Users\\Administrator\\Desktop\\report\\report1.jrxml";

		// 将报表的定义文件HelloWorld.jrxml编译成HelloWorld.jasper文件

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userName", "gugu1");
		try {
			String jasperFile = JasperCompileManager
					.compileReportToFile(sourceFileName);
			String jasperPrint = JasperFillManager.fillReportToFile(jasperFile,
					parameters, connection);
			// 将.jrprint文件转换成HTML格式
			JasperExportManager.exportReportToHtmlFile(jasperPrint);
			// 将.jrprint文件转换成PDF格式
			JasperExportManager.exportReportToPdfFile(jasperPrint);
			// 将.jrprint文件转换成XML格式
			JasperExportManager.exportReportToXmlFile(jasperPrint, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testExcel() throws SQLException {
		HibernateEntityManagerFactory em=(HibernateEntityManagerFactory) entityManagerFactory;
		SessionFactoryImpl sessionFactory = (SessionFactoryImpl)  em.getSessionFactory();
		Connection connection = sessionFactory.getConnectionProvider().getConnection();

		String sourceFileName = "C:\\Users\\Administrator\\Desktop\\report\\report1.jrxml";
		
		String outFileName = "C:\\Users\\Administrator\\Desktop\\report\\report1.xls";

		// 将报表的定义文件HelloWorld.jrxml编译成HelloWorld.jasper文件

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userName", "gugu1");
		try {
			String jasperFile = JasperCompileManager
					.compileReportToFile(sourceFileName);
			 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile,
					parameters, connection);			  
			  
			  JRXlsExporter exporter = new JRXlsExporter();
			  exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			  exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outFileName));
			  SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			  configuration.setOnePagePerSheet(true);
			  //取消导出excel时超大数字格式的字符串会自动转换科学计数法进行显示
			  configuration.setDetectCellType(true);
			  configuration.setCollapseRowSpan(false);
			  //是否删除空行
			  configuration.setRemoveEmptySpaceBetweenRows(true);
			  //是否把每一页放入一个新的sheet
			  configuration.setOnePagePerSheet(true);
			  //背景是否白色
			  configuration.setWhitePageBackground(true);			  
			  exporter.setConfiguration(configuration);
			  exporter.exportReport();
			  
			  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPDF() throws SQLException {
		HibernateEntityManagerFactory em=(HibernateEntityManagerFactory) entityManagerFactory;
		SessionFactoryImpl sessionFactory = (SessionFactoryImpl)  em.getSessionFactory();
		Connection connection = sessionFactory.getConnectionProvider().getConnection();

		String sourceFileName = "C:\\Users\\Administrator\\Desktop\\report\\report1.jrxml";
		
		String outFileName = "C:\\Users\\Administrator\\Desktop\\report\\report1.pdf";

		// 将报表的定义文件HelloWorld.jrxml编译成HelloWorld.jasper文件

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userName", "gugu1");
		try {
			String jasperFile = JasperCompileManager
					.compileReportToFile(sourceFileName);
			 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile,
					parameters, connection);			  
			  
			 JRPdfExporter exporter = new JRPdfExporter();

			 exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			 exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outFileName));
			 SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			 exporter.setConfiguration(configuration);

			 exporter.exportReport();
			  
			  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
