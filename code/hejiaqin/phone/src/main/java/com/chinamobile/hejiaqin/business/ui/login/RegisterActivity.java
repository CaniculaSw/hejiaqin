package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
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
import com.chinamobile.hejiaqin.business.model.login.req.RegisterFirstStepInfo;
import com.chinamobile.hejiaqin.business.model.login.req.VerifyInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.customer.framework.utils.StringUtil;
import com.chinamobile.hejiaqin.business.ui.basic.MyCountDownTimer;

/**
 * Created by Xiadong on 2016/4/27
 */
public class RegisterActivity extends BasicActivity implements View.OnClickListener {

    private EditText accountEditTx;
    private EditText passwordEditTx;
    private EditText verifyCodeEditTx;
    private EditText nicknameEditTx;
    private Button sendVerifyCodeBtn;
    private Button nextActionBtn;

    private ILoginLogic loginLogic;

    private VerifyCodeCountDownTimer countDownTimer;
    private TextView errorInfoTv;
    private int errorFromViewId;
    private boolean getVerifyRequestFailed;
    private boolean checkVerifyRequestFailed;
    private boolean continueCountDown;

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) this.getLogicByInterfaceClass(ILoginLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        TextView headerTitleTx = (TextView) findViewById(R.id.headertitle);
        headerTitleTx.setText(R.string.register_title);

        accountEditTx = (EditText) findViewById(R.id.account_edit_tx);
        passwordEditTx = (EditText) findViewById(R.id.password_edit_tx);
        verifyCodeEditTx = (EditText) findViewById(R.id.verify_code_edit_tx);
        nicknameEditTx = (EditText) findViewById(R.id.nickname_edit_tx);
        sendVerifyCodeBtn = (Button) findViewById(R.id.send_verify_code_button);
        nextActionBtn = (Button) findViewById(R.id.next_action_button);
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
        sendVerifyCodeBtn.setOnClickListener(this);
        nextActionBtn.setOnClickListener(this);
        accountEditTx.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hideErrorInfo(accountEditTx);
            }
        });
        passwordEditTx.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hideErrorInfo(passwordEditTx);
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
        nicknameEditTx.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hideErrorInfo(nicknameEditTx);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_verify_code_button:
                sendVerifyCodeToPhone();
                break;
            case R.id.next_action_button:
                next();
                break;
        }
    }

    //发送验证码
    private void sendVerifyCodeToPhone() {
        String userAccountId = accountEditTx.getText().toString();

        if (TextUtils.isEmpty(userAccountId)) {
            displayErrorInfo(R.string.account_null, accountEditTx);
            accountEditTx.requestFocus();
            return;
        }

        if (!StringUtil.isMobileNO(userAccountId)) {
            displayErrorInfo(R.string.account_illegal, accountEditTx);
            accountEditTx.requestFocus();
            return;
        }

        //重新开始计时
        if (countDownTimer == null || continueCountDown) {
            countDownTimer = new VerifyCodeCountDownTimer(MyCountDownTimer.MILL_IS_INFUTURE);
            continueCountDown = false;
        }
        countDownTimer.start();
        hideErrorInfo(null);
        loginLogic.getVerifyCode(userAccountId);
    }

    //校验验证码
    private void next() {

        String userAccountId = accountEditTx.getText().toString();

        if (TextUtils.isEmpty(userAccountId)) {
            displayErrorInfo(R.string.account_null, accountEditTx);
            accountEditTx.requestFocus();
            return;
        }

        if (!StringUtil.isMobileNO(userAccountId)) {
            displayErrorInfo(R.string.account_illegal, accountEditTx);
            accountEditTx.requestFocus();
            return;
        }
        String verifyCode = verifyCodeEditTx.getText().toString();
        if (TextUtils.isEmpty(verifyCode)) {
            displayErrorInfo(R.string.verify_code_null, accountEditTx);
            accountEditTx.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(passwordEditTx.getText().toString())) {
            displayErrorInfo(R.string.password_null, passwordEditTx);
            passwordEditTx.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(nicknameEditTx.getText().toString())) {
            displayErrorInfo(R.string.nick_name_null, nicknameEditTx);
            nicknameEditTx.requestFocus();
            return;
        }
        VerifyInfo info = new VerifyInfo();
        info.setPhone(userAccountId);
        info.setVerifyCode(verifyCode);
        hideErrorInfo(null);
        super.showWaitDailog();
        loginLogic.checkVerifyCode(info);
    }

    private void displayErrorInfo(int stringId, View view) {
        errorInfoTv.setText(getResources().getString(R.string.error_mark) + getResources().getString(stringId));
        errorInfoTv.setVisibility(View.VISIBLE);
        errorFromViewId = view.getId();
        getVerifyRequestFailed = false;
        checkVerifyRequestFailed = false;
    }


    private void hideErrorInfo(View view) {
        if (view != null && errorFromViewId == view.getId()) {
            errorFromViewId = 0;
            errorInfoTv.setVisibility(View.INVISIBLE);
            errorInfoTv.setText("");
        } else if ((view == null || view.getId() == accountEditTx.getId()) && getVerifyRequestFailed) {
            getVerifyRequestFailed = false;
            errorInfoTv.setVisibility(View.INVISIBLE);
            errorInfoTv.setText("");
        } else if ((view == null || view.getId() == accountEditTx.getId() || view.getId() == verifyCodeEditTx.getId()) && checkVerifyRequestFailed) {
            checkVerifyRequestFailed = false;
            errorInfoTv.setVisibility(View.INVISIBLE);
            errorInfoTv.setText("");
        }
    }

    private void displayRequestErrorInfo(int stringId, boolean isGet) {
        errorInfoTv.setText(getResources().getString(R.string.error_mark) + getResources().getString(stringId));
        errorInfoTv.setVisibility(View.VISIBLE);
        errorFromViewId=0;
        if (isGet) {
            getVerifyRequestFailed = true;
            checkVerifyRequestFailed = false;
        } else {
            getVerifyRequestFailed = false;
            checkVerifyRequestFailed = true;
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
            sendVerifyCodeBtn.setText(millisUntilFinished / 1000 + RegisterActivity.this.getResources().getString(R.string.resend_verify_code_unit));
        }

        @Override
        public void onFinish() {
            super.onFinish();
            sendVerifyCodeBtn.setEnabled(true);
            sendVerifyCodeBtn.setText(R.string.resend_verify_code);
        }
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        String code = "";
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_SUCCESS_MSG_ID:
                super.showToast(R.string.get_verify_code_success, Toast.LENGTH_SHORT,null);
                break;
            case BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_NET_ERROR_MSG_ID:
                if(countDownTimer!=null)
                {
                    countDownTimer.stop();
                    sendVerifyCodeBtn.setEnabled(true);
                    sendVerifyCodeBtn.setText(R.string.resend_verify_code);
                }
                break;
            case BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_FAIL_MSG_ID:
                if (msg.obj != null) {
                    code = (String) msg.obj;
                }
                if (BussinessConstants.LoginHttpErrorCode.HAS_REGISTER.equals(code)) {
                    displayRequestErrorInfo(R.string.has_registered, true);
                } else {
                    displayRequestErrorInfo(R.string.verify_other_error, true);
                }
                if(countDownTimer!=null)
                {
                    countDownTimer.stop();
                    sendVerifyCodeBtn.setEnabled(true);
                    sendVerifyCodeBtn.setText(R.string.resend_verify_code);
                }
                break;
            case BussinessConstants.LoginMsgID.CHECK_VERIFY_CDOE_SUCCESS_MSG_ID:
                super.dismissWaitDailog();
                if(countDownTimer!=null)
                {
                    countDownTimer.stop();
                }
                RegisterFirstStepInfo registerFirstStepInfo = new RegisterFirstStepInfo();
                registerFirstStepInfo.setName(nicknameEditTx.getText().toString());
                registerFirstStepInfo.setLoginid(accountEditTx.getText().toString());
                registerFirstStepInfo.setPwd(passwordEditTx.getText().toString());
                registerFirstStepInfo.setPhone(accountEditTx.getText().toString());
                Intent intent = new Intent(RegisterActivity.this, RegisterDetailActivity.class);
                intent.putExtra(BussinessConstants.Login.INTENT_REGISTER_FIRST_INFO, registerFirstStepInfo);
                startActivity(intent);
                break;
            case BussinessConstants.LoginMsgID.CHECK_VERIFY_CDOE_FAIL_MSG_ID:
                super.dismissWaitDailog();
                if (msg.obj != null) {
                    code = (String) msg.obj;
                }
                if (BussinessConstants.LoginHttpErrorCode.VERIFY_CODE_DISABLE.equals(code)) {
                    displayRequestErrorInfo(R.string.check_verify_code_disable, false);
                } else {
                    displayRequestErrorInfo(R.string.check_verify_code_diff, false);
                }
                break;
            default:
                break;
        }
    }

}
