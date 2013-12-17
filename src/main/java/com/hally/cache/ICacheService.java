package com.hally.cache;

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
public interface ICacheService {
    public void cleanCache(String cacheName);
    public IObjectCache getCache(String cacheName);

}
