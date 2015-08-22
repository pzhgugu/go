package com.ansteel.core.utils;

import org.springframework.util.StringUtils;

import com.ansteel.core.exception.PageException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：下载工具类。  
 */
public class DownloadUtils {
	
	private static Map<String,String> contentTypeMap = null;
	
	 public static void download(HttpServletResponse response, File file,String contentType, String inline) {
	        BufferedInputStream br = null;
	        try {
	            br = new BufferedInputStream(new FileInputStream(file));
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	        byte[] buf = new byte[1024];
	        int len = 0;

	        response.reset(); // 非常重要
	        if(!StringUtils.hasText(inline)){
	        	inline ="attachment";
	        }
	        if (inline.equals("1")) { // 在线打开方式
	            URL u = null;
	            try {
	                u = new URL("file:///" + file.getPath());
	            } catch (MalformedURLException e) {
	                e.printStackTrace();
	            }
	            try {
					response.setContentType(u.openConnection().getContentType());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            response.setHeader("Content-Disposition", "inline; filename=" + file.getName()+";");
	            // 文件名应该编码成UTF-8
	        } else { // 纯下载方式
	            response.setContentType(contentType);
	            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName()+";");
	        }
	        OutputStream out = null;
	        try {
	            out = response.getOutputStream();
	            while ((len = br.read(buf)) > 0)
	                out.write(buf, 0, len);
	            br.close();
	            out.flush();
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

    public static void download(HttpServletResponse response, String filePath,String contentType, String inline) {
        File file = new File(filePath);
        //如果dir对应的文件不存在，或者不是一个目录
        if (!file.exists()) {
            throw new PageException("文件不存在，请检查！");
        }
        download(response, file, contentType, inline);
    }
   
    
    public static String getContentType(String type){
    	if(contentTypeMap==null){
    		contentTypeMap = new HashMap<String, String>();
    		contentTypeMap.put("xls", "application/vnd.ms-excel");
    		contentTypeMap.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    		contentTypeMap.put("pdf", "application/pdf");
    		contentTypeMap.put("htm", "text/html");
    		contentTypeMap.put("html", "text/html");
    	}
    	if(contentTypeMap.containsKey(type)){
    		return contentTypeMap.get(type);
    	}
		return "application/octet-stream";
    }
}
