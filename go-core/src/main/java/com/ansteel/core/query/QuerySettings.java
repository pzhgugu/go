package com.ansteel.core.query;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ansteel.core.domain.BaseEntity;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：数据仓储查询设定对象,用来收集各种查询设定,如过滤条件,分页信息,排序信息等等。  
 */

public class QuerySettings<T> extends Object implements Serializable{
	
	/*private static final long serialVersionUID = 3596442668420381832L;
	
	private Class<T> entityClass;
	private String rootAlias;
	private List<String> fields = new ArrayList<String>();
	private Map<String, String> aliases = new LinkedHashMap<String, String>();
	private Set<QueryCriterion> criterions = new HashSet<QueryCriterion>();
	private List<OrderSetting> orderSettings = new ArrayList<OrderSetting>();
	
	public static <T extends BaseEntity> QuerySettings<T> create(Class<T> entityClass) {
		return new QuerySettings<T>(entityClass);
	}
	
	public static <T extends BaseEntity> QuerySettings<T> create(Class<T> entityClass, String alias) {
		return new QuerySettings<T>(entityClass, alias);
	}
	
	private QuerySettings(Class<T> entityClass) {
		super();
		this.entityClass = entityClass;
	}

	public QuerySettings(Class<T> entityClass, String alias) {
		this.entityClass = entityClass;
		this.rootAlias = alias;
	}

	*//**
	 * @return the entityClass
	 *//*
	public Class<T> getEntityClass() {
		return entityClass;
	}

	public String getRootAlias() {
		return rootAlias;
	}

	public Map<String, String> getAliases() {
		return aliases;
	}

	public Set<QueryCriterion> getCriterions() {
		return criterions;
	}
	
	public List<OrderSetting> getOrderSettings() {
		return orderSettings;
	}


	*//**
	 * 查询属性使用的别名
	 * @param propName
	 * @param aliasName
	 * @return
	 *//*
	public QuerySettings<T> alias(String propName, String aliasName) {
		aliases.put(propName, aliasName);
		return this;
	}
	*//**
	 * 某个字段等于某个值
	 * @param propName
	 * @param value
	 * @return
	 *//*
	public QuerySettings<T> eq(String propName, Object value) {
		addCriterion(Criterions.eq(propName, value));
		return this;
	}
	*//**
	 * 某个字段等于其他的字段
	 * @param propName
	 * @param value
	 * @return
	 *//*
	public QuerySettings<T> notEq(String propName, Object value) {
		addCriterion(Criterions.notEq(propName, value));
		return this;
	}
	*//**
	 * 某个属性大于等于某个值
	 * @param propName
	 * @param value
	 * @return
	 *//*
	public QuerySettings<T> ge(String propName, Comparable<?> value) {
		addCriterion(Criterions.ge(propName, value));
		return this;
	}
	*//**
	 * 某个属性大于某个值
	 * @param propName
	 * @param value
	 * @return
	 *//*
	public QuerySettings<T> gt(String propName, Comparable<?> value) {
		addCriterion(Criterions.gt(propName, value));
		return this;
	}
	*//**
	 * 某个属性小于等于某个值
	 * @param propName
	 * @param value
	 * @return
	 *//*
	public QuerySettings<T> le(String propName, Comparable<?> value) {
		addCriterion(Criterions.le(propName, value));
		return this;
	}
	*//**
	 * 某个属性小于某个值
	 * @param propName
	 * @param value
	 * @return
	 *//*
	public QuerySettings<T> lt(String propName, Comparable<?> value) {
		addCriterion(Criterions.lt(propName, value));
		return this;
	}
	*//**
	 * 某个字段等于其他的字段
	 * @param propName
	 * @param otherProp
	 * @return
	 *//*
	public QuerySettings<T> eqProp(String propName, String otherProp) {
		addCriterion(Criterions.eqProp(propName, otherProp));
		return this;
	}
	*//**
	 * 某个属性不等于另一个属性的值
	 * @param propName
	 * @param otherProp
	 * @return
	 *//*
	public QuerySettings<T> notEqProp(String propName, String otherProp) {
		addCriterion(Criterions.notEqProp(propName, otherProp));
		return this;
	}
	*//**
	 * 某个属性大于另一个属性的值
	 * @param propName
	 * @param otherProp
	 * @return
	 *//*
	public QuerySettings<T> gtProp(String propName, String otherProp) {
		addCriterion(Criterions.gtProp(propName, otherProp));
		return this;
	}
	*//**
	 * 某个属性小于另一个属性的值
	 * @param propName
	 * @param otherProp
	 * @return
	 *//*
	public QuerySettings<T> geProp(String propName, String otherProp) {
		addCriterion(Criterions.geProp(propName, otherProp));
		return this;
	}
	*//**
	 * 某个属性小于等于另一个属性的值
	 * @param propName
	 * @param otherProp
	 * @return
	 *//*
	public QuerySettings<T> ltProp(String propName, String otherProp) {
		addCriterion(Criterions.ltProp(propName, otherProp));
		return this;
	}
	*//**
	 * 某个属性小于等于另一个属性的值
	 * @param propName
	 * @param otherProp
	 * @return
	 *//*
	public QuerySettings<T> leProp(String propName, String otherProp) {
		addCriterion(Criterions.leProp(propName, otherProp));
		return this;
	}
	*//**
	 * 查询某某属性的个数等于多少个 
	 * @param propName
	 * @param size
	 * @return
	 *//*
	public QuerySettings<T> sizeEq(String propName, int size) {
		addCriterion(Criterions.sizeEq(propName, size));
		return this;
	}
	
	public QuerySettings<T> sizeNotEq(String propName, int size) {
		addCriterion(Criterions.sizeNotEq(propName, size));
		return this;
	}
	
	public QuerySettings<T> sizeGt(String propName, int size) {
		addCriterion(Criterions.sizeGt(propName, size));
		return this;
	}
	
	public QuerySettings<T> sizeGe(String propName, int size) {
		addCriterion(Criterions.sizeGe(propName, size));
		return this;
	}
	
	public QuerySettings<T> sizeLt(String propName, int size) {
		addCriterion(Criterions.sizeLt(propName, size));
		return this;
	}
	
	public QuerySettings<T> sizeLe(String propName, int size) {
		addCriterion(Criterions.sizeLe(propName, size));
		return this;
	}

	*//**
	 * 某个字段包含某个值（like）
	 * @param propName
	 * @param value
	 * @return
	 *//*
	public QuerySettings<T> containsText(String propName, String value) {
		addCriterion(Criterions.containsText(propName, value));
		return this;
	}

	public QuerySettings<T> startsWithText(String propName, String value) {
		addCriterion(Criterions.startsWithText(propName, value));
		return this;
	}
	
	public QuerySettings<T> endWithText(String propName, String value) {
		addCriterion(Criterions.endWithText(propName, value));
		return this;
	}

	*//**
	 * 某个属性的值在某个集合中
	 * @param propName
	 * @param value
	 * @return
	 *//*
	public QuerySettings<T> in(String propName, Collection<? extends Object> value) {
		addCriterion(Criterions.in(propName, value));
		return this;
	}

	public QuerySettings<T> in(String propName, Object[] value) {
		addCriterion(Criterions.in(propName, value));
		return this;
	}

	public QuerySettings<T> notIn(String propName, Collection<? extends Object> value) {
		addCriterion(Criterions.notIn(propName, value));
		return this;
	}

	public QuerySettings<T> notIn(String propName, Object[] value) {
		addCriterion(Criterions.notIn(propName, value));
		return this;
	}

	public <E extends Object> QuerySettings<T> between(String propName, Comparable<E> from, Comparable<E> to) {
		addCriterion(Criterions.between(propName, from, to));
		return this;
	}
	public <E extends Object> QuerySettings<T> between(String propName, Object from, Object to) {
		addCriterion(Criterions.between(propName, from, to));
		return this;
	}
	*//**
	 * 某个属性为null
	 * @param propName
	 * @return
	 *//*
	public QuerySettings<T> isNull(String propName) {
		addCriterion(Criterions.isNull(propName));
		return this;
	}
	
	public QuerySettings<T> notNull(String propName) {
		addCriterion(Criterions.notNull(propName));
		return this;
	}
	*//**
	 * 某个属性为空
	 * @param propName
	 * @return
	 *//*
	public QuerySettings<T> isEmpty(String propName) {
		addCriterion(Criterions.isEmpty(propName));
		return this;
	}
	
	public QuerySettings<T> notEmpty(String propName) {
		addCriterion(Criterions.notEmpty(propName));
		return this;
	}
	
	public QuerySettings<T> not(QueryCriterion criterion) {
		addCriterion(Criterions.not(criterion));
		return this;
	}
	
	public QuerySettings<T> and(QueryCriterion... criterions) {
		addCriterion(Criterions.and(criterions));
		return this;
	}
	
	public QuerySettings<T> or(QueryCriterion... criterions) {
		addCriterion(Criterions.or(criterions));
		return this;
	}

	private void addCriterion(QueryCriterion criterion) {
		criterions.add(criterion);
	}
	
	public QuerySettings<T> asc(String propName) {
		orderSettings.add(OrderSetting.asc(propName));
		return this;
	}

	public QuerySettings<T> desc(String propName) {
		orderSettings.add(OrderSetting.desc(propName));
		return this;
	}

	public List<String> getFields() {
		return fields;
	}

	public QuerySettings<T> setField(String field) {
		fields.add(field);
		return this;
	}
	
	public QuerySettings<T> setFields(String[] fields) {
		if(!ArrayUtils.isEmpty(fields)) {
			for(String field : fields) {
				this.fields.add(field);
			}
		}
		return this;
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof QuerySettings))
			return false;
		QuerySettings castOther = (QuerySettings) other;
		return new EqualsBuilder()
				.append(entityClass, castOther.entityClass)
				.append(criterions, castOther.criterions)
				.append(orderSettings, castOther.orderSettings)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(entityClass)
				.append(criterions)
				.append(orderSettings)
				.toHashCode();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Class:").append(entityClass.getSimpleName()).append(SystemUtils.LINE_SEPARATOR);
		result.append("fields: [");
		for (String field : fields) {
			result.append(field);
		}
		result.append("]");
		result.append("criterions: [");
		for (QueryCriterion criteron : criterions) {
			result.append(criteron);
		}
		result.append("]").append(SystemUtils.LINE_SEPARATOR);
		result.append("orderSettings: [");
		for (OrderSetting orderSetting : orderSettings) {
			result.append(orderSetting);
		}
		result.append("]");
		return result.toString();
	}
	*/
}
