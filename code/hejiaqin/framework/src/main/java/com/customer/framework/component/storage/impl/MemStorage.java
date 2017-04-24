package com.customer.framework.component.storage.impl;

import com.customer.framework.component.storage.IMemStorage;

import java.util.HashMap;
import java.util.Map;

/**
 * 内存存储的实现
 */
public class MemStorage implements IMemStorage {

    private final Map<String, Object> memCache = new HashMap<>();

    @Override
    public void save(String key, String value) {
        if (null == key) {
            return;
        }

        synchronized (memCache) {
            memCache.put(key, value);
        }
    }

    @Override
    public void save(String key, int value) {
        if (null == key) {
            return;
        }

        synchronized (memCache) {
            memCache.put(key, value);
        }
    }

    @Override
    public void save(String key, boolean value) {
        if (null == key) {
            return;
        }

        synchronized (memCache) {
            memCache.put(key, value);
        }
    }

    @Override
    public void save(String key, float value) {
        if (null == key) {
            return;
        }

        synchronized (memCache) {
            memCache.put(key, value);
        }
    }

    @Override
    public void save(String key, long value) {
        if (null == key) {
            return;
        }

        synchronized (memCache) {
            memCache.put(key, value);
        }
    }

    @Override
    public void save(String key, Object value) {
        if (null == key) {
            return;
        }

        synchronized (memCache) {
            memCache.put(key, value);
        }
    }

    @Override
    public void save(Map<String, Object> map) {
        if (null == map) {
            return;
        }
        synchronized (memCache) {
            memCache.putAll(map);
        }
    }

    @Override
    public boolean isContain(String key) {
        if (null == key) {
            return false;
        }

        synchronized (memCache) {
            return memCache.containsKey(key);
        }
    }

    @Override
    public String getString(String key) {
        if (null == key) {
            return null;
        }

        Object valueObj;
        synchronized (memCache) {
            valueObj = memCache.get(key);
        }

        if (null == valueObj) {
            return null;
        }

        if (valueObj instanceof String) {
            return (String) valueObj;
        }

        return valueObj.toString();
    }

    @Override
    public int getInt(String key) {
        if (null == key) {
            return Integer.MIN_VALUE;
        }

        Object valueObj;
        synchronized (memCache) {
            valueObj = memCache.get(key);
        }

        if (null == valueObj) {
            return Integer.MIN_VALUE;
        }

        if (valueObj instanceof Integer) {
            return (Integer) valueObj;
        }

        if (valueObj instanceof String) {
            try {
                return Integer.parseInt((String) valueObj);
            } catch (Exception e) {
                return Integer.MIN_VALUE;
            }
        }
        return Integer.MIN_VALUE;
    }

    @Override
    public boolean getBoolean(String key) {
        if (null == key) {
            return Boolean.FALSE;
        }

        Object valueObj;
        synchronized (memCache) {
            valueObj = memCache.get(key);
        }

        if (null == valueObj) {
            return Boolean.FALSE;
        }

        if (valueObj instanceof Boolean) {
            return (Boolean) valueObj;
        }

        if (valueObj instanceof String) {
            try {
                return Boolean.parseBoolean((String) valueObj);
            } catch (Exception e) {
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public float getFloat(String key) {
        if (null == key) {
            return Float.MIN_VALUE;
        }

        Object valueObj;
        synchronized (memCache) {
            valueObj = memCache.get(key);
        }

        if (null == valueObj) {
            return Float.MIN_VALUE;
        }

        if (valueObj instanceof Float) {
            return (Float) valueObj;
        }

        if (valueObj instanceof String) {
            try {
                return Float.parseFloat((String) valueObj);
            } catch (Exception e) {
                return Float.MIN_VALUE;
            }
        }
        return Float.MIN_VALUE;
    }

    @Override
    public long getLong(String key) {
        if (null == key) {
            return Long.MIN_VALUE;
        }

        Object valueObj;
        synchronized (memCache) {
            valueObj = memCache.get(key);
        }

        if (null == valueObj) {
            return Long.MIN_VALUE;
        }

        if (valueObj instanceof Long) {
            return (Long) valueObj;
        }

        if (valueObj instanceof String) {
            try {
                return Long.parseLong((String) valueObj);
            } catch (Exception e) {
                return Long.MIN_VALUE;
            }
        }
        return Long.MIN_VALUE;
    }

    @Override
    public Object getObject(String key) {
        if (null == key) {
            return null;
        }

        synchronized (memCache) {
            return memCache.get(key);
        }
    }

    @Override
    public void remove(String[] key) {
        synchronized (memCache) {
            for (int i = 0; i < key.length; i++) {
                if (memCache.containsKey(key[i])) {
                    memCache.remove(key[i]);
                }
            }
        }
    }

    @Override
    public void clearAll() {
        synchronized (memCache) {
            memCache.clear();
        }
    }

}
