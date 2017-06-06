package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.model.FailResponse;
import com.chinamobile.hejiaqin.business.model.login.req.PasswordInfo;
import com.chinamobile.hejiaqin.business.model.login.req.VerifyInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.MyCountDownTimer;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.login.dialog.DisplayErrorDialog;
import com.customer.framework.utils.StringUtil;

/**
 * Created by eshaohu on 16/6/22.
 */
public class ResetPasswordFirstStepActivity extends BasicActivity implements View.OnClickListener {

    private EditText accountEditTx;

    private EditText verifyCodeEditTx;

    private TextView sendVerifyCodeTv;
    private Button nextActionBtn;

    private HeaderView mHeaderView;

    private ILoginLogic loginLogic;

    private VerifyCodeCountDownTimer countDownTimer;

    private boolean continueCountDown;

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) this.getLogicByInterfaceClass(ILoginLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password_first_step;
    }

    @Override
    protected void initView() {
        mHeaderView = (HeaderView) findViewById(R.id.header);
        mHeaderView.title.setText(R.string.reset_password_title);
        mHeaderView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);

        accountEditTx = (EditText) findViewById(R.id.phone_no_et);
        verifyCodeEditTx = (EditText) findViewById(R.id.verify_code_et);
        sendVerifyCodeTv = (TextView) findViewById(R.id.get_verify_code);
        nextActionBtn = (Button) findViewById(R.id.next_action_button);
        //如果上次计数还没有结束，则重新进入页面后继续
        //        if (MyCountDownTimer.getMyMillisUntilFinished() != 0) {
        //            sendVerifyCodeTv.setEnabled(false);
        //            sendVerifyCodeTv.setText(MyCountDownTimer.getMyMillisUntilFinished() / 1000 + getResources().getString(R.string.resend_verify_code_unit));
        //            countDownTimer = new VerifyCodeCountDownTimer(MyCountDownTimer.getMyMillisUntilFinished());
        //            countDownTimer.start();
        //            continueCountDown = true;
        //        }
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        sendVerifyCodeTv.setOnClickListener(this);
        nextActionBtn.setOnClickListener(this);
        mHeaderView.backImageView.setOnClickListener(this);
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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_verify_code:
                sendVerifyCodeToPhone();
                break;
            case R.id.next_action_button:
                next();
                break;
            case R.id.back_iv:
                doBack();
                break;
            default:
                break;
        }
    }

    //发送验证码
    private void sendVerifyCodeToPhone() {
        String userAccountId = accountEditTx.getText().toString();

        if (TextUtils.isEmpty(userAccountId)) {
            displayErrorInfo(getString(R.string.prompt_phone_no));
            accountEditTx.requestFocus();
            return;
        }

        if (!StringUtil.isMobileNO(userAccountId)) {
            displayErrorInfo(getString(R.string.prompt_wrong_phone_no));
            accountEditTx.requestFocus();
            return;
        }

        hideErrorInfo(null);
        loginLogic.getResetPasswordCode(userAccountId);
    }

    private void startTimer(VerifyCodeCountDownTimer timer) {
        //重新开始计时
        if (timer == null || continueCountDown) {
            timer = new VerifyCodeCountDownTimer(MyCountDownTimer.MILL_IS_INFUTURE);
            continueCountDown = false;
        }
        timer.start();
    }

    //校验验证码
    private void next() {

        String userAccountId = accountEditTx.getText().toString();

        if (TextUtils.isEmpty(userAccountId)) {
            displayErrorInfo(getString(R.string.prompt_phone_no));
            accountEditTx.requestFocus();
            return;
        }

        if (!StringUtil.isMobileNO(userAccountId)) {
            displayErrorInfo(getString(R.string.prompt_wrong_phone_no));
            accountEditTx.requestFocus();
            return;
        }
        String verifyCode = verifyCodeEditTx.getText().toString();
        if (TextUtils.isEmpty(verifyCode)) {
            displayErrorInfo(getString(R.string.prompt_verify_code));
            verifyCodeEditTx.requestFocus();
            return;
        }
        if (!StringUtil.isVerifyCode(verifyCode)) {
            displayErrorInfo(getString(R.string.prompt_wrong_verify_code_format));
            verifyCodeEditTx.requestFocus();
            return;
        }

        VerifyInfo info = new VerifyInfo();
        info.setPhone(userAccountId);
        info.setVerifyCode(verifyCode);
        hideErrorInfo(null);
        super.showWaitDailog();
        loginLogic.checkResetPasswordCode(info);
    }

    private void displayErrorInfo(String errorText) {
        final DisplayErrorDialog dialog = new DisplayErrorDialog(this, R.style.CalendarDialog,
                errorText);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        dialog.show();
    }

    private void hideErrorInfo(View view) {

    }

    private void displayRequestErrorInfo(int stringId, boolean isGet) {

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
            sendVerifyCodeTv.setEnabled(false);
            sendVerifyCodeTv.setText(millisUntilFinished
                    / 1000
                    + ResetPasswordFirstStepActivity.this.getResources().getString(
                            R.string.resend_verify_code_unit));
        }

        @Override
        public void onFinish() {
            super.onFinish();
            sendVerifyCodeTv.setEnabled(true);
            sendVerifyCodeTv.setText(R.string.resend_verify_code);
        }
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        FailResponse response;
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.RESET_GET_VERIFY_CDOE_SUCCESS_MSG_ID:
                startTimer(countDownTimer);
                super.showToast(R.string.get_verify_code_success, Toast.LENGTH_SHORT, null);
                break;
            case BussinessConstants.LoginMsgID.RESET_GET_VERIFY_CDOE_NET_ERROR_MSG_ID:
                if (countDownTimer != null) {
                    countDownTimer.stop();
                    sendVerifyCodeTv.setEnabled(true);
                    sendVerifyCodeTv.setText(R.string.resend_verify_code);
                }
                break;
            case BussinessConstants.LoginMsgID.RESET_GET_VERIFY_CDOE_FAIL_MSG_ID:
                response = (FailResponse) msg.obj;
                if ("1".equals(response.getCode())) {
                    displayErrorInfo(response.getMsg());
                }
                if (countDownTimer != null) {
                    countDownTimer.stop();
                    sendVerifyCodeTv.setEnabled(true);
                    sendVerifyCodeTv.setText(R.string.resend_verify_code);
                }
                break;
            case BussinessConstants.LoginMsgID.RESET_CHECK_VERIFY_CDOE_SUCCESS_MSG_ID:
                super.dismissWaitDailog();
                if (countDownTimer != null) {
                    countDownTimer.stop();
                    sendVerifyCodeTv.setEnabled(true);
                    sendVerifyCodeTv.setText(R.string.send_verify_code);
                }
                PasswordInfo passwordInfo = (PasswordInfo) msg.obj;
                Intent intent = new Intent(ResetPasswordFirstStepActivity.this,
                        ResetPasswordSecondStepActivity.class);
                intent.putExtra(BussinessConstants.Login.PASSWORD_INFO_KEY, passwordInfo);
                startActivity(intent);
                break;
            case BussinessConstants.LoginMsgID.RESET_CHECK_VERIFY_CDOE_FAIL_MSG_ID:
                response = (FailResponse) msg.obj;
                if ("1".equals(response.getCode())) {
                    displayErrorInfo(response.getMsg());
                }
                break;
            default:
                break;
        }
    }

}
