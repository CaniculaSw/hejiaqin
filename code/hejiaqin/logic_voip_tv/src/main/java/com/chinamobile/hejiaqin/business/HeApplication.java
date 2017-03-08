package com.chinamobile.hejiaqin.business;

import com.chinamobile.hejiaqin.business.logic.setting.SettingLogic;
import com.chinamobile.hejiaqin.business.logic.voip.VoipLogic;
import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.RCSApplication;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.hme.HmeAudioTV;
import com.huawei.rcs.hme.HmeVideo;
import com.huawei.rcs.login.LoginApi;
import com.huawei.rcs.message.MessagingApi;
import com.huawei.rcs.system.DmVersionInfo;
import com.huawei.rcs.system.SysApi;
import com.huawei.rcs.tls.DefaultTlsHelper;
import com.huawei.rcs.upgrade.UpgradeApi;
import com.littlec.sdk.manager.CMIMHelper;

/**
 * Created by  on 2016/6/5.
 */
public class HeApplication extends RCSApplication {

    private static final String TAG ="HeApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        UpgradeApi.init(getApplicationContext());
        HmeAudioTV.setup(this);
        String deviceName = getDevice();
        LogUtil.d(TAG,"Const.deviceType:" +Const.deviceType);
        LogUtil.d(TAG,"Const.deviceName:" + deviceName);
        if(Const.deviceType == Const.TYPE_OTHER)
        {
            HmeVideo.setVideoMode(HmeVideo.VIDEO_MODE_VT);
        }else {
            HmeVideo.setVideoMode(HmeVideo.VIDEO_MODE_STB);
        }
        HmeVideo.setup(this);
        CallApi.init(getApplicationContext());
        SysApi.loadTls(new DefaultTlsHelper());
        CallApi.setConfig(CallApi.CONFIG_MAJOR_TYPE_VIDEO_DISPLAY_TYPE, CallApi.CONFIG_MINOR_TYPE_DEFAULT, "0");
        CallApi.setConfig(CallApi.CONFIG_MAJOR_TYPE_SRTP, CallApi.CONFIG_MINOR_TYPE_SRTP_ALL, CallApi.CFG_CALL_ENABLE_SRTP);
//        LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_TPT_TYPE, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, LoginApi.VALUE_MAJOR_TYPE_TPT_TLS);
        LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_KEEP_ALIVE_RSP_TIMER_LEN, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, "5");
        LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_USE_IPV6, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, "1");

        DmVersionInfo  versionInfo = new DmVersionInfo("V1.0.0.96", SysApi.VALUE_MAJOR_TYPE_PLATFORM_STB,
                SysApi.VALUE_MAJOR_TYPE_OS_ANDROID, SysApi.VALUE_MAJOR_TYPE_APP_RCS, "00");
        SysApi.setDMVersion(versionInfo);
        CallApi.setConfig(CallApi.CONFIG_MAJOR_DEVICE_NAME, CallApi.CONFIG_MINOR_TYPE_DEFAULT, deviceName);
        CaaSSdkService.setVideoLevel(0);
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

    }

    public String getDevice()
    {
        String sDevice = android.os.Build.DEVICE;
        String sModel = android.os.Build.MODEL;
        LogUtil.w(TAG, "device=" + sDevice + "--Model=" + sModel);

        //非系统签名版本注销掉这段代码
//        if (sDevice.contains("Hi3716CV200"))
//        {
//            Const.deviceType = Const.TYPE_3719C;
//        }
//        else if (sDevice.contains("Hi3719CV100"))
//        {
//            Const.deviceType = Const.TYPE_3719C;
//        }
//        else if (sDevice.contains("Hi3719MV100"))
//        {
//            Const.deviceType = Const.TYPE_3719M;
//        }
//        else if (sDevice.contains("Hi3798MV100"))
//        {
//            Const.deviceType = Const.TYPE_3798M;
//        }
        //非系统签名版本注销掉这段代码

        String deviceName = null;
        if (Const.TYPE_3798M == Const.deviceType)
        {
            deviceName = CallApi.DEVICE_NAME_3798M;
        }
        else if (Const.TYPE_3719C == Const.deviceType || Const.TYPE_3719M == Const.deviceType)
        {
            deviceName = CallApi.DEVICE_NAME_3719C;
        }
        else{
            deviceName = CallApi.DEVICE_NAME_TINYALSA;
        }
        return deviceName;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        VoipLogic.getInstance(getApplicationContext()).unRegisterVoipReceiver();
        SettingLogic.getInstance(getApplicationContext()).unRegisterMessageReceiver();
        CMIMHelper.getCmAccountManager().doLogOut();
    }

}
