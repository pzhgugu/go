package com.ansteel.report.jasperReports.core;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ansteel.core.utils.DownloadUtils;
import com.ansteel.core.utils.StringUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@Service
public class PdfJasperShow implements IJasperShow {

	@Value("${go_pro.attTempPath}")
	private String attTempPath;

	
	@Override
	public String show(String sourceFileName, String outPath,
			Map<String, Object> parameters, HttpServletResponse response,
			Connection connection) {
		
		if(!StringUtils.hasText(outPath)){
			outPath=attTempPath+"/"+StringUtils.getUuid()+".pdf";
		}

		try {
			JasperRunManager.runReportToPdfFile(sourceFileName, outPath, parameters, connection);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(response!=null){
			DownloadUtils.download(response, outPath, "2");
		}
		return outPath;
	}

}
