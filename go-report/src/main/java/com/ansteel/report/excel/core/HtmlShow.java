package com.ansteel.report.excel.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.ansteel.report.poi.utils.Excel;
import com.ansteel.report.poi.utils.ToHtml;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：html在线展示报表服务类。
 */
@Service("htmlShow")
public class HtmlShow implements IExcelShow {

	@Override
	public String show(Excel excel, String rType, String inline, String outPath,
			HttpServletResponse response,String fileName) {
		response.setCharacterEncoding("utf8");
	    PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ToHtml toHtml = ToHtml.create(excel.getWorkbook(),out);
		toHtml.setCompleteHTML(true);
		try {
			toHtml.printPage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outPath;
	}

}
