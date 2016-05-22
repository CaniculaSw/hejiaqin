package com.chinamobile.hejiaqin.business.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.utils.StringUtil;

import java.util.UUID;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/27.
 */
public class SysInfoUtil {

    /**
     * 获取唯一标识（6.0以上IMEI获取要申请权限,改为唯一ID缓存方式）
     *
     * @param context
     * @return
     */
    public static String getAppUniqueId(Context context) {
        String uniqueId = StorageMgr.getInstance().getMemStorage().getString(BussinessConstants.CommonInfo.APP_UNIQUE_ID);
        if (StringUtil.isNullOrEmpty(uniqueId)) {
            uniqueId = StorageMgr.getInstance().getSharedPStorage(context).getString(BussinessConstants.CommonInfo.APP_UNIQUE_ID);
        } else {
            return uniqueId;
        }
        if (StringUtil.isNullOrEmpty(uniqueId)) {
            uniqueId = UUID.randomUUID().toString();
            StorageMgr.getInstance().getSharedPStorage(context).save(BussinessConstants.CommonInfo.APP_UNIQUE_ID, uniqueId);
        }
        StorageMgr.getInstance().getMemStorage().save(BussinessConstants.CommonInfo.APP_UNIQUE_ID, uniqueId);
        return uniqueId;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getModel() {
        return Build.BRAND + " " + android.os.Build.MODEL;
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static String getOsRelease() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取app VersionCode
     *
     * @param ctx
     * @return
     */
    public static int getVersionCode(Context ctx) {
        int currentVersionCode = 0;
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            String appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
            System.out.println(currentVersionCode + " " + appVersionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch blockd
            e.printStackTrace();
        }
        return currentVersionCode;
    }

    /**
     * 获取app VersionName
     *
     * @param ctx
     * @return
     */
    public static String getVersionName(Context ctx) {
        String appVersionName = "";
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            appVersionName = info.versionName; // 版本名
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch blockd
            e.printStackTrace();
        }
        return appVersionName;
    }
}
