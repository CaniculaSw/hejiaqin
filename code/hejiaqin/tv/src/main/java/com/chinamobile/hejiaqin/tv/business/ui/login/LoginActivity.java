package com.chinamobile.hejiaqin.tv.business.ui.login;

import android.content.Intent;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chinamobile.hejiaqin.tv.business.ui.main.MainFragmentActivity;
import com.squareup.picasso.Picasso;
import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.model.login.LoginHistory;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.tv.business.ui.basic.BasicActivity;
import com.customer.framework.utils.StringUtil;

import de.hdodenhof.circleimageview.CircleImageView;


public class LoginActivity extends BasicActivity implements View.OnClickListener {

    private ILoginLogic loginLogic;
    private EditText accountEditTv;
    private EditText passwdEditTv;
    private Button signBtn;
    private TextView forgetPwdTv;
    private Button registerBtn;
    private TextView errorInfoTv;
    private int errorFromViewId;
    private boolean loginRequestFailed;
    private boolean disPlayAvatar;
    private CircleImageView avatarImageView;

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
        errorInfoTv = (TextView) findViewById(R.id.error_info_tv);
        avatarImageView = (CircleImageView)findViewById(R.id.login_avatar_image_view);
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
                hideErrorInfo(accountEditTv);
                //手机号码11位，获取历史的头像
                if(accountEditTv.getText().length()==11)
                {
                    LoginHistory history = loginLogic.getLoginHistory(accountEditTv.getText().toString());
                    if(history!=null && history.getAvatar()!=null)
                    {
                        disPlayAvatar = true;
                        //加载网络图片
                        //先做一次头像的刷新
                        Picasso.with(LoginActivity.this.getApplicationContext()).invalidate(history.getAvatar());
                        Picasso.with(LoginActivity.this.getApplicationContext()).load(history.getAvatar()).placeholder(R.mipmap.default_avatar).into(avatarImageView);
                    }
                }
                else if(disPlayAvatar && accountEditTv.getText().length()!=11)
                {
                    avatarImageView.setImageResource(R.mipmap.default_avatar);
                    disPlayAvatar =false;
                }
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
                hideErrorInfo(passwdEditTv);
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
                Intent forgetIntent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(forgetIntent);
                break;
            case R.id.register_button:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                passwdEditTv.setText("");
                startActivity(registerIntent);
                break;
        }
    }

    private void login() {
        String account = accountEditTv.getText().toString();
        if (TextUtils.isEmpty(account)) {
            displayErrorInfo(R.string.account_null,accountEditTv);
            accountEditTv.requestFocus();
            return;
        }
        String password = passwdEditTv.getText().toString();
        if (TextUtils.isEmpty(password)) {
            displayErrorInfo(R.string.password_null,passwdEditTv);
            passwdEditTv.requestFocus();
            return;
        }
        if (!StringUtil.isMobileNO(account)) {
            displayErrorInfo(R.string.account_illegal,accountEditTv);
            accountEditTv.requestFocus();
            return;
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginid(accountEditTv.getText().toString());
        loginInfo.setPwd(passwdEditTv.getText().toString());
        hideErrorInfo(null);
        super.showWaitDailog();
        loginLogic.login(loginInfo);
    }

    private void displayErrorInfo(int stringId,View view) {
        errorInfoTv.setText(getResources().getString(R.string.error_mark) + getResources().getString(stringId));
        errorInfoTv.setVisibility(View.VISIBLE);
        errorFromViewId = view.getId();
        loginRequestFailed = false;
    }


    private void hideErrorInfo(View view) {
        if(view!=null && errorFromViewId == view.getId())
        {
            errorFromViewId =0;
            errorInfoTv.setVisibility(View.INVISIBLE);
            errorInfoTv.setText("");
        }
        if(loginRequestFailed)
        {
            loginRequestFailed =false;
            errorInfoTv.setVisibility(View.INVISIBLE);
            errorInfoTv.setText("");
        }
    }

    private void displayRequestErrorInfo() {
        errorInfoTv.setText(getResources().getString(R.string.error_mark) + getResources().getString(R.string.login_failed));
        errorInfoTv.setVisibility(View.VISIBLE);
        loginRequestFailed = true;
        errorFromViewId =0;
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

