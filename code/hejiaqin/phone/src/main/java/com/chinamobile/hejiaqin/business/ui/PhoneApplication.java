package com.chinamobile.hejiaqin.business.ui;

import android.content.Intent;

import com.chinamobile.hejiaqin.BuildConfig;
import com.chinamobile.hejiaqin.business.HeApplication;
import com.chinamobile.hejiaqin.business.HeService;
import com.chinamobile.hejiaqin.business.utils.DirUtil;
import com.customer.framework.utils.LogUtil;

/**
 * Created by  on 2016/6/5.
 */
public class PhoneApplication extends HeApplication implements
        Thread.UncaughtExceptionHandler{
    @Override
    public void onCreate() {
        //根据build.gradle设置日志级别
        LogUtil.setContext(getApplicationContext());
        LogUtil.setLogLevel(BuildConfig.LOG_LEVEL);
        LogUtil.setLogCommonDir(DirUtil.getExternalFileDir(this) + "/log/common/");
        super.onCreate();
        startService(new Intent(this,HeService.class));
        //设置Thread Exception Handler
//        if(BuildConfig.LOG_LEVEL>= LogUtil.WARN) {
            Thread.setDefaultUncaughtExceptionHandler(this);
//        }

        /**
         * 初始化小溪推送SDK
         */
//        CMIMHelper.getCmAccountManager().init(getApplicationContext(), BussinessConstants.CommonInfo.LITTLEC_APP_KEY);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LogUtil.e("GobalException", ex);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
