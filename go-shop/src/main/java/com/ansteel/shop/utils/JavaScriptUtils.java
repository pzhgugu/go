package com.ansteel.shop.utils;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.ansteel.core.utils.ResponseUtils;

public class JavaScriptUtils{

	public static String script(String content) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type=\"text/javascript\">");
		sb.append(content);
		sb.append("</script>");
		return sb.toString();
	}
	
	public static String showDialog(String name,String url){
		StringBuffer sb = new StringBuffer();
		sb.append("showDialog('");
		sb.append(name);
		sb.append("', 'succ', null, function (){window.location.href ='");
		sb.append(url);
		sb.append("'}, 0, null, null, null, null, 2, null);CUR_DIALOG.close();");
		return sb.toString();
	}

	public static String returnShowDialog(String name, String url) {
		StringBuffer sb = new StringBuffer();
		sb.append(name);		
		sb.append(JavaScriptUtils.script(showDialog(name,url)));
		return sb.toString();
	}

	public static void BindingResultError(BindingResult result,String url, HttpServletResponse response) {
		StringBuffer sbError = new StringBuffer();
		List<ObjectError> errors = result.getAllErrors();
		for(ObjectError object :errors){
			sbError.append(object.getDefaultMessage());
		}
		ResponseUtils.xmlCDataOut(response,JavaScriptUtils.returnShowDialog(sbError.toString(),url));
	}

}
