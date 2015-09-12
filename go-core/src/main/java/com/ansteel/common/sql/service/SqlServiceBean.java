package com.ansteel.common.sql.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.common.model.repository.ModelsRepository;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.service.BaseService;
import com.ansteel.core.service.SqlBaseService;
import com.ansteel.core.utils.RequestUtils;
import com.ansteel.common.beetl.service.BeetlService;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql服务接口实现。  
 */
@Service
@Transactional(readOnly=true)
public class SqlServiceBean implements SqlService {
	
	@Autowired
	BeetlService beetlService;
	
	@Autowired
	SqlBaseService sqlBaseService;

	@Override
	public List querySql(String sqlContent, HttpServletRequest request,
			Map<String, Object> operMap) {
		String sql = beetlService.outContent(sqlContent, request,operMap);
        sql=asToUpperCase(sql);
		return sqlBaseService.findSqlMap(sql, operMap);
	}
	
	@Override
	public Page querySqlPage(String sqlContent, HttpServletRequest request,
			Map<String, Object> operMap,Pageable pageable) {
		String sql = beetlService.outContent(sqlContent, request,operMap);
        sql=asToUpperCase(sql);
		String countSql = getCountQuerySyntax(sql);		
		int totalCount = findByCount(countSql,operMap);		
		List resultList = find(sql, operMap, pageable);
		return new PageImpl(resultList, pageable, totalCount);	
	}
	
	public  List find(final String queryString, final Map<String, Object> paramsValueName
			,Pageable pageable) {
		return sqlBaseService.findSqlMap(queryString, paramsValueName,pageable);
	}
	
	public  int findByCount(final String queryString,
			final Map<String, Object> paramsValueName) {
		return (int) sqlBaseService.sqlCount(queryString, paramsValueName);
	}

	public String getCountQuerySyntax(String sql) {
		StringBuffer countHql = new StringBuffer("select count(*) ");
		sql = sql.trim();
		
		String sqlUpperCase=sql.toUpperCase();
		int iFrom =sqlUpperCase.indexOf("FROM");
		if(iFrom>-1){
			countHql.append(sql.substring(iFrom, sql.length()));
			return countHql.toString();
		}
		throw new PageException("没有找到关键字from，请检查sql语句！");
	}

	@Override
	public List querySql(String sqlContent, HttpServletRequest request) {
		String sql = beetlService.outContent(sqlContent, request);		
		Map<String,Object> operMap =RequestUtils.getRequestMap(request);
        sql=asToUpperCase(sql);
		return sqlBaseService.findSqlMap(sql, operMap);
	}

	@Override
	public List querySqlArray(String sqlContent, HttpServletRequest request) {
		String sql = beetlService.outContent(sqlContent, request);
		Map<String,Object> operMap =RequestUtils.getRequestMap(request);
        sql=asToUpperCase(sql);
		return sqlBaseService.findSql(sql, operMap);
	}

	/**
	 * 别名转大写，用于兼容oracle查询sql字段名都为大写
	 * @param sql
	 * @return
	 */
	public String asToUpperCase(String sql){
		String reg = "(\\s+AS\\s+)(\\w+)((\\s*,)|(\\s+)|$)";
		Pattern pc = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
		Matcher mc = pc.matcher(sql);
		StringBuffer sb=new StringBuffer();
		while (mc.find()) {
			String replacement=mc.group().toUpperCase();
			mc.appendReplacement(sb, replacement);
		}
		mc.appendTail(sb);
		return sb.toString();
	}
}
