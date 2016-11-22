package com.chinamobile.hejiaqin.business.ui.contact.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.model.dial.DialInfo;
import com.chinamobile.hejiaqin.business.model.dial.DialInfoGroup;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.FocusManager;
import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.DialNumberDialog;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.VideoOutDialog;
import com.chinamobile.hejiaqin.business.ui.dial.VideoCallActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.component.log.Logger;
import com.customer.framework.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactInfoFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "ContactInfoFragment";

    private LayoutInflater inflater;

    private TextView mContactNameText;
    private TextView mContactNumberText;
    private CircleImageView mContactHeadImg;
    private View contactMoreView;
    private View strangerMoreView;
    private View dialCallBtn;

    private LinearLayout mDialInfoLayout;


    private ContactsInfo mContactsInfo;
    private List<DialInfoGroup> mDialInfoGroupList = new ArrayList<>();
    private boolean isStranger = false;

    private IContactsLogic contactsLogic;
    private IVoipLogic voipLogic;

    public static ContactInfoFragment newInstance(String contactNumber) {
        ContactInfoFragment fragment = new ContactInfoFragment();
        Bundle args = new Bundle();
        args.putString(BussinessConstants.Contact.INTENT_CONTACT_NUMBER_KEY, contactNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public static ContactInfoFragment newInstance(ContactsInfo contactsInfo) {
        ContactInfoFragment fragment = new ContactInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY, contactsInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_SUCCESS_MSG_ID:
                showToast(R.string.contact_info_add_contact_success_toast);
                break;
            case BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_FAILED_MSG_ID:
                showToast(R.string.contact_info_add_contact_failed_toast);
                break;
            case BussinessConstants.ContactMsgID.DEL_APP_CONTACTS_SUCCESS_MSG_ID:
                showToast(R.string.contact_info_del_contact_success_toast);
                FragmentMgr.getInstance().finishContactFragment(this);
                break;
            case BussinessConstants.ContactMsgID.DEL_APP_CONTACTS_FAILED_MSG_ID:
                showToast(R.string.contact_info_del_contact_failed_toast);
                break;
            case BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_SUCCESS_MSG_ID:
                showToast(R.string.contact_info_edit_contact_success_toast);
                ContactsInfo newContactsInfo = (ContactsInfo) msg.obj;
                if (null == newContactsInfo) {
                    return;
                }

                this.mContactsInfo = newContactsInfo;
                showViews();
                break;
            case BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_FAILED_MSG_ID:
                showToast(R.string.contact_info_edit_contact_failed_toast);
                break;
            case BussinessConstants.ContactMsgID.DEL_CALL_RECORDS_SUCCESS_MSG_ID:
                showDialData(null);
                break;
            case BussinessConstants.ContactMsgID.GET_CALL_RECORDS_SUCCESS_MSG_ID:
                List<DialInfoGroup> dialInfoGroupList = (List<DialInfoGroup>) msg.obj;
                showDialData(dialInfoGroupList);
                break;
            case BussinessConstants.DialMsgID.CALL_RECORD_REFRESH_MSG_ID:
                if (null != contactsLogic) {
                    contactsLogic.queryContactCallRecords(mContactsInfo);
                }
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_contact_info;
    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) super.getLogicByInterfaceClass(IContactsLogic.class);
        voipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    @Override
    protected void initView(View view) {
        inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        // 联系人姓名
        mContactNameText = (TextView) view.findViewById(R.id.contact_name_text);
        mContactNumberText = (TextView) view.findViewById(R.id.contact_number_text);
        // 联系人头像
        mContactHeadImg = (CircleImageView) view.findViewById(R.id.contact_head_img);


        dialCallBtn = view.findViewById(R.id.dial_call_btn);
        view.findViewById(R.id.dial_call_layout).setOnClickListener(this);

        view.findViewById(R.id.dial_more_btn).setOnClickListener(this);
        view.findViewById(R.id.dial_clear_btn).setOnClickListener(this);

        contactMoreView = view.findViewById(R.id.contact_more_layout);
        view.findViewById(R.id.edit_contact_btn).setOnClickListener(this);
        view.findViewById(R.id.del_contact_btn).setOnClickListener(this);
        view.findViewById(R.id.contact_cancel_btn).setOnClickListener(this);

        strangerMoreView = view.findViewById(R.id.stranger_more_layout);
        view.findViewById(R.id.add_contact_btn).setOnClickListener(this);
        view.findViewById(R.id.stranger_cancel_btn).setOnClickListener(this);

        mDialInfoLayout = (LinearLayout) view.findViewById(R.id.dial_info_layout);
    }

    @Override
    public void onResume() {
        super.onResume();
        FocusManager.getInstance().requestFocus(dialCallBtn);
    }

    @Override
    protected void initData() {
        Bundle argBundle = getArguments();
        if (null == argBundle) {
            return;
        }
        mContactsInfo = (ContactsInfo) argBundle.getSerializable(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY);

        if (mContactsInfo == null) {
            //通话记录传入的号码
            String callRecordNumber = argBundle.getString(BussinessConstants.Contact.INTENT_CONTACT_NUMBER_KEY);
            isStranger = true;
            mContactsInfo = new ContactsInfo();
            NumberInfo numberInfo = new NumberInfo();
            numberInfo.setType(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            numberInfo.setNumber(callRecordNumber);
            mContactsInfo.setContactId("");
            mContactsInfo.setName("");
            mContactsInfo.setPhotoLg("");
            mContactsInfo.setPhotoSm("");
            mContactsInfo.addNumber(numberInfo);
            isStranger = true;
        }

        showViews();
    }

    private void showViews() {
        mContactNameText.setText(mContactsInfo.getName());
        List<NumberInfo> numberInfoList = mContactsInfo.getNumberLst();
        if (null != numberInfoList && !numberInfoList.isEmpty()) {
            NumberInfo numberInfo = numberInfoList.get(0);
            mContactNumberText.setText(numberInfo.getNumber());
        }

        if (!StringUtil.isNullOrEmpty(mContactsInfo.getPhotoSm())) {
            Picasso.with(getContext())
                    .load(mContactsInfo.getPhotoSm())
                    .placeholder(R.drawable.contact_photo_default)
                    .error(R.drawable.contact_photo_default).into(mContactHeadImg);
        }

        contactsLogic.queryContactCallRecords(mContactsInfo);
//        setDialInfo(genDialInfoGroup());
        refreshView();
    }

    private void showDialData(List<DialInfoGroup> callRecordList) {
        if (null == callRecordList) {
            callRecordList = new ArrayList<DialInfoGroup>();
        }
        setDialInfo(callRecordList);
        refreshView();

    }

    public void setDialInfo(List<DialInfoGroup> dialInfoGroupList) {
        if (null == dialInfoGroupList) {
            return;
        }
        mDialInfoGroupList.clear();
        mDialInfoGroupList.addAll(dialInfoGroupList);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_btn:
                break;
            case R.id.back_iv:
                FragmentMgr.getInstance().finishContactFragment(this);
                break;
            case R.id.dial_more_btn:
                if (isStranger) {
                    showStrangerMoreView();
                } else {
                    showContactMoreView();
                }
                break;
            case R.id.dial_clear_btn:
                doDelCallRecords();
                break;
            case R.id.edit_contact_btn:
                doEditContact();
                break;
            case R.id.del_contact_btn:
                doDelContact();
                break;
            case R.id.contact_cancel_btn:
                dismissMoreView();
                break;
            case R.id.add_contact_btn:
                doAddContact();
                break;
            case R.id.stranger_cancel_btn:
                dismissMoreView();
                break;
            case R.id.dial_call_layout:
                startVideoCall();
                break;
        }
    }

    private void doDelCallRecords() {
        // 拨号逻辑接口删除通话记录
        contactsLogic.deleteContactCallRecords(mContactsInfo);
        dismissMoreView();
    }

    private void doEditContact() {
        ContactEditFragment fragment = ContactEditFragment.newInstance(mContactsInfo);
        FragmentMgr.getInstance().showContactFragment(fragment);
        dismissMoreView();
    }

    private void doDelContact() {
        contactsLogic.deleteAppContact(mContactsInfo.getContactId());
        dismissMoreView();
    }

    private void doAddContact() {
        ContactEditFragment fragment = ContactEditFragment.newInstance(mContactsInfo);
        FragmentMgr.getInstance().showContactFragment(fragment);
        dismissMoreView();
    }


    private void startVideoCall() {
        List<NumberInfo> numberInfoList = mContactsInfo.getNumberLst();
        if (numberInfoList.isEmpty()) {
            Logger.w(TAG, "startVideoCall, no number info.");
            return;
        }

        if (numberInfoList.size() > 1) {
            showDialNumberDialog();
            return;
        }


        NumberInfo numberInfo = numberInfoList.get(0);
        VideoOutDialog.show(getContext(), numberInfo.getNumber(), voipLogic, contactsLogic);

    }


    private void showDialNumberDialog() {
        final DialNumberDialog dialNumberDialog = new DialNumberDialog(getContext(), R.style.CalendarDialog
                , mContactsInfo, voipLogic, contactsLogic);
        Window window = dialNumberDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        dialNumberDialog.setCancelable(true);
        dialNumberDialog.show();


        dialNumberDialog.cancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumberDialog.dismiss();
            }
        });
    }

    public void setContactsInfo(ContactsInfo contactsInfo) {
        mContactsInfo = contactsInfo;
    }

    public void refreshView() {
        if (null == mDialInfoGroupList) {
            return;
        }

        mDialInfoLayout.removeAllViews();
        for (DialInfoGroup dialInfoGroup : mDialInfoGroupList) {
            if (null == dialInfoGroup.getGroupName() || null == dialInfoGroup.getDialInfoList()
                    || dialInfoGroup.getDialInfoList().isEmpty()) {
                continue;
            }

            for (DialInfo dialInfo : dialInfoGroup.getDialInfoList()) {
                View dialInfoView = inflater.inflate(R.layout.layout_contact_dial_info_view, null);

                ImageView dialTypeImage = (ImageView) dialInfoView.findViewById(R.id.dial_type_icon);
                dialTypeImage.setImageResource(getIconResIdByDialType(dialInfo.getType()));

                TextView dialTypeText = (TextView) dialInfoView.findViewById(R.id.dial_type_text);
                dialTypeText.setText(getStringResIdByDialType(dialInfo.getType()));

                TextView dialTimeText = (TextView) dialInfoView.findViewById(R.id.dial_time_text);
                dialTimeText.setText(dialInfo.getDialTime());

                TextView dialDurationText = (TextView) dialInfoView.findViewById(R.id.dial_duration_text);
                dialDurationText.setText(dialInfo.getDialDuration());
                mDialInfoLayout.addView(dialInfoView);
            }
        }
    }

    private void showContactMoreView() {
        if (contactMoreView.getVisibility() == View.VISIBLE) {
            dismissMoreView();
            return;
        }
        strangerMoreView.setVisibility(View.GONE);
        contactMoreView.setVisibility(View.VISIBLE);
    }

    private void showStrangerMoreView() {
        if (strangerMoreView.getVisibility() == View.VISIBLE) {
            dismissMoreView();
            return;
        }
        contactMoreView.setVisibility(View.GONE);
        strangerMoreView.setVisibility(View.VISIBLE);
    }

    private void dismissMoreView() {
        strangerMoreView.setVisibility(View.GONE);
        contactMoreView.setVisibility(View.GONE);
    }

    private int getIconResIdByDialType(DialInfo.Type type) {
        if (null == type) {
            return R.drawable.icon_incoming;
        }
        switch (type) {
            case in:
                return R.drawable.icon_incoming;
            case out:
                return R.drawable.icon_outbound_call;
            case missed:
                return R.drawable.icon_missed_call;
            case reject:
                return R.drawable.icon_reject_call;
            default:
                return R.drawable.icon_incoming;
        }
    }

    private int getStringResIdByDialType(DialInfo.Type type) {
        if (null == type) {
            return R.string.contact_info_dial_incoming_text;
        }
        switch (type) {
            case in:
                return R.string.contact_info_dial_incoming_text;
            case out:
                return R.string.contact_info_dial_outbound_text;
            case missed:
                return R.string.contact_info_dial_missed_text;
            case reject:
                return R.string.contact_info_dial_reject_text;
            default:
                return R.string.contact_info_dial_incoming_text;
        }
    }

    private List<DialInfoGroup> genDialInfoGroup() {
        List<DialInfoGroup> dialInfoGroupList = new ArrayList<>();


        DialInfoGroup group1 = new DialInfoGroup();
        group1.setGroupName("今天");
        group1.setDialInfoList(genDialInfoList());
        dialInfoGroupList.add(group1);

        DialInfoGroup group2 = new DialInfoGroup();
        group2.setGroupName("周一");
        group2.setDialInfoList(genDialInfoList());
        dialInfoGroupList.add(group2);


        DialInfoGroup group3 = new DialInfoGroup();
        group3.setGroupName("周日");
        group3.setDialInfoList(genDialInfoList());
        dialInfoGroupList.add(group3);
        return dialInfoGroupList;
    }

    private List<DialInfo> genDialInfoList() {
        List<DialInfo> dialInfoList = new ArrayList<>();
        DialInfo dialInfo1 = new DialInfo();
        dialInfo1.setType(DialInfo.Type.in);
        dialInfo1.setDialTime("10:51");
        dialInfo1.setDialDuration("1分10秒");
        dialInfoList.add(dialInfo1);

        DialInfo dialInfo2 = new DialInfo();
        dialInfo2.setType(DialInfo.Type.in);
        dialInfo2.setDialTime("周一");
        dialInfo2.setDialDuration("1分10秒");
        dialInfoList.add(dialInfo2);

        DialInfo dialInfo3 = new DialInfo();
        dialInfo3.setType(DialInfo.Type.out);
        dialInfo3.setDialTime("周一");
        dialInfo3.setDialDuration("1分10秒");
        dialInfoList.add(dialInfo3);

        DialInfo dialInfo4 = new DialInfo();
        dialInfo4.setType(DialInfo.Type.missed);
        dialInfo4.setDialTime("10月1日");
        dialInfo4.setDialDuration("1分10秒");
        dialInfoList.add(dialInfo4);

        DialInfo dialInfo5 = new DialInfo();
        dialInfo5.setType(DialInfo.Type.reject);
        dialInfo5.setDialTime("5月10日");
        dialInfo5.setDialDuration("1分10秒");
        dialInfoList.add(dialInfo5);

        DialInfo dialInfo6 = new DialInfo();
        dialInfo6.setType(DialInfo.Type.in);
        dialInfo6.setDialTime("5月9日");
        dialInfo6.setDialDuration("1分10秒");
        dialInfoList.add(dialInfo6);

        DialInfo dialInfo7 = new DialInfo();
        dialInfo7.setType(DialInfo.Type.out);
        dialInfo7.setDialTime("2月3日");
        dialInfo7.setDialDuration("1分10秒");
        dialInfoList.add(dialInfo7);
        return dialInfoList;
    }
}
