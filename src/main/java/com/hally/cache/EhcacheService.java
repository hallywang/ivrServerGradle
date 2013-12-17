package com.hally.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-17 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
@Component
public class EhcacheService implements ICacheService{

    public static CacheManager manager = CacheManager.getInstance();
    Cache cache = manager.getCache("blackUser"); //默认
    HashMap<String, IObjectCache> cacheMap = new HashMap<String, IObjectCache>();

    /**
     * 清除缓存
     *
     * @param cacheName cache名称
     */
    public void cleanCache(String cacheName) {
        cache = manager.getCache(cacheName);
        cache.removeAll();
    }

    /**
     * 获取缓存
     *
     * @param cacheName cache名称
     * @return 缓存对象
     */
    public ObjectEhCache getCache(String cacheName) {
        ObjectEhCache objectCache = (ObjectEhCache) cacheMap.get(cacheName);
        if (objectCache == null) {
            objectCache = new ObjectEhCache(manager.getCache(cacheName));
            cacheMap.put(cacheName, objectCache);
        }
        return objectCache;
    }
}
