package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.req.PasswordInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class ResetPasswordSecondStepActivityTest extends
        ActivityUnitTestCase<ResetPasswordSecondStepActivity> {
    private ResetPasswordSecondStepActivity mActivity;

    private Button registerActionBtn;
    private EditText passwordEt;
    private EditText confirmPwdEt;
    private HeaderView mHeaderView;

    public ResetPasswordSecondStepActivityTest() {
        super(ResetPasswordSecondStepActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setPassword("abcd123456");
        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                ResetPasswordSecondStepActivity.class);
        intent.putExtra(BussinessConstants.Login.PASSWORD_INFO_KEY, passwordInfo);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mHeaderView = (HeaderView) mActivity.findViewById(R.id.header);
        registerActionBtn = (Button) mActivity.findViewById(R.id.reset_action_button);
        passwordEt = (EditText) mActivity.findViewById(R.id.password_et);
        confirmPwdEt = (EditText) mActivity.findViewById(R.id.repeat_password_et);
    }

    public void testPreconditons() {
        assertNotNull(mHeaderView);
        assertNotNull(registerActionBtn);
        assertNotNull(passwordEt);
        assertNotNull(confirmPwdEt);
    }
}
