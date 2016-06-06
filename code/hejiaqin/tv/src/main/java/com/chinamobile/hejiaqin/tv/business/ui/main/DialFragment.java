package com.chinamobile.hejiaqin.tv.business.ui.main;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.logic.voip.VoipLogic;
import com.chinamobile.hejiaqin.tv.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.tv.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.tv.business.ui.basic.view.keypad.BaseDigitKeypadView;
import com.chinamobile.hejiaqin.tv.business.ui.basic.view.keypad.DialDigitKeypadView;
import com.chinamobile.hejiaqin.tv.business.ui.basic.view.keypad.DigitsEditText;
import com.customer.framework.utils.StringUtil;
import com.huawei.rcs.login.LoginApi;
import com.huawei.rcs.login.LoginCfg;
import com.huawei.rcs.login.UserInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eshaohu on 16/5/22.
 */
public class DialFragment extends BasicFragment implements View.OnClickListener{


    /**
     * 号码输入框的字符串小于3位则不进行搜索
     */
    private static final int SEARCH_START_LENGTH = 1;

    /**
     * 两次输入间隔时间, 如果小于这个时间, 则不响应搜索
     */
    private static final int SEARCH_WORD_CHANGE_TIME = 200;

    HeaderView headerView;
    RecyclerView callRecordRecyclerView;
    RecyclerView dialContactRecyclerView;

    RelativeLayout dialSaveContactLayout;
    LinearLayout dialSaveContactArrowLayout;

    LinearLayout inputNumberAboveLine;
    LinearLayout inputNumberLayout;
    DigitsEditText inputNumber;
    LinearLayout dialNumberDelLayout;
    ImageView dialNumberDelIv;
    LinearLayout inputNumberBelowLine;
    DialDigitKeypadView digitKeypad;
    /**
     * 输入的最后的字符串
     */
    private String mLastText;

    /**
     *  搜索字符串
     */
    private String mSearchString;

    private IVoipLogic mVoipLogic;


    @Override
    protected void handleFragmentMsg(Message msg) {

        switch (msg.what)
        {
            case BussinessConstants.FragmentActionId.DAIL_FRAGMENT_SHOW_KEYBORD_MSG_ID:
                showKeyPad();
                break;
            case BussinessConstants.FragmentActionId.DAIL_FRAGMENT_HIDE_KEYBORD_MSG_ID:
                hideKeyPad();
                break;
            case BussinessConstants.FragmentActionId.DAIL_FRAGMENT_CALL_MSG_ID:
                //TODO CALL
                break;
        }
    }

    @Override
    protected void handleLogicMsg(Message msg) {
        switch (msg.what) {
            case BussinessConstants.DialMsgID.CALL_RECORD_START_SERTCH_CONTACT:
                //TODO：开始查询
                dialSaveContactLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_dail;
    }

    @Override
    protected void initView(View view) {
        headerView = (HeaderView) view.findViewById(R.id.header_view_id);
        headerView.title.setText(R.string.dial_title);
        headerView.rightBtn.setImageResource(R.drawable.selector_title_icon_delete);

        callRecordRecyclerView = (RecyclerView)view.findViewById(R.id.call_record_recycler_view);
        dialContactRecyclerView = (RecyclerView)view.findViewById(R.id.dial_contact_recycler_view);
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
        inputNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = inputNumber.getText().toString();
                String str = stringFilter(editable);
                if (!editable.equals(str))
                {
                    inputNumber.setText(str);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String txt = s.toString();
                if (!txt.equals(mLastText))
                {
                    mLastText = txt;
                    textChanged(txt);
                }
                if (txt.length() > 0)
                {
                     DialFragment.this.mListener.onAction(BussinessConstants.FragmentActionId.DAIL_FRAGMENT_SHOW_CALL_ACTION_ID,null);
                    callRecordRecyclerView.setVisibility(View.GONE);
                }
                else
                {
                    DialFragment.this.mListener.onAction(BussinessConstants.FragmentActionId.DAIL_FRAGMENT_HIDE_CALL_ACTION_ID, null);
                    dialSaveContactLayout.setVisibility(View.GONE);
                    dialContactRecyclerView.setVisibility(View.GONE);
                    callRecordRecyclerView.setVisibility(View.VISIBLE);
                }
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

    }

    private String stringFilter(String str)
    {
        String regEx = "[\\s]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        return m.replaceAll("");
    }

    private void showKeyPad()
    {
        inputNumberAboveLine.setVisibility(View.VISIBLE);
        inputNumberLayout.setVisibility(View.VISIBLE);
        inputNumberBelowLine.setVisibility(View.VISIBLE);
        digitKeypad.setVisibility(View.VISIBLE);
    }

    private void hideKeyPad()
    {
        inputNumberAboveLine.setVisibility(View.GONE);
        inputNumberLayout.setVisibility(View.GONE);
        inputNumberBelowLine.setVisibility(View.GONE);
        digitKeypad.setVisibility(View.GONE);
    }

    @Override
    protected void initLogics()
    {
        mVoipLogic = (IVoipLogic)super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    @Override
    protected void initData() {
        UserInfo userInfo = new UserInfo();
        userInfo.countryCode="+86";
        userInfo.username = "2886544005";
        userInfo.password = "Vconf2015!";
        mVoipLogic.login(userInfo,null,null);
    }

    /**
     * 号码编辑框内容发生变化
     * @param nowText nowText
     */
    private void textChanged(String nowText)
    {
        mSearchString = nowText;

        // 号码输入小于三位，不进行搜索
        if (StringUtil.isNullOrEmpty(mSearchString))
        {
            mSearchString = "";
            dialSaveContactLayout.setVisibility(View.GONE);
            dialContactRecyclerView.setVisibility(View.GONE);
            callRecordRecyclerView.setVisibility(View.VISIBLE);
        }

        Handler handle = getHandler();
        if (null != handle)
        {
            int what =
                    BussinessConstants.DialMsgID.CALL_RECORD_START_SERTCH_CONTACT;
            if (handle.hasMessages(what))
            {
                handle.removeMessages(what);
            }
            if(!StringUtil.isNullOrEmpty(mSearchString)) {

                handle.sendEmptyMessageDelayed(what, SEARCH_WORD_CHANGE_TIME);
            }
        }
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
           case R.id.dial_save_contact_arrow_layout:
               //TODO 保存联系人

       }
    }
}
