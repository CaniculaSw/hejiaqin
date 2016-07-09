package com.chinamobile.hejiaqin.business;

import android.content.Intent;

import com.chinamobile.hejiaqin.business.logic.voip.VoipLogic;
import com.huawei.rcs.RCSApplication;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.hme.HmeAudio;
import com.huawei.rcs.hme.HmeVideo;
import com.huawei.rcs.stg.NatStgHelper;
import com.huawei.rcs.system.SysApi;
import com.huawei.rcs.tls.DefaultTlsHelper;
import com.huawei.rcs.upgrade.UpgradeApi;
import com.littlec.sdk.manager.CMIMHelper;

/**
 * Created by zhanggj on 2016/6/5.
 */
public class HeApplication extends RCSApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        UpgradeApi.init(getApplicationContext());

        HmeAudio.setup(this);
        HmeVideo.setup(this);
        CallApi.init(getApplicationContext());
        CallApi.setConfig(CallApi.CONFIG_MAJOR_TYPE_VIDEO_DISPLAY_TYPE,CallApi.CONFIG_MINOR_TYPE_DEFAULT,"1");
        CallApi.setConfig(CallApi.CONFIG_MAJOR_PREVIEW_BEFORE_CONNED,
                CallApi.CONFIG_MINOR_TYPE_DEFAULT, CallApi.CFG_VALUE_YES);
        SysApi.loadTls(new DefaultTlsHelper());
        SysApi.loadStg(new NatStgHelper());

//        SysApi.loadStg(new SvnStgHelper());

//        ConferenceApi.initiateApi(getApplicationContext());

//        CaasOmp.initCMIMSdk();
//        CaasOmpCfg.setString(CaasOmpCfg.EN_OMP_CFG_SERVER_IP, "205.177.226.80");
//        CaasOmpCfg.setUint(CaasOmpCfg.EN_OMP_CFG_SERVER_PORT, 8543);

//        CaasOmpCfg.setString(CaasOmpCfg.EN_OMP_CFG_SERVER_IP, "223.87.12.235");
//        CaasOmpCfg.setUint(CaasOmpCfg.EN_OMP_CFG_SERVER_PORT, 443);

	   /* UI must set  the interface for safety certification.
       see detail in Developer's Guide*/
        // SysApi.setTrustCaFilePath("/rootcert.pem");
        startService(new Intent(HeService.SERVICE_NAME));
        VoipLogic.getInstance(getApplicationContext()).registerVoipReceiver();
        /**
         * 初始化小溪推送SDK
         */
        CMIMHelper.getCmAccountManager().init(getApplicationContext(), BussinessConstants.CommonInfo.LITTLEC_APP_KEY);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        VoipLogic.getInstance(getApplicationContext()).unRegisterVoipReceiver();
        CMIMHelper.getCmAccountManager().doLogOut();
    }

}
