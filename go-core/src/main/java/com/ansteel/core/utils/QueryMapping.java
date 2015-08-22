package com.ansteel.core.utils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：查询映射。  
 */
public class QueryMapping {
	/**
	 * 查询名称
	 */
	private String name;
	/**
	 * 操作符
	 */
	private String operator;
	/**
	 * 查询值
	 */
	private String value;
	/**
	 * 开始值
	 */
	private String begin;
	/**
	 * 结束值
	 */
	private String end;
	
	

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
