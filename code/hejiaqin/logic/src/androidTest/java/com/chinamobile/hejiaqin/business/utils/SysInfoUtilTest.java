package com.chinamobile.hejiaqin.business.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.test.AndroidTestCase;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class SysInfoUtilTest extends AndroidTestCase {

    public void testGetAppUniqueId() throws Exception {
        String uniqueId = SysInfoUtil.getAppUniqueId(getContext());
        assertNotNull(uniqueId);

        // 跑100次，确保每次生成的值都是一样的
        for (int i = 0; i < 100; i++) {
            assertEquals(uniqueId, SysInfoUtil.getAppUniqueId(getContext()));
        }
    }

    public void testGetModel() throws Exception {
        String model = Build.BRAND + " " + android.os.Build.MODEL;
        assertEquals(model, SysInfoUtil.getModel());
    }

    public void testGetOsRelease() throws Exception {
        String release = android.os.Build.VERSION.RELEASE;
        assertEquals(release, SysInfoUtil.getModel());
    }

    public void testGetVersionCode() throws Exception {
        int currentVersionCode = 0;
        PackageManager manager = getContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
        }
        assertEquals(currentVersionCode, SysInfoUtil.getVersionCode(getContext()));
    }

    public void testGetVersionName() throws Exception {
        String appVersionName = "";
        PackageManager manager = getContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
            appVersionName = info.versionName; // 版本名
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch blockd
        }
        assertEquals(appVersionName, SysInfoUtil.getVersionName(getContext()));
    }
}
