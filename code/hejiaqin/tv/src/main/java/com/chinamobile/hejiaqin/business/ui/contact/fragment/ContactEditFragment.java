package com.chinamobile.hejiaqin.business.ui.contact.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.SearchResultContacts;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.FocusManager;
import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.PhotoManage;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.StringUtil;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactEditFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "ContactEditFragment";
    private View headView;
    private CircleImageView headImg;

    private View nameView;
    private EditText nameText;
    String newName;
    String oldName;

    private View numberView;
    private EditText numberText;
    String newNumber;
    String oldNumber;

    /**
     * 当前支持两种模式:新增联系人和修改联系人;
     * 默认为新增联系人.
     * 新增联系人包括纯新增和拨号记录中过来的新增,不同处表现在是否携带号码
     */
    private boolean addContactMode = true;
    // 是否来自拨号记录的添加联系人
    private boolean isFromDialPage = false;

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
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_SUCCESS_MSG_ID:
                showToast(R.string.contact_info_add_contact_success_toast);
                if (isFromDialPage) {
                    FragmentMgr.getInstance().finishDialFragment(this);
                } else {
                    FragmentMgr.getInstance().finishContactFragment(this);
                }
                break;
            case BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_FAILED_MSG_ID:
                showToast(R.string.contact_info_add_contact_failed_toast);
                break;
            case BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_SUCCESS_MSG_ID:
                showToast(R.string.contact_info_edit_contact_success_toast);
                FragmentMgr.getInstance().finishContactFragment(this);
                break;
            case BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_FAILED_MSG_ID:
                showToast(R.string.contact_info_edit_contact_failed_toast);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_contact_edit;
    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) super.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    protected void initView(View view) {
        // 头像
        headView = view.findViewById(R.id.contact_head_layout);
        // headView.setOnClickListener(this);
        headImg = (CircleImageView) view.findViewById(R.id.contact_head_img);
        headView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                headImg.setBorderColorResource(hasFocus ? R.color.contact_info_head_select : R.color.white);
            }
        });

        // 姓名
        nameView = view.findViewById(R.id.contact_name_layout);
        nameText = (EditText) view.findViewById(R.id.contact_name_hint);
        nameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                nameView.setBackgroundResource(hasFocus ? R.drawable.btn_bg_selected : R.color.transparent);
            }
        });

        // 号码
        numberView = view.findViewById(R.id.contact_number_layout);
        numberText = (EditText) view.findViewById(R.id.contact_number_hint);
        numberText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                numberView.setBackgroundResource(hasFocus ? R.drawable.btn_bg_selected : R.color.transparent);
            }
        });

        // 保存按钮
        view.findViewById(R.id.contact_info_save_layout).setOnClickListener(this);

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
        // 新增联系人
        if (addContactMode) {
            // 添加带号码的陌生人
            if (inputNumber != null) {
                numberText.setText(inputNumber);
                numberText.setSelection(numberText.getEditableText().length());
                oldNumber = inputNumber;
                isFromDialPage = true;
            }
            // 纯添加联系人
            else {
                isFromDialPage = false;
            }
        }
        // 编辑联系人
        else {
            oldName = editContactsInfo.getName();
            nameText.setText(oldName);
            nameText.setSelection(nameText.getEditableText().length());

            oldNumber = editContactsInfo.getPhone();
            numberText.setText(oldNumber);
            numberText.setSelection(numberText.getEditableText().length());

            Picasso.with(getContext())
                    .load(editContactsInfo.getPhotoSm())
                    .placeholder(R.drawable.contact_photo_default)
                    .error(R.drawable.contact_photo_default).into(headImg);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        FocusManager.getInstance().requestFocus(nameText);
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
            case R.id.contact_head_layout:
                doClickHeadLayout();
                break;
            case R.id.contact_info_save_layout:
                doClickSubmit();
                break;
        }
    }


    private void doClickHeadLayout() {
        PhotoManage.getInstance(this.getContext()).showDialog();
    }

    private void doClickSubmit() {
        // 获取新的
        newName = nameText.getText().toString();
        newNumber = numberText.getText().toString();

        // 添加联系人
        if (addContactMode) {
            if (StringUtil.isNullOrEmpty(newName)) {
                // TODO
                showToast(R.string.contact_name_input_empty_toast);
                return;
            }

            if (StringUtil.isNullOrEmpty(newNumber)) {
                // TODO
                showToast(R.string.contact_number_input_empty_toast);
                return;
            }
            contactsLogic.addAppContact(newName, newNumber, newPhotoName);
        }
        // 更新联系人
        else {
            boolean isNameValidAndChanged = !StringUtil.isNullOrEmpty(newName)
                    && !newName.equals(oldName);
            boolean isNumberValidAndChanged = !StringUtil.isNullOrEmpty(newNumber)
                    && !newNumber.equals(oldNumber);

            if (isNameValidAndChanged || isNumberValidAndChanged) {
                contactsLogic.updateAppContact(editContactsInfo.getContactId()
                        , newName, newNumber, newPhotoName);
            }
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
