package com.ansteel.core.cache;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：缓存回调函数。 
 */
public interface ICacheCallback {
    Object get(String key);
}
