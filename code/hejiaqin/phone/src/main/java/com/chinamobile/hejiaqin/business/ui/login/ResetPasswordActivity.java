package com.chinamobile.hejiaqin.business.ui.login;

import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.MyCountDownTimer;
import com.customer.framework.utils.StringUtil;

/**
 * Created by Xiadong on 2016/4/27.
 */
public class ResetPasswordActivity extends BasicActivity implements View.OnClickListener {

    private EditText phoneEditTx;
    private EditText verifyCodeEditTx;
    private EditText pwdEditTx;
    private EditText repeatPwdEditTx;

    private Button sendVerifyCodeBtn;
    private Button submitButton;

    private VerifyCodeCountDownTimer countDownTimer;
    private TextView errorInfoTv;
    private int errorFromViewId;
    private boolean getVerifyRequestFailed;
    private boolean updatePwdRequestFailed;
    private boolean notMatch;

    private ILoginLogic loginLogic;
    private boolean continueCountDown;


    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) this.getLogicByInterfaceClass(ILoginLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void initView() {
        TextView headerTitleTv = (TextView) findViewById(R.id.headertitle);
        headerTitleTv.setText(R.string.reset_password_title);

        phoneEditTx = (EditText) findViewById(R.id.phone_no_edit_tx);
        verifyCodeEditTx = (EditText) findViewById(R.id.verify_code_edit_tx);
        pwdEditTx = (EditText) findViewById(R.id.new_password_edit_tx);
        repeatPwdEditTx = (EditText) findViewById(R.id.repeat_password_edit_tx);
        sendVerifyCodeBtn = (Button) findViewById(R.id.send_verify_code_button);
        submitButton = (Button) findViewById(R.id.submit_action_button);
        errorInfoTv = (TextView) findViewById(R.id.error_info_tv);
        //如果上次计数还没有结束，则重新进入页面后继续
        if(MyCountDownTimer.getMyMillisUntilFinished()!=0)
        {
            sendVerifyCodeBtn.setEnabled(false);
            sendVerifyCodeBtn.setText(MyCountDownTimer.getMyMillisUntilFinished() / 1000 + getResources().getString(R.string.resend_verify_code_unit));
            countDownTimer = new VerifyCodeCountDownTimer(MyCountDownTimer.getMyMillisUntilFinished());
            countDownTimer.start();
            continueCountDown =true;
        }
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        submitButton.setOnClickListener(this);
        sendVerifyCodeBtn.setOnClickListener(this);
        phoneEditTx.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hideErrorInfo(phoneEditTx);
            }
        });
        verifyCodeEditTx.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hideErrorInfo(verifyCodeEditTx);
            }
        });

        pwdEditTx.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hideErrorInfo(pwdEditTx);
            }
        });

        repeatPwdEditTx.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hideErrorInfo(repeatPwdEditTx);
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_verify_code_button:
                sendVerifyCodeToPhone();
                break;
            case R.id.submit_action_button:
                resetPwd();
                break;
        }
    }

    //发送验证码
    private void sendVerifyCodeToPhone() {
        String userAccountId = phoneEditTx.getText().toString();

        if (TextUtils.isEmpty(userAccountId)) {
            displayErrorInfo(R.string.account_null, phoneEditTx);
            phoneEditTx.requestFocus();
            return;
        }

        if (!StringUtil.isMobileNO(userAccountId)) {
            displayErrorInfo(R.string.account_illegal, phoneEditTx);
            phoneEditTx.requestFocus();
            return;
        }

        if (countDownTimer == null || continueCountDown) {
            countDownTimer = new VerifyCodeCountDownTimer(MyCountDownTimer.MILL_IS_INFUTURE);
            continueCountDown = false;
        }
        countDownTimer.start();
        //TODO:获取验证码(和注册时的获取验证码有差别)
//        hideErrorInfo(null);
    }

    private void resetPwd() {
        String userAccountId = phoneEditTx.getText().toString();

        if (TextUtils.isEmpty(userAccountId)) {
            displayErrorInfo(R.string.account_null, phoneEditTx);
            phoneEditTx.requestFocus();
            return;
        }

        if (!StringUtil.isMobileNO(userAccountId)) {
            displayErrorInfo(R.string.account_illegal, phoneEditTx);
            phoneEditTx.requestFocus();
            return;
        }
        String verifyCode = verifyCodeEditTx.getText().toString();
        if (TextUtils.isEmpty(verifyCode)) {
            displayErrorInfo(R.string.verify_code_null, verifyCodeEditTx);
            verifyCodeEditTx.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pwdEditTx.getText().toString())) {
            displayErrorInfo(R.string.password_null, pwdEditTx);
            pwdEditTx.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(repeatPwdEditTx.getText().toString())) {
            displayErrorInfo(R.string.password_null, repeatPwdEditTx);
            repeatPwdEditTx.requestFocus();
            return;
        }

        if (!pwdEditTx.getText().toString().equals(repeatPwdEditTx.getText().toString())) {
            displayNotMatchInfo(R.string.password_not_match);
            repeatPwdEditTx.requestFocus();
        }
        //TODO:修改密码
