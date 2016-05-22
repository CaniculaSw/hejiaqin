package com.customer.framework.component.storage;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/14.
 */
public interface IMemStorage extends  IStorage{
    void save(String key, Object value);
    Object getObject(String key);
}
