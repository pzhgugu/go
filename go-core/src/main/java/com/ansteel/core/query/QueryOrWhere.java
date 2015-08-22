package com.ansteel.core.query;

import java.util.HashMap;
import java.util.Map;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 一个工具类，作为各种查询条件的工厂。  
 */
public class QueryOrWhere {
	
	public String syntax;
	
	public Map<String,Object> whereValueMap = new HashMap<>();

	public String getSyntax() {
		return syntax;
	}

	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	public Map<String, Object> getWhereValueMap() {
		return whereValueMap;
	}

	public void setWhereValueMap(Map<String, Object> whereValueMap) {
		this.whereValueMap = whereValueMap;
	}
	
	

}
