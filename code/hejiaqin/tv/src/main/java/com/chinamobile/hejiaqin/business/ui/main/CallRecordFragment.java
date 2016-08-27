package com.chinamobile.hejiaqin.business.ui.main;


import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.model.dial.CallRecord;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.main.adapter.CallRecordAdapter;
import com.chinamobile.hejiaqin.tv.R;

import java.util.List;

/**
 * Created by eshaohu on 16/5/22.
 */
public class CallRecordFragment extends BasicFragment{

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
    RecyclerView callRecordRecyclerView;

    private IVoipLogic mVoipLogic;

    private IContactsLogic mContactsLogic;

    private CallRecordAdapter mCallRecordAdapter;

    @Override
    protected void handleFragmentMsg(Message msg) {
    }

    @Override
    protected void handleLogicMsg(Message msg) {
        Object obj = msg.obj;
        switch (msg.what) {
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
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_call_record;
    }

    @Override
    protected void initView(View view) {
        headerView = (HeaderView) view.findViewById(R.id.header_view_id);
        headerView.logoIv.setImageResource(R.mipmap.logo_small);
        headerView.title.setText(R.string.dial_title);
        headerView.rightBtn.setImageResource(R.drawable.selector_title_icon_delete);

        callRecordRecyclerView = (RecyclerView)view.findViewById(R.id.call_record_recycler_view);

        mCallRecordAdapter = new CallRecordAdapter(getContext(),mContactsLogic);
        callRecordRecyclerView.setAdapter(mCallRecordAdapter);
        callRecordRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        callRecordRecyclerView.setHasFixedSize(true);
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


}
