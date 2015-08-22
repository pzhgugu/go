package com.ansteel.report.jasperReports.core;

import java.io.File;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ansteel.core.utils.DownloadUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.report.swftools.service.SWFToolsService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：Jasper报表以swf样式在线显示。
 */
@Service
public class SwfJasperShow implements IJasperShow {

	@Value("${go_pro.attTempPath}")
	private String attTempPath;
	
	@Value("${go_pro.attTempWeb}")
	private String attTempWeb;

	@Autowired
	SWFToolsService swfToolsService;
	
	@Override
	public String show(String sourceFileName, String outPath,
			Map<String, Object> parameters, HttpServletResponse response,
			Connection connection) {
		String uuid=StringUtils.getUuid();
		if(!StringUtils.hasText(outPath)){
			outPath=attTempPath+"/"+uuid+".swf";
		}

		String pdfPath=attTempPath+"/"+uuid+".pdf";
		try {
			JasperRunManager.runReportToPdfFile(sourceFileName, pdfPath, parameters, connection);
		} catch (JRException e) {
			e.printStackTrace();
		}
		swfToolsService.convert2SWF(new File(pdfPath), new File(outPath));
		return "/"+attTempWeb+"/"+uuid+".swf";
	}

}
