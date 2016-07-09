package com.chinamobile.hejiaqin.business;

import android.content.Intent;
import android.util.Log;

import com.huawei.rcs.RCSService;

/**
 * Created by zhanggj on 2016/7/9.
 */
public class HeService extends RCSService {

    public final static String SERVICE_NAME = "com.chinamobile.hejiaqin.business.heservice";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("HeService", "HeService");
        return super.onStartCommand(intent, flags, startId);
    }
}
