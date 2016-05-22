package com.customer.framework.component.storage;

import java.util.Map;

/**
 * Created by Administrator on 2016/3/28 0028.
 */
public interface IStorage {

    void save(String key, String value);

    void save(String key, int value);

    void save(String key, boolean value);

    void save(String key, float value);

    void save(String key, long value);

    void save(Map<String, Object> map);

    boolean isContain(String key);

    String getString(String key);

    int getInt(String key);

    boolean getBoolean(String key);

    float getFloat(String key);

    long getLong(String key);

    void remove(String[] keys);

    void clearAll();
}
