package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class LoginActivityTest extends ActivityUnitTestCase<LoginActivity> {
    private LoginActivity mActivity;

    private EditText accountEditTv;
    private EditText passwdEditTv;
    private Button signBtn;
    private TextView forgetPwdTv;
    private Button registerBtn;
    private ImageView clearAccountBtn;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), LoginActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        accountEditTv = (EditText) mActivity.findViewById(R.id.account_edit_tv);
        passwdEditTv = (EditText) mActivity.findViewById(R.id.password_edit_tv);
        signBtn = (Button) mActivity.findViewById(R.id.sign_in_button);
        forgetPwdTv = (TextView) mActivity.findViewById(R.id.forget_pwd_tv);
        registerBtn = (Button) mActivity.findViewById(R.id.register_button);
        clearAccountBtn = (ImageView) mActivity.findViewById(R.id.clear_btn);
    }

    public void testPreconditons() {
        assertNotNull(accountEditTv);
        assertNotNull(passwdEditTv);
        assertNotNull(signBtn);
        assertNotNull(forgetPwdTv);
        assertNotNull(registerBtn);
        assertNotNull(clearAccountBtn);
    }
}