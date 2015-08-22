package com.ansteel.core.cache;


import java.io.Serializable;
import java.util.Set;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Redis缓存实现。 
 */
public class RedisCache /*implements ICache */{

/*
    private static JedisSentinelPool jedisSentinelPool;

    private JedisSentinelPool getJedisSentinelPool(){
        if(jedisSentinelPool==null){
            jedisSentinelPool=ContextHolder.getSpringBean("jedisSentinelPool");
        }
        return jedisSentinelPool;
    }

    @Override
    public Object getCache(String key)  throws CacheExceprion {
        if(StringUtils.hasText(key)){
            Jedis jedis = getJedisSentinelPool().getResource();
            byte[]  o = jedis.get(key.getBytes());
            if(o==null||o.length<=0){
                return o;
            }
            return RedisUtil.decode(o);
        }
        return null;
    }

    @Override
    public int addCache(String key, Serializable value) {
        Jedis jedis = getJedisSentinelPool().getResource();
        jedis.set(key.getBytes(), RedisUtil.encode(value));
        return 1;
    }

    @Override
    public int clearCache() {
        //不提供此方法
        return 1;
    }

    @Override
    public int removeCache(String key) {
        Jedis jedis = getJedisSentinelPool().getResource();
        jedis.del(key);
        return 1;
    }

    @Override
    public int removeGroupCache(String prefix) {
        Jedis jedis = getJedisSentinelPool().getResource();
        Set<String> keys = jedis.keys(prefix+"*");
        jedis.del((String[])keys.toArray());
        return 1;
    }*/
}
