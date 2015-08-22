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
 * 描   述：路径映射实体类注解，用于通过url链接名称映射实体类。 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface PathEntity {
	String value() default "";
}
