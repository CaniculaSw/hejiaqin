package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.business.model.login.req.RegisterSecondStepInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.main.MainFragmentActivity;
import com.customer.framework.utils.StringUtil;

/**
 * Created by Xiadong on 2016/4/27
 */
public class RegisterSecondStepActivity extends BasicActivity implements View.OnClickListener {

    private Button registerActionBtn;
    private ILoginLogic loginLogic;
    private RegisterSecondStepInfo registerSecondStepInfo;

    private EditText passwordEt;
    private EditText confirmPwdEt;

    private HeaderView mHeaderView;


    private int errorFromViewId;
    private boolean requestFailed;


    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic)this.getLogicByInterfaceClass(ILoginLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_second_step;
    }

    @Override
    protected void initView() {
        mHeaderView = (HeaderView) findViewById(R.id.header);
        mHeaderView.title.setText(R.string.register_title);
        mHeaderView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);

        registerActionBtn = (Button) findViewById(R.id.reset_action_button);
        passwordEt = (EditText) findViewById(R.id.password_et);
        confirmPwdEt = (EditText)findViewById(R.id.repeat_password_et);

    }

    @Override
    protected void initDate() {
        registerSecondStepInfo = getIntent().getParcelableExtra(BussinessConstants.Login.REGISTER_SECOND_STEP_INFO_KEY);
    }

    @Override
    protected void initListener() {
        registerActionBtn.setOnClickListener(this);
        mHeaderView.backImageView.setClickable(true);
        mHeaderView.backImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reset_action_button:
                next();
                break;
            case R.id.back_iv:
                doBack();
                break;
        }
    }

    private void next()
    {
        if (TextUtils.isEmpty(passwordEt.getText().toString())) {
            passwordEt.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(confirmPwdEt.getText().toString())) {
            confirmPwdEt.requestFocus();
            return;
        }
        if (! StringUtil.equals(passwordEt.getText().toString(),confirmPwdEt.getText().toString())){
            confirmPwdEt.requestFocus();
            return;
        }
        registerSecondStepInfo.setPwd(passwordEt.getText().toString());//loginLogic.encryPassword(passwordEt.getText().toString()));
        loginLogic.registerSecondStep(registerSecondStepInfo);
    }

    private void displayErrorInfo(int stringId, View view) {

    }


    private void hideErrorInfo(View view) {

    }

    private void displayRequestErrorInfo(int stringId) {

    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        String code="";
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.REGISTER_SECOND_STEP_SUCCESS_MSG_ID:
                super.dismissWaitDailog();
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setPhone(registerSecondStepInfo.getPhone());
                loginInfo.setPassword(loginLogic.encryPassword(registerSecondStepInfo.getPwd()));
                loginLogic.login(loginInfo);
                break;
            case BussinessConstants.LoginMsgID.LOGIN_SUCCESS_MSG_ID:
                super.dismissWaitDailog();
                Intent intent = new Intent(RegisterSecondStepActivity.this, MainFragmentActivity.class);
                this.startActivity(intent);
                this.finishAllActivity(MainFragmentActivity.class.getName());
                break;
            case BussinessConstants.LoginMsgID.REGISTER_FIRST_STEP_FAIL_MSG_ID:
                super.dismissWaitDailog();
                if (msg.obj != null) {
                    code = (String) msg.obj;
                }
                if (BussinessConstants.LoginHttpErrorCode.HAS_REGISTER.equals(code)) {
                    displayRequestErrorInfo(R.string.has_registered);
                } else if (BussinessConstants.LoginHttpErrorCode.HAS_REGISTER_FORBIDDEN.equals(code)) {
                    displayRequestErrorInfo(R.string.has_registered_forbidden);
                } else {
                    displayRequestErrorInfo(R.string.register_fail);
                }
                break;
            default:
                break;
        }
    }
}
