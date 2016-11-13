package com.chinamobile.hejiaqin.business.ui.dial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.call.CallSession;
import com.huawei.rcs.log.LogApi;
import com.huawei.rcs.system.SysApi;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class VideoCallActivity extends BasicActivity implements View.OnClickListener {

    public static final String TAG = VideoCallActivity.class.getSimpleName();

    private LinearLayout mLargeVideoLayout;
    private LinearLayout mSmallVideoLayout;

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

    /**
     * 如果重力感应变化角度过小，则不处理.
     */
    private static final int ORIENTATION_SENSITIVITY = 45;

    private int lastOrientation = 270;

    private int lastDisplayRotation = Surface.ROTATION_0;

    private SurfaceView localVideoView;
    private SurfaceView remoteVideoView;

    private boolean hasStoped = false;

    private boolean speakerState;

    private boolean closed;

    /* display the video stream which arrived from remote. */
    private BroadcastReceiver remoteVideoStreamArrivedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CallSession session = (CallSession) intent.getSerializableExtra(CallApi.PARAM_CALL_SESSION);
            if (!mCallSession.equals(session)) {
                return;
            }
            remoteVideoView.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mLargeVideoLayout.updateViewLayout(remoteVideoView, layoutParams);
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
        //视频布局(呼出和通话中)
        mLargeVideoLayout = (LinearLayout) findViewById(R.id.large_video_layout);
        mSmallVideoLayout = (LinearLayout) findViewById(R.id.small_video_layout);

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
        mIsTalking =true;
        AudioManager audioManamger = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        speakerState = audioManamger.isSpeakerphoneOn();
        if (!speakerState) {
            audioManamger.setSpeakerphoneOn(!speakerState);
        }

            OrientationEventListener listener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {

                @Override
                public void onOrientationChanged(int orientation) {
                    if (orientation != OrientationEventListener.ORIENTATION_UNKNOWN) {
                        orientationChanged(orientation);
                    }
                }
            };
            if (listener.canDetectOrientation()) {
                listener.enable();
            } else {
                LogUtil.e("V2OIP", "OrientationEventListener enable failed!!");
            }

        /* In order to properly display video, Camera rotate should be set. */
        setCameraRotate();
        /* creat local video view and remote video view. */
        createVideoView();
        registerReceivers();
        mCallSession.showVideoWindow();
        startCallTimeTask();
    }

    private void setCameraRotate() {
        Display display = this.getWindowManager().getDefaultDisplay();
        int displayRotation = display.getRotation();
        int cameraRotate = getCameraOrientation(displayRotation);
        CallApi.setCameraRotate(cameraRotate);
    }

    private void createVideoView() {

        if (remoteVideoView == null) {
            remoteVideoView = CallApi.createRemoteVideoView(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            remoteVideoView.setVisibility(View.GONE);
            mLargeVideoLayout.addView(remoteVideoView, layoutParams);
            remoteVideoView.setZOrderOnTop(false);
        }

        if (localVideoView == null) {
            localVideoView = CallApi.createLocalVideoView(getApplicationContext());
            localVideoView.setZOrderOnTop(false);
            localVideoView.setVisibility(View.GONE);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSmallVideoLayout.addView(localVideoView, layoutParams);
        localVideoView.setVisibility(View.VISIBLE);
        mCallSession.showVideoWindow();

    }

    private void orientationChanged(int orientation) {
        int cameraRotate = 270;
        int displayRotation = Surface.ROTATION_0;
        if (Math.abs(orientation - lastOrientation) < ORIENTATION_SENSITIVITY
                || Math.abs(orientation - lastOrientation - 360) < ORIENTATION_SENSITIVITY) {
            return;
        }

        lastOrientation = orientation;

        if (orientation < ORIENTATION_SENSITIVITY
                || 360 - orientation < ORIENTATION_SENSITIVITY) {
            displayRotation = Surface.ROTATION_0;
        } else if (Math.abs(orientation - 90) <= ORIENTATION_SENSITIVITY) {
            displayRotation = Surface.ROTATION_90;
        } else if (Math.abs(orientation - 180) <= ORIENTATION_SENSITIVITY) {
            displayRotation = Surface.ROTATION_180;
        } else if (Math.abs(orientation - 270) <= ORIENTATION_SENSITIVITY) {
            displayRotation = Surface.ROTATION_270;
        } else {
            LogUtil.e("V2OIP", "orientationChanged get wrong orientation:" + orientation + ", getCameraOrientation with default displayRotation " + Surface.ROTATION_0);
            lastDisplayRotation = Surface.ROTATION_0;
        }

        if (lastDisplayRotation == displayRotation) {
            return;
        }

        lastDisplayRotation = displayRotation;
        cameraRotate = getCameraOrientation(lastDisplayRotation);
        CallApi.setCameraRotate(cameraRotate);
    }

    /**
     * get orientation to set camera orientation in this situation
     *
     * @param displayRotation get the displayRotation by Gravity sensor(onOrientationChanged event).
     */
    private int getCameraOrientation(int displayRotation) {

        boolean isFrontCamera = true;
        int cameraOrientation = 0;
        int degrees = 0;
        int result = 0;

        if (CallApi.getCameraCount() < 2) {
            isFrontCamera = false;
            LogUtil.d("V2OIP", "getCameraOrientation getCameraCount " + CallApi.getCameraCount());
        } else {
            if (CallApi.getCamera() == CallApi.CAMERA_TYPE_FRONT) {
                isFrontCamera = true;
            } else {
                isFrontCamera = false;
            }
        }

        cameraOrientation = CallApi.getCameraRotate();

        switch (displayRotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 270;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 90;
                break;
            default:
                LogUtil.e("V2OIP", "getCameraOrientation wrong displayRotation " + displayRotation);
                break;
        }

        CallApi.setVideoRenderRotate(degrees);

        if (isFrontCamera) {
            result = (cameraOrientation + degrees) % 360;
        } else {
            result = (cameraOrientation - degrees + 360) % 360;
        }

        return result;
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
                remoteVideoStreamArrivedReceiver,
                new IntentFilter(CallApi.EVENT_CALL_VIDEO_STREAM_ARRIVED));


    }

    private void unRegisterReceivers() {

        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(remoteVideoStreamArrivedReceiver);

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
                mVoipLogic.hangup(mCallSession, mIsInComing, mIsTalking,callTime);
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
                        }, 2000);
                    } else if (session != null && session.getType() == CallSession.TYPE_VIDEO_INCOMING) {
                        mVoipLogic.dealOnClosed(session, true, false,0);
                    }
                }
                break;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mIsTalking) {
            return;
        }
        if ((null != localVideoView || null != remoteVideoView)
                && CallSession.INVALID_ID != mCallSession.getSessionId()) {
            if (hasStoped) {
                mCallSession.showVideoWindow();
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                if (remoteVideoView != null) {
                    mLargeVideoLayout.addView(remoteVideoView, layoutParams);
                }
                if (localVideoView != null) {
                    mSmallVideoLayout.addView(localVideoView, layoutParams);
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!mIsTalking) {
            return;
        }
        if ((null != localVideoView || null != remoteVideoView)
                && CallSession.INVALID_ID != mCallSession.getSessionId()) {
            mCallSession.hideVideoWindow();
            mLargeVideoLayout.removeAllViews();
            mSmallVideoLayout.removeAllViews();
            hasStoped = true;
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
        if (mIsTalking) {
            unRegisterReceivers();
        }
    }

    private void destroyVideoView() {

        if (localVideoView != null) {
            localVideoView.setVisibility(View.GONE);
            mLargeVideoLayout.removeView(localVideoView);
            mSmallVideoLayout.removeView(localVideoView);
            CallApi.deleteRemoteVideoView(localVideoView);
            localVideoView = null;
        }

        if (remoteVideoView != null) {
            remoteVideoView.setVisibility(View.GONE);
            mLargeVideoLayout.removeView(remoteVideoView);
            CallApi.deleteRemoteVideoView(remoteVideoView);
            remoteVideoView = null;
        }
    }

    @Override
    public void onBackPressed()
    {

    }

}
