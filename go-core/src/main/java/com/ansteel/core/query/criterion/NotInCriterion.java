package com.ansteel.core.query.criterion;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
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

public class NotInCriterion implements QueryCriterion {
	
	private Collection<? extends Object> value;
	private String propName;

	public NotInCriterion(String propName, Collection<? extends Object> value) {
		this.propName = propName;
		this.value = value;
	}
	
	public NotInCriterion(String propName, Object[] value) {
		this.propName = propName;
		this.value = Arrays.asList(value);
	}

	public Collection<? extends Object> getValue() {
		return value;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof NotInCriterion))
			return false;
		NotInCriterion castOther = (NotInCriterion) other;
		return new EqualsBuilder()
			.append(this.getPropName(), castOther.getPropName())
			.append(value, castOther.value).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPropName()).append(value).toHashCode();
	}

	@Override
	public String toString() {
		return getPropName() + " not in collection [" + collectionToString(value) + "]";
	}

	private String collectionToString(Collection<? extends Object> value) {
		return StringUtils.join(value, ",");
	}

	public String getPropName() {
		return propName;
	}
	
	@Override
	public String getCriterionName() {
		return QueryCriterion.TYPE_NOT_IN;
	}

	@Override
	public String getExpre() {
		return PREFIX + getPropName() + " not in (" + collectionToString(value) + ")";
	}
	
	@Override
	public Object getPropValue() {
		return null;
	}
}
