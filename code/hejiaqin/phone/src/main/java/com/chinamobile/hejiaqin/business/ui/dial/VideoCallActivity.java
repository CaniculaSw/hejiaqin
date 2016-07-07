package com.chinamobile.hejiaqin.business.ui.dial;

import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.call.CallSession;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideoCallActivity extends BasicActivity {

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
    private LinearLayout mHangupLayout;
    private LinearLayout mCameraLayout;

    //来电布局
    private RelativeLayout mIncomingLayout;
    private CircleImageView mCallerIv;
    private TextView mCallerNameTv;
    private Button mComingAnswerCallBtn;
    private Button mComingRejectCallBtn;

    //是否来电
    private boolean mIsInComing;
    //是否通话中
    private boolean mIsTalking;

    //被叫号码
    private String mCalleeNumber;

    private IVoipLogic mVoipLogic;

    private SurfaceView localVideoView;

    //通话会话对象
    private CallSession mCallSession = null;

    @Override
    protected void initLogics() {
        mVoipLogic =(IVoipLogic)super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_call;
    }

    @Override
    protected void initView() {
        //视频布局(呼出和通话中)
        mVideoLayout = (RelativeLayout)findViewById(R.id.video_layout);
        mLargeVideoLayout = (LinearLayout)findViewById(R.id.large_video_layout);
        mSmallVideoLayout = (LinearLayout)findViewById(R.id.small_video_layout);

        //顶部布局(呼出和通话中)
        mTopLayout = (LinearLayout)findViewById(R.id.top_layout);
        mContactNameTv = (TextView)findViewById(R.id.contact_name_tv);
        mCallStatusLayout = (LinearLayout)findViewById(R.id.call_status_layout);
        mTalkingTimeTv = (TextView)findViewById(R.id.talking_time_tv);

        //底部布局(呼出和通话中)
        mBottomLayout = (LinearLayout)findViewById(R.id.bottom_layout);
        mMuteLayout = (LinearLayout)findViewById(R.id.mute_layout);
        mHangupLayout = (LinearLayout)findViewById(R.id.hangup_layout);
        mCameraLayout = (LinearLayout)findViewById(R.id.camera_layout);

        //来电布局
        mIncomingLayout = (RelativeLayout)findViewById(R.id.incoming_layout);
        mCallerIv = (CircleImageView)findViewById(R.id.caller_iv);
        mCallerNameTv = (TextView)findViewById(R.id.caller_name_tv);
        mComingAnswerCallBtn = (Button)findViewById(R.id.coming_answer_call_btn);
        mComingRejectCallBtn = (Button)findViewById(R.id.coming_reject_call_btn);

    }

    @Override
    protected void initDate() {
        mIsInComing = getIntent().getBooleanExtra(BussinessConstants.Dial.INTENT_CALL_INCOMING,false);
        if(mIsInComing)
        {
            long sessionId = getIntent().getLongExtra(BussinessConstants.Dial.INTENT_INCOMING_SESSION_ID, CallSession.INVALID_ID);
            mCallSession = CallApi.getCallSessionById(sessionId);
            if(mCallSession == null)
            {
                finish();
                return;
            }

        }
        else
        {
            mCalleeNumber = getIntent().getStringExtra(BussinessConstants.Dial.INTENT_CALLEE_NUMBER);
            showOuting();
            outingCall();
        }
    }

    private void showIncoming()
    {
        //TODO 查询姓名和头像信息
        mCallerNameTv.setText(mCallSession.getPeer().getNumber());
        mContactNameTv.setText(mCallSession.getPeer().getNumber());
        //TODO 设置头像
        mIncomingLayout.setVisibility(View.VISIBLE);
    }

    private void showOuting()
    {
        //TODO 查询姓名
        mContactNameTv.setText(mCalleeNumber);
        mTopLayout.setVisibility(View.VISIBLE);
        mVideoLayout.setVisibility(View.VISIBLE);
        mBottomLayout.setVisibility(View.VISIBLE);
    }

    private void outingCall() {
        createLocalPreviewVideo();
        mCallSession = mVoipLogic.call(mCalleeNumber,true);
        if (mCallSession.getErrCode() != CallSession.ERRCODE_OK) {
            showToast(R.string.call_outing_error, Toast.LENGTH_SHORT,null);
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }}, 2000);
        }
    }

    private void createLocalPreviewVideo()
    {
        if(localVideoView == null) {
            localVideoView = CallApi.createLocalVideoView(getApplicationContext());
            ViewGroup.LayoutParams layoutParams= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            mLargeVideoLayout.addView(localVideoView, layoutParams);
//            localVideoView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyVideoView();
    }

    private void destroyVideoView() {

        if (localVideoView != null) {
            localVideoView.setVisibility(View.GONE);
            mLargeVideoLayout.removeView(localVideoView);
            mSmallVideoLayout.removeView(localVideoView);
            CallApi.deleteRemoteVideoView(localVideoView);
            localVideoView = null;
        }
    }
}
