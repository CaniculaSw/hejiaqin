package com.customer.framework.component.storage;

import java.io.Serializable;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/14.
 */
public interface ISharedPStorage extends  IStorage {
    void save(String key, Serializable value);
    Serializable getObject(String key);
}
