package com.chinamobile.hejiaqin.business.logic.voip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.customer.framework.component.log.Logger;
import com.customer.framework.logic.LogicImp;
import com.customer.framework.utils.StringUtil;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.call.CallSession;
import com.huawei.rcs.login.LoginApi;
import com.huawei.rcs.login.LoginCfg;
import com.huawei.rcs.login.UserInfo;

/**
 * Created by zhanggj on 2016/6/5.
 */
public class VoipLogic extends LogicImp implements IVoipLogic {

    public static final String TAG = VoipLogic.class.getSimpleName();

    private static final String DEFAULT_IP = "223.87.12.235";

    private static final String DEFAULT_PORT = "443";

    private static VoipLogic instance;

    //当前call session
    private CallSession mCallSession;

    //当前通话类型
    private boolean isIncoming;

    private BroadcastReceiver mLoginStatusChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int old_status = intent.getIntExtra(LoginApi.PARAM_OLD_STATUS, -1);
            int new_status = intent.getIntExtra(LoginApi.PARAM_NEW_STATUS, -1);
            int reason = intent.getIntExtra(LoginApi.PARAM_REASON, -1);
            Logger.d(VoipLogic.TAG, "the status is " + new_status);
            switch (new_status) {
                case LoginApi.STATUS_CONNECTED:
                    break;
                case LoginApi.STATUS_CONNECTING:
                    break;
                case LoginApi.STATUS_DISCONNECTED:
                    if (reason == LoginApi.REASON_SRV_FORCE_LOGOUT) {
                        //TODO 服务器强制注销 如：同一账号在多终端上登录
                    } else if (reason == LoginApi.REASON_NET_UNAVAILABLE) {
                        //TODO 网络不可用
                    }

                    break;
                case LoginApi.STATUS_DISCONNECTING:
                    break;

                case LoginApi.STATUS_IDLE:
                    break;
            }
        }
    };

    private BroadcastReceiver mCallInvitationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mCallSession = (CallSession) intent.getSerializableExtra(CallApi.PARAM_CALL_SESSION);

            if (mCallSession.getType() == CallSession.TYPE_VIDEO_SHARE) {
                return;
            }
            if (mCallSession.getType() == CallSession.TYPE_AUDIO_INCOMING) {
                return;
            }
            if (mCallSession.getType() == CallSession.TYPE_VIDEO_INCOMING) {
                VoipLogic.this.sendMessage(BussinessConstants.DialMsgID.CALL_VIDEO_INCOMING_MSG_ID, mCallSession);
            }
            //TODO 保存通话记录
        }
    };

    private BroadcastReceiver mCallStatusChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mCallSession = (CallSession) intent.getSerializableExtra(CallApi.PARAM_CALL_SESSION);

            //TODO 修改通话记录
        }
    };


    private VoipLogic(Context context) {
        init(context.getApplicationContext());
    }

    /**
     * 获取单例对象
     *
     * @param context 系统的context对象
     * @return LogicBuilder对象
     */
    public static VoipLogic getInstance(Context context) {
        if (instance == null) {
            synchronized (VoipLogic.class) {
                if (instance == null) {
                    instance = new VoipLogic(context);
                }
            }
        }
        return instance;
    }

    public void registerVoipReceiver() {
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mLoginStatusChangedReceiver, new IntentFilter(LoginApi.EVENT_LOGIN_STATUS_CHANGED));
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mCallInvitationReceiver, new IntentFilter(CallApi.EVENT_CALL_INVITATION));
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mCallStatusChangedReceiver, new IntentFilter(CallApi.EVENT_CALL_STATUS_CHANGED));
    }

    public void unRegisterVoipReceiver() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mLoginStatusChangedReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mCallInvitationReceiver);
    }

    @Override
    public void login(UserInfo userInfo, String ip, String port) {
        if (!StringUtil.isNullOrEmpty(ip) && !StringUtil.isNullOrEmpty(port)) {
            LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_IP, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, ip);
            LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_PORT, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, port);
        } else {
            LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_IP, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, DEFAULT_IP);
            LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_PORT, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, DEFAULT_PORT);
        }
        LoginCfg loginCfg = new LoginCfg();
        //断网SDK自动重连设置
        loginCfg.isAutoLogin = true;
        loginCfg.isRememberPassword = true;
        loginCfg.isVerified = false;
        LoginApi.login(userInfo, loginCfg);
    }

    @Override
    public void logout() {
        LoginApi.logout();
    }

    public void call(String calleeNumber,boolean isVideo) {
        LoginApi.logout();
    }

}
