package com.ansteel.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-06
 * 修 改 人：
 * 修改日 期：
 * 描   述： 通过id到数据库查询实体，并将人Request有的值写入实体
 * 如果更新需要版本，请在前台设置version字段
 * 通常用在Update中
 * 如果数据库查询不到则，按照参数装配
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface PathDatabaseEntity {
	String value() default "";
}
