package com.customer.framework.component.storage;

import android.content.Context;

import com.customer.framework.component.storage.impl.MemStorage;
import com.customer.framework.component.storage.impl.SharePStorageById;
import com.customer.framework.component.storage.impl.SharedPStorage;

/**
 * Created by Administrator on 2016/3/28 0028.
 */
public final class StorageMgr {

    private static final String TAG = "StorageMgr";

    private static final StorageMgr mInstance = new StorageMgr();

    private IMemStorage memStorage = new MemStorage();

    private ISharedPStorage sharedPStorage;

    private ISharedPStorage sharedPStorageById;

    private String currentId = "";

    private StorageMgr() {
    }

    public static StorageMgr getInstance() {
        return mInstance;
    }

    public IMemStorage getMemStorage() {
        return memStorage;
    }

    public ISharedPStorage getSharedPStorage(Context context) {
        if (sharedPStorage == null) {
            synchronized (SharedPStorage.class) {
                if (sharedPStorage == null) {
                    sharedPStorage = new SharedPStorage(context);
                }
            }
        }
        return sharedPStorage;
    }

    public synchronized ISharedPStorage getSharedPStorageById(Context context, String id) {
        if (sharedPStorageById == null || !currentId.equals(id)) {
            sharedPStorageById = new SharePStorageById(context, id);
            this.currentId = id;
        }
        return sharedPStorageById;
    }
}
