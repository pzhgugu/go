package com.ansteel.report.excel.core;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.ansteel.core.utils.DownloadUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.report.poi.utils.Excel;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：excel报表展示服务类。
 */
@Service("excelShow")
public class ExcelShow implements IExcelShow{

	@Override
	public String show(Excel excel, String rType, String inline, String outPath,
			HttpServletResponse response,String fileName) {
		if(StringUtils.hasText(outPath)){
			
		}else{
			if(!StringUtils.hasText(fileName)){
				fileName=StringUtils.getUuid()+"."+excel.getType();
			}else{
				fileName=fileName+"."+excel.getType();
			}
			EexcelDownloadUtils.download(response, excel.getWorkbook(), DownloadUtils.getContentType(excel.getType()), fileName,inline);
		}
		return outPath;
	}

}
