package com.chinamobile.hejiaqin.business.ui.basic.view.keypad;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chinamobile.hejiaqin.R;


/**
 * 数字键盘界面
 * @author z00166692
 */
public class DialDigitKeypadView extends BaseDigitKeypadView
{
    /**
     * DialDigitKeypadView
     * @param context context
     * @param attrs attrs
     */
    public DialDigitKeypadView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void initView(Context context)
    {
        LinearLayout layoutShowDial =
            (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.linearlayout_dial_digit_keypad, null);
        
        addView(layoutShowDial, new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT));
        // 实例化拨号盘中的按键
        btnOne = (Button) findViewById(R.id.key_one);
        btnTwo = (Button) findViewById(R.id.key_two);
        btnThree = (Button) findViewById(R.id.key_three);
        btnFour = (Button) findViewById(R.id.key_four);
        btnFive = (Button) findViewById(R.id.key_five);
        btnSix = (Button) findViewById(R.id.key_six);
        btnSeven = (Button) findViewById(R.id.key_seven);
        btnEight = (Button) findViewById(R.id.key_eight);
        btnNine = (Button) findViewById(R.id.key_nine);
        btnAsterisk = (Button) findViewById(R.id.key_asterisk);
        btnZero = (Button) findViewById(R.id.key_zero);
        btnDot = (Button) findViewById(R.id.key_dot);
    }
}
