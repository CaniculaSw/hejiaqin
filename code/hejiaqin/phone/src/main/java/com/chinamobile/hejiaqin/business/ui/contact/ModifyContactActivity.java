package com.chinamobile.hejiaqin.business.ui.contact;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.PhotoManage;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.basic.view.MyToast;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.customer.framework.utils.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ModifyContactActivity extends BasicActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_INPUT_NAME = 10001;

    public static final int REQUEST_CODE_INPUT_NUMBER = 10002;

    public static final int REQUEST_CODE_INPUT_PHOTO = 10003;

    public static final String INTENT_DATA_INPUT_INFO = "intent_data_input_info";

    public static final String INTENT_DATA_EDIT_INFO = "intent_data_edit_info";

    private HeaderView titleLayout;

    private View headView;
    private ImageView headImg;

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

    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) this.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_modify;
    }

    @Override
    protected void initView() {
        // title
        titleLayout = (HeaderView) findViewById(R.id.title);
        titleLayout.rightBtn.setImageResource(R.mipmap.title_icon_check_nor);
        titleLayout.backImageView.setImageResource(R.mipmap.title_icon_back_nor);

        // 头像
        headView = findViewById(R.id.contact_head_layout);
        headImg = (ImageView) findViewById(R.id.contact_head_img);

        // 姓名
        nameView = findViewById(R.id.contact_name_layout);
        nameText = (TextView) findViewById(R.id.contact_name_hint);

        // 号码
        numberView = findViewById(R.id.contact_number_layout);
        numberText = (TextView) findViewById(R.id.contact_number_hint);
    }

    @Override
    protected void initDate() {
        PhotoManage.getInstance(this).setPhotoListener(mPhotoChangeListener);

        editContactsInfo = (ContactsInfo) getIntent().getSerializableExtra(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY);
        addContactMode = (null == editContactsInfo);
        titleLayout.title.setText(addContactMode ? R.string.contact_modify_title_add_text : R.string.contact_modify_title_edit_text);
        if (!addContactMode) {
            // TODO headImg 使用实际头像
            nameText.setText(editContactsInfo.getName());
            numberText.setText(editContactsInfo.getPhone());
        }
    }

    @Override
    protected void initListener() {
        titleLayout.rightBtn.setOnClickListener(this);
        titleLayout.backImageView.setOnClickListener(this);

        headView.setOnClickListener(this);
        nameView.setOnClickListener(this);
        numberView.setOnClickListener(this);
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
                doClickSubmit();
                break;
            case R.id.back_iv:
                doBack();
                break;
            case R.id.contact_head_layout:
                doClickHeadLayout();
                break;
            case R.id.contact_name_layout:
                doClickNameLayout();
                break;
            case R.id.contact_number_layout:
                doClickNumberLayout();
                break;
        }
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);

        switch (msg.what) {
            case BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_SUCCESS_MSG_ID:
                showToast(R.string.contact_info_add_contact_success_toast);
                finish();
                break;
            case BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_FAILED_MSG_ID:
                showToast(R.string.contact_info_add_contact_failed_toast);
                break;
            case BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_SUCCESS_MSG_ID:
                showToast(R.string.contact_info_edit_contact_success_toast);
                ContactsInfo newContactsInfo = (ContactsInfo) msg.obj;
                if (null == newContactsInfo) {
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY, newContactsInfo);
                setResult(0, intent);
                finish();
                break;
            case BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_FAILED_MSG_ID:
                showToast(R.string.contact_info_edit_contact_failed_toast);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PhotoManage.IMAGE_CODE:
                PhotoManage.getInstance(this).startPhotoZoom(data.getData());
                break;
            case PhotoManage.CAMERA_CODE:
                if (CommonUtils.hasSdcard()) {
                    File tempFile = new File(Environment.getExternalStorageDirectory() + BussinessConstants.Setting.APP_SAVE_PATH + BussinessConstants.Setting.APP_IMG_DEFAULT_NAME);
                    PhotoManage.getInstance(this).startPhotoZoom(Uri.fromFile(tempFile));
                } else {
                    showToast(R.string.no_sdcard_update_header, 1, null);
                }
                break;
            case PhotoManage.CROP_CODE:
                if (null != data) {
                    PhotoManage.getInstance(this).sendPhotoEnd(data);
                }
                break;
            case REQUEST_CODE_INPUT_NAME:
                if (null != data) {
                    newName = data.getStringExtra(INTENT_DATA_INPUT_INFO);
                    if (!StringUtil.isNullOrEmpty(newName)) {
                        nameText.setText(newName);
                    }
                }
                break;
            case REQUEST_CODE_INPUT_NUMBER:
                if (null != data) {
                    newNumber = data.getStringExtra(INTENT_DATA_INPUT_INFO);
                    if (!StringUtil.isNullOrEmpty(newNumber)) {
                        numberText.setText(newNumber);
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PhotoManage.getInstance(this).setPhotoListener(null);
        PhotoManage.getInstance(this).removeContext();
    }

    private void doClickSubmit() {
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
        } else {
            if (StringUtil.isNullOrEmpty(newName) && StringUtil.isNullOrEmpty(newNumber)
                    && StringUtil.isNullOrEmpty(newPhotoName)) {
                return;
            }

            contactsLogic.updateAppContact(editContactsInfo.getContactId()
                    , newName, newNumber, newPhotoName);
        }
    }

    private void doClickHeadLayout() {
        PhotoManage.getInstance(this).showDialog();
    }

    private void doClickNameLayout() {
        Intent intent = new Intent(this, InputInfoActivity.class);

        if (!addContactMode) {
            intent.putExtra(INTENT_DATA_EDIT_INFO, editContactsInfo.getName());
        }
        startActivityForResult(intent, REQUEST_CODE_INPUT_NAME);

    }

    private void doClickNumberLayout() {
        Intent intent = new Intent(this, InputInfoActivity.class);

        if (!addContactMode) {
            intent.putExtra(INTENT_DATA_EDIT_INFO, editContactsInfo.getNumberLst().get(0).getNumber());
        }
        startActivityForResult(intent, REQUEST_CODE_INPUT_NUMBER);
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
