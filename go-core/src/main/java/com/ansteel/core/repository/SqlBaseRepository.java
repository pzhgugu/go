package com.ansteel.core.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：原生sql数据持久层接口。  
 */
public interface SqlBaseRepository {

	/**
	 * 查询
	 * @param queryString sql语句
	 * @param whereMap 条件
	 * @param pageable 分页
	 * @return
	 */
	List findSqlMap(String queryString, Map<String, Object> whereMap, Pageable pageable);

	/**
	 * 查询记录数
	 * @param countQuery
	 * @param whereMap
	 * @return
	 */
	long sqlCount(String countQuery, Map<String, Object> whereMap);

	/**
	 * 查询不带分页，返回maplist
	 * @param queryString
	 * @param whereMap
	 * @return
	 */
	List findSqlMap(String queryString, Map<String, Object> whereMap);

	/**
	 * 查询不带分页，返回list
	 * @param queryString
	 * @param whereMap
	 * @return
	 */
	List findSql(String queryString, Map<String, Object> whereMap);

	void executeUpdate(String queryString, Map<String, Object> whereMap);
}
