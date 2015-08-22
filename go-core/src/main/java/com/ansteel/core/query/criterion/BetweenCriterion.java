package com.ansteel.core.query.criterion;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ansteel.core.query.QueryCriterion;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 一个工具类，作为查询条件。  
 */
public class BetweenCriterion implements QueryCriterion {
	
	private Comparable<?> from;
	
	private Comparable<?> to;

	private String propName;

	public BetweenCriterion(String propName, Comparable<?> from, Comparable<?> to) {
		this.propName = propName;
		this.from = from;
		this.to = to;
	}

	public Comparable<?> getFrom() {
		return from;
	}

	public Comparable<?> getTo() {
		return to;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof BetweenCriterion))
			return false;
		BetweenCriterion castOther = (BetweenCriterion) other;
		return new EqualsBuilder()
			.append(this.getPropName(), castOther.getPropName())
			.append(from, castOther.from)
			.append(this.to, castOther.to)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPropName())
		.append(from).append(to).toHashCode();
	}

	@Override
	public String toString() {
		return getPropName() + " between " + from + " and " + to;
	}

	public String getPropName() {
		return propName;
	}
	
	@Override
	public String getCriterionName() {
		return QueryCriterion.TYPE_BETWEEN;
	}

	@Override
	public String getExpre() {
		throw new RuntimeException("系统不支持Between表达式");
	}

	@Override
	public Object getPropValue() {
		return null;
	}
}
