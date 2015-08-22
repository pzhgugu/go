package com.ansteel.core.query.criterion;

import com.ansteel.core.query.QueryCriterion;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 一个工具类，作为查询条件。  
 */
public class OrCriterion implements QueryCriterion {
	private QueryCriterion[] criterons;

	public OrCriterion(QueryCriterion... criterons) {
		this.criterons = criterons;
	}

	public QueryCriterion[] getCriterons() {
		return criterons;
	}
	
	@Override
	public String getCriterionName() {
		return QueryCriterion.TYPE_OR;
	}

	@Override
	public String getExpre() {
		if(criterons == null || criterons.length < 2) {
			throw new RuntimeException("Or表达式异常,必须有两个或两个以上的条件!");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(" ( ");
		for(int index = 0; index < criterons.length - 1; index++) {
			sb.append(criterons[index].getExpre());
			sb.append(" or ");
		}
		sb.append(criterons[criterons.length-1].getExpre());
		sb.append(" ) ");
		return sb.toString();
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
