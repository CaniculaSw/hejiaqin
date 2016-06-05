package com.chinamobile.hejiaqin.business.logic.voip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

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
public class VoipLogic extends LogicImp implements IVoipLogic{

    public static final String TAG = VoipLogic.class.getSimpleName();

    private static final String DEFAULT_IP ="223.87.12.235";

    private static final String DEFAULT_PORT ="443";

    private static VoipLogic instance;

    private BroadcastReceiver mCallInvitationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CallSession session = (CallSession) intent.getSerializableExtra(CallApi.PARAM_CALL_SESSION);

            if (session.getType() == CallSession.TYPE_VIDEO_SHARE) {
                return;
            }

//            Intent newIntent = new Intent(context, ACT_DemoCallIncoming.class);
//            newIntent.putExtra("session_id", session.getSessionId());
//            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(newIntent);
        }
    };

    private BroadcastReceiver mLoginStatusChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int old_status = intent.getIntExtra(LoginApi.PARAM_OLD_STATUS, -1);
            int new_status = intent.getIntExtra(LoginApi.PARAM_NEW_STATUS, -1);
            int reason = intent.getIntExtra(LoginApi.PARAM_REASON, -1);
            Logger.d(VoipLogic.TAG, "the status is " + new_status);
            switch (new_status)
            {
                case LoginApi.STATUS_CONNECTED:
                    break;
                case LoginApi.STATUS_CONNECTING:
                    break;
                case LoginApi.STATUS_DISCONNECTED:
                    break;
                case LoginApi.STATUS_DISCONNECTING:
                    break;
                case LoginApi.STATUS_IDLE:
                    break;
            }
        }
    };


    private VoipLogic(Context context)
    {
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
    public void registerVoipReceiver()
    {
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mCallInvitationReceiver, new IntentFilter(CallApi.EVENT_CALL_INVITATION));
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mLoginStatusChangedReceiver, new IntentFilter(LoginApi.EVENT_LOGIN_STATUS_CHANGED));

    }

    public void unRegisterVoipReceiver(){
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mCallInvitationReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mLoginStatusChangedReceiver);
    }

    @Override
    public void login(UserInfo userInfo, String ip, String port) {
        if(!StringUtil.isNullOrEmpty(ip) && !StringUtil.isNullOrEmpty(port))
        {
            LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_IP, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, ip);
            LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_PORT, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, port);
        }else {
            LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_IP, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, DEFAULT_IP);
            LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_PORT, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, DEFAULT_PORT);
        }
        LoginCfg loginCfg = new LoginCfg();
        loginCfg.isAutoLogin = true;
        loginCfg.isRememberPassword = true;
        loginCfg.isVerified = false;
        LoginApi.login(userInfo, loginCfg);
    }

    @Override
    public void logout()
    {
        LoginApi.logout();
    }

}
