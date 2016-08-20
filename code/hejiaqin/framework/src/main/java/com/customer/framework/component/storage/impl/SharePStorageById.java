package com.customer.framework.component.storage.impl;

import android.content.Context;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/14.
 */
public class SharePStorageById extends AbsSharedPStorage {

    public SharePStorageById(Context context, String id) {
        super.sharedPreferencesCache = context.getSharedPreferences("storage_private_" + id, Context.MODE_PRIVATE);
    }
}
