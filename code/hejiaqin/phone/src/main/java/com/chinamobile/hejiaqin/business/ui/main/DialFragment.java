package com.chinamobile.hejiaqin.business.ui.main;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.SearchResultContacts;
import com.chinamobile.hejiaqin.business.model.dial.CallRecord;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.basic.view.keypad.BaseDigitKeypadView;
import com.chinamobile.hejiaqin.business.ui.basic.view.keypad.DialDigitKeypadView;
import com.chinamobile.hejiaqin.business.ui.basic.view.keypad.DigitsEditText;
import com.chinamobile.hejiaqin.business.ui.contact.ModifyContactActivity;
import com.chinamobile.hejiaqin.business.ui.dial.VideoCallActivity;
import com.chinamobile.hejiaqin.business.ui.main.adapter.CallRecordAdapter;
import com.chinamobile.hejiaqin.business.ui.main.adapter.DialContactAdapter;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
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
    RecyclerView callRecordSearchRecyclerView;

    RelativeLayout dialSaveContactLayout;
    LinearLayout dialSaveContactArrowLayout;
    LinearLayout blankLayout;

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

    private IContactsLogic mContactsLogic;

    private CallRecordAdapter mCallRecordAdapter;

    private DialContactAdapter mDialContactAdapter;

    private CallRecordAdapter mCallRecordSearchAdapter;

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
                // CALL
                if(inputNumber.length()>0) {
                    Intent outingIntent = new Intent(getContext(), VideoCallActivity.class);
                    outingIntent.putExtra(BussinessConstants.Dial.INTENT_CALLEE_NUMBER, inputNumber.getText().toString());
                    getContext().startActivity(outingIntent);
                }
                break;
        }
    }

    @Override
    protected void handleLogicMsg(Message msg) {
        Object obj = msg.obj;
        switch (msg.what) {
            case BussinessConstants.DialMsgID.CALL_RECORD_START_SERTCH_CONTACT_MSG_ID:
                //开始查询
                mVoipLogic.search(mSearchString);
                break;
            case BussinessConstants.ContactMsgID.DEL_CALL_RECORDS_SUCCESS_MSG_ID:
            case BussinessConstants.ContactMsgID.GET_LOCAL_CONTACTS_SUCCESS_MSG_ID:
            case BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID:
            case BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_SUCCESS_MSG_ID:
            case BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_SUCCESS_MSG_ID:
            case BussinessConstants.ContactMsgID.DEL_APP_CONTACTS_SUCCESS_MSG_ID:
            case BussinessConstants.DialMsgID.CALL_RECORD_REFRESH_MSG_ID:
                refreshCallRecord();
                break;
            case BussinessConstants.DialMsgID.CALL_RECORD_GET_ALL_MSG_ID:
                if(obj!=null)
                {
                    List<CallRecord> callRecords = (List<CallRecord>)obj;
                    mCallRecordAdapter.refreshData(callRecords);
                }
                break;
            case BussinessConstants.DialMsgID.CALL_RECORD_DEL_ALL_MSG_ID:
                mCallRecordAdapter.refreshData(null);
                break;
            case BussinessConstants.DialMsgID.SEARCH_CONTACTS_SUCCESS_MSG_ID:
                SearchResultContacts resultContacts = (SearchResultContacts) msg.obj;
                mCallRecordSearchAdapter.refreshData(new ArrayList<CallRecord>());
                mDialContactAdapter.refreshData(resultContacts.getContactsInfos());
                if (resultContacts.getContactsInfos() != null && resultContacts.getContactsInfos().size() > 0) {
                    dialSaveContactLayout.setVisibility(View.GONE);
                    callRecordSearchRecyclerView.setVisibility(View.GONE);
                    dialContactRecyclerView.setVisibility(View.VISIBLE);
                }
                break;
            case BussinessConstants.DialMsgID.CALL_RECORD_SEARCH_MSG_ID:
                if(obj!=null)
                {
                    List<CallRecord> callRecords = (List<CallRecord>)obj;
                    mDialContactAdapter.refreshData(new ArrayList<ContactsInfo>());
                    mCallRecordSearchAdapter.refreshData(callRecords);
                    if(callRecords!=null && callRecords.size() > 0)
                    {
                        dialSaveContactLayout.setVisibility(View.GONE);
                        dialContactRecyclerView.setVisibility(View.GONE);
                        callRecordSearchRecyclerView.setVisibility(View.VISIBLE);
                    }else{
                        dialContactRecyclerView.setVisibility(View.GONE);
                        callRecordSearchRecyclerView.setVisibility(View.GONE);
                        dialSaveContactLayout.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
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

        callRecordRecyclerView = (RecyclerView)view.findViewById(R.id.call_record_recycler_view);
        dialContactRecyclerView = (RecyclerView)view.findViewById(R.id.dial_contact_recycler_view);
        callRecordSearchRecyclerView = (RecyclerView)view.findViewById(R.id.call_record_search_recycler_view);
        dialSaveContactLayout = (RelativeLayout)view.findViewById(R.id.dial_save_contact_layout);

        dialSaveContactArrowLayout = (LinearLayout)view.findViewById(R.id.dial_save_contact_arrow_layout);
        blankLayout = (LinearLayout)view.findViewById(R.id.blank_layout);
        inputNumberAboveLine = (LinearLayout)view.findViewById(R.id.input_number_above_line);
        inputNumberLayout = (LinearLayout)view.findViewById(R.id.input_number_layout);
        inputNumber = (DigitsEditText)view.findViewById(R.id.input_number);
        dialNumberDelLayout = (LinearLayout)view.findViewById(R.id.dial_number_del_layout);
        dialNumberDelIv = (ImageView)view.findViewById(R.id.dial_number_del_iv);
        inputNumberBelowLine = (LinearLayout)view.findViewById(R.id.input_number_below_line);
        digitKeypad = (DialDigitKeypadView)view.findViewById(R.id.digit_keypad);
        dialSaveContactLayout.setOnClickListener(this);
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
                if (!editable.equals(str)) {
                    inputNumber.setText(str);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String txt = s.toString();
                if (!txt.equals(mLastText)) {
                    mLastText = txt;
                    textChanged(txt);
                }
                if (txt.length() > 0) {
                    DialFragment.this.mListener.onAction(BussinessConstants.FragmentActionId.DAIL_FRAGMENT_SHOW_CALL_ACTION_ID, null);
                    callRecordRecyclerView.setVisibility(View.GONE);
                } else {
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
        headerView.rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputNumber.length() > 0) {
                    return;
                }
                mListener.onAction(BussinessConstants.FragmentActionId.DAIL_SHOW_DEL_POP_WINDOW_MSG_ID, null);
            }
        });

        mCallRecordAdapter = new CallRecordAdapter(getContext(),mContactsLogic);
        callRecordRecyclerView.setAdapter(mCallRecordAdapter);
        callRecordRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        callRecordRecyclerView.setHasFixedSize(true);
        callRecordRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                hideKeyPad();
                DialFragment.this.mListener.onAction(BussinessConstants.FragmentActionId.DAIL_FRAGMENT_RECORD_HIDE_KEYBORD_MSG_ID, null);
            }
        });

        mCallRecordSearchAdapter = new CallRecordAdapter(getContext(),mContactsLogic);
        callRecordSearchRecyclerView.setAdapter(mCallRecordSearchAdapter);
        callRecordSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        callRecordSearchRecyclerView.setHasFixedSize(true);
        callRecordSearchRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                hideKeyPad();
                DialFragment.this.mListener.onAction(BussinessConstants.FragmentActionId.DAIL_FRAGMENT_CONTACT_HIDE_KEYBORD_MSG_ID, null);
            }
        });

        mDialContactAdapter = new DialContactAdapter (getContext());
        dialContactRecyclerView.setAdapter(mDialContactAdapter);
        dialContactRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dialContactRecyclerView.setHasFixedSize(true);
        dialContactRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                hideKeyPad();
                DialFragment.this.mListener.onAction(BussinessConstants.FragmentActionId.DAIL_FRAGMENT_CONTACT_HIDE_KEYBORD_MSG_ID, null);
            }
        });

        blankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyPad();
                if (inputNumber.length() > 0) {
                    DialFragment.this.mListener.onAction(BussinessConstants.FragmentActionId.DAIL_FRAGMENT_CONTACT_HIDE_KEYBORD_MSG_ID, null);
                }else{
                    DialFragment.this.mListener.onAction(BussinessConstants.FragmentActionId.DAIL_FRAGMENT_RECORD_HIDE_KEYBORD_MSG_ID, null);
                }
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
        mContactsLogic = (IContactsLogic)super.getLogicByInterfaceClass(IContactsLogic.class);
    }
    @Override
    protected void initData() {
        refreshCallRecord();
    }

    private void refreshCallRecord()
    {
        mVoipLogic.getCallRecord();
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
            callRecordSearchRecyclerView.setVisibility(View.GONE);
        }
        Handler handle = getHandler();
        if (null != handle)
        {
            int what =
                    BussinessConstants.DialMsgID.CALL_RECORD_START_SERTCH_CONTACT_MSG_ID;
            if (handle.hasMessages(what))
            {
                handle.removeMessages(what);
            }
            if(!StringUtil.isNullOrEmpty(mSearchString)) {

                handle.sendEmptyMessageDelayed(what, SEARCH_WORD_CHANGE_TIME);
            }else{
                mDialContactAdapter.refreshData(new ArrayList<ContactsInfo>());
                mCallRecordSearchAdapter.refreshData(new ArrayList<CallRecord>());
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
           case R.id.dial_save_contact_layout:
               // 保存联系人
               if(inputNumber.length()>0) {
                   Intent intent = new Intent(getActivity(), ModifyContactActivity.class);
                   intent.putExtra(BussinessConstants.Contact.INTENT_CONTACT_NUMBER_KEY,inputNumber.getText().toString() );
                   getActivity().startActivity(intent);
               }
               break;
       }
    }

}
