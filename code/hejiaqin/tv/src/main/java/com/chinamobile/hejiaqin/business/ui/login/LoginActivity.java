package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.FailResponse;
import com.chinamobile.hejiaqin.business.model.login.req.TvLoginInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.MyCountDownTimer;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.RegistingDialog;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.UpdateDialog;
import com.chinamobile.hejiaqin.business.ui.basic.view.LoginToast;
import com.chinamobile.hejiaqin.business.ui.main.MainFragmentActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.component.qrcode.QRCodeEncoder;
import com.customer.framework.component.qrcode.core.DisplayUtils;
import com.customer.framework.component.threadpool.ThreadPoolUtil;
import com.customer.framework.component.threadpool.ThreadTask;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.StringUtil;
import com.huawei.rcs.log.LogApi;

/**
 * Created by Administrator on 2015/12/21.
 */

public class LoginActivity extends BasicActivity implements View.OnClickListener {
    ImageButton qrCode;
    LinearLayout loginBtn;
    ILoginLogic loginLogic;
    private boolean logining;
    IVoipLogic mVoipLogic;
    RegistingDialog registingDialog;
    private VerifyCodeCountDownTimer countDownTimer;
    EditText phoneEditText;
    LinearLayout phoneLayout;
    EditText verifyCodeEditText;
    LinearLayout verifyCodeLayout;
    LinearLayout getVerifyCodeButton;
    TextView verifyCodeText;
    private static boolean verifyCodeIsClicked;


    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class);
        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        qrCode = (ImageButton) findViewById(R.id.qrCode);
        loginBtn = (LinearLayout) findViewById(R.id.btn_login_ll);
        registingDialog = new RegistingDialog(this, R.style.CalendarDialog);
        phoneEditText = (EditText) findViewById(R.id.number_et);
        phoneLayout = (LinearLayout) findViewById(R.id.phone_ll);
        verifyCodeEditText = (EditText) findViewById(R.id.verify_code_et);
        verifyCodeLayout = (LinearLayout) findViewById(R.id.verify_code_ll);
        getVerifyCodeButton = (LinearLayout) findViewById(R.id.btn_commit_ll);
        verifyCodeText = (TextView) findViewById(R.id.btn_commit_tv);
        phoneLayout.setBackgroundResource(R.drawable.btn_bg_selected);
    }

    @Override
    protected void initDate() {
        createQRCode(BussinessConstants.Login.DOWNLOAD_URL, 210, qrCode);
        countDownTimer = new VerifyCodeCountDownTimer(MyCountDownTimer.MILL_IS_INFUTURE);
        if (!StringUtil.isNullOrEmpty(UserInfoCacheManager.getBindPhoneFromLoacl(getApplicationContext()))) {
            phoneEditText.setText(UserInfoCacheManager.getBindPhoneFromLoacl(getApplicationContext()));
            phoneEditText.setSelection(phoneEditText.getText().length());
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(phoneEditText, InputMethodManager.RESULT_HIDDEN);
        } else {
            phoneEditText.requestFocus();
        }

    }

    @Override
    protected void initListener() {
        loginBtn.setOnClickListener(this);
        getVerifyCodeButton.setOnClickListener(this);

        phoneEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    phoneLayout.setBackgroundResource(R.drawable.btn_bg_selected);
//                    verifyCodeLayout.setBackgroundResource(R.color.transparent);
                } else {
                    phoneLayout.setBackgroundResource(R.color.transparent);
                }
            }
        });

        verifyCodeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    verifyCodeLayout.setBackgroundResource(R.drawable.btn_bg_selected);
