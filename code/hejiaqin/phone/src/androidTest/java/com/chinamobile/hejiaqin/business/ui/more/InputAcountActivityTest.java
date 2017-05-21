package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class InputAcountActivityTest extends ActivityUnitTestCase<InputAcountActivity> {
    private InputAcountActivity mActivity;

    private HeaderView mHeaderView;
    private EditText mName;
    private EditText mNumber;
    private RelativeLayout progressLayout;
    private TextView progressTip;

    public InputAcountActivityTest() {
        super(InputAcountActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                InputAcountActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mHeaderView = (HeaderView) mActivity.findViewById(R.id.more_input_contact_title);
        mName = (EditText) mActivity.findViewById(R.id.more_input_contact_name_et);
        mNumber = (EditText) mActivity.findViewById(R.id.more_input_contact_number_et);
        progressLayout = (RelativeLayout) mActivity.findViewById(R.id.progress_tips);
        progressTip = (TextView) mActivity.findViewById(R.id.progress_text);
    }

    public void testInitView() {
        assertNotNull(mHeaderView);
        assertNotNull(mName);
        assertNotNull(mNumber);
        assertNotNull(progressLayout);
        assertNotNull(progressTip);
    }

    public void testOnClick() {
        mHeaderView.backImageView.performClick();
        mHeaderView.rightBtn.performClick();
    }

    public void testHandleStateMessage() {
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_DELIVERY_OK));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_DISPLAY_OK));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_SEND_FAILED));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_UNDELIVERED));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.BIND_SUCCESS));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.BIND_DENIED));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.SENDING_BIND_REQUEST));

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
