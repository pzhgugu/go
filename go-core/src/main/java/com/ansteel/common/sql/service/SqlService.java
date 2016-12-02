package com.ansteel.common.sql.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql服务接口。  
 */
public interface SqlService {

	List querySql(String sqlContent, HttpServletRequest request,
			Map<String, Object> operMap);

	Page querySqlPage(String sqlContent, HttpServletRequest request,
			Map<String, Object> operMap,Pageable pageable);

	List querySql(String sqlContent, HttpServletRequest request);

	List querySqlArray(String sqlContent, HttpServletRequest request);

	Map querySqlTotal(String sqlContent, HttpServletRequest request, Map<String, Object> operMap);

	void executeUpdate(String sqlContent, HttpServletRequest request, Map<String, Object> parameterMap);
}
