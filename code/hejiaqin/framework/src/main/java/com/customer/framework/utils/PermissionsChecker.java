package com.customer.framework.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * desc:i
 * version 001
 * author: zhanggj
 * Created: 2016/6/12.
 */
public class PermissionsChecker {


    public static boolean lacksPermissions(Context context,String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(context,permission)) {
                return true;
            }
         }
        return false;
    }
    // 判断是否缺少权限
    @TargetApi(23)
    private static boolean lacksPermission(Context context,String permission) {
        if(Build.VERSION.SDK_INT>=23) {
            return context.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED;
        }
        return false;
    }
}
