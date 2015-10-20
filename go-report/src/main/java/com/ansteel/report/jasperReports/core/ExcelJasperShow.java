package com.ansteel.report.jasperReports.core;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.springframework.stereotype.Service;

import com.ansteel.core.utils.DownloadUtils;
import com.ansteel.core.utils.StringUtils;

@Service
public class ExcelJasperShow extends ExelBaseJasper implements IJasperShow {

	@Override
	public String show(String sourceFileName, String outPath,
			Map<String, Object> parameters, HttpServletResponse response,
			Connection connection) {
		if(!StringUtils.hasText(outPath)){
			outPath=attTempPath+"/"+StringUtils.getUuid()+".xls";
		}
		try {
			super.getExcel(sourceFileName, outPath, parameters,connection);
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
