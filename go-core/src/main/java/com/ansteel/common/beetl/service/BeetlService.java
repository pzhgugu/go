package com.ansteel.common.beetl.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板语法beetl接口。  
 */
public interface BeetlService {

	/**
	 * 输出动态sql语句
	 * @param optionValue
	 * @param request
	 * @return
	 */
	String outSQLQuery(String optionValue, HttpServletRequest request);
	/**
	 * 输出模板解析
	 * @param content
	 * @param request
	 * @return
	 */
	String outContent(String content,HttpServletRequest request);
	/**
	 * 输出模板解析
	 * @param content
	 * @param request
	 * @return
	 */
	String outContent(String content, Map<String, Object> rMap);
	
	String outContent(String content,HttpServletRequest request, Map<String, Object> sMap);
	
	Map<String, Class> getRegisterFunction();
	
	Map<String, Class> getRegisterTag();

}
