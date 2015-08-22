package com.ansteel.report.jasperReports.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：Jasper报表以pdf样式在线显示。
 */
@Service
public class PdfInlineJasperShow implements IJasperShow {
	
	@Autowired
	protected EntityManagerFactory entityManagerFactory;

	@Override
	public String show(String sourceFileName,
			String outPath, Map<String, Object> parameters,
			HttpServletResponse response,Connection connection) {
		
		try {
			FileInputStream in = new FileInputStream(new File(sourceFileName));
			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/pdf");
			JasperRunManager.runReportToPdfStream(in, out, parameters, connection);
			out.flush();
            out.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	


}
