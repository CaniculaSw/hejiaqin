package com.chinamobile.hejiaqin.business.ui.main;


import android.content.Intent;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.basic.view.keypad.BaseDigitKeypadView;
import com.chinamobile.hejiaqin.business.ui.basic.view.keypad.DialDigitKeypadView;
import com.chinamobile.hejiaqin.business.ui.basic.view.keypad.DigitsEditText;
import com.chinamobile.hejiaqin.business.ui.contact.ModifyContactActivity;
import com.chinamobile.hejiaqin.tv.R;

/**
 * Created by eshaohu on 16/5/22.
 */
public class DialFragment extends BasicFragment implements View.OnClickListener{

    private static final String TAG = "DialFragment";

    /**
     * 号码输入框的字符串小于3位则不进行搜索
     */
    private static final int SEARCH_START_LENGTH = 1;

    /**
     * 两次输入间隔时间, 如果小于这个时间, 则不响应搜索
     */
    private static final int SEARCH_WORD_CHANGE_TIME = 200;

    HeaderView headerView;


    RelativeLayout dialSaveContactLayout;
    LinearLayout dialSaveContactArrowLayout;

    LinearLayout inputNumberAboveLine;
    LinearLayout inputNumberLayout;
    DigitsEditText inputNumber;
    LinearLayout dialNumberDelLayout;
    ImageView dialNumberDelIv;
    LinearLayout inputNumberBelowLine;
    DialDigitKeypadView digitKeypad;

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_dail;
    }

    @Override
    protected void initView(View view) {
        headerView = (HeaderView) view.findViewById(R.id.header_view_id);
        headerView.logoIv.setImageResource(R.mipmap.logo_small);
        headerView.title.setText(R.string.dial_title);
        headerView.rightBtn.setImageResource(R.drawable.selector_title_icon_delete);

        dialSaveContactLayout = (RelativeLayout)view.findViewById(R.id.dial_save_contact_layout);

        dialSaveContactArrowLayout = (LinearLayout)view.findViewById(R.id.dial_save_contact_arrow_layout);;
        inputNumberAboveLine = (LinearLayout)view.findViewById(R.id.input_number_above_line);
        inputNumberLayout = (LinearLayout)view.findViewById(R.id.input_number_layout);
        inputNumber = (DigitsEditText)view.findViewById(R.id.input_number);
        dialNumberDelLayout = (LinearLayout)view.findViewById(R.id.dial_number_del_layout);
        dialNumberDelIv = (ImageView)view.findViewById(R.id.dial_number_del_iv);
        inputNumberBelowLine = (LinearLayout)view.findViewById(R.id.input_number_below_line);
        digitKeypad = (DialDigitKeypadView)view.findViewById(R.id.digit_keypad);
        dialSaveContactArrowLayout.setOnClickListener(this);
        digitKeypad.setDigitKeyPressEvent(new BaseDigitKeypadView.DigitKeyPressEvent() {

            @Override
            public void onKeyPressed(int keyCode, String keyStr) {
                KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
                inputNumber.onKeyDown(keyCode, event);
            }
        });
        inputNumber.setCursorVisible(false);
        inputNumber.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (inputNumber.length() > 0) {
                    inputNumber.setCursorVisible(true);
                }
                return false;
            }
        });
        inputNumber.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (inputNumber.length() > 0) {
                    inputNumber.setCursorVisible(true);
                }
                return false;
            }
        });
        inputNumber.setOnClickListener(this);
        dialNumberDelIv.setClickable(false);
        dialNumberDelLayout.setOnClickListener(this);
        dialNumberDelLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 长按删除键, 清空输入内容
                inputNumber.setText("");
                inputNumber.setCursorVisible(false);
                return true;
            }
        });
        headerView.rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputNumber.length() > 0) {
                    Intent intent = new Intent(getActivity(), ModifyContactActivity.class);
                    intent.putExtra(BussinessConstants.Contact.INTENT_CONTACT_NUMBER_KEY,inputNumber.getText().toString() );
                    getActivity().startActivity(intent);
                }
            }
        });
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
       switch (v.getId())
       {
           case R.id.input_number:
               if (inputNumber.length() > 0)
               {
                   inputNumber.setCursorVisible(true);
               }
               break;
           case R.id.dial_number_del_layout:
               if (inputNumber.length() > 0)
               {
                   inputNumber.setCursorVisible(true);
               }
               inputNumber.onKeyDown(KeyEvent.KEYCODE_DEL, new KeyEvent(
                       KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));

               //如果是输入框中无数字，将光标隐藏
               final int length = inputNumber.length();
               if (length == inputNumber.getSelectionStart()
                       && length == inputNumber.getSelectionEnd())
               {
                   inputNumber.setCursorVisible(false);
               }
               break;
       }
    }

}
