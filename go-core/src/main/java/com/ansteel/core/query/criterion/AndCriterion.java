package com.ansteel.core.query.criterion;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 一个工具类，作为查询条件。  
 */
import com.ansteel.core.query.QueryCriterion;

public class AndCriterion implements QueryCriterion {
	private QueryCriterion[] criterons;

	public AndCriterion(QueryCriterion... criterons) {
		this.criterons = criterons;
	}

	public QueryCriterion[] getCriterons() {
		return criterons;
	}

	@Override
	public String getCriterionName() {
		return QueryCriterion.TYPE_AND;
	}

	@Override
	public String getExpre() {
		throw new RuntimeException("系统不支持And表达式");
	}

	@Override
	public Object getPropValue() {
		return null;
	}
	
	@Override
	public String getPropName() {
		return null;
	}
}
