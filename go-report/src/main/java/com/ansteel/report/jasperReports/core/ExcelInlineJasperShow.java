package com.ansteel.report.jasperReports.core;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.springframework.stereotype.Service;

import com.ansteel.core.utils.DownloadUtils;
import com.ansteel.core.utils.StringUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Jasper报表以Excel样式在线显示。
 */
@Service
public class ExcelInlineJasperShow extends ExelBaseJasper implements IJasperShow {

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
		DownloadUtils.download(response, outPath, "1");
		return outPath;
	}

}
