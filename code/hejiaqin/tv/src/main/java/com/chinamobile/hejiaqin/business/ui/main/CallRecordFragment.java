package com.chinamobile.hejiaqin.business.ui.main;


import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.model.dial.CallRecord;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.FocusManager;
import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.DelCallRecordConfirmDialog;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.VideoOutDialog;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.contact.fragment.ContactInfoFragment;
import com.chinamobile.hejiaqin.business.ui.main.adapter.CallRecordAdapter;
import com.chinamobile.hejiaqin.tv.R;

import java.util.List;

/**
 * Created by eshaohu on 16/5/22.
 */
public class CallRecordFragment extends BasicFragment {

    private static final String TAG = "CallRecordFragment";

    private HeaderView headerView;
    private ListView callRecordListView;
    private LinearLayout deleteLayout;

    private IVoipLogic mVoipLogic;

    private IContactsLogic mContactsLogic;

    private CallRecordAdapter mCallRecordAdapter;

    private int selection;

    private View moreView;

    private View recordDetail;

    private View delRecordLayout;

    private View recordCancelLayout;

    private DelCallRecordConfirmDialog confirmDialog;

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
                if (obj != null) {
                    List<CallRecord> callRecords = (List<CallRecord>) obj;
                    mCallRecordAdapter.refreshData(callRecords);
                    if(mCallRecordAdapter!=null && mCallRecordAdapter.getCount()>0)
                    {
                        callRecordListView.setSelection(0);
                    }
                }
                break;
            case BussinessConstants.DialMsgID.CALL_RECORD_DEL_ALL_MSG_ID:
                mCallRecordAdapter.refreshData(null);
                break;
            case BussinessConstants.DialMsgID.CALL_RECORD_DEL_MSG_ID:
                if (msg.obj != null) {
                    mCallRecordAdapter.delData((String[]) msg.obj);
                }
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
        headerView.title.setText(R.string.call_record_title);

        deleteLayout = (LinearLayout) view.findViewById(R.id.delete_all_layout);
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del();
            }
        });
        callRecordListView = (ListView) view.findViewById(R.id.call_record_recycler_view);
        moreView = view.findViewById(R.id.call_record_more_layout);
        recordDetail = view.findViewById(R.id.record_detail);
        delRecordLayout = view.findViewById(R.id.del_record_layout);
        recordCancelLayout = view.findViewById(R.id.record_cancel_layout);

        mCallRecordAdapter = new CallRecordAdapter(getContext(), mContactsLogic, new CallRecordAdapter.onClickListen() {
            public void onClick(CallRecord info) {
                VideoOutDialog.show(getActivity(), info.getPeerNumber(), mVoipLogic, mContactsLogic,true);
            }

            @Override
            public void onLongClick(int position) {
                CallRecordFragment.this.selection = position;
                showMoreView();
                moreView.findViewById(R.id.record_detail).requestFocus();
            }
        });
        recordDetail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteLayout.requestFocus();
                if(mCallRecordAdapter!=null && mCallRecordAdapter.getCount()>0)
                {
                    callRecordListView.setSelection(0);
                }
                dismissMoreView();
                if (mCallRecordAdapter.getData(CallRecordFragment.this.selection).getContactsInfo() != null) {
                    ContactInfoFragment fragment = ContactInfoFragment.newInstance(mCallRecordAdapter.getData(CallRecordFragment.this.selection).getContactsInfo());
                    FragmentMgr.getInstance().showRecentFragment(fragment);
                } else {
                    ContactInfoFragment fragment = ContactInfoFragment.newInstance(mCallRecordAdapter.getData(CallRecordFragment.this.selection).getPeerNumber());
                    FragmentMgr.getInstance().showRecentFragment(fragment);
                }
            }
        });
        delRecordLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteLayout.requestFocus();
                if(mCallRecordAdapter!=null && mCallRecordAdapter.getCount()>0)
                {
                    callRecordListView.setSelection(0);
                }
                dismissMoreView();
                mVoipLogic.delCallRecord(new String[]{mCallRecordAdapter.getData(CallRecordFragment.this.selection).getId()});
            }
        });
        recordCancelLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismissMoreView();
                callRecordListView.setSelection(0);
                callRecordListView.setFocusable(true);
                callRecordListView.requestFocus();
            }
        });

        callRecordListView.setAdapter(mCallRecordAdapter);
        callRecordListView.setItemsCanFocus(true);
        callRecordListView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        callRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoOutDialog.show(getActivity(), mCallRecordAdapter.getData(position).getPeerNumber(), mVoipLogic, mContactsLogic,true);
            }
        });
        callRecordListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CallRecordFragment.this.selection = position;
                showMoreView();
                moreView.findViewById(R.id.record_detail).requestFocus();
                return true;
            }
        });
//        FocusManager.getInstance().addFocusViewInLeftFrag("0", deleteLayout);
    }


    @Override
    protected void initLogics() {
        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
        mContactsLogic = (IContactsLogic) super.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    protected void initData() {
        refreshCallRecord();
    }

    private void refreshCallRecord() {
        mVoipLogic.getCallRecord();
    }

    private void showMoreView() {
        moreView.setVisibility(View.VISIBLE);
    }

    private void dismissMoreView() {
        moreView.setVisibility(View.GONE);
    }

    private void del() {
        if (mCallRecordAdapter == null || mCallRecordAdapter.getCount() == 0) {
            return;
        }
        confirmDialog = new DelCallRecordConfirmDialog(getActivity(), R.style.CalendarDialog);
        Window window = confirmDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        confirmDialog.setCancelable(true);
        confirmDialog.show();

        confirmDialog.mConfirmLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVoipLogic.delAllCallRecord();
                confirmDialog.dismiss();
            }
        });

        confirmDialog.mCancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
            }
        });
    }

    public View getFirstFouseView()
    {
        return deleteLayout;
    }
}
