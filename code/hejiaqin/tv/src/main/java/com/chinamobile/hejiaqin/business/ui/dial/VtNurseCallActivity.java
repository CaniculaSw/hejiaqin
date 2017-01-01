package com.chinamobile.hejiaqin.business.ui.dial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
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


public class VtNurseCallActivity extends BasicActivity implements View.OnClickListener {

    public static final String TAG = VtNurseCallActivity.class.getSimpleName();

    private TextView mTalkingTimeTv;

    private LinearLayout mHangupLayout;

    //通话会话对象
    private CallSession mCallSession = null;

    private Timer timer;
    private int callTime;
    private Handler handler = new Handler();

    private SurfaceView localVideoSurface;

    private boolean m_isBigVideoCreate_MPEG;

    private boolean hasRegistReceiver;

    private boolean closed;

    private boolean hasStoped = false;

    private boolean accepted;

    private boolean bCameraClose = false;

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
            LogApi.d(Const.TAG_CALL, " m_isBigVideoCreate_MPEG: " + m_isBigVideoCreate_MPEG);
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
        return R.layout.activity_nurse_call_vt;
    }

    @Override
    protected void initView() {
        CallApi.setPauseMode(1);
        localVideoSurface = (SurfaceView) findViewById(R.id.large_video_surface);
        mTalkingTimeTv = (TextView) findViewById(R.id.talking_time_tv);
        mHangupLayout = (LinearLayout) findViewById(R.id.hangup_layout);
        mHangupLayout.setOnClickListener(this);
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
        accepted = true;
        //接受一键看护请求
        mCallSession.accept(CallSession.TYPE_VIDEO);
        localVideoSurface.getHolder().addCallback(surfaceCb);
        localVideoSurface.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        localVideoSurface.setZOrderOnTop(false);
        registerReceivers();
        startCallTimeTask();
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
    }

    private void unRegisterReceivers() {
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
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume");
        if(accepted) {
            IntentFilter intent = new IntentFilter();
            intent.addAction(Const.ACTION_USB_CAMERA_PLUG_IN_OUT);
            registerReceiver(mCameraPlugReciver, intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause");
        if(accepted) {
            unregisterReceiver(mCameraPlugReciver);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopCallTimeTask();
        if (hasRegistReceiver) {
            unRegisterReceivers();
        }
    }

    @Override
    public void onBackPressed() {

    }

}
