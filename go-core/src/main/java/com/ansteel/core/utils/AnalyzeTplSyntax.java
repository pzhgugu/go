package com.ansteel.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ansteel.core.constant.ExceptionConstant;
import com.ansteel.core.constant.Public;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.exception.PageException;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：解析模板语法。  
 */
public class AnalyzeTplSyntax {
	
	public static Map<String, Object> getTplModelMap(ViewElement viewElement){
		Map<String, Object> modelMap=new HashMap<String, Object>();		
		modelMap.put(ViewModelConstant.P_MODULE, viewElement.getRequestMappingName());
		modelMap.put(ViewModelConstant.P_TPL,viewElement.getTpl());
		//modelMap.put(ViewModelConstant.P_CLASS_NAME,viewElement.getPresentClazz());
		modelMap.put(ViewModelConstant.P_CLASS_NAME,viewElement.getCurrentName());
		return modelMap;
		
	}
	
	public static String analyze(String source ,Map modelMap){
		Pattern p = Pattern.compile("\\$\\{(.*?)\\}");
		Matcher m = p.matcher(source);
		StringBuffer sb = new StringBuffer(); 
		boolean result = m.find(); 
		
		while(result) { 
			String s = replace(m.group(1),modelMap);
			m.appendReplacement(sb, s);
			result = m.find(); 
		}		
		
		if(sb.length()>0){
			return m.appendTail(sb).toString();
		}else{
			return source;
		}
	}

	private static String replace(String fielName, Map parameter) {
		if(!parameter.containsKey(fielName)){
			throw new PageException(ExceptionConstant.E_ERROR+fielName+"类型没有找到！");
		}
		return parameter.get(fielName).toString();
	}
}
