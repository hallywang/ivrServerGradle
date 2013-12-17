package com.hally.cache;

import org.springframework.stereotype.Component;

/**
 * function description.
 * <p/>
 * <p><h2>Change History</h2>
 * <p/>
 * 13-12-17 | hally | created
 * <p/>
 * </p>
 *
 * @author hally
 * @version 1.0.0
 */
@Component
public class MemcacheService implements ICacheService {
    @Override
    public void cleanCache(String cacheName) {

    }

    @Override
    public IObjectCache getCache(String cacheName) {
        return null;
    }
}
