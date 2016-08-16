package com.chinamobile.hejiaqin.business.ui.contact.fragment;

import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactEditFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "ContactInfoFragment";

    private LayoutInflater inflater;

    private RelativeLayout contactInfoLayout;
    private HeaderView titleLayout;
    private TextView mContactNameText;
    private CircleImageView mContactHeadImg;


    private ContactsInfo mContactsInfo;
    private boolean isStranger;

    public static ContactEditFragment newInstance(ContactsInfo contactsInfo) {
        ContactEditFragment fragment = new ContactEditFragment();
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
        return R.layout.fragment_contact_edit;
    }

    @Override
    protected void initView(View view) {
        contactInfoLayout = (RelativeLayout) view.findViewById(R.id.contact_info_layout);

        // title
        titleLayout = (HeaderView) view.findViewById(R.id.title);
        titleLayout.title.setText(R.string.contact_info_title_text);
        titleLayout.backImageView.setImageResource(R.mipmap.title_icon_back_nor);

        // 联系人姓名
        mContactNameText = (TextView) view.findViewById(R.id.contact_name_text);
        // 联系人头像
        mContactHeadImg = (CircleImageView) view.findViewById(R.id.contact_head_img);
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
        }
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

}
