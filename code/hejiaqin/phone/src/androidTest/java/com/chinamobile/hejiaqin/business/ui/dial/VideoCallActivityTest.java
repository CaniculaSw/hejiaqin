package com.chinamobile.hejiaqin.business.ui.dial;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class VideoCallActivityTest extends ActivityUnitTestCase<VideoCallActivity> {

    private VideoCallActivity mActivity;
    //视频布局(呼出和通话中)
    private RelativeLayout mVideoLayout;
    private LinearLayout mLargeVideoLayout;
    private LinearLayout mSmallVideoLayout;

    //顶部布局(呼出和通话中)
    private LinearLayout mTopLayout;
    private TextView mContactNameTv;
    private LinearLayout mCallStatusLayout;
    private TextView mTalkingTimeTv;

    //底部布局(呼出和通话中)
    private LinearLayout mBottomLayout;
    private LinearLayout mMuteLayout;
    private ImageView mMuteIv;
    private LinearLayout mHangupLayout;
    private LinearLayout mCameraLayout;

    //来电布局
    private RelativeLayout mIncomingLayout;
    private CircleImageView mCallerIv;
    private TextView mCallerNameTv;
    private Button mComingAnswerCallBtn;
    private Button mComingRejectCallBtn;

    public VideoCallActivityTest() {
        super(VideoCallActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), VideoCallActivity.class);
        intent.putExtra(BussinessConstants.Dial.INTENT_CALLEE_NUMBER, "123456");
        intent.putExtra(BussinessConstants.Dial.INTENT_CALLEE_NAME, "abc");
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        //视频布局(呼出和通话中)
        mVideoLayout = (RelativeLayout) mActivity.findViewById(R.id.video_layout);
        mLargeVideoLayout = (LinearLayout) mActivity.findViewById(R.id.large_video_layout);
        mSmallVideoLayout = (LinearLayout) mActivity.findViewById(R.id.small_video_layout);

        //顶部布局(呼出和通话中)
        mTopLayout = (LinearLayout) mActivity.findViewById(R.id.top_layout);
        mContactNameTv = (TextView) mActivity.findViewById(R.id.contact_name_tv);
        mCallStatusLayout = (LinearLayout) mActivity.findViewById(R.id.call_status_layout);
        mTalkingTimeTv = (TextView) mActivity.findViewById(R.id.talking_time_tv);

        //底部布局(呼出和通话中)
        mBottomLayout = (LinearLayout) mActivity.findViewById(R.id.bottom_layout);
        mMuteLayout = (LinearLayout) mActivity.findViewById(R.id.mute_layout);
        mMuteIv = (ImageView) mActivity.findViewById(R.id.mute_iv);
        mHangupLayout = (LinearLayout) mActivity.findViewById(R.id.hangup_layout);
        mCameraLayout = (LinearLayout) mActivity.findViewById(R.id.camera_layout);

        //来电布局
        mIncomingLayout = (RelativeLayout) mActivity.findViewById(R.id.incoming_layout);
        mCallerIv = (CircleImageView) mActivity.findViewById(R.id.caller_iv);
        mCallerNameTv = (TextView) mActivity.findViewById(R.id.caller_name_tv);
        mComingAnswerCallBtn = (Button) mActivity.findViewById(R.id.coming_answer_call_btn);
        mComingRejectCallBtn = (Button) mActivity.findViewById(R.id.coming_reject_call_btn);
    }

    public void testPreconditons() {
        assertNotNull(mVideoLayout);
        assertNotNull(mLargeVideoLayout);
        assertNotNull(mSmallVideoLayout);
        assertNotNull(mTopLayout);
        assertNotNull(mContactNameTv);
        assertNotNull(mCallStatusLayout);
        assertNotNull(mTalkingTimeTv);
        assertNotNull(mBottomLayout);
        assertNotNull(mMuteLayout);
        assertNotNull(mMuteIv);
        assertNotNull(mHangupLayout);
        assertNotNull(mCameraLayout);
        assertNotNull(mIncomingLayout);
        assertNotNull(mCallerIv);
        assertNotNull(mCallerNameTv);
        assertNotNull(mComingAnswerCallBtn);
        assertNotNull(mComingRejectCallBtn);
    }
}
