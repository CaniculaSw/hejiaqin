package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.business.model.login.req.TvLoginInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.main.MainFragmentActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.LogUtil;


public class LoginActivity extends BasicActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private ILoginLogic loginLogic;
    private IVoipLogic voipLogic;
    private boolean logining;
    private EditText accountEditTv;
    private EditText passwdEditTv;
    private ImageButton deleteBtn;
    private LinearLayout loginBtn;
    private LinearLayout accountLayout;
    private RelativeLayout accountRl;
    private LinearLayout passwordLayout;
    private LinearLayout passwordSelectedLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
//        String phone = intent.getStringExtra("phone");
//        String passowrd = intent.getStringExtra("password");
//        //注册成功,自动登陆
//        if (!StringUtil.isNullOrEmpty(phone) && !StringUtil.isNullOrEmpty(passowrd)) {
//            accountEditTv.setText(phone);
//            passwdEditTv.setText(passowrd);
//            login();
//        }
    }

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) this.getLogicByInterfaceClass(ILoginLogic.class);
        voipLogic = (IVoipLogic) this.getLogicByInterfaceClass(IVoipLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        accountEditTv = (EditText) findViewById(R.id.account_et);
        passwdEditTv = (EditText) findViewById(R.id.password_et);
        accountLayout = (LinearLayout) findViewById(R.id.account_ll);
        accountRl = (RelativeLayout) findViewById(R.id.account_rl);
        passwordLayout = (LinearLayout) findViewById(R.id.password_ll);
        deleteBtn = (ImageButton) findViewById(R.id.delete_all_btn);
        loginBtn = (LinearLayout) findViewById(R.id.login_ll);
        passwordSelectedLayout = (LinearLayout) findViewById(R.id.password_selected_ll);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        accountEditTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    accountLayout.setBackgroundResource(R.drawable.login_btn_bg);
                    accountRl.setBackgroundResource(R.color.transparent);
                }else {
                    accountLayout.setBackgroundResource(R.color.transparent);
                    accountRl.setBackgroundResource(R.drawable.edittext_bg);
                }

            }
        });
        deleteBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    accountLayout.setBackgroundResource(R.drawable.login_btn_bg);
                    accountRl.setBackgroundResource(R.color.transparent);
                }else {
                    accountLayout.setBackgroundResource(R.color.transparent);
                    accountRl.setBackgroundResource(R.drawable.edittext_bg);
                }
            }
        });
        passwdEditTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    passwordSelectedLayout.setBackgroundResource(R.drawable.login_btn_bg);
                    passwordLayout.setBackgroundResource(R.color.transparent);
                }else {
                    passwordSelectedLayout.setBackgroundResource(R.color.transparent);
                    passwordLayout.setBackgroundResource(R.drawable.edittext_bg);
                }
            }
        });
        loginBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete_all_btn:
                accountEditTv.setText("");
                accountEditTv.requestFocus();
                break;
            case R.id.login_ll:
                login();
                break;
            default:
                break;
        }
