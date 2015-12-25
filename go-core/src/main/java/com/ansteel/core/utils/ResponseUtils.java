package com.ansteel.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.core.exception.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：Response工具类。  
 */
public class ResponseUtils {

	public static void reload(HttpServletResponse response) {
		print(response,reload());
	}

	public static void print(HttpServletResponse response,String context){
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(context);
		out.flush();
		out.close();
	}
	
	private static String reload() {
		StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/javascript\">");
		builder.append("window.location.reload();");
		builder.append("</script>");
		return builder.toString();
	}
	/**
	 * 返回ajax消息
	 * @param response
	 * @param content
	 */
	public static void returnAjaxMessage(HttpServletResponse response,String content,int code){
		response.setCharacterEncoding("UTF-8");
    	String result = null;
    	ErrorMessage message = new ErrorMessage();
		message.setCode(code);
		message.setMessage(content);
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			result = objectMapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setContentType(HttpServletResponse response){
		response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print("{}");
            writer.flush();
        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
	}

	public static void xmlCDataOut(HttpServletResponse response, String content) {
		response.setContentType("text/xml; charset=UTF-8");
		StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sb.append("<root>");
        sb.append("<![CDATA[").append(content).append("]]>");
        sb.append("</root>");
        print(response, sb.toString());
	}

	/**
	 * 通过URL下载报表服务器生成的报表文件.
	 * @param request
	 * @param rsp
	 * @param url
	 * @param fileName
	 * @throws IOException
	 */
	public static void downloadReport(HttpServletRequest request, HttpServletResponse rsp, String url, String fileName) throws IOException {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {

			HttpGet httpget = new HttpGet(url.toString());

			HttpResponse response = httpClient.execute(httpget);

			HttpEntity entity = response.getEntity();
			//设置MIME
			Header contentType = entity.getContentType();
			String mimeType=null;
			if(contentType!=null){
				mimeType=contentType.getValue().toString();
			}
			//文件扩展名
			if (mimeType != null) {
				rsp.setContentType(mimeType);
			}

			// 中文文件名支持
			if(StringUtils.hasText(fileName)) {
				String encodedfileName ="";
				if(fileName.indexOf(".")>-1){
					encodedfileName = URLEncoder.encode(fileName, "UTF-8");
				}else{
					String fileType=null;
					try {
						Header head = response.getFirstHeader(HttpHeaders.CONTENT_DISPOSITION);
						String headValue = head.getValue();
						if (StringUtils.hasText(headValue)) {
							String[] hArray = headValue.split(";");
							for (String s : hArray) {
								if (s.indexOf("filename=") > -1) {
									String[] fArray = s.split("=");
									String name = fArray[1];
									String[] nArray = name.split("\\.");
									fileType = nArray[1];
								}
							}
						}
					}catch (Exception e){

					}
					if(fileType==null){
						fileType="xls";
					}
					encodedfileName = URLEncoder.encode(fileName + "." + fileType, "UTF-8");
				}
				rsp.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedfileName + "\"");

			}
			InputStream in = entity.getContent();
			try {
				ServletOutputStream fout = rsp.getOutputStream();
				int l = -1;
				byte[] tmp = new byte[1024];
				while ((l = in.read(tmp)) != -1) {
					fout.write(tmp, 0, l);
					// 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
				}
				fout.flush();
				fout.close();
			} finally {
				// 关闭低层流。
				in.close();
			}
		} finally {
			httpClient.close();
		}
	}
}
