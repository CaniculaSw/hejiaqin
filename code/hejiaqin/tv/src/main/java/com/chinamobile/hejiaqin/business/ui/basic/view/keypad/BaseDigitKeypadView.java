package com.chinamobile.hejiaqin.business.ui.basic.view.keypad;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.chinamobile.hejiaqin.tv.R;

/**
 * 数字键盘界面
 * @author z00166692
 */
public abstract class BaseDigitKeypadView extends LinearLayout
{
    /**
     * 按鍵音播放延迟时间
     */
    private static final int TONE_LENGTH = 150;
    
    /**
     * key 1
     */
    public LinearLayout btnOne;
    
    /**
     * key 2
     */
    public LinearLayout btnTwo;
    
    /**
     * key 3
     */
    public LinearLayout btnThree;
    
    /**
     * key 4
     */
    public LinearLayout btnFour;
    
    /**
     * key 5
     */
    public LinearLayout btnFive;
    
    /**
     * key 6
     */
    public LinearLayout btnSix;
    
    /**
     * key 7
     */
    public LinearLayout btnSeven;
    
    /**
     * key8
     */
    public LinearLayout btnEight;
    
    /**
     * key 9
     */
    public LinearLayout btnNine;
    
    /**
     * key *
     */
    public LinearLayout btnAsterisk;
    
    /**
     * key 0
     */
    public LinearLayout btnZero;
    
    /**
     * key #
     */
    public LinearLayout btnDot;
    
    /**
     * context
     */
    private Context context;
    
    /**
     * 按键响应接口
     */
    private DigitKeyPressEvent mKeyPressEvent;
    
    /**
     * 按键音播放
     */
    private ToneGenerator mToneGenerator;
    
    /**
     * 监视器对象锁
     */
    private Object mToneGeneratorLock = new Object();
    
