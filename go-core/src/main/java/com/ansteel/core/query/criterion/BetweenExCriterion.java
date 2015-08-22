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
public class BetweenExCriterion implements QueryCriterion {
	
	private Object from;
	
	private Object to;

	private String propName;

	public BetweenExCriterion(String propName, Object from, Object to) {
		this.propName = propName;
		this.from = from;
		this.to = to;
	}

	public Object getFrom() {
		return from;
	}



	public void setFrom(Object from) {
		this.from = from;
	}

	public Object getTo() {
		return to;
	}



	public void setTo(Object to) {
		this.to = to;
	}



	public void setPropName(String propName) {
		this.propName = propName;
	}



	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof BetweenExCriterion))
			return false;
		BetweenExCriterion castOther = (BetweenExCriterion) other;
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
		String name=getPropName().replaceAll("\\.", "");
		String begin = name+"_begin";
		String end = name+"_end";
		return PREFIX + getPropName() + " between :" + begin+" AND :"+end+" ";
	}

	@Override
	public Object getPropValue() {
		return null;
	}
}
