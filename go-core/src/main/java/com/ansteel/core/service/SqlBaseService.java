package com.ansteel.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql服务接口。  
 */
public interface SqlBaseService {


	List findSql(String sql, Map<String, Object> whereMap);
	

	List findSqlMap(String queryString, Map<String, Object> whereMap,Pageable pageable);	


	long sqlCount(String countQuery, Map<String, Object> whereMap);


	List findSqlMap(String queryString, Map<String, Object> whereMap);

	void executeUpdate(String queryString, Map<String, Object> whereMap);
}
