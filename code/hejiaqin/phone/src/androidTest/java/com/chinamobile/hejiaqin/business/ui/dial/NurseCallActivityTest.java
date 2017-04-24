package com.chinamobile.hejiaqin.business.ui.dial;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class NurseCallActivityTest extends ActivityUnitTestCase<NurseCallActivity> {

    private NurseCallActivity mActivity;
    //视频布局(呼出和通话中)
    private RelativeLayout mVideoLayout;
    private LinearLayout mLargeVideoLayout;

    //顶部布局(呼出和通话中)
    private LinearLayout mTopLayout;
    private TextView mContactNameTv;
    private LinearLayout mCallStatusLayout;
    private TextView mTalkingTimeTv;
    private TextView mCallStatusTv;

    //底部布局(呼出和通话中)
    private LinearLayout mBottomLayout;
    private LinearLayout mHangupLayout;

    public NurseCallActivityTest() {
        super(NurseCallActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), NurseCallActivity.class);
        intent.putExtra("tvAccount", "123456");
        intent.putExtra("tvName", "abc");
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mVideoLayout = (RelativeLayout) mActivity.findViewById(R.id.video_layout);
        mLargeVideoLayout = (LinearLayout) mActivity.findViewById(R.id.large_video_layout);

        //顶部布局(呼出和通话中)
        mTopLayout = (LinearLayout) mActivity.findViewById(R.id.top_layout);
        mContactNameTv = (TextView) mActivity.findViewById(R.id.contact_name_tv);
        mCallStatusLayout = (LinearLayout) mActivity.findViewById(R.id.call_status_layout);
        mCallStatusTv = (TextView) mActivity.findViewById(R.id.call_status_tv);
        mTalkingTimeTv = (TextView) mActivity.findViewById(R.id.talking_time_tv);

        //底部布局(呼出和通话中)
        mBottomLayout = (LinearLayout) mActivity.findViewById(R.id.bottom_layout);
        mHangupLayout = (LinearLayout) mActivity.findViewById(R.id.hangup_layout);
    }

    public void testPreconditons() {
        assertNotNull(mVideoLayout);
        assertNotNull(mLargeVideoLayout);
        assertNotNull(mTopLayout);
        assertNotNull(mContactNameTv);
        assertNotNull(mCallStatusLayout);
        assertNotNull(mCallStatusTv);
        assertNotNull(mTalkingTimeTv);
        assertNotNull(mBottomLayout);
        assertNotNull(mHangupLayout);
    }
}