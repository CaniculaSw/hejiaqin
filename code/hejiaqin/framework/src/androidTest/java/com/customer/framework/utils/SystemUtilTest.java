package com.customer.framework.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.test.AndroidTestCase;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

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

    public void testGetStackTraceString() {
        assertNotNull(SystemUtil.getStackTraceString(new Throwable("abc")));
    }

    public void testGetApkPackageName() {
        Context context = getContext();
        assertNotNull(context);

        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo("", PackageManager.GET_ACTIVITIES);
        String packageName = null;
        if (null != info) {
            packageName = info.applicationInfo.packageName;
        }
        assertEquals(packageName, SystemUtil.getApkPackageName(context, ""));
    }

    public void testGetPseudoUIdOfPhone() {
        Context context = getContext();
        assertNotNull(context);

        assertNotNull(SystemUtil.getPseudoUIdOfPhone(context));
    }

    public void testGetAndroidId() {
        Context context = getContext();
        assertNotNull(context);

        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        assertEquals(androidId, SystemUtil.getAndroidId(context));
    }

    public void testIsApkInstalled() {
        Context context = getContext();
        assertNotNull(context);

        assertFalse(SystemUtil.isApkInstalled(context, "123"));
    }

    public void testGetuserAgent() {
        Context context = getContext();
        assertNotNull(context);
        assertNotNull(SystemUtil.getuserAgent(context));
    }

    public void testVibrate() {
        Context context = getContext();
        assertNotNull(context);

        try {
            SystemUtil.vibrate(context);
        } catch (Exception e) {

        }
    }

    public void testOpenApk() {
        Context context = getContext();
        assertNotNull(context);

        assertFalse(SystemUtil.openApk(context, "123"));
    }

    public void testGoHome() {
        Context context = getContext();
        assertNotNull(context);

        try {
            SystemUtil.goHome(context);
        } catch (Exception e) {

        }
    }

    public void testUnInstallAPK() {
        Context context = getContext();
        assertNotNull(context);

        SystemUtil.unInstallAPK(context, "123");
    }

    public void testGetAndroidSDKVersion() {
        assertEquals(Build.VERSION.SDK_INT, SystemUtil.getAndroidSDKVersion());
    }

    public void testEnableHttpResponseCache() {
        Context context = getContext();
        assertNotNull(context);

        SystemUtil.enableHttpResponseCache(context, 100);
    }

    public void testFlushRespCache() {
        SystemUtil.flushRespCache();
    }

    public void testPrintCachehitCount() {
        SystemUtil.printCachehitCount();
    }
}