//                    verifyCodeLayout.setBackgroundResource(R.color.transparent);
                } else {
                    verifyCodeLayout.setBackgroundResource(R.color.transparent);
                }
            }
        });

        getVerifyCodeButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getVerifyCodeButton.setBackgroundResource(R.drawable.btn_bg_selected);
                } else {
                    getVerifyCodeButton.setBackgroundResource(R.color.transparent);
                }
            }
        });

        loginBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    loginBtn.setBackgroundResource(R.drawable.btn_bg_selected);
                } else {
                    loginBtn.setBackgroundResource(R.color.transparent);
                }
            }
        });

    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_SUCCESS_MSG_ID:
                LoginToast toast = new LoginToast(this);
                LoginToast.Position position = new LoginToast.Position();
                toast.showToast(R.string.get_verify_code_success, Toast.LENGTH_LONG, position);
                break;
            case BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_FAIL_MSG_ID:
                FailResponse failResponse = (FailResponse) msg.obj;
                showUpdateDialog(failResponse.getMsg());
                break;
            case BussinessConstants.LoginMsgID.LOGIN_SUCCESS_MSG_ID:
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_CONNECTED_MSG_ID:
                logining = true;
                Intent intent = new Intent(LoginActivity.this, MainFragmentActivity.class);
                mVoipLogic.setNotNeedVoipLogin();
                registingDialog.dismiss();
                UserInfoCacheManager.clearTvIsLogout(getApplicationContext());
                this.startActivity(intent);
                this.finishAllActivity(MainFragmentActivity.class.getName());
                break;
            case BussinessConstants.LoginMsgID.LOGIN_FAIL_MSG_ID:
                loginBtn.setFocusable(true);
                loginBtn.requestFocus();
                registingDialog.dismiss();
                FailResponse response = (FailResponse) msg.obj;
                showUpdateDialog(response.getMsg());
                logining = false;
                break;
            case BussinessConstants.CommonMsgId.LOGIN_NETWORK_ERROR_MSG_ID:
                showToast(R.string.network_error_tip, Toast.LENGTH_SHORT, null);
                logining = false;
                loginBtn.setFocusable(true);
                registingDialog.dismiss();
                loginBtn.requestFocus();
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_NET_UNAVAILABLE_MSG_ID:
                if (logining) {
                    logining = false;
                }
                loginBtn.setFocusable(true);
                registingDialog.dismiss();
                loginBtn.requestFocus();
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
                loginBtn.setFocusable(true);
                registingDialog.dismiss();
                loginBtn.requestFocus();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_ll:
                login();
                break;
            case R.id.btn_commit_ll:
                getVerifyCode();
                break;
            default:
                break;
        }

    }

    private void getVerifyCode() {
        verifyCodeIsClicked = true;
        countDownTimer.start();
        loginLogic.getTvLoginCode(UserInfoCacheManager.getTvUserID(getApplicationContext()));
    }

    private void login() {
        LoginToast toast = new LoginToast(this);
        LoginToast.Position position = new LoginToast.Position();
        if (StringUtil.isNullOrEmpty(verifyCodeEditText.getText().toString())) {
            if (!verifyCodeIsClicked) {
                toast.showToast(R.string.get_verify_code_first, Toast.LENGTH_LONG, position);
            } else {
                toast.showToast(R.string.verify_code_null, Toast.LENGTH_LONG, position);
            }
            return;
        }

        registingDialog.show("正在登录，请稍后...");
        TvLoginInfo loginInfo = new TvLoginInfo();
        loginInfo.setTvId(UserInfoCacheManager.getTvUserID(this));
        loginInfo.setCode(verifyCodeEditText.getText().toString());
        loginLogic.tvLogin(loginInfo);
    }

    private void createQRCode(String url, int size, final ImageView view) {
        QRCodeEncoder.encodeQRCode(url, DisplayUtils.dp2px(this, size),
                Color.parseColor("#000000"), Color.parseColor("#ffffff"),
                new QRCodeEncoder.Delegate() {
                    @Override
                    public void onEncodeQRCodeSuccess(Bitmap qrCode) {
                        view.setImageBitmap(qrCode);
                    }

                    @Override
                    public void onEncodeQRCodeFailure() {
                        LogUtil.e(tag, "生成中文二维码失败");
                    }
                });
    }

    private void showUpdateDialog(String text) {
        UpdateDialog.show(this, text, true);
        //        finish();
    }

    /**
     * 获取验证码计时器
     */
    private class VerifyCodeCountDownTimer extends MyCountDownTimer {

        /**
         * @param millisInFuture 总的时间
         */
        public VerifyCodeCountDownTimer(long millisInFuture) {
            super(millisInFuture);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            super.onTick(millisUntilFinished);
            getVerifyCodeButton.setEnabled(false);
            verifyCodeText.setText(millisUntilFinished
                    / 1000
                    + LoginActivity.this.getResources().getString(
                    R.string.resend_verify_code_unit));
        }

        @Override
        public void onFinish() {
            super.onFinish();
            getVerifyCodeButton.setEnabled(true);
            verifyCodeText.setText(R.string.resend_verify_code);
        }
    }
}
