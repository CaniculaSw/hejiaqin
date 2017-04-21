package com.chinamobile.hejiaqin.business.ui.dial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.CaaSSdkService;
import com.chinamobile.hejiaqin.business.Const;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.call.CallSession;
import com.huawei.rcs.log.LogApi;
import com.huawei.rcs.system.SysApi;

import java.util.Timer;
import java.util.TimerTask;


public class StbVideoCallActivity extends BasicActivity implements View.OnClickListener {

    private TextView mTalkingTimeTv;

    private LinearLayout mHangupLayout;

    //是否来电
    private boolean mIsInComing;
    //是否通话中
    private boolean mIsTalking;

    private IVoipLogic mVoipLogic;

    //通话会话对象
    private CallSession mCallSession = null;

    private Timer timer;
    private int callTime;
    private Handler handler = new Handler();

    private boolean speakerState;

    private boolean closed;

    private boolean hasRegistReceiver;

    private int mVideoPadding;
    private int mVideoHeight;
    private int mVideoWidth;
    private SurfaceView m_svLocalVideo;

    private int[] mMetrics = new int[2];

    private BroadcastReceiver mCameraPlugReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int iState = intent.getIntExtra("state", -1);
            LogApi.d(Const.TAG_UI, "camera stat change:" + iState);
            if (1 == iState) {
                getDisplayMetrics(StbVideoCallActivity.this);
                Rect rectLocal = new Rect();
                rectLocal.left = mVideoPadding;
                rectLocal.top = mMetrics[1] - mVideoPadding - mVideoHeight;
                rectLocal.right = mVideoPadding + mVideoWidth;
                rectLocal.bottom = mMetrics[1] - mVideoPadding;
                CaaSSdkService.setLocalRenderPos(rectLocal, CallApi.VIDEO_LAYER_TOP);
                CaaSSdkService.openLocalView();
            } else {
                CaaSSdkService.closeLocalView();
            }
        }
    };

    /* display the video stream which arrived from remote. */
    private BroadcastReceiver remoteNetStatusChangeReciverr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CallSession session = (CallSession) intent.getSerializableExtra(CallApi.PARAM_CALL_SESSION);
            if (!mCallSession.equals(session)) {
                return;
            }
            Bundle bundle = intent.getExtras();
            int status = bundle.getInt(CallApi.PARAM_CALL_NET_STATUS);
            if (status == 1) {
                getDisplayMetrics(StbVideoCallActivity.this);
                Rect rectLocal = new Rect();
                rectLocal.left = mVideoPadding;
                rectLocal.top = mMetrics[1] - mVideoPadding - mVideoHeight;
                rectLocal.right = mVideoPadding + mVideoWidth;
                rectLocal.bottom = mMetrics[1] - mVideoPadding;
                CaaSSdkService.setLocalRenderPos(rectLocal, CallApi.VIDEO_LAYER_TOP);
                CaaSSdkService.showRemoteVideoRender(true);
            }
        }
    };

    @Override
    protected void initLogics() {
        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_call_stb;
    }

    @Override
    protected void initView() {
        CallApi.setPauseMode(1);
        //视频布局(呼出和通话中)
        mTalkingTimeTv = (TextView) findViewById(R.id.talking_time_tv);
        mHangupLayout = (LinearLayout) findViewById(R.id.hangup_layout);
        mHangupLayout.setOnClickListener(this);
        mVideoPadding = StbVideoCallActivity.this.getResources().getDimensionPixelOffset(R.dimen.small_video_padding);
        mVideoHeight =StbVideoCallActivity.this.getResources().getDimensionPixelOffset(R.dimen.small_video_height);
        mVideoWidth= StbVideoCallActivity.this.getResources().getDimensionPixelOffset(R.dimen.small_video_width);
    }

    @Override
    protected void initDate() {
        mIsInComing = getIntent().getBooleanExtra(BussinessConstants.Dial.INTENT_CALL_INCOMING, false);
        mCallSession = CallApi.getForegroudCallSession();

        if (null == mCallSession) {
            LogUtil.d("V2OIP", "hejiaqin initDate: no call is talking any more.");
            finish();
            return;
        }
        m_svLocalVideo = (SurfaceView) findViewById(R.id.sv_localvideo);
        m_svLocalVideo.setBackgroundDrawable(null);
        showTalking();
    }

    private void showTalking() {
        mIsTalking = true;
        AudioManager audioManamger = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        speakerState = audioManamger.isSpeakerphoneOn();
        if (!speakerState) {
            audioManamger.setSpeakerphoneOn(!speakerState);
        }
        /* creat local video view and remote video view. */
        createVideoView();
        registerReceivers();
        startCallTimeTask();
    }

    private void createVideoView() {
        mCallSession.showVideoWindow();
        CaaSSdkService.setRemoteRenderPos(getFullScreenRect(), CallApi.VIDEO_LAYER_BOTTOM);
    }

    private Rect getFullScreenRect() {
        getDisplayMetrics(this);

        Rect rect = new Rect();
        rect.left = 0;
        rect.top = 0;
        rect.right = mMetrics[0];
        rect.bottom = mMetrics[1];
        return rect;
    }

    private void getDisplayMetrics(Context context) {

        if (mMetrics[0] > 0 && mMetrics[1] > 0) {
            return;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        mMetrics[0] = screenWidth;
        mMetrics[1] = screenHeight;
    }


    private void stopCallTimeTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void startCallTimeTask() {
        timer = new Timer();
        callTime = (int) ((System.currentTimeMillis() - mCallSession.getOccurDate()) / 1000);
        mTalkingTimeTv.setText(SysApi.PhoneUtils.getCallDurationTime(callTime));
        mTalkingTimeTv.setVisibility(View.VISIBLE);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                callTime++;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTalkingTimeTv.setText(SysApi.PhoneUtils.getCallDurationTime(callTime));
                    }
                });
            }
        }, 1000, 1000);
    }

    private void registerReceivers() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                remoteNetStatusChangeReciverr,
                new IntentFilter(CallApi.EVENT_CALL_VIDEO_NET_STATUS_CHANGE));
        hasRegistReceiver = true;
    }

    private void unRegisterReceivers() {
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(remoteNetStatusChangeReciverr);

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
        if (closed) {
            LogUtil.w(TAG, "is closed");
        }
        switch (v.getId()) {
            case R.id.hangup_layout:
                //呼出和接通后的挂断
                mVoipLogic.hangup(mCallSession, mIsInComing, mIsTalking, callTime);
                finish();
                break;
        }

    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.DialMsgID.CALL_CLOSED_MSG_ID:
                if (msg.obj != null) {
                    CallSession session = (CallSession) msg.obj;
                    if (mCallSession != null && mCallSession.equals(session)) {
                        mVoipLogic.dealOnClosed(mCallSession, mIsInComing, mIsTalking, callTime);
                        closed = true;
                        if (mCallSession.getSipCause() == BussinessConstants.DictInfo.SIP_TEMPORARILY_UNAVAILABLE) {
                            showToast(R.string.sip_temporarily_unavailable, Toast.LENGTH_SHORT, null);
                        } else if (!mIsInComing && !mIsTalking && (mCallSession.getSipCause() == BussinessConstants.DictInfo.SIP_BUSY_HERE
                                || mCallSession.getSipCause() == BussinessConstants.DictInfo.SIP_DECLINE)) {
                            showToast(R.string.sip_busy_here, Toast.LENGTH_SHORT, null);
                        }
                        getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 3000);
                    } else if (session != null && session.getType() == CallSession.TYPE_VIDEO_INCOMING) {
                        mVoipLogic.dealOnClosed(session, true, false, 0);
                    }
                }
                break;
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intent = new IntentFilter();
        intent.addAction(Const.CAMERA_PLUG);
        registerReceiver(mCameraPlugReciver, intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //还原免提设置
        if (!speakerState) {
            AudioManager audioManamger = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManamger.setSpeakerphoneOn(speakerState);
        }
        destroyVideoView();
        stopCallTimeTask();
        if (hasRegistReceiver) {
            unRegisterReceivers();
        }
        unregisterReceiver(mCameraPlugReciver);
    }

    private void destroyVideoView() {
    }

    @Override
    public void onBackPressed() {

    }

}
