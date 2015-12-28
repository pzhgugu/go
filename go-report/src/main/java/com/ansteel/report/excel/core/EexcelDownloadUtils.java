package com.ansteel.report.excel.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import com.ansteel.core.utils.DownloadUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel文件下载工具类。 
 */
public class EexcelDownloadUtils {

	 public static void download(HttpServletResponse response, Workbook wb,String contentType, String fileName, String type) {
		 response.reset(); // 非常重要
		 response.setContentType(contentType);
		 try {
			 fileName=URLEncoder.encode(fileName, "UTF-8");
		 } catch (UnsupportedEncodingException e) {
			 e.printStackTrace();
		 }
		 if("1".equals(type)){
			 response.setHeader("Content-Disposition", "inline; filename=" + fileName+";");
		 }else{
			 response.setHeader("Content-Disposition", "attachment; filename=" + fileName+";");
		 }
         try {
        	ServletOutputStream out = response.getOutputStream();
			wb.write(response.getOutputStream());
			out.flush();
	        out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	     
}
