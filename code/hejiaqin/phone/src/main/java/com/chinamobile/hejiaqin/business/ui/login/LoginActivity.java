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

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.main.MainFragmentActivity;
import com.customer.framework.utils.StringUtil;


public class LoginActivity extends BasicActivity implements View.OnClickListener {

    private ILoginLogic loginLogic;
    private EditText accountEditTv;
    private EditText passwdEditTv;
    private Button signBtn;
    private TextView forgetPwdTv;
    private Button registerBtn;

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) this.getLogicByInterfaceClass(ILoginLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        accountEditTv = (EditText) findViewById(R.id.account_edit_tv);
        passwdEditTv = (EditText) findViewById(R.id.password_edit_tv);
        signBtn = (Button) findViewById(R.id.sign_in_button);
        forgetPwdTv = (TextView) findViewById(R.id.forget_pwd_tv);
        registerBtn = (Button) findViewById(R.id.register_button);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        signBtn.setOnClickListener(this);
        forgetPwdTv.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        accountEditTv.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwdEditTv.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                login();
                break;
            case R.id.forget_pwd_tv:
                Intent forgetIntent = new Intent(LoginActivity.this, ResetPasswordFirstStepActivity.class);
                startActivity(forgetIntent);
                break;
            case R.id.register_button:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterFirstStepActivity.class);
                passwdEditTv.setText("");
                startActivity(registerIntent);
                break;
        }
    }

    private void login() {
        String account = accountEditTv.getText().toString();
        if (TextUtils.isEmpty(account)) {
            accountEditTv.requestFocus();
            return;
        }
        String password = passwdEditTv.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwdEditTv.requestFocus();
            return;
        }
        if (!StringUtil.isMobileNO(account)) {
            accountEditTv.requestFocus();
            return;
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setPhone(accountEditTv.getText().toString());
        loginInfo.setPassword(loginLogic.encryPassword(passwdEditTv.getText().toString()));
        hideErrorInfo(null);
        //super.showWaitDailog();
        loginLogic.login(loginInfo);
    }

    private void displayErrorInfo(int stringId,View view) {

    }


    private void hideErrorInfo(View view) {

    }

    private void displayRequestErrorInfo() {

    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.LOGIN_SUCCESS_MSG_ID:
                super.dismissWaitDailog();
                Intent intent = new Intent(LoginActivity.this, MainFragmentActivity.class);
                this.startActivity(intent);
                this.finishAllActivity(MainFragmentActivity.class.getName());
                break;
            case BussinessConstants.LoginMsgID.LOGIN_FAIL_MSG_ID:
                super.dismissWaitDailog();
                displayRequestErrorInfo();
                accountEditTv.requestFocus();
                break;
            default:
                break;
        }
    }

}

