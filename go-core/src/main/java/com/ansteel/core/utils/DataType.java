package com.ansteel.core.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：数据类型抽象类。  
 */
public interface DataType extends Serializable {

	@SuppressWarnings("unchecked")
	List<? extends Class<?>> PRIMITIVE_DATA_TYPES = Arrays.asList(
			byte[].class, short.class, int.class, long.class, double.class, char.class, boolean.class, 
			Byte[].class, Short.class, Integer.class, Long.class, Double.class, Character.class, Boolean.class, 
			String.class, Date.class, Number.class,
			Class.class
			);
	
	@SuppressWarnings("unchecked")
	List<? extends Class<?>> COLLECTION_DATA_TYPES = Arrays.asList(
			Collection.class, List.class, Set.class
			);
	
	@SuppressWarnings("unchecked")
	List<? extends Class<?>> MAP_DATA_TYPES = Arrays.asList(Map.class);
	
	List<String> PRIMITIVE_CONFIG_TYPES = Arrays.asList(
		    "double","int","long","string");

	Class<?> getType();

	String getTypeName();

	Object getRealValue(String data);

	String getStringValue(Object data);
}
