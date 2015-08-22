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
public class NotEqPropCriterion implements QueryCriterion {
	
	private String propName1;
	private String propName2;
	
	public NotEqPropCriterion(String propName1, String propName2) {
		this.propName1 = propName1;
		this.propName2 = propName2;
	}

	public String getPropName2() {
		return propName2;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof NotEqPropCriterion))
			return false;
		NotEqPropCriterion castOther = (NotEqPropCriterion) other;
		return new EqualsBuilder()
			.append(this.getPropName1(), castOther.getPropName1())
			.append(propName2, castOther.propName2).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPropName1()).append(propName2).toHashCode();
	}

	@Override
	public String toString() {
		return getPropName1() + " != " + propName2;
	}

	public String getPropName1() {
		return propName1;
	}
	
	@Override
	public String getCriterionName() {
		return QueryCriterion.TYPE_NOT_EQ_PROP;
	}

	@Override
	public String getExpre() {
		return PREFIX + getPropName1() + " != " + PREFIX + propName2;
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
