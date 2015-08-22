package com.ansteel.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ansteel.core.domain.BaseEntity;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：org.springframework.util.ClassUtils扩展。  
 */
public class ClassUtils extends org.springframework.util.ClassUtils{

	/**
	 * 获取多对一名称
	 * @param one
	 * @param many
	 * @return
	 */
	public static String getManyToOneName(Class one,Class many){
		String name="";
		Field[] fields = many.getDeclaredFields();
		for(Field f:fields){
			if(f.getType()==one){
				Annotation[] annots = f.getAnnotations();
				for(Annotation o:annots){
					if(o.annotationType().getClass()== ManyToOne.class.getClass()){
						name=f.getName();
					}
				}				
			}
		}
		return name;
		
	}
	

	/**
	 * 通过class得到表名
	 * @param clazz
	 * @return
	 */
	public static String getTableName(Class clazz) {
		String tableName="";
		Annotation[] annots = clazz.getAnnotations();
		for(Annotation a:annots ){
			Class typeClass=a.annotationType();
			Class otmClass=Table.class;
			if(typeClass==otmClass){
				Table table=(Table) a;
				tableName=table.name();
			}
		}
		return tableName;
	}
	
	/**
	 * 通过class类的spring控制器映射
	 * @param clazz
	 * @return
	 */
	public static String[] getClassRequestMapping(Class clazz) {
		if(clazz.isAnnotationPresent(RequestMapping.class)){
	        RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
	        String[] names = requestMapping.value();
	        return names;
        }
		return null;
	}
	
	public static void setManyToOneEntity(String oneName,BaseEntity target,BaseEntity value){
		Field[] fields = target.getClass().getDeclaredFields();
		for(Field f:fields){
			if(f.getName().equals(oneName)){
				f.setAccessible(true);
				ReflectionUtils.setField(f, target, value);
				break;
			}
		}
	}
}
