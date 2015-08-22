package com.ansteel.core.query;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 一个工具类，作为各种查询条件的工厂。  
 */
public interface IQueryBuilder {
	/**
	 * 获取总数查询语法
	 * @return
	 */
	QueryOrWhere getCountQueryOrWhere(final QuerySettings settings);
	/**
	 * 通过语法获取总数
	 * @param syntax
	 * @return
	 */
	String getCountQuerySyntax(String syntax) ;
	/**
	 * 获取查询语法
	 * @return
	 */
	QueryOrWhere getQueryOrWhere(final QuerySettings settings);
	

}
