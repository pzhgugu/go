package com.ansteel.interfaces.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：与报表模块接口。  
 */
public interface MakeReport {

	String show(List listMap, Map<String, String> hashMap,  String rType,
			String inline, HttpServletRequest request,
			HttpServletResponse response,String fileName);

	String show(String modelName, List listMap, String type, Object inline,
			HttpServletRequest request, HttpServletResponse response,String fileName);

	List inExcel(String modelName, String inName, MultipartFile file);

}