//        switch (view.getId()) {
//            case R.id.sign_in_button:
//                if (logining) {
//                    return;
//                }
//                login();
//                break;
//            case R.id.forget_pwd_tv:
//                Intent forgetIntent = new Intent(LoginActivity.this, ResetPasswordFirstStepActivity.class);
//                startActivity(forgetIntent);
//                break;
//            case R.id.register_button:
//                Intent registerIntent = new Intent(LoginActivity.this, RegisterFirstStepActivity.class);
//                passwdEditTv.setText("");
//                startActivity(registerIntent);
//                break;
//            case R.id.clear_btn:
//                accountEditTv.setText("");
//                accountEditTv.requestFocus();
//                break;
//        }
    }

    private void login() {
        TvLoginInfo loginInfo = new TvLoginInfo();
        loginInfo.setTvId(accountEditTv.getText().toString());
        loginInfo.setTvToken(loginLogic.encryPassword(passwdEditTv.getText().toString()));
        loginLogic.tvLogin(loginInfo);
//        String account = accountEditTv.getText().toString();
//        if (TextUtils.isEmpty(account)) {
//            accountEditTv.requestFocus();
//            displayErrorInfo(getString(R.string.prompt_phone_no));
//            return;
//        }
//        String password = passwdEditTv.getText().toString();
//        if (TextUtils.isEmpty(password)) {
//            passwdEditTv.requestFocus();
//            displayErrorInfo(getString(R.string.prompt_password));
//            return;
//        }
//        if (!StringUtil.isMobileNO(account)) {
//            accountEditTv.requestFocus();
//            displayErrorInfo(getString(R.string.prompt_wrong_phone_no));
//            return;
//        }
//        if (!StringUtil.isPassword(password)) {
//            passwdEditTv.requestFocus();
//            displayErrorInfo(getString(R.string.prompt_wrong_password_format));
//            return;
//        }
//        logining = true;
//        LoginInfo loginInfo = new LoginInfo();
//        loginInfo.setPhone(accountEditTv.getText().toString());
//        loginInfo.setPassword(loginLogic.encryPassword(passwdEditTv.getText().toString()));
//        inputTheVOIPSetting(loginInfo);
//        loginLogic.login(loginInfo);
    }

    private void displayErrorInfo(String errorText) {
//        final DisplayErrorDialog dialog = new DisplayErrorDialog(this, R.style.CalendarDialog, errorText);
//        Window window = dialog.getWindow();
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        window.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(params);
//        dialog.show();
    }

    //TODO For test
    private void inputTheVOIPSetting(final LoginInfo loginInfo) {
//        final VoipSettingDialog.Builder builder = new VoipSettingDialog.Builder(LoginActivity.this);
//        if(voipUserName==null)
//        {
//            String account = accountEditTv.getText().toString();
//            //TODO TEST
//            if(Integer.parseInt(account.substring(account.length() - 1)) % 2 == 0) {
//                builder.setDefaultInfo("2886544004", "Vconf2015!");
//            }
//            else
//            {
//                builder.setDefaultInfo("2886544005", "Vconf2015!");
//            }
//            //TODO TEST
//        }else{
//            builder.setDefaultInfo(voipUserName,voipPassword);
//        }
//
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                voipUserName = builder.getUserName();
//                voipPassword = builder.getPassword();
//                LogUtil.i("LoginActivity","The VOIP user name is: "+voipUserName+" The VOIP password is: "+voipPassword);
//                loginLogic.login(loginInfo);
//                dialog.dismiss();
//            }
//        });
//
//        Dialog dialog = builder.create();
//        dialog.setCancelable(false);
//        dialog.show();
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
                sdkuserInfo.countryCode="+86";
                sdkuserInfo.username = userInfo.getSdkAccount();
                sdkuserInfo.password = userInfo.getSdkPassword();
                //TODO TEST
                if(Integer.parseInt(userInfo.getTvAccount().substring(userInfo.getTvAccount().length() - 1)) % 2 == 0) {
                    sdkuserInfo.username = "2886544004";
                    sdkuserInfo.password = "Vconf2015!";
                }
                else
                {
                    sdkuserInfo.username = "2886544005";
                    sdkuserInfo.password = "Vconf2015!";
                }
                //TODO TEST
                LogUtil.i(TAG,"SDK username: " + sdkuserInfo.username);
                voipLogic.login(sdkuserInfo,null,null);
            case BussinessConstants.DialMsgID.VOIP_REGISTER_CONNECTED_MSG_ID:
                Intent intent = new Intent(LoginActivity.this, MainFragmentActivity.class);
                intent.putExtra(BussinessConstants.Login.INTENT_FROM_LONGIN, true);
                this.startActivity(intent);
                this.finishAllActivity(MainFragmentActivity.class.getName());
                break;
            case BussinessConstants.LoginMsgID.LOGIN_FAIL_MSG_ID:
                displayErrorInfo(getString(R.string.prompt_wrong_password_or_phone_no));
                accountEditTv.requestFocus();
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
                    logining = false;
                }
                break;
            default:
                break;
        }
    }

}

