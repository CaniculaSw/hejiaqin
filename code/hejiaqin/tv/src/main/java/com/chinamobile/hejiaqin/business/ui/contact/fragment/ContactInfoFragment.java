package com.chinamobile.hejiaqin.business.ui.contact.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;
import com.chinamobile.hejiaqin.business.ui.contact.adapter.AppContactAdapter;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactInfoFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "ContactInfoFragment";

    private LayoutInflater inflater;

    private RelativeLayout contactInfoLayout;
    private TextView mContactNameText;
    private CircleImageView mContactHeadImg;
    private View contactMoreView;
    private View strangerMoreView;


    private ContactsInfo mContactsInfo;
    private boolean isStranger = false;

    private IContactsLogic contactsLogic;

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

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_contact_info;
    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) super.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    protected void initView(View view) {
        contactInfoLayout = (RelativeLayout) view.findViewById(R.id.contact_info_layout);

        // 联系人姓名
        mContactNameText = (TextView) view.findViewById(R.id.contact_name_text);
        // 联系人头像
        mContactHeadImg = (CircleImageView) view.findViewById(R.id.contact_head_img);


        view.findViewById(R.id.dial_more_btn).setOnClickListener(this);
        view.findViewById(R.id.dial_clear_btn).setOnClickListener(this);

        contactMoreView = view.findViewById(R.id.contact_more_layout);
        view.findViewById(R.id.edit_contact_btn).setOnClickListener(this);
        view.findViewById(R.id.del_contact_btn).setOnClickListener(this);
        view.findViewById(R.id.contact_cancel_btn).setOnClickListener(this);

        strangerMoreView = view.findViewById(R.id.stranger_more_layout);
        view.findViewById(R.id.add_contact_btn).setOnClickListener(this);
        view.findViewById(R.id.stranger_cancel_btn).setOnClickListener(this);
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

        mContactNameText.setText(mContactsInfo.getName());
        if (!StringUtil.isNullOrEmpty(mContactsInfo.getPhotoSm())) {
            Picasso.with(getContext())
                    .load(mContactsInfo.getPhotoSm())
                    .placeholder(R.drawable.contact_photo_default)
                    .error(R.drawable.contact_photo_default).into(mContactHeadImg);
        }
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


    public void setContactsInfo(ContactsInfo contactsInfo) {
        mContactsInfo = contactsInfo;
    }

    public void refreshView() {
        if (null == mContactsInfo) {
            return;
        }

        List<NumberInfo> numberInfoList = mContactsInfo.getNumberLst();
        if (null == numberInfoList) {
            return;
        }

        contactInfoLayout.removeAllViews();
        for (NumberInfo numberInfo : numberInfoList) {
            View contactNumberView = inflater.inflate(R.layout.layout_contact_number_info_view, null);
            TextView numberDesc = (TextView) contactNumberView.findViewById(R.id.number_desc_text);
            numberDesc.setText(numberInfo.getDesc());
            TextView number = (TextView) contactNumberView.findViewById(R.id.number_text);
            number.setText(numberInfo.getNumber());
            contactInfoLayout.addView(contactNumberView);
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

}
