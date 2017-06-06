package com.chinamobile.hejiaqin.business.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.Const;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.FailResponse;
import com.chinamobile.hejiaqin.business.model.login.RespondInfo;
import com.chinamobile.hejiaqin.business.model.login.req.TvLoginInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.UpdateDialog;
import com.chinamobile.hejiaqin.business.ui.login.CreateAccountActivity;
import com.chinamobile.hejiaqin.business.ui.login.LoginActivity;
import com.chinamobile.hejiaqin.business.ui.main.MainFragmentActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.component.threadpool.ThreadPoolUtil;
import com.customer.framework.component.threadpool.ThreadTask;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.StringUtil;
import com.huawei.rcs.log.LogApi;

/***/
public class MainActivity extends BasicActivity {

    private ILoginLogic loginLogic;
    private IVoipLogic mVoipLogic;
    private ISettingLogic settingLogic;
    private boolean logining;
    private ProgressBar progressBar;

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.SettingMsgID.TEST_ADAPT_FAIL:
                progressBar.setVisibility(View.INVISIBLE);
                RespondInfo info = (RespondInfo) msg.obj;
                if (!StringUtil.isNullOrEmpty(info.getMsg())) {
                    LogUtil.d(tag, "msg is: " + info.getMsg());
                    showUpdateDialog(info.getMsg());
                } else {
                    showUpdateDialog();
                }
                break;
            case BussinessConstants.SettingMsgID.TEST_ADAPT_PASS:
                break;
            case BussinessConstants.SettingMsgID.TEST_ADAPT_ERROR:
                progressBar.setVisibility(View.INVISIBLE);
                showUpdateDialog(getString(R.string.exception_tips));
                break;
            case BussinessConstants.LoginMsgID.TV_ACCOUNT_UNREGISTERED:
                jumpToRegisterActivity();
                break;
            case BussinessConstants.LoginMsgID.TV_ACCOUNT_REGISTERED:
                if (UserInfoCacheManager.getTvIsLogout(getApplicationContext())
                        && !UserInfoCacheManager.getTvAccount(getApplicationContext()).equals(
                        "unknown")) {
                    jumpToLoginActivity();
                }
                break;
            case BussinessConstants.LoginMsgID.LOGIN_SUCCESS_MSG_ID:
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_CONNECTED_MSG_ID:
                logining = true;
                Intent intent = new Intent(MainActivity.this, MainFragmentActivity.class);
                mVoipLogic.setNotNeedVoipLogin();
                this.startActivity(intent);
                this.finishAllActivity(MainFragmentActivity.class.getName());
                break;
            case BussinessConstants.LoginMsgID.LOGIN_FAIL_MSG_ID:
                if (msg.obj != null) {
                    FailResponse response = (FailResponse) msg.obj;
                    if (!StringUtil.isNullOrEmpty(response.getMsg())) {
                        showUpdateDialog(response.getMsg());
                    } else {
                        showUpdateDialog(getString(R.string.voip_register_fail));
                    }
                } else {
                    showToast(R.string.voip_register_fail, Toast.LENGTH_LONG, null);
                }

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
                    ThreadPoolUtil.execute(new ThreadTask() {
                        @Override
                        public void run() {
                            LogApi.copyLastLog();
                        }
                    });
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
        if (Const.getLoginStatus() == Const.LOGINSTATUS.LOGIN_FAILED) {
            getSTBConfig();
            settingLogic.testAdapt();
        } else if (Const.getLoginStatus() == Const.LOGINSTATUS.LOGINED) {
            jumpToMainFragmentActivity();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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
        loginInfo.setTvId(UserInfoCacheManager.getTvUserID(this));
        loginInfo.setTvToken(loginLogic.encryPassword(UserInfoCacheManager.getTvToken(this)));
        loginLogic.tvLogin(loginInfo);
    }

    private void jumpToMainFragmentActivity() {
        Intent intent = new Intent(MainActivity.this, MainFragmentActivity.class);
        startActivity(intent);
        finish();
    }

    private void jumpToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finishAllActivity(LoginActivity.class.getName());
    }

    private void showUpdateDialog() {
        UpdateDialog.show(this);
        //        finish();
    }

    private void showUpdateDialog(String text) {
        UpdateDialog.show(this, text);
        //        finish();
    }

    private void jumpToRegisterActivity() {
        Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean getSTBConfig() {
        boolean flag = true;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(BussinessConstants.Login.BASE_URI), null,
                null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            if (StringUtil.isNullOrEmpty(cursor.getString(cursor.getColumnIndex("UserId")))
                    || StringUtil
                    .isNullOrEmpty(cursor.getString(cursor.getColumnIndex("UserToken")))) {
                progressBar.setVisibility(View.INVISIBLE);
                showUpdateDialog(getString(R.string.exception_tips));
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                return false;
            }
            UserInfoCacheManager.saveSTBConfig(this,
                    cursor.getString(cursor.getColumnIndex("UserId")),
                    cursor.getString(cursor.getColumnIndex("UserToken")),
                    cursor.getString(cursor.getColumnIndex("SoftwareVersion")));
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            showUpdateDialog(getString(R.string.exception_tips));
            flag = false;
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return flag;
    }
}
