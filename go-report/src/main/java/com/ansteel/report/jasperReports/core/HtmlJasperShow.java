package com.ansteel.report.jasperReports.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ansteel.core.utils.DownloadUtils;
import com.ansteel.core.utils.StringUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Jasper报表以html在线显示。
 */
@Service
public class HtmlJasperShow implements IJasperShow {
	
	@Value("${go_pro.attTempPath}")
	private String attTempPath;


	@Override
	public String show(String sourceFileName, String outPath,
			Map<String, Object> parameters, HttpServletResponse response,
			Connection connection) {
		
			if(!StringUtils.hasText(outPath)){
				outPath=attTempPath+"/"+StringUtils.getUuid()+".html";
			}
			try {
				JasperRunManager.runReportToHtmlFile(sourceFileName, outPath,parameters, connection);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(response!=null){
				DownloadUtils.download(response, outPath, "1");
			}
	
		return outPath;
	}

}
