package com.customer.framework.component.storage;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/14.
 */
public interface IMemStorage extends  IStorage{
    void save(String key, Object value);
    Object getObject(String key);
}
