package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class MessageActivityTest extends ActivityUnitTestCase<MessageActivity> {
    private MessageActivity mActivity;

    ViewPager mViewPager;
    View mMissCallLay;
    View mSysMessageLay;
    View editTv;
    private ImageView mBackButton;

    public MessageActivityTest() {
        super(MessageActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), MessageActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mMissCallLay = mActivity.findViewById(R.id.more_sys_msg_miss_call_layout);
        mSysMessageLay = mActivity.findViewById(R.id.more_sys_msg_sys_message_layout);
        mViewPager = (ViewPager) mActivity.findViewById(R.id.more_msg_viewpager);
        editTv = mActivity.findViewById(R.id.more_sys_msg_edit);
        mBackButton = (ImageView) mActivity.findViewById(R.id.more_sys_msg_back_btn);
    }

    public void testInitView() {
        assertNotNull(mMissCallLay);
        assertNotNull(mSysMessageLay);
        assertNotNull(mViewPager);
        assertNotNull(editTv);
    }

    public void testOnClick() {
        mMissCallLay.performClick();
        mSysMessageLay.performClick();
        mBackButton.performClick();
    }

    public void testHandleStateMessage() {

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
