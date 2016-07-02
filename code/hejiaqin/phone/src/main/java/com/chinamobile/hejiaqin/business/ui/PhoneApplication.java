package com.chinamobile.hejiaqin.business.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.chinamobile.hejiaqin.BuildConfig;
import com.chinamobile.hejiaqin.business.HeApplication;
import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.RCSApplication;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.call.CallSession;
import com.huawei.rcs.hme.HmeAudio;
import com.huawei.rcs.hme.HmeVideo;
import com.huawei.rcs.stg.NatStgHelper;
import com.huawei.rcs.system.SysApi;
import com.huawei.rcs.tls.DefaultTlsHelper;
import com.huawei.rcs.upgrade.UpgradeApi;

/**
 * Created by zhanggj on 2016/6/5.
 */
public class PhoneApplication extends HeApplication implements
        Thread.UncaughtExceptionHandler{
    @Override
    public void onCreate() {
        super.onCreate();
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
