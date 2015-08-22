package com.ansteel.core.cache;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：map缓存实现。 
 */
public class MapCache implements ICache{

    private static Map<String,Object> cache = new HashMap<String,Object>();


    @Override
    public Object getCache(String key) {
        if(cache.containsKey(key)){
            return cache.get(key);
        }
        return null;
    }

    @Override
    public int addCache(String key, Serializable value) {
        cache.put(key,value);
        return 1;
    }

    @Override
    public int clearCache() {
        cache.clear();
        return 1;
    }

    @Override
    public int removeCache(String key) {
        cache.remove(key);
        return 1;
    }

    @Override
    public int removeGroupCache(String prefix) {
        for(String key:cache.keySet()){
            if(key.indexOf(prefix)==0){
                cache.remove(key);
            }
        }
        return 0;
    }
}
