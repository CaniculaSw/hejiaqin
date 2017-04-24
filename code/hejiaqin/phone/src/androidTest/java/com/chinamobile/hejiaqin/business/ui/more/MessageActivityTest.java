package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.test.ActivityUnitTestCase;
import android.view.View;

import com.chinamobile.hejiaqin.R;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class MessageActivityTest extends ActivityUnitTestCase<MessageActivity> {
    private MessageActivity mActivity;

    ViewPager mViewPager;
    View mMissCallLay;
    View mSysMessageLay;

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
    }

    public void testPreconditons() {
        assertNotNull(mMissCallLay);
        assertNotNull(mSysMessageLay);
        assertNotNull(mViewPager);
    }
}
