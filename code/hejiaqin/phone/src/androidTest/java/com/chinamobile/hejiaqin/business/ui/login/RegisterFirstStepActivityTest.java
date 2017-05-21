package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.FailResponse;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class RegisterFirstStepActivityTest extends ActivityUnitTestCase<RegisterFirstStepActivity> {
    private RegisterFirstStepActivity mActivity;

    private EditText accountEditTx;
    private EditText verifyCodeEditTx;
    private TextView sendVerifyCodeTv;
    private Button nextActionBtn;
    private HeaderView mHeaderView;

    public RegisterFirstStepActivityTest() {
        super(RegisterFirstStepActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                RegisterFirstStepActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mHeaderView = (HeaderView) mActivity.findViewById(R.id.header);
        accountEditTx = (EditText) mActivity.findViewById(R.id.phone_no_et);
        verifyCodeEditTx = (EditText) mActivity.findViewById(R.id.verify_code_et);
        sendVerifyCodeTv = (TextView) mActivity.findViewById(R.id.get_verify_code);
        nextActionBtn = (Button) mActivity.findViewById(R.id.next_action_button);
    }

    public void testInitView() {
        assertNotNull(accountEditTx);
        assertNotNull(verifyCodeEditTx);
        assertNotNull(mHeaderView);
        assertNotNull(sendVerifyCodeTv);
        assertNotNull(nextActionBtn);
    }

    public void testOnClick() {
        mHeaderView.backImageView.performClick();
    }

    public void testHandleStateMessage() {

        mActivity.handleStateMessage(generateMessage(BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_SUCCESS_MSG_ID));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_NET_ERROR_MSG_ID));

        FailResponse failResponse = new FailResponse();
        failResponse.setCode("123");
        mActivity.handleStateMessage(generateMessage(BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_FAIL_MSG_ID, failResponse));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.LoginMsgID.CHECK_VERIFY_CDOE_SUCCESS_MSG_ID));

        FailResponse failResponse2 = new FailResponse();
        failResponse2.setCode("123");
        mActivity.handleStateMessage(generateMessage(BussinessConstants.LoginMsgID.CHECK_VERIFY_CDOE_FAIL_MSG_ID, failResponse2));

    }

    private Message generateMessage(int what) {
        return generateMessage(what, null);
    }

    private Message generateMessage(int what, Object obj) {
        Message message = Message.obtain();
        message.what = what;
        message.obj = obj;
        return message;
    }
}
