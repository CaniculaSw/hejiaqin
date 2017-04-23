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
import com.chinamobile.hejiaqin.business.model.login.req.RegisterSecondStepInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.login.dialog.DisplayErrorDialog;
import com.customer.framework.utils.StringUtil;

/**
 * Created by Xiadong on 2016/4/27
 */
public class RegisterSecondStepActivity extends BasicActivity implements View.OnClickListener {

    private Button registerActionBtn;
    private ILoginLogic loginLogic;
//    private IVoipLogic voipLogic;
    private RegisterSecondStepInfo registerSecondStepInfo;

    private EditText passwordEt;
    private EditText confirmPwdEt;

    private HeaderView mHeaderView;


    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) this.getLogicByInterfaceClass(ILoginLogic.class);
//        voipLogic = (IVoipLogic) this.getLogicByInterfaceClass(IVoipLogic.class);
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
        confirmPwdEt = (EditText) findViewById(R.id.repeat_password_et);

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
        registerSecondStepInfo.setPwd(passwordEt.getText().toString());//loginLogic.encryPassword(passwordEt.getText().toString()));
        loginLogic.registerSecondStep(registerSecondStepInfo);
    }

    private void displayErrorInfo(String errorText) {
        final DisplayErrorDialog dialog = new DisplayErrorDialog(this, R.style.CalendarDialog, errorText);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        dialog.show();
    }


    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.REGISTER_SECOND_STEP_SUCCESS_MSG_ID:
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.putExtra("phone",registerSecondStepInfo.getPhone());
                intent.putExtra("password",registerSecondStepInfo.getPwd());
                startActivity(intent);
                finishAllActivity("");
                break;
            case BussinessConstants.LoginMsgID.REGISTER_FIRST_STEP_FAIL_MSG_ID:
                FailResponse response = (FailResponse) msg.obj;
                if (response.getCode().equals("1")) {
                    displayErrorInfo(response.getMsg());
                }
                break;
            default:
                break;
        }
    }
}
