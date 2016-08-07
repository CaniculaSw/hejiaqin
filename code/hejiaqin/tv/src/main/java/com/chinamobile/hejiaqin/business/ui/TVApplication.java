package com.chinamobile.hejiaqin.business.ui;

import android.app.Application;
import android.content.Intent;

import com.chinamobile.hejiaqin.tv.BuildConfig;
import com.chinamobile.hejiaqin.business.HeApplication;
import com.chinamobile.hejiaqin.business.HeService;
import com.customer.framework.utils.LogUtil;

/**
 * Created by zhanggj on 2016/6/5.
 */
public class TVApplication extends Application implements
        Thread.UncaughtExceptionHandler{
    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this,HeService.class));
        //设置Thread Exception Handler
        if(BuildConfig.LOG_LEVEL>= LogUtil.WARN) {
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LogUtil.e("GobalException", ex);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
