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
 * 描   述：通过从表在路径名中的class名称，得到从表中的关联主表的名称。 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface PathManyToOneClass {
	String value() default "";
	EntityType type() default EntityType.TABLE;
}
