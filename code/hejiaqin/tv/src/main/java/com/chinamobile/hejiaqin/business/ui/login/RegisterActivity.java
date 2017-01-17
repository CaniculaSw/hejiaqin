package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.TvLoginInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.RegistingDialog;
import com.chinamobile.hejiaqin.business.ui.main.MainFragmentActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.component.ThreadPool.ThreadPoolUtil;
import com.customer.framework.component.ThreadPool.ThreadTask;
import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.log.LogApi;

/**
 * Created by eshaohu on 17/1/4.
 */
public class RegisterActivity extends BasicActivity implements View.OnClickListener {
    private LinearLayout registerLayout;
    private ILoginLogic loginLogic;
    private IVoipLogic mVoipLogic;
    private boolean logining;
    private RegistingDialog registingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activiity_register;
    }

    @Override
    protected void initView() {
        registerLayout = (LinearLayout) findViewById(R.id.register_ll);
        registerLayout.setClickable(true);
        registerLayout.setOnClickListener(this);
        registingDialog = new RegistingDialog(this, R.style.CalendarDialog);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
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
//                if (Integer.parseInt(userInfo.getTvAccount().substring(userInfo.getTvAccount().length() - 1)) % 2 == 0) {
//                    sdkuserInfo.username = "2886544004";
//                    sdkuserInfo.password = "Vconf2015!";
//                } else {
//                    sdkuserInfo.username = "2886544005";
//                    sdkuserInfo.password = "Vconf2015!";
//                }
//                //TODO TEST
                LogUtil.i(TAG, "SDK username: " + sdkuserInfo.username);
                mVoipLogic.login(sdkuserInfo, null, null);
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_CONNECTED_MSG_ID:
                logining = true;
                Intent intent = new Intent(RegisterActivity.this, MainFragmentActivity.class);
                intent.putExtra(BussinessConstants.Login.INTENT_FROM_LONGIN, true);
                this.startActivity(intent);
                this.finishAllActivity(MainFragmentActivity.class.getName());
                break;
            case BussinessConstants.LoginMsgID.LOGIN_FAIL_MSG_ID:
//                displayErrorInfo(getString(R.string.prompt_wrong_password_or_phone_no));
//                accountEditTv.requestFocus();
                registerLayout.setFocusable(true);
                registerLayout.requestFocus();
                registingDialog.dismiss();
                showToast(R.string.voip_register_fail, Toast.LENGTH_LONG, null);
                logining = false;
                break;
            case BussinessConstants.CommonMsgId.LOGIN_NETWORK_ERROR_MSG_ID:
                showToast(R.string.network_error_tip, Toast.LENGTH_SHORT, null);
                logining = false;
                registerLayout.setFocusable(true);
                registingDialog.dismiss();
                registerLayout.requestFocus();
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_NET_UNAVAILABLE_MSG_ID:
                if (logining) {
                    logining = false;
                }
                registerLayout.setFocusable(true);
                registingDialog.dismiss();
                registerLayout.requestFocus();
                showToast(R.string.network_error_tip, Toast.LENGTH_SHORT, null);
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_DISCONNECTED_MSG_ID:
                if (logining) {
                    ThreadPoolUtil.execute(new ThreadTask() {

                        @Override
                        public void run() {
                            LogApi.copyLastLog();
                        }
                    });
                    logining = false;
                }
                registerLayout.setFocusable(true);
                registingDialog.dismiss();
                registerLayout.requestFocus();
                showToast(R.string.voip_register_fail, Toast.LENGTH_SHORT, null);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class);
        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    private void autoLogin() {
        TvLoginInfo loginInfo = new TvLoginInfo();
        loginInfo.setTvId(UserInfoCacheManager.getTvUserID(this));
        loginInfo.setTvToken(loginLogic.encryPassword(UserInfoCacheManager.getTvToken(this)));
        loginLogic.tvLogin(loginInfo);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_ll:
                registerLayout.setFocusable(false);
                registingDialog.show();
                autoLogin();
                break;
        }
    }
}
