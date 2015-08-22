package com.ansteel.core.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：变量获取接口实现。  
 */
public abstract class Variable implements  IVariable{

    protected  Map<String,Object> variable = new HashMap<String,Object>();

    Variable(){
        Map<String,Object>  map=this.init();
        if(map!=null){
            variable.putAll(map);
        }
    }

    public abstract Map<String,Object> init();

    @Override
    public Object get(String key) {
        if(variable.containsKey(key)){
            return variable.get(key);
        }
        return null;
    }

    @Override
    public Object get(String key, Object defaultValue) {
        if(variable.containsKey(key)){
            return variable.get(key);
        }else{
            return defaultValue;
        }
    }

    @Override
    public Map<String, Object> getAll() {
        return variable;
    }

    @Override
    public void add(String key,Object value) {
        variable.put(key,value);
    }

    @Override
    public void clear() {
        variable.clear();
    }

    @Override
    public void remove(String key) {
        variable.remove(key);
    }

}