    /**
     * DigitKeypadLinearLayout
     * @param context context
     * @param attrs attrs
     */
    public BaseDigitKeypadView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        // 初始化界面控件
        initView(context);
        // 设置空间响应事件
        setBtnClickListener();
        // 初始化按键音播放控件
        initTone();
    }
    
    /**
     * 初始化控件
     * @param context context
     */
    protected abstract void initView(Context context);
    
    //    {
    //        layoutShowDial = (LinearLayout) LayoutInflater.from(context)
    //                .inflate(R.layout.linearlayout_digit_keypad, null);
    //        addView(layoutShowDial, new LayoutParams(LayoutParams.MATCH_PARENT,
    //                LayoutParams.MATCH_PARENT));
    //        // 实例化拨号盘中的按键
    //        ImageView btnOne = (ImageView) findViewById(R.id.key_one);
    //        ImageView btnTwo = (ImageView) findViewById(R.id.key_two);
    //        ImageView btnThree = (ImageView) findViewById(R.id.key_three);
    //        ImageView btnFour = (ImageView) findViewById(R.id.key_four);
    //        ImageView btnFive = (ImageView) findViewById(R.id.key_five);
    //        ImageView btnSix = (ImageView) findViewById(R.id.key_six);
    //        ImageView btnSeven = (ImageView) findViewById(R.id.key_seven);
    //        ImageView btnEight = (ImageView) findViewById(R.id.key_eight);
    //        ImageView btnNine = (ImageView) findViewById(R.id.key_nine);
    //        ImageView btnAsterisk = (ImageView) findViewById(R.id.key_clear);
    //        ImageView btnZero = (ImageView) findViewById(R.id.key_zero);
    //        ImageView btnDot = (ImageView) findViewById(R.id.key_dot);
    /**
     * 设置按钮点击事件
     */
    private void setBtnClickListener()
    {
        // 拨号盘中按键的点击事件
        ButtonOnclick buttonOnclick = new ButtonOnclick();
        btnOne.setOnClickListener(buttonOnclick);
        btnTwo.setOnClickListener(buttonOnclick);
        btnThree.setOnClickListener(buttonOnclick);
        btnFour.setOnClickListener(buttonOnclick);
        btnFive.setOnClickListener(buttonOnclick);
        btnSix.setOnClickListener(buttonOnclick);
        btnSeven.setOnClickListener(buttonOnclick);
        btnEight.setOnClickListener(buttonOnclick);
        btnNine.setOnClickListener(buttonOnclick);
        btnAsterisk.setOnClickListener(buttonOnclick);
        btnZero.setOnClickListener(buttonOnclick);
        btnDot.setOnClickListener(buttonOnclick);
        
//        btnZero.setOnLongClickListener(new OnLongClickListener()
//        {
//            @Override
//            public boolean onLongClick(View v)
//            {
//                // 系统“触感反馈”未开启
//                if (Settings.System.getInt(context.getContentResolver(),
//                    Settings.System.HAPTIC_FEEDBACK_ENABLED,
//                    1) == 1)
//                {
//                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                }
//                playTone(ToneGenerator.TONE_DTMF_0);
//                // 长按0键,输入+号
//                mKeyPressEvent.onKeyPressed(KeyEvent.KEYCODE_PLUS, "+");
//
//                return true;
//            }
//        });
    }
    /***/
    public void playTone(int tone)
    {
        // 系统“按键操作音”未开启
        if (Settings.System.getInt(context.getContentResolver(),
            Settings.System.DTMF_TONE_WHEN_DIALING,
            1) == 0)
        {
            return;
        }
        
        AudioManager audioManager =
            (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int ringerMode = audioManager.getRingerMode();
        if ((ringerMode == AudioManager.RINGER_MODE_SILENT)
            || (ringerMode == AudioManager.RINGER_MODE_VIBRATE))
        {
            //静音或震动时不发出按键声音
            return;
        }
        
        synchronized (mToneGeneratorLock)
        {
            if (mToneGenerator == null)
            {
                return;
            }
            // 播放按键音
            mToneGenerator.startTone(tone, TONE_LENGTH);
        }
    }
    
    /**
     * 初始化按键音数据
     */
    private void initTone()
    {
        if(isInEditMode())
        {
            return;
        }
        synchronized (mToneGeneratorLock)
        {
            if (mToneGenerator == null)
            {
                try
                {
                    mToneGenerator =
                        new ToneGenerator(AudioManager.STREAM_SYSTEM, 60);
                }
                catch (RuntimeException e)
                {
                    mToneGenerator = null;
                }
            }
        }
    }
    
    /**
     * 设置点击事件
     * @param event 事件
     */
    public void setDigitKeyPressEvent(DigitKeyPressEvent event)
    {
        if (event != null)
        {
            mKeyPressEvent = event;
        }
    }
    
    /**
     * 按钮点击
     * @author z00166692
     */
    class ButtonOnclick implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            int keyCode = -1;
            String keyStr = null;
            // 系统“触感反馈”未开启
            if (Settings.System.getInt(context.getContentResolver(),
                Settings.System.HAPTIC_FEEDBACK_ENABLED,
                1) == 1)
            {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            }
            
            switch (v.getId())
            {
                case R.id.key_one:
                    playTone(ToneGenerator.TONE_DTMF_1);
                    keyCode = KeyEvent.KEYCODE_1;
                    keyStr = "1";
                    break;
                case R.id.key_two:
                    playTone(ToneGenerator.TONE_DTMF_2);
                    keyCode = KeyEvent.KEYCODE_2;
                    keyStr = "2";
                    break;
                case R.id.key_three:
                    playTone(ToneGenerator.TONE_DTMF_3);
                    keyCode = KeyEvent.KEYCODE_3;
                    keyStr = "3";
                    break;
                case R.id.key_four:
                    playTone(ToneGenerator.TONE_DTMF_4);
                    keyCode = KeyEvent.KEYCODE_4;
                    keyStr = "4";
                    break;
                case R.id.key_five:
                    playTone(ToneGenerator.TONE_DTMF_5);
                    keyCode = KeyEvent.KEYCODE_5;
                    keyStr = "5";
                    break;
                case R.id.key_six:
                    playTone(ToneGenerator.TONE_DTMF_6);
                    keyCode = KeyEvent.KEYCODE_6;
                    keyStr = "6";
                    break;
                case R.id.key_seven:
                    playTone(ToneGenerator.TONE_DTMF_7);
                    keyCode = KeyEvent.KEYCODE_7;
                    keyStr = "7";
                    break;
                case R.id.key_eight:
                    playTone(ToneGenerator.TONE_DTMF_8);
                    keyCode = KeyEvent.KEYCODE_8;
                    keyStr = "8";
                    break;
                case R.id.key_nine:
                    playTone(ToneGenerator.TONE_DTMF_9);
                    keyCode = KeyEvent.KEYCODE_9;
                    keyStr = "9";
                    break;
                case R.id.key_asterisk:
                    playTone(ToneGenerator.TONE_DTMF_S);
                    // *键
                    keyCode = KeyEvent.KEYCODE_STAR;
                    keyStr = "*";
                    break;
                case R.id.key_zero:
                    playTone(ToneGenerator.TONE_DTMF_0);
                    keyCode = KeyEvent.KEYCODE_0;
                    keyStr = "0";
                    break;
                case R.id.key_dot:
                    playTone(ToneGenerator.TONE_DTMF_P);
                    // #键
                    keyCode = KeyEvent.KEYCODE_POUND;
                    keyStr = "#";
                    break;
                default:
                    break;
            }
            
            if (keyCode > -1)
            {
                keyDown(keyCode, keyStr);
            }
        }
        
    }
    /***/
    public void keyDown(int keyCode,String keyStr)
    {
         if(mKeyPressEvent!=null)
         {
             mKeyPressEvent.onKeyPressed(keyCode, keyStr);
         }
    }
    
    /**
     * 按键点击接口
     * @author z00166692
     */
    public interface DigitKeyPressEvent
    {
        /**
         * 数字键被按下事件
         * @param keyCode 按键号码
         * @param keyStr 按键号码
         */
        void onKeyPressed(int keyCode, String keyStr);
    }
    
}
