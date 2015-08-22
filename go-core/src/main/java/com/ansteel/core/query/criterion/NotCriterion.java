package com.ansteel.core.query.criterion;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 一个工具类，作为查询条件。  
 */
import com.ansteel.core.query.QueryCriterion;

public class NotCriterion implements QueryCriterion {
	private QueryCriterion criteron;

	public NotCriterion(QueryCriterion criteron) {
		this.criteron = criteron;
	}

	public QueryCriterion getCriteron() {
		return criteron;
	}
	
	@Override
	public String getCriterionName() {
		return QueryCriterion.TYPE_NOT;
	}

	@Override
	public String getExpre() {
		throw new RuntimeException("系统不支持Not表达式");
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
