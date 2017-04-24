package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class ResetPasswordFirstStepActivityTest extends
        ActivityUnitTestCase<ResetPasswordFirstStepActivity> {
    private ResetPasswordFirstStepActivity mActivity;

    private EditText accountEditTx;
    private EditText verifyCodeEditTx;
    private TextView sendVerifyCodeTv;
    private Button nextActionBtn;
    private HeaderView mHeaderView;

    public ResetPasswordFirstStepActivityTest() {
        super(ResetPasswordFirstStepActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                ResetPasswordFirstStepActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mHeaderView = (HeaderView) mActivity.findViewById(R.id.header);
        accountEditTx = (EditText) mActivity.findViewById(R.id.phone_no_et);
        verifyCodeEditTx = (EditText) mActivity.findViewById(R.id.verify_code_et);
        sendVerifyCodeTv = (TextView) mActivity.findViewById(R.id.get_verify_code);
        nextActionBtn = (Button) mActivity.findViewById(R.id.next_action_button);
    }

    public void testPreconditons() {
        assertNotNull(mHeaderView);
        assertNotNull(accountEditTx);
        assertNotNull(verifyCodeEditTx);
        assertNotNull(sendVerifyCodeTv);
        assertNotNull(nextActionBtn);
    }
}
