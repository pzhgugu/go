package com.ansteel.core.query;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 一个工具类，作为各种查询条件的工厂。  
 */
import java.io.Serializable;

public interface QueryCriterion extends Serializable {

	public static final String PREFIX = "o.";

	public static final String TYPE_AND = "and";
	public static final String TYPE_BETWEEN = "between";
	public static final String TYPE_LIKE = "like";
	public static final String TYPE_EQ = "eq";
	public static final String TYPE_EQ_PROP = "eqProp";
	public static final String TYPE_GE = "ge";
	public static final String TYPE_GE_PROP = "geProp";
	public static final String TYPE_GT = "gt";
	public static final String TYPE_GT_PROP = "gtProp";
	public static final String TYPE_IN = "in";
	public static final String TYPE_IS_EMPTY = "empty";
	public static final String TYPE_IS_NULL = "null";
	public static final String TYPE_LE = "le";
	public static final String TYPE_LE_PROP = "leProp";
	public static final String TYPE_LT = "lt";
	public static final String TYPE_LT_PROP = "ltProp";
	public static final String TYPE_NOT = "not";
	public static final String TYPE_NOT_EMPTY = "notEmpty";
	public static final String TYPE_NOT_EQ = "notEq";
	public static final String TYPE_NOT_EQ_PROP = "notEqProp";
	public static final String TYPE_NOT_IN = "notIn";
	public static final String TYPE_NOT_NULL = "notNull";
	public static final String TYPE_OR = "or";
	public static final String TYPE_SIZE_EQ = "sizeEq";
	public static final String TYPE_SIZE_GE = "sizeGe";
	public static final String TYPE_SIZE_GT = "sizeGt";
	public static final String TYPE_SIZE_LE = "sizeLe";
	public static final String TYPE_SIZE_LT = "sizeLt";
	public static final String TYPE_SIZE_NOT_EQ = "sizeNotEq";
	public static final String TYPE_STARTS_WITH_TEXT = "startsWith";
	public static final String TYPE_END_WITH_TEXT = "endWith";

	/**
	 * 获取标准查询类型的实别名
	 * 
	 * <pre>
	 * 主要用于通过实别名转换为相应的标准查询类型
	 * @return
	 */
	String getCriterionName();

	String getExpre();

	Object getPropValue();

	String getPropName();

}
