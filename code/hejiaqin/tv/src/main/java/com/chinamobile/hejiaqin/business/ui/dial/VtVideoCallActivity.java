package com.chinamobile.hejiaqin.business.ui.dial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.Const;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.call.CallSession;
import com.huawei.rcs.system.SysApi;

import java.util.Timer;
import java.util.TimerTask;


public class VtVideoCallActivity extends BasicActivity implements View.OnClickListener {

    private TextView mTalkingTimeTv;

    private LinearLayout mHangupLayout;
    private RelativeLayout largeVideoContainer;

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

    private SurfaceView localVideoView;
    private SurfaceView remoteVideoView;

    private boolean speakerState;

    private boolean closed;

    private boolean m_isSmallVideoCreate_MPEG;
    private boolean m_isBigVideoCreate_MPEG;

    private boolean hasRegistReceiver;

    private boolean bCameraClose = false;

    //远程视频码率（分辨率、大小）变换
    private BroadcastReceiver callVideoReSolutionChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.e(TAG, "callVideoEncodeSolutionChangeReceiver");
            CallSession session = (CallSession) intent.getSerializableExtra(CallApi.PARAM_CALL_SESSION);
            if (!mCallSession.equals(session)) {
                return;
            }
            // 视频横竖屏切换是否会调用该广播
            int videoWidth = intent.getIntExtra(CallApi.PARAM_CALL_VIDEO_RESOLUTION_WIDTH, -1);
            int videoHeight = intent.getIntExtra(CallApi.PARAM_CALL_VIDEO_RESOLUTION_HEIGHT, -1);
            LogUtil.d(TAG, "videoWidth: " + videoWidth + " | videoHeight: " + videoHeight);
            if (videoWidth > 0 && videoHeight > 0) {
                int screenHeight = getScreenHeight(VtVideoCallActivity.this);
                int width = (int) (screenHeight * (1.0f * videoWidth / videoHeight));
                LogUtil.d(TAG, "width: " + width + " | screenHeight: " + screenHeight);
                if (remoteVideoView != null) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) largeVideoContainer.getLayoutParams();
                    layoutParams.width = width;
                    layoutParams.height = screenHeight;
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                    largeVideoContainer.setLayoutParams(layoutParams);
                }
            }

        }
    };

    private BroadcastReceiver mCameraPlugReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            if (null == bundle)
            {
                LogUtil.d(TAG, "Enter ACTION_USB_CAMERA_PLUG_IN_OUT bundle is null");
                return;
            }
            int state = bundle.getInt(Const.USB_CAMERA_STATE);

            LogUtil.d(TAG, "videotalk mCameraPlugReciver " + state);
            if (mCallSession == null)return;
            if (0 == state)
            {
                if (!bCameraClose)
                {
                    mCallSession.closeLocalVideo();
                    bCameraClose = true;
                }
            }
            else
            {
                if (bCameraClose)
                {
                    int iRet = mCallSession.openLocalVideo();
                    LogUtil.d(TAG, "mCameraPlugReciver open localView " + iRet);
                    bCameraClose = false;
                }
            }

        }
    };

    protected final SurfaceHolder.Callback surfaceCb = new SurfaceHolder.Callback() {
        @Override
        public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            LogUtil.d(Const.TAG_CALL, "surfaceCreated:");
            if (localVideoView.getHolder() == surfaceHolder) {
                m_isSmallVideoCreate_MPEG = true;
            } else if (remoteVideoView.getHolder() == surfaceHolder) {
                m_isBigVideoCreate_MPEG = true;
            }
            if (m_isSmallVideoCreate_MPEG && m_isBigVideoCreate_MPEG) {
                showMpegView();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder arg0) {
            LogUtil.d(Const.TAG_CALL, "surfaceDestroyed deleteLocalVideoSurface");
            if (localVideoView.getHolder() == arg0) {
                LogUtil.d(Const.TAG_CALL, "surfaceDestroyed deleteLocalVideoSurface==m_svSmallVideo.getHolder()");
                m_isSmallVideoCreate_MPEG = false;
            } else if (remoteVideoView.getHolder() == arg0) {
                LogUtil.d(Const.TAG_CALL, "surfaceDestroyed deleteLocalVideoSurface==m_svBigVideo.getHolder()");
                m_isBigVideoCreate_MPEG = false;
            }
        }

        protected void showMpegView() {
            if (mCallSession == null || localVideoView == null || remoteVideoView == null) {
                LogUtil.e(Const.TAG_CALL, "show view failed callSession " + mCallSession + " m_svSmallVideo " + localVideoView + " m_svBigVideo " + remoteVideoView);
                return;
            }
            LogUtil.d(Const.TAG_CALL, "m_isSmallVideoCreate_MPEG: " + m_isSmallVideoCreate_MPEG + ", m_isBigVideoCreate_MPEG: " + m_isBigVideoCreate_MPEG);
            if (m_isSmallVideoCreate_MPEG && m_isBigVideoCreate_MPEG && mCallSession.getStatus() == CallSession.STATUS_CONNECTED && mCallSession.getType() == CallSession.TYPE_VIDEO) {
                int result1 = CallApi.createLocalVideoSurface(localVideoView.getHolder().getSurface());
                int result2 = CallApi.createRemoteVideoSurface(remoteVideoView.getHolder().getSurface());
                LogUtil.d(Const.TAG_CALL, "result1: " + result1 + ", result2: " + result2);
                mCallSession.showVideoWindow();
            }
        }
    };

    @Override
    protected void initLogics() {
        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_call_vt;
    }

    @Override
    protected void initView() {
        CallApi.setPauseMode(1);
        //视频布局(呼出和通话中)
        remoteVideoView = (SurfaceView) findViewById(R.id.large_video_layout);
        remoteVideoView.setZOrderMediaOverlay(false);
        localVideoView = (SurfaceView) findViewById(R.id.small_video_layout);
        localVideoView.setZOrderMediaOverlay(false);
        mTalkingTimeTv = (TextView) findViewById(R.id.talking_time_tv);
        mHangupLayout = (LinearLayout) findViewById(R.id.hangup_layout);
        mHangupLayout.setOnClickListener(this);
        largeVideoContainer = (RelativeLayout) findViewById(R.id.video_layout);
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
        remoteVideoView.getHolder().addCallback(surfaceCb);
//        remoteVideoView.setZOrderOnTop(false);
        localVideoView.getHolder().addCallback(surfaceCb);
        localVideoView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        localVideoView.setZOrderOnTop(true);
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
                callVideoReSolutionChangeReceiver,
                new IntentFilter(CallApi.EVENT_CALL_VIDEO_RESOLUTION_CHANGE));
        hasRegistReceiver = true;
    }

    private void unRegisterReceivers() {
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(callVideoReSolutionChangeReceiver);
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
            case BussinessConstants.DialMsgID.CALL_INCOMING_FINISH_CLOSING_MSG_ID:
                if(!closed)
                {
                    mVoipLogic.dealOnClosed(mCallSession, mIsInComing, mIsTalking, callTime);
                    closed = true;
                }
                finish();
                break;
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
        LogUtil.d(TAG, "onResume");
        IntentFilter intent = new IntentFilter();
        intent.addAction(Const.ACTION_USB_CAMERA_PLUG_IN_OUT);
        registerReceiver(mCameraPlugReciver, intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause");
        unregisterReceiver(mCameraPlugReciver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //还原免提设置
        if (!speakerState) {
            AudioManager audioManamger = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManamger.setSpeakerphoneOn(speakerState);
        }
        stopCallTimeTask();
        if (hasRegistReceiver) {
            unRegisterReceivers();
        }
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    private int getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

}
