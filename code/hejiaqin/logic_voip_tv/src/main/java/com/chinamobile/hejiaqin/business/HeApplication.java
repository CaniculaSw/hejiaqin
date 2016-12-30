package com.chinamobile.hejiaqin.business;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.logic.setting.SettingLogic;
import com.chinamobile.hejiaqin.business.logic.voip.VoipLogic;
import com.chinamobile.hejiaqin.business.utils.DirUtil;
import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.RCSApplication;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.call.MediaApi;
import com.huawei.rcs.hme.HmeAudioTV;
import com.huawei.rcs.hme.HmeVideo;
import com.huawei.rcs.message.MessagingApi;
import com.huawei.rcs.system.DmVersionInfo;
import com.huawei.rcs.system.SysApi;
import com.huawei.rcs.tls.DefaultTlsHelper;
import com.huawei.rcs.upgrade.UpgradeApi;
import com.huawei.usp.UspCfg;
import com.littlec.sdk.manager.CMIMHelper;

import java.io.File;

/**
 * Created by  on 2016/6/5.
 */
public class HeApplication extends RCSApplication {

    private BroadcastReceiver mCameraPlugReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Rect rectLocal = new Rect();

            int iState = intent.getIntExtra("state", -1);
            LogUtil.d(Const.TAG_UI, "camera stat change:" + iState);

            Toast.makeText(getApplicationContext(), "mCameraPlugReciver", Toast.LENGTH_SHORT).show();

            if (1 == iState)
            {
                Toast.makeText(getApplicationContext(), "open the camera", Toast.LENGTH_SHORT).show();
                LogUtil.d(Const.TAG_UI, "open the camera");
                rectLocal.left = 0;
                rectLocal.top = 0;
                rectLocal.right = 1280;
                rectLocal.bottom = 720;

                CaaSSdkService.setLocalRenderPos(rectLocal, CallApi.VIDEO_LAYER_BOTTOM);
                CaaSSdkService.openLocalView();
                CaaSSdkService.showLocalVideoRender(true);
            }
            else
            {
                LogUtil.d(Const.TAG_UI, "close the camera");
                CaaSSdkService.closeLocalView();
            }

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        String hmeLogPath = DirUtil.getExternalFileDir(getApplicationContext()) + "/log/hme";
        LogUtil.d(Const.TAG_UI, "The hmelogpath is " + hmeLogPath);
        File targetDir = new File(hmeLogPath);
        if (!targetDir.exists())
        {
            if (!targetDir.mkdirs())
            {
                LogUtil.e(Const.TAG_UI, "mkdir failed: " + hmeLogPath);
            }
        }
        MediaApi.setConfigString(UspCfg.JEN_UMME_CFG_HME_LOGPATH, hmeLogPath + "/");

        SysApi.loadTls(new DefaultTlsHelper());

        UpgradeApi.init(getApplicationContext());

        HmeAudioTV.setup(this);
        String deviceName = getDevice();
        if(Const.deviceType == Const.TYPE_OTHER)
        {
            HmeVideo.setVideoMode(HmeVideo.VIDEO_MODE_VT);
        }else {
            HmeVideo.setVideoMode(HmeVideo.VIDEO_MODE_STB);
        }
        HmeVideo.setup(this);
        CallApi.init(getApplicationContext());
        DmVersionInfo  versionInfo = new DmVersionInfo("V1.0.0.96", SysApi.VALUE_MAJOR_TYPE_PLATFORM_STB,
                SysApi.VALUE_MAJOR_TYPE_OS_ANDROID, SysApi.VALUE_MAJOR_TYPE_APP_RCS, "00");
        SysApi.setDMVersion(versionInfo);
        CallApi.setConfig(CallApi.CONFIG_MAJOR_DEVICE_NAME, CallApi.CONFIG_MINOR_TYPE_DEFAULT, deviceName);
        CaaSSdkService.setVideoLevel(0);
        IntentFilter filter=new IntentFilter();
        filter.addAction(Const.CAMERA_PLUG);
        registerReceiver(mCameraPlugReciver, filter);
        //initial message API
        MessagingApi.init(getApplicationContext());
        MessagingApi.setAllowSendDisplayStatus(true);
        MessagingApi.openTolistUncompletedMessage();
//        //设置为IM不同源
        MessagingApi.setConfig(
                MessagingApi.CONFIG_MAJOR_USE_SYS_SMS,
                MessagingApi.CONFIG_MINOR_TYPE_DEFAULT, "0");

        VoipLogic.getInstance(getApplicationContext()).registerVoipReceiver();
        SettingLogic.getInstance(getApplicationContext()).registerMessageReceiver();
        /**
         * 初始化小溪推送SDK
         */
        CMIMHelper.getCmAccountManager().init(getApplicationContext(), BussinessConstants.CommonInfo.LITTLEC_APP_KEY);
    }

    public String getDevice()
    {
        String sDevice = android.os.Build.DEVICE;
        String sModel = android.os.Build.MODEL;
        LogUtil.d(Const.TAG_UI, "device=" + sDevice + "--sModel=" + sModel);
        if (sDevice.contains("Hi3716CV200"))
        {
            Const.deviceType = Const.TYPE_3719C;
        }
        else if (sDevice.contains("Hi3719CV100"))
        {
            Const.deviceType = Const.TYPE_3719C;
        }
        else if (sDevice.contains("Hi3719MV100"))
        {
            Const.deviceType = Const.TYPE_3719M;
        }
        else if (sDevice.contains("Hi3798MV100"))
        {
            Const.deviceType = Const.TYPE_3798M;
        }
        else
        {
            LogUtil.e(Const.TAG_UI, "the device is Other!");
            Const.deviceType = Const.TYPE_OTHER;
        }
        String deviceName = null;
        if (Const.TYPE_3798M == Const.deviceType)
        {
            deviceName = "STB_3798M";
        }
        else if (Const.TYPE_3719C == Const.deviceType || Const.TYPE_3719M == Const.deviceType)
        {
            deviceName = "STB_3719C";
        }
        else{
            deviceName = sDevice;
        }
        return deviceName;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(mCameraPlugReciver);
        VoipLogic.getInstance(getApplicationContext()).unRegisterVoipReceiver();
        SettingLogic.getInstance(getApplicationContext()).unRegisterMessageReceiver();
        CMIMHelper.getCmAccountManager().doLogOut();
    }

}
