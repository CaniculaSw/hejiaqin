package com.chinamobile.hejiaqin.business.ui.dial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.Const;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.call.CallSession;
import com.huawei.rcs.log.LogApi;
import com.huawei.rcs.system.SysApi;

import java.util.Timer;
import java.util.TimerTask;


public class NurseCallActivity extends BasicActivity implements View.OnClickListener {

    public static final String TAG = NurseCallActivity.class.getSimpleName();

    private TextView mTalkingTimeTv;

    private LinearLayout mHangupLayout;

    private LinearLayout mBackLayout;

    //通话会话对象
    private CallSession mCallSession = null;

    private Timer timer;
    private int callTime;
    private Handler handler = new Handler();

    private LinearLayout mLargeVideoLayout;
    private SurfaceView localVideoView;
    private SurfaceView localVideoSurface;

    private boolean m_isBigVideoCreate_MPEG;

    private boolean hasRegistReceiver;

    private boolean closed;

    private boolean hasStoped = false;

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
            if (localVideoSurface.getHolder() == surfaceHolder) {
                m_isBigVideoCreate_MPEG = true;
                showMpegView();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder arg0) {
            LogApi.d("Const.TAG_CALL", "surfaceDestroyed deleteLocalVideoSurface");
            if (localVideoSurface.getHolder() == arg0) {
                LogApi.d("Const.TAG_CALL", "surfaceDestroyed deleteLocalVideoSurface==m_svBigVideo.getHolder()");
                m_isBigVideoCreate_MPEG = false;
            }
        }

        protected void showMpegView() {
            if (mCallSession == null || localVideoSurface == null) {
                LogApi.e(Const.TAG_CALL, "show view failed callSession " + mCallSession + " m_svBigVideo " + localVideoSurface);
                return;
            }
            LogApi.d(Const.TAG_CALL,  " m_isBigVideoCreate_MPEG: " + m_isBigVideoCreate_MPEG);
            if (m_isBigVideoCreate_MPEG && mCallSession.getStatus() == CallSession.STATUS_CONNECTED && mCallSession.getType() == CallSession.TYPE_VIDEO) {
                int result1 = CallApi.createLocalVideoSurface(localVideoSurface.getHolder().getSurface());
                LogApi.d(Const.TAG_CALL, "result1: " + result1);
                mCallSession.showVideoWindow();
            }
        }
    };

    @Override
    protected void initLogics() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nurse_call;
    }

    @Override
    protected void initView() {
        CallApi.setPauseMode(1);
        mLargeVideoLayout = (LinearLayout) findViewById(R.id.large_video_layout);
        localVideoSurface = (SurfaceView)findViewById(R.id.large_video_surface);
        mTalkingTimeTv = (TextView) findViewById(R.id.talking_time_tv);
        mHangupLayout = (LinearLayout) findViewById(R.id.hangup_layout);
        mBackLayout = (LinearLayout) findViewById(R.id.back_layout);
        mHangupLayout.setOnClickListener(this);
        mBackLayout.setOnClickListener(this);
    }

    @Override
    protected void initDate() {
        mCallSession = CallApi.getForegroudCallSession();

        if (null == mCallSession) {
            LogUtil.d("V2OIP", "hejiaqin initDate: no call is talking any more.");
            finish();
            return;
        }
        acceptTalking();
    }

    private void acceptTalking() {
        //接受一键看护请求
        mCallSession.accept(CallSession.TYPE_VIDEO);
        if (Const.DEVICE_TYPE != Const.TYPE_OTHER) {
            //创建本端显示画面句柄
            localVideoSurface.setVisibility(View.GONE);
            localVideoView = CallApi.createLocalVideoView(getApplicationContext());
            mCallSession.showVideoWindow();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mLargeVideoLayout.addView(localVideoView, layoutParams);
            localVideoView.setZOrderOnTop(false);
        }else{
            localVideoSurface.getHolder().addCallback(surfaceCb);
            localVideoSurface.getHolder().setFormat(PixelFormat.TRANSLUCENT);
            localVideoSurface.setZOrderOnTop(false);
        }
        registerReceivers();
        startCallTimeTask();
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

        IntentFilter intent = new IntentFilter();
        intent.addAction(Const.CAMERA_PLUG);
        registerReceiver(mOtherCameraPlugReciver, intent);
        hasRegistReceiver = true;
    }

    private void unRegisterReceivers() {
        IntentFilter intent = new IntentFilter();
        unregisterReceiver(mOtherCameraPlugReciver);
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
            case R.id.back_layout:
                moveTaskToBack(true);
                break;
        }

    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.DialMsgID.NURSE_CALL_CLOSED_MSG_ID:
                if (msg.obj != null) {
                    CallSession session = (CallSession) msg.obj;
                    if (mCallSession != null && mCallSession.equals(session)) {
                        closed = true;
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
        if (null != localVideoView && CallSession.INVALID_ID != mCallSession.getSessionId()) {
            if (hasStoped) {
                mCallSession.showVideoWindow();
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                if (localVideoView != null) {
                    mLargeVideoLayout.addView(localVideoView, layoutParams);
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != localVideoView && CallSession.INVALID_ID != mCallSession.getSessionId()) {
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
        if (Const.DEVICE_TYPE == Const.TYPE_OTHER) {
            CallApi.deleteLocalVideoSurface(localVideoSurface.getHolder().getSurface());
        }else{
            if (localVideoView != null) {
                localVideoView.setVisibility(View.GONE);
                mLargeVideoLayout.removeView(localVideoView);
                CallApi.deleteLocalVideoView(localVideoView);
                localVideoView = null;
            }
        }
    }

    @Override
    public void onBackPressed() {

    }

}
