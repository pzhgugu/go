package com.ansteel.core.context;

import com.ansteel.core.constant.Public;
import com.ansteel.core.service.PropertiesVariable;

import java.util.HashMap;
import java.util.Map;

/**
 * 创 建 人：gugu
 * 创建日期：2015-03-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：系统全局变量工具。 
 */
public class SystemVariable {

    protected static Map<String,Object> variable = new HashMap<String,Object>();


    public static void init(){
        defaultVariable();
        Map<String,Object> pMap=new PropertiesVariable().getAll();
        variable.putAll(pMap);
        //Map<String,Object> dMap=new DatabaseVariable().getAll();
        //variable.putAll(dMap);
    }

    public static void refresh(){
        variable.clear();
        init();
    }

    private static void defaultVariable() {
        //默认没有缓存
        variable.put(Public.S_IS_CACHE, Public.FALSE);
    }

    public static Object get(String key) {
        if(variable.containsKey(key)){
            return variable.get(key);
        }
        return null;
    }

    public static Object get(String key, Object defaultValue) {
        if(variable.containsKey(key)){
            return variable.get(key);
        }else{
            return defaultValue;
        }
    }

    public static Map<String, Object> getAll() {
        return variable;
    }

    public static void add(String key,Object value) {
        variable.put(key,value);
    }

    public static void clear() {
        variable.clear();
    }

    public static void remove(String key) {
        variable.remove(key);
    }


    public static void set(String name, Object value) {
        variable.put(name,value);
    }
}
