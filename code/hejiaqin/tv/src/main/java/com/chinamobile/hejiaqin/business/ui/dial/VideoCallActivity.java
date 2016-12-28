package com.chinamobile.hejiaqin.business.ui.dial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.SurfaceHolder;
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


public class VideoCallActivity extends BasicActivity implements View.OnClickListener {

    public static final String TAG = VideoCallActivity.class.getSimpleName();

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

    private SurfaceView localVideoView;
    private SurfaceView remoteVideoView;

    private boolean speakerState;

    private boolean closed;

    private boolean m_isSmallVideoCreate_MPEG;
    private boolean m_isBigVideoCreate_MPEG;

    private boolean hasRegistReceiver;

    private BroadcastReceiver mCameraPlugReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Rect rectLocal = new Rect();
            int iState = intent.getIntExtra("state", -1);
            LogApi.d(Const.TAG_UI, "camera stat change:" + iState);
            if (1 == iState) {
                rectLocal.left = 0;
                rectLocal.top = 0;
                rectLocal.right = 320;
                rectLocal.bottom = 180;
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
                Rect rectLocal = new Rect();
                rectLocal.left = 0;
                rectLocal.top = 0;
                rectLocal.right = 320;
                rectLocal.bottom = 180;
                CaaSSdkService.setLocalRenderPos(rectLocal, CallApi.VIDEO_LAYER_TOP);
                CaaSSdkService.showRemoteVideoRender(true);
            }
        }
    };

    private BroadcastReceiver mOtherCameraPlugReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Rect rectLocal = new Rect();
            int iState = intent.getIntExtra("state", -1);
            LogApi.d(Const.TAG_UI, "camera stat change:" + iState);
            if (1 == iState) {
                mCallSession.openLocalVideo();
            } else {
                mCallSession.closeLocalVideo();
            }
        }
    };

    protected final SurfaceHolder.Callback surfaceCb = new SurfaceHolder.Callback() {
        @Override
        public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            LogApi.d(Const.TAG_CALL, "surfaceCreated:");
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
            LogApi.d("Const.TAG_CALL", "surfaceDestroyed deleteLocalVideoSurface");
            if (localVideoView.getHolder() == arg0) {
                LogApi.d("Const.TAG_CALL", "surfaceDestroyed deleteLocalVideoSurface==m_svSmallVideo.getHolder()");
                m_isSmallVideoCreate_MPEG = false;
            } else if (remoteVideoView.getHolder() == arg0) {
                LogApi.d("Const.TAG_CALL", "surfaceDestroyed deleteLocalVideoSurface==m_svBigVideo.getHolder()");
                m_isBigVideoCreate_MPEG = false;
            }
        }

        protected void showMpegView() {
            if (mCallSession == null || localVideoView == null || remoteVideoView == null) {
                LogApi.e(Const.TAG_CALL, "show view failed callSession " + mCallSession + " m_svSmallVideo " + localVideoView + " m_svBigVideo " + remoteVideoView);
                return;
            }
            LogApi.d(Const.TAG_CALL, "m_isSmallVideoCreate_MPEG: " + m_isSmallVideoCreate_MPEG + ", m_isBigVideoCreate_MPEG: " + m_isBigVideoCreate_MPEG);
            if (m_isSmallVideoCreate_MPEG && m_isBigVideoCreate_MPEG && mCallSession.getStatus() == CallSession.STATUS_CONNECTED && mCallSession.getType() == CallSession.TYPE_VIDEO) {
                int result1 = CallApi.createLocalVideoSurface(localVideoView.getHolder().getSurface());
                int result2 = CallApi.createRemoteVideoSurface(remoteVideoView.getHolder().getSurface());
                LogApi.d(Const.TAG_CALL, "result1: " + result1 + ", result2: " + result2);
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
        return R.layout.activity_video_call;
    }

    @Override
    protected void initView() {
        CallApi.setPauseMode(1);
        //视频布局(呼出和通话中)
        remoteVideoView = (SurfaceView) findViewById(R.id.large_video_layout);
        localVideoView = (SurfaceView) findViewById(R.id.small_video_layout);
        mTalkingTimeTv = (TextView) findViewById(R.id.talking_time_tv);
        mHangupLayout = (LinearLayout) findViewById(R.id.hangup_layout);
        mHangupLayout.setOnClickListener(this);
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
        if (Const.DEVICE_TYPE != Const.TYPE_OTHER) {
            remoteVideoView.setVisibility(View.GONE);
            localVideoView.setVisibility(View.GONE);
            mCallSession.showVideoWindow();
            CaaSSdkService.setRemoteRenderPos(getFullScreenRect(), CallApi.VIDEO_LAYER_BOTTOM);
            CaaSSdkService.showRemoteVideoRender(true);
        }else{
            remoteVideoView.getHolder().addCallback(surfaceCb);
            localVideoView.getHolder().addCallback(surfaceCb);
            localVideoView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
            localVideoView.setZOrderOnTop(true);
        }
    }

    private Rect getFullScreenRect() {
        int[] metrics = new int[2];
        getDisplayMetrics(this, metrics);

        Rect rect = new Rect();
        rect.left = 0;
        rect.top = 0;
        rect.right = metrics[0];
        rect.bottom = metrics[1];
        return rect;
    }

    private void getDisplayMetrics(Context context, int metrics[]) {
        if (null == metrics) {
            metrics = new int[2];
        }

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        metrics[0] = screenWidth;
        metrics[1] = screenHeight;
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
        if (Const.DEVICE_TYPE != Const.TYPE_OTHER) {
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                    remoteNetStatusChangeReciverr,
                    new IntentFilter(CallApi.EVENT_CALL_VIDEO_NET_STATUS_CHANGE));
            IntentFilter intent = new IntentFilter();
            intent.addAction(Const.CAMERA_PLUG);
            registerReceiver(mCameraPlugReciver, intent);
        }else{
            IntentFilter intent = new IntentFilter();
            intent.addAction(Const.CAMERA_PLUG);
            registerReceiver(mOtherCameraPlugReciver, intent);
        }
        hasRegistReceiver = true;
    }

    private void unRegisterReceivers() {
        if (Const.DEVICE_TYPE != Const.TYPE_OTHER) {
            LocalBroadcastManager.getInstance(getApplicationContext())
                    .unregisterReceiver(remoteNetStatusChangeReciverr);
            unregisterReceiver(mCameraPlugReciver);
        }else{
            IntentFilter intent = new IntentFilter();
            unregisterReceiver(mOtherCameraPlugReciver);
        }
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
    }

    private void destroyVideoView() {
        if (Const.DEVICE_TYPE == Const.TYPE_OTHER) {
            CallApi.deleteLocalVideoSurface(localVideoView.getHolder().getSurface());
            CallApi.deleteRemoteVideoSurface(remoteVideoView.getHolder().getSurface());
        }
    }

    @Override
    public void onBackPressed() {

    }

}