//        hideErrorInfo(null);
//        super.showWaitDailog();
    }

    private void displayErrorInfo(int stringId, View view) {
        errorInfoTv.setText(getResources().getString(R.string.error_mark) + getResources().getString(stringId));
        errorInfoTv.setVisibility(View.VISIBLE);
        errorFromViewId = view.getId();
        getVerifyRequestFailed = false;
        updatePwdRequestFailed = false;
        notMatch = false;
    }


    private void hideErrorInfo(View view) {
        if (view != null && errorFromViewId == view.getId()) {
            errorFromViewId = 0;
            errorInfoTv.setVisibility(View.INVISIBLE);
            errorInfoTv.setText("");
        } else if ((view == null && view.getId() == phoneEditTx.getId()) && getVerifyRequestFailed) {
            getVerifyRequestFailed = false;
            errorInfoTv.setVisibility(View.INVISIBLE);
            errorInfoTv.setText("");
        } else if ((view != null && (view.getId() == pwdEditTx.getId() || view.getId() == repeatPwdEditTx.getId())) && notMatch) {
            notMatch = false;
            errorInfoTv.setVisibility(View.INVISIBLE);
            errorInfoTv.setText("");
        } else if (updatePwdRequestFailed) {
            updatePwdRequestFailed = false;
            errorInfoTv.setVisibility(View.INVISIBLE);
            errorInfoTv.setText("");
        }
    }

    private void displayRequestErrorInfo(int stringId, boolean isGet) {
        errorInfoTv.setText(getResources().getString(R.string.error_mark) + getResources().getString(stringId));
        errorInfoTv.setVisibility(View.VISIBLE);
        errorFromViewId = 0;
        notMatch = false;
        if (isGet) {
            getVerifyRequestFailed = true;
            updatePwdRequestFailed = false;
        } else {
            getVerifyRequestFailed = false;
            updatePwdRequestFailed = true;
        }
    }

    private void displayNotMatchInfo(int stringId) {
        errorInfoTv.setText(getResources().getString(R.string.error_mark) + getResources().getString(stringId));
        errorInfoTv.setVisibility(View.VISIBLE);
        errorFromViewId = 0;
        notMatch = true;
        getVerifyRequestFailed = false;
        updatePwdRequestFailed = false;
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        String code = "";
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.RESET_GET_VERIFY_CDOE_SUCCESS_MSG_ID:
                super.showToast(R.string.get_verify_code_success, Toast.LENGTH_SHORT,null);
                break;
            case BussinessConstants.LoginMsgID.RESET_GET_VERIFY_CDOE_NET_ERROR_MSG_ID:
                if(countDownTimer!=null)
                {
                    countDownTimer.stop();
                    sendVerifyCodeBtn.setEnabled(true);
                    sendVerifyCodeBtn.setText(R.string.resend_verify_code);
                }
                break;
            case BussinessConstants.LoginMsgID.RESET_GET_VERIFY_CDOE_FAIL_MSG_ID:
                if (msg.obj != null) {
                    code = (String) msg.obj;
                }
                //TODO:网络接口提供后增加未注册错误的提示
//                if (BussinessConstants.LoginHttpErrorCode.HAS_NOT_REGISTER.equals(code)) {
//                    displayRequestErrorInfo(R.string.has_not_registered, true);
//                } else {
                displayRequestErrorInfo(R.string.verify_other_error, true);
//                }
                if(countDownTimer!=null)
                {
                    countDownTimer.stop();
                    sendVerifyCodeBtn.setEnabled(true);
                    sendVerifyCodeBtn.setText(R.string.resend_verify_code);
                }
                break;
            case BussinessConstants.LoginMsgID.RESET_PWD_SUCCESS_MSG_ID:
                super.dismissWaitDailog();
                super.showToast(R.string.reset_success, Toast.LENGTH_SHORT,null);
                this.finish();
                break;
            case BussinessConstants.LoginMsgID.RESET_PWD_FAIL_MSG_ID:
                super.dismissWaitDailog();
                //TODO：提示修改失败
                break;
            default:
                break;
        }

    }

    /**
     * 获取验证码计时器
     */
    private class VerifyCodeCountDownTimer extends MyCountDownTimer {

        /**
         * @param millisInFuture    总的时间
         */
        public VerifyCodeCountDownTimer(long millisInFuture) {
            super(millisInFuture);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            super.onTick(millisUntilFinished);
            sendVerifyCodeBtn.setEnabled(false);
            sendVerifyCodeBtn.setText(millisUntilFinished / 1000 + ResetPasswordActivity.this.getResources().getString(R.string.resend_verify_code_unit));
        }

        @Override
        public void onFinish() {
            super.onFinish();
            sendVerifyCodeBtn.setEnabled(true);
            sendVerifyCodeBtn.setText(R.string.resend_verify_code);
        }
    }
}