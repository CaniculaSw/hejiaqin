package com.chinamobile.hejiaqin.business.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.TvLoginInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.login.RegisterActivity;
import com.chinamobile.hejiaqin.business.ui.main.MainFragmentActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.log.LogApi;

public class MainActivity extends BasicActivity {

    private ILoginLogic loginLogic;
    private IVoipLogic mVoipLogic;
    private ISettingLogic settingLogic;
    private static final String TAG = "MainActivity";
    private boolean logining;


    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.TV_ACCOUNT_UNREGISTERED:
                jumpToRegisterActivity();
                break;
            case BussinessConstants.LoginMsgID.TV_ACCOUNT_REGISTERED:
                autoLogin();
                break;
            case BussinessConstants.LoginMsgID.LOGIN_SUCCESS_MSG_ID:
//                jumpToMainFragmentActivity();
                UserInfo userInfo = UserInfoCacheManager.getUserInfo(getApplicationContext());
//                if (!StringUtil.isNullOrEmpty(voipUserName) && !StringUtil.isNullOrEmpty(voipPassword)){
//                    LogUtil.i(TAG,"Update the voip setting");
//                    userInfo.setSdkAccount(voipUserName);
//                    userInfo.setSdkPassword(voipPassword);
//                }
                com.huawei.rcs.login.UserInfo sdkuserInfo = new com.huawei.rcs.login.UserInfo();
                sdkuserInfo.countryCode = "+86";
                sdkuserInfo.username = userInfo.getSdkAccount();
                sdkuserInfo.password = userInfo.getSdkPassword();
                //TODO TEST
                if (Integer.parseInt(userInfo.getTvAccount().substring(userInfo.getTvAccount().length() - 1)) % 2 == 0) {
                    sdkuserInfo.username = "2886544004";
                    sdkuserInfo.password = "Vconf2015!";
                } else {
                    sdkuserInfo.username = "2886544005";
                    sdkuserInfo.password = "Vconf2015!";
                }
                //TODO TEST
                LogUtil.i(TAG, "SDK username: " + sdkuserInfo.username);
                mVoipLogic.login(sdkuserInfo, null, null);
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_CONNECTED_MSG_ID:
                logining = true;
                Intent intent = new Intent(MainActivity.this, MainFragmentActivity.class);
                intent.putExtra(BussinessConstants.Login.INTENT_FROM_LONGIN, true);
                this.startActivity(intent);
                this.finishAllActivity(MainFragmentActivity.class.getName());
                break;
            case BussinessConstants.LoginMsgID.LOGIN_FAIL_MSG_ID:
//                displayErrorInfo(getString(R.string.prompt_wrong_password_or_phone_no));
//                accountEditTv.requestFocus();
                logining = false;
                break;
            case BussinessConstants.CommonMsgId.LOGIN_NETWORK_ERROR_MSG_ID:
                showToast(R.string.network_error_tip, Toast.LENGTH_SHORT, null);
                logining = false;
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_NET_UNAVAILABLE_MSG_ID:
                if (logining) {
                    showToast(R.string.network_error_tip, Toast.LENGTH_SHORT, null);
                    logining = false;
                }
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_DISCONNECTED_MSG_ID:
                if (logining) {
                    showToast(R.string.voip_register_fail, Toast.LENGTH_SHORT, null);
                    LogApi.copyLastLog();
                    logining = false;
                }
                break;
            default:
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (loginLogic.hasLogined() && mVoipLogic.hasLogined()) {
//            jumpToMainFragmentActivity();
//        } else {
//            jumpToLoginActivity();
//        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDate() {
        TvLoginInfo tvLoginInfo = new TvLoginInfo();
        tvLoginInfo.setTvId("2886544004");
        tvLoginInfo.setTvToken("fdsfsdfwe23ef3eff");
        loginLogic.checkTvAccount(tvLoginInfo);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class);
        settingLogic = (ISettingLogic) super.getLogicByInterfaceClass(ISettingLogic.class);
        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    private void autoLogin() {
        TvLoginInfo loginInfo = new TvLoginInfo();
        loginInfo.setTvId("2886544004");
        loginInfo.setTvToken(loginLogic.encryPassword("fdsfneiwjfkdsn3"));
        loginLogic.tvLogin(loginInfo);
    }

    private void jumpToMainFragmentActivity() {
        Intent intent = new Intent(MainActivity.this, MainFragmentActivity.class);
        startActivity(intent);
        finish();
    }

    private void jumpToRegisterActivity() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
