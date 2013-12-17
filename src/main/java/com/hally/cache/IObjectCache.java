package com.hally.cache;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-17 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
public interface IObjectCache {

    public void cleanCache();

    public void put(Object key, Object value);

    public void put(Object key, Object value, long time);

    public Object get(Object key);

    public boolean remove(Object key);

    public int size();

}
