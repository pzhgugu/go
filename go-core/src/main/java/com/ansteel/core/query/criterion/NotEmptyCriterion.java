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

public class NotEmptyCriterion implements QueryCriterion {

	private String propName;

	public NotEmptyCriterion(String propName) {
		this.propName = propName;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof NotEmptyCriterion))
			return false;
		NotEmptyCriterion castOther = (NotEmptyCriterion) other;
		return new EqualsBuilder()
			.append(this.getPropName(), castOther.getPropName())
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPropName()).toHashCode();
	}

	@Override
	public String toString() {
		return getPropName() + " is not empty";
	}

	public String getPropName() {
		return propName;
	}
	
	@Override
	public String getCriterionName() {
		return QueryCriterion.TYPE_NOT_EMPTY;
	}

	@Override
	public String getExpre() {
		return PREFIX + getPropName() + " is not empty";
	}
	
	@Override
	public Object getPropValue() {
		return null;
	}
}
