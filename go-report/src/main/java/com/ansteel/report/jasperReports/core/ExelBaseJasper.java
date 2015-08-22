package com.ansteel.report.jasperReports.core;

import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import org.springframework.beans.factory.annotation.Value;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Jasper报表以Excel样式在线显示基类。
 */
public class ExelBaseJasper {
	
	@Value("${go_pro.attTempPath}")
	protected String attTempPath;

	public void getExcel(String sourceFileName, String destFileName,
			Map<String, Object> parameters, Connection connection) throws JRException {
		 JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName,
				parameters, connection);			  
		  
		  JRXlsExporter exporter = new JRXlsExporter();
		  exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		  exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFileName));
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
	}

}
