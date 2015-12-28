package com.ansteel.report.excel.core;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.ansteel.report.aspose.service.AsposeExcelToPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.DownloadUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.report.poi.utils.Excel;
import com.ansteel.report.swftools.service.SWFToolsService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-09
 * 修 改 人：
 * 修改日 期：
 * 描   述：PDF报表展示。
 */
@Service("pdfShow")
public class PdfShow implements IExcelShow {


	@Value("${go_pro.attTempPath}")
	private String attTempPath;
	
	@Autowired
	AsposeExcelToPdfService asposeExcelToPdfService;

	@Override
	public String show(Excel excel, String rType, String inline,
			String outPath, HttpServletResponse response,String fileName) {
		String uuid = StringUtils.getUuid();
		if(!StringUtils.hasText(outPath)){
			outPath=attTempPath+"/";
		}
		String excelPath=outPath+uuid+"."+excel.getType();
		String pdfPath=outPath+uuid+".pdf";
		try {
			excel.write(excelPath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new PageException("转换pdf时写入excel错误！");
		}
		File excelFile = new File(excelPath);
		if(excelFile.exists()){
			File pdfFile = new File(pdfPath);
			if(pdfFile.exists())//pdf文件存在则删除
				pdfFile.delete();
			if (!asposeExcelToPdfService.convert2PDF(excelFile, pdfFile)) {
				throw new PageException("pdf文档转换失败！");
			}

			DownloadUtils.download(response, pdfFile, inline,fileName);
		}
		return pdfPath;
	}


}
