package com.ansteel.core.utils;
import java.lang.reflect.InvocationTargetException;  
import java.util.ArrayList;  
import java.util.Collection;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
    
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：Map工具类。  
 */
public class MapUtils extends org.apache.commons.collections.MapUtils {  
  
    /** 
     * 将Map转换为Object 
     *  
     * @param clazz 
     *            目标对象的类 
     * @param map 
     *            待转换Map 
     * @return 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws InvocationTargetException 
     */  
    public static <T, V> T toObject(Class<T> clazz, Map<String, V> map) throws InstantiationException, IllegalAccessException, InvocationTargetException {  
        T object = clazz.newInstance();  
        return toObject(object, map);  
    }  
  
    /** 
     * 将Map转换为Object 
     *  
     * @param clazz 
     *            目标对象的类 
     * @param map 
     *            待转换Map 
     * @param toCamelCase 
     *            是否去掉下划线 
     * @return 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws InvocationTargetException 
     */  
    public static <T, V> T toObject(Class<T> clazz, Map<String, V> map, boolean toCamelCase) throws InstantiationException, IllegalAccessException, InvocationTargetException {  
        T object = clazz.newInstance();  
        return toObject(object, map, toCamelCase);  
    }  
  
    /** 
     * 将Map转换为Object 
     *  
     * @param object 
     *            目标对象 
     * @param map 
     *            待转换Map 
     * @return 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws InvocationTargetException 
     */  
    public static <T, V> T toObject(T object, Map<String, V> map) throws InstantiationException, IllegalAccessException, InvocationTargetException {  
        return toObject(object, map, false);  
    }  
  
    public static <T, V> T toObject(T object, Map<String, V> map, boolean toCamelCase) throws InstantiationException, IllegalAccessException, InvocationTargetException {  
        if (toCamelCase)  
            map = toCamelCaseMap(map);  
        BeanUtils.populate(object, map);  
        return object;  
    }  
  
    /** 
     * 对象转Map 
     *  
     * @param object 
     *            目标对象 
     * @return 
     * @throws IllegalAccessException 
     * @throws InvocationTargetException 
     * @throws NoSuchMethodException 
     */  
    @SuppressWarnings("unchecked")  
    public static Map<String, String> toMap(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {  
        return BeanUtils.describe(object);  
    }  
  
    /** 
     * 转换为Collection<Map<K, V>> 
     *  
     * @param collection 
     *            待转换对象集合 
     * @return 转换后的Collection<Map<K, V>> 
     * @throws IllegalAccessException 
     * @throws InvocationTargetException 
     * @throws NoSuchMethodException 
     */  
    public static <T> Collection<Map<String, String>> toMapList(Collection<T> collection) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {  
        List<Map<String, String>> retList = new ArrayList<Map<String, String>>();  
        if (collection != null && !collection.isEmpty()) {  
            for (Object object : collection) {  
                Map<String, String> map = toMap(object);  
                retList.add(map);  
            }  
        }  
        return retList;  
    }  
  
    /** 
     * 转换为Collection,同时为字段做驼峰转换<Map<K, V>> 
     *  
     * @param collection 
     *            待转换对象集合 
     * @return 转换后的Collection<Map<K, V>> 
     * @throws IllegalAccessException 
     * @throws InvocationTargetException 
     * @throws NoSuchMethodException 
     */  
    public static <T> Collection<Map<String, String>> toMapListForFlat(Collection<T> collection) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {  
        List<Map<String, String>> retList = new ArrayList<Map<String, String>>();  
        if (collection != null && !collection.isEmpty()) {  
            for (Object object : collection) {  
                Map<String, String> map = toMapForFlat(object);  
                retList.add(map);  
            }  
        }  
        return retList;  
    }  
  
    /** 
     * 转换成Map并提供字段命名驼峰转平行 
     *  
     * @param clazz 
     *            目标对象所在类 
     * @param object 
     *            目标对象 
     * @param map 
     *            待转换Map 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */  
    public static Map<String, String> toMapForFlat(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {  
        Map<String, String> map = toMap(object);  
        return toUnderlineStringMap(map);  
    }  
  
    /** 
     * 将Map的Keys去下划线<br> 
     * (例:branch_no -> branchNo )<br> 
     *  
     * @param map 
     *            待转换Map 
     * @return 
     */  
    public static <V> Map<String, V> toCamelCaseMap(Map<String, V> map) {  
        Map<String, V> newMap = new HashMap<String, V>();  
        for (String key : map.keySet()) {  
            safeAddToMap(newMap, JavaBeanUtil.toCamelCaseString(key), map.get(key));  
        }  
        return newMap;  
    }  
  
    /** 
     * 将Map的Keys转译成下划线格式的<br> 
     * (例:branchNo -> branch_no)<br> 
     *  
     * @param map 
     *            待转换Map 
     * @return 
     */  
    public static <V> Map<String, V> toUnderlineStringMap(Map<String, V> map) {  
        Map<String, V> newMap = new HashMap<String, V>();  
        for (String key : map.keySet()) {  
            newMap.put(JavaBeanUtil.toUnderlineString(key), map.get(key));  
        }  
        return newMap;  
    }  
  
}  