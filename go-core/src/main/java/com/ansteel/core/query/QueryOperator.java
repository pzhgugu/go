package com.ansteel.core.query;

import com.ansteel.core.domain.BaseEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 通过操作符组织QuerySettings。  
 */
public class QueryOperator {
	
/*	*//**
	 * 通过条件组织
	 * @param querySettings
	 * @param where
	 * @param operator
	 * @param whereValue
	 * @return
	 *//*
	public static QuerySettings getQuerySettings(QuerySettings querySettings,
			String where,String operator,Object whereValue){
		Operator operEnum=Operator.EQ;
		for(Operator oe:Operator.values()){
			if(oe.getCode().equals(operator)){
				operEnum=oe;
				break;
			}
		}
		switch (operEnum) {
			case EQ:
				querySettings.eq(where, whereValue);
				break;
			case NOTEQ:
				querySettings.notEq(where, whereValue);
				break;
			case GT:
				querySettings.gt(where, (Comparable)whereValue);
				break;
			case GE:
				querySettings.ge(where, (Comparable)whereValue);
				break;
			case LT:
				querySettings.lt(where, (Comparable)whereValue);
				break;
			case LE:
				querySettings.le(where, (Comparable)whereValue);
				break;
			case LIKE:
				querySettings.containsText(where,(String) whereValue);
				break;
			case ISEMPTY:
				querySettings.isEmpty(where);
				break;
			case NOTEMPTY:
				querySettings.notEmpty(where);
				break;
			case ISNULL:
				querySettings.isNull(where);
				break;
			case NOTNULL:
				querySettings.notNull(where);
				break;
			default:
				break;
		}
		return querySettings;
		
	}

	public static QuerySettings getQuerySettings(
			QuerySettings<BaseEntity> querySettings, String name,
			String operator, Object begin, Object end) {
		querySettings.between(name, begin, end);
				return querySettings;
		
	}*/

	public static SqlQuerySettings getQuerySettings(
			SqlQuerySettings querySettings, String name,
			String operator, Object begin, Object end) {
		querySettings.between(name, begin, end);
				return querySettings;
		
	}
	
	public static SqlQuerySettings getQuerySettings(SqlQuerySettings querySettings,
			String where,String operator,Object whereValue){
		Operator operEnum=Operator.EQ;
		for(Operator oe:Operator.values()){
			if(oe.getCode().equals(operator)){
				operEnum=oe;
				break;
			}
		}
		switch (operEnum) {
			case EQ:
				querySettings.eq(where, whereValue);
				break;
			case NOTEQ:
				querySettings.notEq(where, whereValue);
				break;
			case GT:
				querySettings.gt(where, (Comparable)whereValue);
				break;
			case GE:
				querySettings.ge(where, (Comparable)whereValue);
				break;
			case LT:
				querySettings.lt(where, (Comparable)whereValue);
				break;
			case LE:
				querySettings.le(where, (Comparable)whereValue);
				break;
			case LIKE:
				querySettings.containsText(where,(String) whereValue);
				break;
			case ISEMPTY:
				querySettings.isEmpty(where);
				break;
			case NOTEMPTY:
				querySettings.notEmpty(where);
				break;
			case ISNULL:
				querySettings.isNull(where);
				break;
			case NOTNULL:
				querySettings.notNull(where);
				break;
			default:
				break;
		}
		return querySettings;
		
	}
}
