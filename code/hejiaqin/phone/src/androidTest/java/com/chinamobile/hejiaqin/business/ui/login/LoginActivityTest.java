package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;

import java.util.Date;

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

    public void testInitView() {
        assertNotNull(accountEditTv);
        assertNotNull(passwdEditTv);
        assertNotNull(signBtn);
        assertNotNull(forgetPwdTv);
        assertNotNull(registerBtn);
        assertNotNull(clearAccountBtn);
    }

    public void testOnClick() {
        forgetPwdTv.performClick();
        registerBtn.performClick();
        clearAccountBtn.performClick();
        clearAccountBtn.performClick();
    }

    public void testHandleStateMessage() {

        UserInfo userInfo = new UserInfo();
        userInfo.setSdkAccount("aaa");
        userInfo.setSdkPassword("123456");
        Date now = new Date();
        UserInfoCacheManager.saveUserToMem(mActivity, userInfo, now.getTime());
        mActivity.handleStateMessage(generateMessage(BussinessConstants.LoginMsgID.LOGIN_SUCCESS_MSG_ID));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.DialMsgID.VOIP_REGISTER_NET_UNAVAILABLE_MSG_ID));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.DialMsgID.VOIP_REGISTER_DISCONNECTED_MSG_ID));
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
