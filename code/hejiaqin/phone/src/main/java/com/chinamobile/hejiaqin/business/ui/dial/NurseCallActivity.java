package com.chinamobile.hejiaqin.business.ui.dial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
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

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.StringUtil;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.call.CallSession;
import com.huawei.rcs.system.SysApi;

import java.util.Timer;
import java.util.TimerTask;

public class NurseCallActivity extends BasicActivity implements View.OnClickListener {

    public static final String TAG = NurseCallActivity.class.getSimpleName();

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

    //是否通话中
    private boolean mIsTalking;

    //被叫号码
    private String mCalleeNumber;

    //被叫姓名
    private String mCalleeName;

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

    private SurfaceView remoteVideoView;

    private boolean hasStoped = false;

    private boolean closed;

    private boolean hasRegistReceiver;

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
        //获取voiplogic,便于收到voiplogic通知
        super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nurse_call;
    }

    @Override
    protected void initView() {
        //视频布局(呼出和通话中)
        mVideoLayout = (RelativeLayout) findViewById(R.id.video_layout);
        mLargeVideoLayout = (LinearLayout) findViewById(R.id.large_video_layout);

        //顶部布局(呼出和通话中)
        mTopLayout = (LinearLayout) findViewById(R.id.top_layout);
        mContactNameTv = (TextView) findViewById(R.id.contact_name_tv);
        mCallStatusLayout = (LinearLayout) findViewById(R.id.call_status_layout);
        mCallStatusTv =(TextView)findViewById(R.id.call_status_tv);
        mTalkingTimeTv = (TextView) findViewById(R.id.talking_time_tv);

        //底部布局(呼出和通话中)
        mBottomLayout = (LinearLayout) findViewById(R.id.bottom_layout);
        mHangupLayout = (LinearLayout) findViewById(R.id.hangup_layout);
        mHangupLayout.setOnClickListener(this);
    }

    @Override
    protected void initDate() {
        mCalleeNumber = getIntent().getStringExtra("tvAccount");
        mCalleeName = getIntent().getStringExtra("tvName");
        outingCall();
        showOuting();
    }

    private void showOuting() {
        //TODO 查询姓名
        if (!StringUtil.isNullOrEmpty(mCalleeName)) {
            mContactNameTv.setText(mCalleeName);
        } else {
            mContactNameTv.setText(mCalleeNumber);
        }
        mTopLayout.setVisibility(View.VISIBLE);
        mVideoLayout.setVisibility(View.VISIBLE);
        mBottomLayout.setVisibility(View.VISIBLE);
    }

    private void outingCall() {
        Intent extParas = new Intent();
        extParas.putExtra(CallApi.EN_CALL_VIDEO_EXTPARAS_NURSE, 1);
        mCallSession = CallApi.initiateVideoCallWithExtParas(CommonUtils.getCountryPhoneNumber(mCalleeNumber), extParas);
        if (mCallSession.getErrCode() != CallSession.ERRCODE_OK) {
            showToast(R.string.nurse_outing_error, Toast.LENGTH_SHORT, null);
            closed = true;
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);
        } else {
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
            setCameraRotate();
            registerReceivers();
        }
    }

    private void setCameraRotate() {
        Display display = this.getWindowManager().getDefaultDisplay();
        int displayRotation = display.getRotation();
        int cameraRotate = getCameraOrientation(displayRotation);
        CallApi.setCameraRotate(cameraRotate);
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


    private void showTalking() {
        mIsTalking = true;
        CallApi.setPauseMode(1);
        createVideoView();
        mCallSession.showVideoWindow();
        mCallStatusTv.setText(R.string.nurse_call_talking);
        startCallTimeTask();
        mCallSession.mute();
    }

    private void createVideoView() {

        if (remoteVideoView == null) {
            remoteVideoView = CallApi.createRemoteVideoView(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            remoteVideoView.setVisibility(View.GONE);
            mLargeVideoLayout.addView(remoteVideoView, layoutParams);
            remoteVideoView.setZOrderOnTop(false);
        }

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
        hasRegistReceiver = true;
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
                closed = true;
                mCallSession.terminate();
                finish();
                break;
        }

    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.DialMsgID.NURSE_CALL_ON_TALKING_MSG_ID:
            case BussinessConstants.DialMsgID.CALL_ON_TALKING_MSG_ID:
                LogUtil.d(TAG,"NURSE_CALL_ON_TALKING_MSG_ID");
                showTalking();
                break;
            case BussinessConstants.DialMsgID.NURSE_CALL_CLOSED_MSG_ID:
            case BussinessConstants.DialMsgID.CALL_CLOSED_MSG_ID:
                if (msg.obj != null) {
                    CallSession session = (CallSession) msg.obj;
                    if (mCallSession != null && mCallSession.equals(session)) {
                        closed = true;
                        if (mCallSession.getSipCause() == BussinessConstants.DictInfo.SIP_TEMPORARILY_UNAVAILABLE) {
                            showToast(R.string.nurse_temporarily_unavailable, Toast.LENGTH_SHORT, null);
                        } else if ( !mIsTalking && (mCallSession.getSipCause() == BussinessConstants.DictInfo.SIP_BUSY_HERE
                                || mCallSession.getSipCause() == BussinessConstants.DictInfo.SIP_DECLINE)) {
                            showToast(R.string.nurse_busy_here, Toast.LENGTH_SHORT, null);
                        }
                        getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 3000);
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
        if ( null != remoteVideoView && CallSession.INVALID_ID != mCallSession.getSessionId()) {
            if (hasStoped) {
                mCallSession.showVideoWindow();
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                if (remoteVideoView != null) {
                    mLargeVideoLayout.addView(remoteVideoView, layoutParams);
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
        if (null != remoteVideoView
                && CallSession.INVALID_ID != mCallSession.getSessionId()) {
            mCallSession.hideVideoWindow();
            mLargeVideoLayout.removeAllViews();
            hasStoped = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyVideoView();
        stopCallTimeTask();
        if (hasRegistReceiver) {
            unRegisterReceivers();
        }
    }

    private void destroyVideoView() {
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
