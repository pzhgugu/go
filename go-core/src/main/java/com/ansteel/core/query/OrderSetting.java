package com.ansteel.core.query;

import java.io.Serializable;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 一个工具类，作为各种查询条件的工厂。  
 */
public class OrderSetting implements Serializable{

	private static final long serialVersionUID = 7481499155046018134L;
	
	private boolean ascending = true;
	private String propName;
	
	public OrderSetting() {
	}

	public boolean isAscending() {
		return ascending;
	}

	public String getPropName() {
		return propName;
	}

	private OrderSetting(boolean ascending, String propName) {
		if (StringUtils.isEmpty(propName)) {
			throw new InvalidParameterException("propName should not be empty!");
		}
		this.ascending = ascending;
		this.propName = propName;
	}

	public static OrderSetting asc(String propName) {
		return new OrderSetting(true, propName);
	}

	public static OrderSetting desc(String propName) {
		return new OrderSetting(false, propName);
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof OrderSetting))
			return false;
		OrderSetting castOther = (OrderSetting) other;
		return new EqualsBuilder().append(ascending, castOther.ascending)
				.append(propName, castOther.propName).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(ascending).append(propName)
				.toHashCode();
	}

	@Override
	public String toString() {
		return propName + (ascending ? " asc" : " desc");
	}
}
