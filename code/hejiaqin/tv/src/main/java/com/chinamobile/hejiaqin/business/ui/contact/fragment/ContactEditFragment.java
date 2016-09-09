package com.chinamobile.hejiaqin.business.ui.contact.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.FocusManager;
import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.PhotoManage;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactEditFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "ContactEditFragment";
    private View headView;
    private CircleImageView headImg;

    private View nameView;
    private TextView nameText;
    String newName;

    private View numberView;
    private TextView numberText;
    String newNumber;


    /**
     * 当前支持两种模式:新增联系人和修改联系人;
     * 默认为新增联系人
     */
    private boolean addContactMode = true;

    private IContactsLogic contactsLogic;

    private String newPhotoName = null;

    /**
     * 待编辑的联系人信息
     */
    private ContactsInfo editContactsInfo;

    public static ContactEditFragment newInstance() {
        ContactEditFragment fragment = new ContactEditFragment();
        return fragment;
    }

    public static ContactEditFragment newInstance(ContactsInfo contactsInfo) {
        ContactEditFragment fragment = new ContactEditFragment();
        Bundle args = new Bundle();
        args.putSerializable(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY, contactsInfo);
        fragment.setArguments(args);
        return fragment;
    }

    public static ContactEditFragment newInstance(String contactNumber) {
        ContactEditFragment fragment = new ContactEditFragment();
        Bundle args = new Bundle();
        args.putString(BussinessConstants.Contact.INTENT_CONTACT_NUMBER_KEY, contactNumber);
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
        // 头像
        headView = view.findViewById(R.id.contact_head_layout);
        headImg = (CircleImageView) view.findViewById(R.id.contact_head_img);

        // 姓名
        nameView = view.findViewById(R.id.contact_name_layout);
        nameText = (TextView) view.findViewById(R.id.contact_name_hint);

        // 号码
        numberView = view.findViewById(R.id.contact_number_layout);
        numberText = (TextView) view.findViewById(R.id.contact_number_hint);

        FocusManager.getInstance().requestFocus(headView);
    }

    @Override
    protected void initData() {

        String inputNumber = null;
        Bundle argBundle = getArguments();
        if (null != argBundle) {
            editContactsInfo = (ContactsInfo) argBundle.getSerializable(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY);
            //拨号传入的号码保存至和家亲
            inputNumber = argBundle.getString(BussinessConstants.Contact.INTENT_CONTACT_NUMBER_KEY);
        }

        PhotoManage.getInstance(getContext()).setPhotoListener(mPhotoChangeListener);

        addContactMode = (null == editContactsInfo);
        if (!addContactMode) {
            nameText.setText(editContactsInfo.getName());
            numberText.setText(editContactsInfo.getPhone());
            Picasso.with(getContext())
                    .load(editContactsInfo.getPhotoSm())
                    .placeholder(R.drawable.contact_photo_default)
                    .error(R.drawable.contact_photo_default).into(headImg);
        } else if (inputNumber != null) {
            numberText.setText(inputNumber);
            newNumber = inputNumber;
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

    /**
     * 监听拍摄后得到照片信息
     */
    private PhotoManage.PhotoChangeListener mPhotoChangeListener = new PhotoManage.PhotoChangeListener() {

        @Override
        public void end(String url, Bitmap bitmap) {
            if (bitmap != null) {
                headImg.setImageBitmap(bitmap);
                newPhotoName = url;
            }
        }
    };
}
