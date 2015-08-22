package com.ansteel.core.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.ansteel.core.query.criterion.BetweenExCriterion;
import com.ansteel.core.query.criterion.OrCriterion;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 一个工具类，作为各种查询条件的工厂。  
 */
public class SqlQueryBuilder {
	
	private static List<String> paraCriterion = new ArrayList<String>();

	static {
		paraCriterion.add(QueryCriterion.TYPE_EQ);
		paraCriterion.add(QueryCriterion.TYPE_GE);
		paraCriterion.add(QueryCriterion.TYPE_GT);
		paraCriterion.add(QueryCriterion.TYPE_LE);
		paraCriterion.add(QueryCriterion.TYPE_LIKE);
		paraCriterion.add(QueryCriterion.TYPE_LT);
		paraCriterion.add(QueryCriterion.TYPE_NOT_EQ);
		paraCriterion.add(QueryCriterion.TYPE_SIZE_EQ);
		paraCriterion.add(QueryCriterion.TYPE_SIZE_GE);
		paraCriterion.add(QueryCriterion.TYPE_SIZE_GT);
		paraCriterion.add(QueryCriterion.TYPE_SIZE_LE);
		paraCriterion.add(QueryCriterion.TYPE_SIZE_LT);
		paraCriterion.add(QueryCriterion.TYPE_SIZE_NOT_EQ);
		paraCriterion.add(QueryCriterion.TYPE_LIKE);
		paraCriterion.add(QueryCriterion.TYPE_STARTS_WITH_TEXT);
		paraCriterion.add(QueryCriterion.TYPE_END_WITH_TEXT);
		paraCriterion.add(QueryCriterion.TYPE_BETWEEN);
	}
	
	public static final String ALIAS = " o";
	public static final String SPACE = " ";
	public static final String FROM = " from ";
	public static final String WHERE = " where ";
	public static final String AND = " and ";
	public static final String ORDER_BY = " order by ";
	
	private static  String getFrom(final SqlQuerySettings settings) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * ").append(FROM).append(settings.getTableName()).append(ALIAS).append(SPACE);
		return sb.toString();
	}
	
	private static  QueryOrWhere getWhere(final SqlQuerySettings settings) {
		QueryOrWhere where = new QueryOrWhere();
		Set<QueryCriterion> criterions = settings.getCriterions();
		if (criterions.size() >0){
			Map<String,Object> whereValueMap = new HashMap<>();
			StringBuilder sb = new StringBuilder();
			boolean loop = true;
			sb.append(WHERE);
			for (QueryCriterion criterion : criterions) {
				if (loop) {
					loop = false;
				} else {
					sb.append(AND);
				}
				if (paraCriterion.contains(criterion.getCriterionName())) {
					sb.append(criterion.getExpre());
				}else if(QueryCriterion.TYPE_OR.equals(criterion.getCriterionName())){
					OrCriterion orCriterion = (OrCriterion) criterion;
					QueryCriterion[] queryCriterionArr = orCriterion.getCriterons();
					for(QueryCriterion criterionOr : queryCriterionArr){
						
					}
				}
				//加入条件、值
				if (paraCriterion.contains(criterion.getCriterionName())) {
					if(QueryCriterion.TYPE_BETWEEN.endsWith(criterion.getCriterionName())){
						BetweenExCriterion betweenCriterion =(BetweenExCriterion) criterion;
						String name=criterion.getPropName().replaceAll("\\.", "");
						String begin = name+"_begin";
						String end = name+"_end";
						whereValueMap.put(begin,betweenCriterion.getFrom());
						whereValueMap.put(end,betweenCriterion.getTo());
					}else{
						whereValueMap.put(criterion.getPropName().replaceAll("\\.", ""),
							criterion.getPropValue());
					}
				}
			}
			where.setSyntax(sb.toString());
			where.setWhereValueMap(whereValueMap);
		}
		return where;
	}
	
	private static  String getOrder(final SqlQuerySettings settings) {
		StringBuilder sb = new StringBuilder();
		List<OrderSetting> orderSettings = settings.getOrderSettings();
		int orderSize = orderSettings.size();
		if (orderSize > 0){
			sb.append(ORDER_BY);
			for (int index = 0; index < orderSize; index++) {
				sb.append(ALIAS).append(".").append(orderSettings.get(index));
				if (index != (orderSize - 1)) {
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}
	

	public String getCountQuerySyntax(String hql) {
		StringBuffer countHql = new StringBuffer("select count(*) ");
		hql = hql.trim();
		String hqlUpperCase=hql.toUpperCase();
		int iFrom =hqlUpperCase.indexOf("FROM");
		if(iFrom==0){
			countHql.append(hql);
		}else{
			countHql.append(hql.substring(iFrom, hql.length()));
		}
		return countHql.toString();
	}


	public QueryOrWhere getCountQueryOrWhere(SqlQuerySettings settings) {
		QueryOrWhere queryOrWhere = this.getQueryOrWhere(settings);
		queryOrWhere.setSyntax(this.getCountQuerySyntax(queryOrWhere.getSyntax()));
		return queryOrWhere;
	}


	public QueryOrWhere getQueryOrWhere(SqlQuerySettings settings) {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getFrom(settings));
		QueryOrWhere queryOrWhere = this.getWhere(settings);
		
		String where = queryOrWhere.getSyntax();
		if(StringUtils.hasText(where)){
			sb.append(where);
		}

		String order = this.getOrder(settings);
		if(StringUtils.hasText(order)){
			sb.append(this.getOrder(settings));
		}
		queryOrWhere.setSyntax(sb.toString());
		return queryOrWhere;
	}
}
