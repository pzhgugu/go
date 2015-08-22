package com.ansteel.core.cache;


import java.io.Serializable;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：缓存类接口。 
 */
public interface ICache {

    /**
     * 获取一个缓存
     */
    Object getCache(String key);
    /**
     * 增加缓存
     */
    int addCache(String key,Serializable value);
    /**
     * 清除全部缓存
     */
    int clearCache();
    /**
     * 清除一个缓存
     */
    int removeCache(String key);
    /**
     * 清除组缓存
     */
    int removeGroupCache(String prefix);
}
