package com.chinamobile.hejiaqin.business.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by eshaohu on 2017/5/23.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
//            Intent service = new Intent(context, MainService.class);
//            context.startService(service);
        }
    }
}
