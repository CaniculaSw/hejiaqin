package com.customer.framework.component.storage.impl;

import android.content.Context;

/**
 * Created by Administrator on 2016/3/28 0028.
 */
public class SharedPStorage extends AbsSharedPStorage {

    public SharedPStorage(Context context) {
        super.sharedPreferencesCache = context.getSharedPreferences("storage_public", Context.MODE_PRIVATE);
    }
}
