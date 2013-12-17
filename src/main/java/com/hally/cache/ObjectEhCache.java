package com.hally.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-17 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
public class ObjectEhCache implements IObjectCache {


    private Cache cache;

    public ObjectEhCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void cleanCache() {
        cache.removeAll();
    }

    @Override
    public void put(Object key, Object value) {
        Element element = new Element(key, value);
        cache.put(element);
    }

    @Override
    public void put(Object key, Object value, long time) {
        put(key, value);
    }

    @Override
    public Object get(Object key) {
        Element element = cache.get(key);
        if (element != null) {
            return element.getObjectValue();
        } else {
            return null;
        }
    }

    @Override
    public boolean remove(Object key) {
        return cache.remove(key);
    }

    @Override
    public int size() {
        return cache.getSize();
    }
}
