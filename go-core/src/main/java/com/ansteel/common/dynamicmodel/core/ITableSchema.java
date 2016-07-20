package com.ansteel.common.dynamicmodel.core;

import java.util.Collection;
import java.util.Map;

import com.ansteel.common.dynamicmodel.domain.DynamicFields;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态建模核心类接口。  
 */
public interface ITableSchema {

	/**
	 * 创建表
	 * @param name 表名
	 * @param fields 字段集合
	 */
	void updateTable(String name, Collection<DynamicFields> fields);

	/**
	 * 保存
	 * @param name
	 * @param fields
	 * @param valueMap
	 */
	void save(String name, Collection<DynamicFields> fields,
			Map<String, Object> valueMap);

	void delect(String name, Collection<DynamicFields> fields, String id);

	String getTableName(String name);

	int clean(String name, Collection<DynamicFields> fields);
}
