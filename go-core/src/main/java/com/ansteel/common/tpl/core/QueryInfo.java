package com.ansteel.common.tpl.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ansteel.dhtmlx.widget.form.Options;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：简单类型查询类。
 */
public class QueryInfo {
	/**
	 * 名称
	 */
	private List<Options> nameList = new ArrayList<Options>();
	
	/**
	 * 操作符
	 */
	private Map<String, List<Options>> operatorMap=new LinkedHashMap<String, List<Options>>();
	/**
	 * 值
	 */
	private Map<String, List<Options>> valueMap=new LinkedHashMap<String, List<Options>>();
	/**
	 * 是否常用
	 */
	private Map<String, Boolean> oftenMap=new LinkedHashMap<String, Boolean>();	
	/**
	 * 表单类型
	 */
	private Map<String, String> formTypeMap=new LinkedHashMap<String, String>();	
	
	
	
	public Map<String, String> getFormTypeMap() {
		return formTypeMap;
	}
	public void setFormTypeMap(Map<String, String> formTypeMap) {
		this.formTypeMap = formTypeMap;
	}
	public Map<String, Boolean> getOftenMap() {
		return oftenMap;
	}
	public void setOftenMap(Map<String, Boolean> oftenMap) {
		this.oftenMap = oftenMap;
	}
	public List<Options> getNameList() {
		return nameList;
	}
	public void setNameList(List<Options> nameList) {
		this.nameList = nameList;
	}
	public Map<String, List<Options>> getOperatorMap() {
		return operatorMap;
	}
	public void setOperatorMap(Map<String, List<Options>> operatorMap) {
		this.operatorMap = operatorMap;
	}
	public Map<String, List<Options>> getValueMap() {
		return valueMap;
	}
	public void setValueMap(Map<String, List<Options>> valueMap) {
		this.valueMap = valueMap;
	}
	
	

}
