package com.chinamobile.hejiaqin.business.ui.setting.dialog;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.ui.basic.MyCountDownTimer;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.FileUtil;
import com.huawei.rcs.call_recording.CallSessionRecording;
import com.huawei.rcs.system.SysApi;

import java.io.IOException;

/**
 * Created by eshaohu on 17/3/6.
 */
public class RecordingDialog extends Dialog {
    private Context mContext;
    private ImageView iv;
    private Animation animation;
    private TextView speakTips;
    private TextView tips;
    private RecordingCountDownTimer recordingCountDownTimer;
    private PlayingCountDownTimer playingCountDownTimer;
    private boolean isRecording;
    private TextView controlBtnText;
    private boolean isPaused;
    private ISettingLogic settingLogic;
    private final String ptt_filename = SysApi.DEFAULT_FILE_STORED_LOCATION + "/receivedPtt/" + "record_audio.amr";
    private long duration;
    private long begin;
    private long end;
    private int inputVol;

    public RecordingDialog(Context context, ISettingLogic settingLogic) {
        super(context);
        this.mContext = context;
        this.settingLogic = settingLogic;
    }

    public RecordingDialog(Context context, int themeResId, ISettingLogic settingLogic) {
        super(context, themeResId);
        this.mContext = context;
        this.settingLogic = settingLogic;
    }

    protected RecordingDialog(Context context, boolean cancelable, OnCancelListener cancelListener, ISettingLogic settingLogic) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.settingLogic = settingLogic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recording);
        iv = (ImageView) findViewById(R.id.iv);
        animation = AnimationUtils.loadAnimation(this.mContext, R.anim.progress_scan);
        speakTips = (TextView) findViewById(R.id.speak_to_mic_tv);
        tips = (TextView) findViewById(R.id.tips_text);
        controlBtnText = (TextView) findViewById(R.id.control_text);
        controlBtnText.setClickable(true);
        controlBtnText.requestFocus();
        startRecording();
    }


    private void startRecording() {
        if (!FileUtil.isFileExists(this.ptt_filename)) {
            try {
                FileUtil.createFile(this.ptt_filename);
            } catch (IOException e) {
                Toast.makeText(mContext, "不能创建录音文件，请检查机顶盒存储空间。", Toast.LENGTH_LONG);
                return;
            }
        }
        isRecording = true;
        this.inputVol = 0;
        begin = System.currentTimeMillis();
        CallSessionRecording.startMediaRec(this.ptt_filename);
        tips.setText(R.string.recording);
        controlBtnText.setText(R.string.stop_recording);
        iv.startAnimation(animation);
        startRecTimer(this.recordingCountDownTimer);
    }

    private void startPlayBack(long howLong) {
        AudioManager audioManamger = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        boolean speakerState = audioManamger.isSpeakerphoneOn();
        if (!speakerState) {
            audioManamger.setSpeakerphoneOn(!speakerState);
        }
        CallSessionRecording.startMediaPlay(this.ptt_filename);
        isRecording = false;
        tips.setText(R.string.playing);
        controlBtnText.setText(R.string.stop_playback);
        animation.setDuration(duration * 1000);
        iv.startAnimation(animation);
        startPlayingTimer(this.playingCountDownTimer, duration);
        isPaused = false;
    }

    private void pause() {
        iv.clearAnimation();
        CallSessionRecording.stopMediaPlay();
        controlBtnText.setText(R.string.start_playback);
        tips.setText("已停止播放");
        speakTips.setText(mContext.getString(R.string.playing_tips, duration));
        isPaused = true;
        isRecording = false;
    }

    private void startRecTimer(RecordingCountDownTimer timer) {
        startRecTimer(timer, 10);
    }

    private void startRecTimer(RecordingCountDownTimer timer, long howLong) {
        timer = new RecordingCountDownTimer(howLong * 1000);
        timer.start();
    }

    private void startPlayingTimer(PlayingCountDownTimer timer, long howLong) {
        timer = new PlayingCountDownTimer(howLong * 1000);
        timer.start();
    }

    private class RecordingCountDownTimer extends MyCountDownTimer {

        /**
         * @param millisInFuture 总的时间
         */
        public RecordingCountDownTimer(long millisInFuture) {
            super(millisInFuture);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            super.onTick(millisUntilFinished);
            RecordingDialog.this.inputVol += CallSessionRecording.getInputVolumeWave();
            speakTips.setText(mContext.getString(R.string.speak_to_mic, millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            super.onFinish();
            if (isRecording) {
                CallSessionRecording.stopMediaRec();
                end = System.currentTimeMillis();
                duration = (end - begin) / 1000;
                startPlayBack(duration);
            }
        }
    }

    private class PlayingCountDownTimer extends MyCountDownTimer {

        /**
         * @param millisInFuture 总的时间
         */
        public PlayingCountDownTimer(long millisInFuture) {
            super(millisInFuture);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            super.onTick(millisUntilFinished);
            if (!isPaused) {
                speakTips.setText(mContext.getString(R.string.playing_tips, millisUntilFinished / 1000));
            }
        }

        @Override
        public void onFinish() {
            super.onFinish();
            if (!isPaused) {
                CallSessionRecording.stopMediaPlay();
                AudioManager audioManamger = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
                boolean speakerState = audioManamger.isSpeakerphoneOn();
                if (speakerState) {
                    audioManamger.setSpeakerphoneOn(!speakerState);
                }
                settingLogic.checkMicFinished(inputVol);
                dismiss();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                if (isRecording || isPaused) {
                    if (isRecording) {
                        CallSessionRecording.stopMediaRec();
                        end = System.currentTimeMillis();
                        duration = (end - begin) / 1000;
                    }
                    startPlayBack(duration);
                } else {
                    pause();
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        AudioManager audioManamger = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        boolean speakerState = audioManamger.isSpeakerphoneOn();
        if (speakerState) {
            audioManamger.setSpeakerphoneOn(!speakerState);
        }
        CallSessionRecording.stopMediaRec();
        CallSessionRecording.stopMediaPlay();
        super.onBackPressed();
    }
}
