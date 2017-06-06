package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.model.FailResponse;
import com.chinamobile.hejiaqin.business.model.login.req.PasswordInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.login.dialog.DisplayErrorDialog;
import com.customer.framework.utils.StringUtil;

/**
 * Created by Xiadong on 2016/4/27.
 */
public class ResetPasswordSecondStepActivity extends BasicActivity implements View.OnClickListener {

    private Button registerActionBtn;
    private ILoginLogic loginLogic;
    private PasswordInfo passwordInfo;

    private EditText passwordEt;
    private EditText confirmPwdEt;

    private HeaderView mHeaderView;

    private int errorFromViewId;
    private boolean requestFailed;

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) this.getLogicByInterfaceClass(ILoginLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password_second_step;
    }

    @Override
    protected void initView() {
        mHeaderView = (HeaderView) findViewById(R.id.header);
        mHeaderView.title.setText(R.string.reset_password_title);
        mHeaderView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);

        registerActionBtn = (Button) findViewById(R.id.reset_action_button);
        passwordEt = (EditText) findViewById(R.id.password_et);
        confirmPwdEt = (EditText) findViewById(R.id.repeat_password_et);

    }

    @Override
    protected void initDate() {
        passwordInfo = getIntent().getParcelableExtra(BussinessConstants.Login.PASSWORD_INFO_KEY);
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
            default:
                break;
        }
    }

    private void next() {
        if (TextUtils.isEmpty(passwordEt.getText().toString())) {
            displayErrorInfo(getString(R.string.prompt_password));
            passwordEt.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(confirmPwdEt.getText().toString())) {
            displayErrorInfo(getString(R.string.prompt_empty_confirm_password));
            confirmPwdEt.requestFocus();
            return;
        }
        if (!StringUtil.equals(passwordEt.getText().toString(), confirmPwdEt.getText().toString())) {
            displayErrorInfo(getString(R.string.prompt_password_not_the_same));
            confirmPwdEt.requestFocus();
            return;
        }
        if (!StringUtil.isPassword(passwordEt.getText().toString())) {
            displayErrorInfo(getString(R.string.prompt_wrong_password_format));
            passwordEt.requestFocus();
            return;
        }
        passwordInfo.setPassword(passwordEt.getText().toString());
        loginLogic.updatePassword(passwordInfo);
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

    private void displayRequestErrorInfo(int stringId) {

    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.UPDATE_PWD_SUCCESS_MSG_ID:
                super.dismissWaitDailog();
                Intent intent = new Intent(ResetPasswordSecondStepActivity.this,
                        LoginActivity.class);
                this.startActivity(intent);
                this.finishAllActivity(LoginActivity.class.getName());
                break;
            case BussinessConstants.LoginMsgID.UPDATE_PWD_FAIL_MSG_ID:
                FailResponse response = (FailResponse) msg.obj;
                if ("1".equals(response.getCode())) {
                    displayErrorInfo(response.getMsg());
                }
                break;
            default:
                break;
        }
    }
}
