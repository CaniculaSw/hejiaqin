package com.chinamobile.hejiaqin.business.ui.basic.view.keypad;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.chinamobile.hejiaqin.tv.R;

/**
 * 数字键盘界面
 * @author z00166692
 */
public class DialDigitKeypadView extends BaseDigitKeypadView {
    /**
     * DialDigitKeypadView
     * @param context context
     * @param attrs attrs
     */
    public DialDigitKeypadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initView(Context context) {
        LinearLayout layoutShowDial = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.linearlayout_dial_digit_keypad, null);

        addView(layoutShowDial, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        // 实例化拨号盘中的按键
        btnOne = (LinearLayout) findViewById(R.id.key_one);
        btnTwo = (LinearLayout) findViewById(R.id.key_two);
        btnThree = (LinearLayout) findViewById(R.id.key_three);
        btnFour = (LinearLayout) findViewById(R.id.key_four);
        btnFive = (LinearLayout) findViewById(R.id.key_five);
        btnSix = (LinearLayout) findViewById(R.id.key_six);
        btnSeven = (LinearLayout) findViewById(R.id.key_seven);
        btnEight = (LinearLayout) findViewById(R.id.key_eight);
        btnNine = (LinearLayout) findViewById(R.id.key_nine);
        btnAsterisk = (LinearLayout) findViewById(R.id.key_asterisk);
        btnZero = (LinearLayout) findViewById(R.id.key_zero);
        btnDot = (LinearLayout) findViewById(R.id.key_dot);
    }
}
