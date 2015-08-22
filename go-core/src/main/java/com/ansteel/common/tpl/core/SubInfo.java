package com.ansteel.common.tpl.core;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：从表信息。
 */
public class SubInfo {
	/**
	 * 编码
	 */
	private String name ;
	/**
	 * 名称
	 */
	private String alias;
	/**
	 * 主表映射名称
	 */
	private String oneName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getOneName() {
		return oneName;
	}
	public void setOneName(String oneName) {
		this.oneName = oneName;
	}
	
	
}
