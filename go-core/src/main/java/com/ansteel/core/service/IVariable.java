package com.ansteel.core.service;

import java.util.Map;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：变量获取接口。  
 */
public interface IVariable {
    /**
     * 获取变量
     */
    Object get(String key);
    /**
     * 获取变量
     */
    Object get(String key,Object defaultValue);
    /**
     * 获取所有
     */
    Map<String,Object> getAll();
    /**
     * 增加变量
     */
    void add(String key,Object value);
    /**
     * 清除全部变量
     */
    void clear();
    /**
     * 清除一个变量
     */
    void remove(String key);
}
