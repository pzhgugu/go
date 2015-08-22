package com.ansteel.core.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.ansteel.core.exception.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
