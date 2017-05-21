package com.customer.framework.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class SystemUtilTest extends AndroidTestCase {

    public void testGetPackageVersionName() {
        Context context = getContext();
        assertNotNull(context);

        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (packageInfo != null) {
            assertEquals(packageInfo.versionName, SystemUtil.getPackageVersionName(context));
        }
    }

    public void testGetPackageInfo() {
        Context context = getContext();
        assertNotNull(context);
        assertNotNull(SystemUtil.getPackageInfo(context));
    }
}
