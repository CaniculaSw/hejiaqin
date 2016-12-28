package com.chinamobile.hejiaqin.business;

import com.chinamobile.hejiaqin.business.logic.setting.SettingLogic;
import com.chinamobile.hejiaqin.business.logic.voip.VoipLogic;
import com.huawei.rcs.RCSApplication;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.hme.HmeAudio;
import com.huawei.rcs.hme.HmeVideo;
import com.huawei.rcs.login.LoginApi;
import com.huawei.rcs.message.MessagingApi;
import com.huawei.rcs.system.SysApi;
import com.huawei.rcs.tls.DefaultTlsHelper;
import com.huawei.rcs.upgrade.UpgradeApi;
import com.littlec.sdk.manager.CMIMHelper;

/**
 * Created by  on 2016/6/5.
 */
public class HeApplication extends RCSApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        UpgradeApi.init(getApplicationContext());
        HmeAudio.setup(this);
        HmeVideo.setup(this);
        CallApi.init(getApplicationContext());
        CallApi.setConfig(CallApi.CONFIG_MAJOR_TYPE_VIDEO_DISPLAY_TYPE, CallApi.CONFIG_MINOR_TYPE_DEFAULT, "1");
        CallApi.setConfig(CallApi.CONFIG_MAJOR_PREVIEW_BEFORE_CONNED,
                CallApi.CONFIG_MINOR_TYPE_DEFAULT, CallApi.CFG_VALUE_YES);
        CallApi.setConfig(CallApi.CONFIG_MAJOR_TYPE_SRTP, CallApi.CONFIG_MINOR_TYPE_SRTP_ALL, CallApi.CFG_CALL_ENABLE_SRTP);
//        LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_TPT_TYPE, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, LoginApi.VALUE_MAJOR_TYPE_TPT_TLS);
        LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_KEEP_ALIVE_RSP_TIMER_LEN ,LoginApi.CONFIG_MINOR_TYPE_DEFAULT,"5");
        LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_USE_IPV6 ,LoginApi.CONFIG_MINOR_TYPE_DEFAULT,"1");
        //设置不插入系统通话记录
        CallApi.setCustomCfg(CallApi.CFG_CALLLOG_INSERT_SYS_DB, CallApi.CFG_VALUE_NO);
        SysApi.loadTls(new DefaultTlsHelper());
//        SysApi.loadStg(new NatStgHelper());
//        SysApi.loadStg(new SvnStgHelper());
        SysApi.setDMVersion("V1.2.88.5-02230000");
        //initial message API
        MessagingApi.init(getApplicationContext());
        MessagingApi.setAllowSendDisplayStatus(true);
        MessagingApi.openTolistUncompletedMessage();
//        //设置为IM不同源
        MessagingApi.setConfig(
                MessagingApi.CONFIG_MAJOR_USE_SYS_SMS,
                MessagingApi.CONFIG_MINOR_TYPE_DEFAULT, "0");

//        CaasOmp.init();
//        CaasOmpCfg.setString(CaasOmpCfg.EN_OMP_CFG_SERVER_IP, "205.177.226.80");
//        CaasOmpCfg.setUint(CaasOmpCfg.EN_OMP_CFG_SERVER_PORT, 8543);
       /* UI must set  the interface for safety certification.
       see detail in Developer's Guide*/
        // SysApi.setTrustCaFilePath("/rootcert.pem");
        VoipLogic.getInstance(getApplicationContext()).registerVoipReceiver();
        SettingLogic.getInstance(getApplicationContext()).registerMessageReceiver();
        /**
         * 初始化小溪推送SDK
         */
        CMIMHelper.getCmAccountManager().init(getApplicationContext(), BussinessConstants.CommonInfo.LITTLEC_APP_KEY);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        VoipLogic.getInstance(getApplicationContext()).unRegisterVoipReceiver();
        SettingLogic.getInstance(getApplicationContext()).unRegisterMessageReceiver();
        CMIMHelper.getCmAccountManager().doLogOut();
    }

}
