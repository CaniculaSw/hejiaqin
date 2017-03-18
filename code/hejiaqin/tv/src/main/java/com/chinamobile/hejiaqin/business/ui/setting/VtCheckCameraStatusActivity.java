package com.chinamobile.hejiaqin.business.ui.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.business.CaaSSdkService;
import com.chinamobile.hejiaqin.business.Const;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.log.LogApi;

public class VtCheckCameraStatusActivity extends BasicActivity {

    private RelativeLayout abLayout;
    private int[] mMetrics = new int[2];
    SurfaceView videoSurfaceView;
    private BroadcastReceiver mCameraPlugReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int iState = intent.getIntExtra("state", -1);
            LogApi.d(Const.TAG_UI, "camera stat change:" + iState);
            if (1 == iState) {
                createLocalView();
            } else {
                closeLocalView();
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_status_vt;
    }

    @Override
    protected void initView() {
        abLayout = (RelativeLayout) findViewById(R.id.camera_ab_ll);
        videoSurfaceView = (SurfaceView) findViewById(R.id.video_view);
        if (CallApi.getCameraCount() > 0) {
            createLocalView();
        } else {
            closeLocalView();
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
        unregisterReceiver(mCameraPlugReciver);
        closeLocalView();
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

    private Rect getFullScreenRect() {
        getDisplayMetrics(this);

        Rect rect = new Rect();
        rect.left = 0;
        rect.top = 0;
        rect.right = mMetrics[0];
        rect.bottom = mMetrics[1];
        return rect;
    }


    private void getDisplayMetrics(Context context, int metrics[]) {
        if (null == metrics) {
            metrics = new int[2];
        }

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        if (screenHeight > screenWidth) {
            int temp = screenWidth;
            screenWidth = screenHeight;
            screenHeight = temp;
        }
        metrics[0] = screenWidth;
        metrics[1] = screenHeight;
    }

    private LinearLayout.LayoutParams getLocalPreviewViewMetrics(int height, int width) {
        int[] metrics = new int[2];
        int rmtHeight = 0;
        getDisplayMetrics(this, metrics);
        LinearLayout.LayoutParams rp;
        rmtHeight = metrics[0] * width / height;
        rp = new LinearLayout.LayoutParams((int) metrics[0], rmtHeight);
        return rp;
    }


    private void createLocalView() {
        abLayout.setVisibility(View.GONE);
//        CaaSSdkService.setVideoLevel(0);
//        CallApi.setVisible(CallApi.VIDEO_TYPE_LOCAL, true);
//        CallApi.setRegion(CallApi.VIDEO_TYPE_LOCAL, 0, 0, 1280, 720, CallApi.VIDEO_LAYER_TOP);
//        CallApi.switchCameraTo(CallApi.getCamera());
//        CallApi.switchCamera();
//        int result = CallApi.openLocalView();
//        showToast("" + result, Toast.LENGTH_LONG, null);
//        videoSurfaceView.setBackgroundDrawable(null);

//        mSession = CallApi.initiateAudioCall(UserInfoCacheManager.getUserInfo(this).getSdkAccount());
//        videoSurfaceView = CallApi.createLocalVideoView(getApplicationContext());
//        videoSurfaceView.setZOrderOnTop(false);
//        mLargeVideoLayout.addView(videoSurfaceView, getLocalPreviewViewMetrics(3, 4));
//        videoSurfaceView.setVisibility(View.VISIBLE);
//        mSession.prepareVideo();
//        getDisplayMetrics(VtCheckCameraStatusActivity.this);
//
//        CaaSSdkService.setLocalRenderPos(getFullScreenRect(), CallApi.VIDEO_LAYER_TOP);
//        boolean falg = CaaSSdkService.openLocalView();
//        showToast(""+falg, Toast.LENGTH_LONG,null);
    }

    private void closeLocalView() {
        CaaSSdkService.closeLocalView();
        abLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initDate() {

    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initLogics() {

    }

}
