package com.ansteel.core.cache;

import org.springframework.util.StringUtils;

import com.ansteel.core.constant.Public;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.context.SystemVariable;

import java.io.Serializable;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：缓存类工厂，用于缓存类型使用的分配。 
 */
public class CacheFactory {
	/**
	 * spring注册缓存类型
	 */
    private static final String UI_CACHE="cacheService";

    private String prefix="";

    private static ICache instance = null ;
    

    public CacheFactory(String prefix){
    	/**
    	 * 用于Redis，区分各个系统缓存前缀
    	 */
        String cacheName=(String)SystemVariable.get(Public.S_CACHE_NAME);
        if(StringUtils.hasText(cacheName)){
            prefix=cacheName+prefix;
        }
        this.prefix=prefix;
    }

    private ICache getInstance(){
        if (instance == null) {
             instance = getCacheService();
        }
        return instance;
    }

    private synchronized ICache getCacheService(){
        ICache cacheService=ContextHolder.getSpringBean(UI_CACHE);
        if(cacheService==null){
            return new MapCache();
        }
        return cacheService;

    }

    public Object getCache(String key,ICacheCallback callBack) {
        if(SystemVariable.get(Public.S_IS_CACHE)!=null&&SystemVariable.get(Public.S_IS_CACHE).equals(Public.TRUE)){
            Serializable value=null;
            value=(Serializable)getInstance().getCache(this.prefix+key);
            if(value==null){
                value=(Serializable)callBack.get(key);
                getInstance().addCache(this.prefix+key,value);
                return value;
            }else{
                return value;
            }
        }else{
            return callBack.get(key);
        }

    }

    public void clearCache(){
        getInstance().removeGroupCache(prefix);
    }

    public void removeCache(String key){
        getInstance().removeCache(key);
    }

}
