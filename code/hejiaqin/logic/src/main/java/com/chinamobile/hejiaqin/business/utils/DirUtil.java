package com.chinamobile.hejiaqin.business.utils;

import android.content.Context;
import android.os.Environment;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/27.
 */
public class DirUtil {
    public static String getExternalCacheDir(Context context) {
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return Environment.getExternalStorageDirectory().getPath() + cacheDir;
    }

    public static String getExternalFileDir(Context context) {
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/files/";
        return Environment.getExternalStorageDirectory().getPath() + cacheDir;
    }
}
