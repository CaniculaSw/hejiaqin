package com.chinamobile.hejiaqin.business.ui.contact;

import android.content.Intent;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.model.dial.DialInfo;
import com.chinamobile.hejiaqin.business.model.dial.DialInfoGroup;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragmentActivity;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.AddContactDialog;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.DelCallRecordDialog;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.DialNumberDialog;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.EditContactDialog;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.contact.fragment.ContactInfoFragment;
import com.chinamobile.hejiaqin.business.ui.contact.fragment.DialInfoFragment;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/***/
public class ContactInfoActivity extends BasicFragmentActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_EDIT_CONTACT = 10001;

    private HeaderView titleLayout;
    private TextView mContactNameText;
    private CircleImageView mContactHeadImg;

    private View mContactInfoLay;

    private ImageView mContactInfoIcon;

    private ImageView mContactInfoSelected;

    private ImageView mContactInfoUnSelected;


    private View mDialInfoLay;

    private ImageView mDialInfoIcon;

    private ImageView mDialInfoSelected;

    private ImageView mDialInfoUnSelected;

    /**
     * 作为页面容器的ViewPager.
     */
    ViewPager mViewPager;

    private List<Fragment> fragmentList;

    private final static int CONTACT_INFO_INDEX = 0;
    private final static int DIAL_INFO_INDEX = 1;
    //当前选中的项
    int currentIndex = -1;

    private ContactsInfo mContactsInfo;

    //是否陌生人
    private boolean isStranger;

    private IContactsLogic contactsLogic;
    private BasicFragment.BackListener listener = new BasicFragment.BackListener() {
        public void onAction(int actionId, Object obj) {

        }
    };

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) this.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_info;
    }

    @Override
    protected void initView() {
        // title
        titleLayout = (HeaderView) findViewById(R.id.title);
        titleLayout.title.setText(R.string.contact_info_title_text);
        titleLayout.backImageView.setImageResource(R.mipmap.title_icon_back_nor);

        // 联系人姓名
        mContactNameText = (TextView) findViewById(R.id.contact_name_text);
        // 联系人头像
        mContactHeadImg = (CircleImageView) findViewById(R.id.contact_head_img);

        mContactInfoLay = findViewById(R.id.contact_info_layout);
        mContactInfoLay.setOnClickListener(this);
        mContactInfoIcon = (ImageView) findViewById(R.id.contact_info_icon);
        mContactInfoSelected = (ImageView) findViewById(R.id.contact_info_selected);
        mContactInfoUnSelected = (ImageView) findViewById(R.id.contact_info_unselected);

        mDialInfoLay = findViewById(R.id.dial_info_layout);
        mDialInfoLay.setOnClickListener(this);
        mDialInfoIcon = (ImageView) findViewById(R.id.dial_info_icon);
        mDialInfoSelected = (ImageView) findViewById(R.id.dial_info_selected);
        mDialInfoUnSelected = (ImageView) findViewById(R.id.dial_info_unselected);

        mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);

        ImageView dialImg = (ImageView) findViewById(R.id.dial_img);
        dialImg.setOnClickListener(this);
    }

    @Override
    protected void initDate() {
        mContactsInfo = (ContactsInfo) getIntent().getSerializableExtra(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY);

        if (mContactsInfo == null) {
            //通话记录传入的号码
            String callRecordNumber = getIntent().getStringExtra(BussinessConstants.Contact.INTENT_CONTACT_NUMBER_KEY);
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
            Picasso.with(this.getApplicationContext())
                    .load(mContactsInfo.getPhotoSm())
                    .placeholder(R.drawable.contact_photo_default)
                    .error(R.drawable.contact_photo_default).into(mContactHeadImg);
        }

        fragmentList = new ArrayList<Fragment>();
        BasicFragment contactInfoFragment = new ContactInfoFragment();
        contactInfoFragment.setActivityListener(listener);
        ((ContactInfoFragment) contactInfoFragment).setContactsInfo(mContactsInfo);
        fragmentList.add(contactInfoFragment);

        BasicFragment dialInfoFragment = new DialInfoFragment();
        dialInfoFragment.setActivityListener(listener);
        fragmentList.add(dialInfoFragment);

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        showViewByCurIndex(mViewPager.getCurrentItem());

        contactsLogic.queryContactCallRecords(mContactsInfo);
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
        dialInfoList.add(dialInfo1);

        DialInfo dialInfo2 = new DialInfo();
        dialInfo2.setType(DialInfo.Type.in);
        dialInfoList.add(dialInfo2);

        DialInfo dialInfo3 = new DialInfo();
        dialInfo3.setType(DialInfo.Type.out);
        dialInfoList.add(dialInfo3);

        DialInfo dialInfo4 = new DialInfo();
        dialInfo4.setType(DialInfo.Type.missed);
        dialInfoList.add(dialInfo4);

        DialInfo dialInfo5 = new DialInfo();
        dialInfo5.setType(DialInfo.Type.reject);
        dialInfoList.add(dialInfo5);

        DialInfo dialInfo6 = new DialInfo();
        dialInfo6.setType(DialInfo.Type.in);
        dialInfoList.add(dialInfo6);

        DialInfo dialInfo7 = new DialInfo();
        dialInfo7.setType(DialInfo.Type.out);
        dialInfoList.add(dialInfo7);
        return dialInfoList;
    }

    private void showContactData() {

        mContactNameText.setText(mContactsInfo.getName());
        if (!StringUtil.isNullOrEmpty(mContactsInfo.getPhotoSm())) {
            Picasso.with(this.getApplicationContext())
                    .load(mContactsInfo.getPhotoSm())
                    .placeholder(R.drawable.contact_photo_default)
                    .error(R.drawable.contact_photo_default).into(mContactHeadImg);
        }

        ContactInfoFragment contactInfoFragment = (ContactInfoFragment) fragmentList.get(CONTACT_INFO_INDEX);
        contactInfoFragment.setContactsInfo(mContactsInfo);
        contactInfoFragment.refreshView();
    }

    private void showDialData(List<DialInfoGroup> callRecordList) {
        DialInfoFragment dialInfoFragment = (DialInfoFragment) fragmentList.get(DIAL_INFO_INDEX);

        if (null == callRecordList) {
            callRecordList = new ArrayList<DialInfoGroup>();
        }
        dialInfoFragment.setDialInfo(callRecordList);
        dialInfoFragment.refreshView();

        showViewByCurIndex(currentIndex);
    }

    @Override
    protected void initListener() {
        titleLayout.rightBtn.setOnClickListener(this);
        titleLayout.backImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_btn:
                doClickTitleRight();
                break;
            case R.id.back_iv:
                this.finish();
                break;
            case R.id.contact_info_layout:
                if (currentIndex != CONTACT_INFO_INDEX) {
                    switchFragment(CONTACT_INFO_INDEX);
                }
                break;
            case R.id.dial_info_layout:
                if (currentIndex != DIAL_INFO_INDEX) {
                    switchFragment(DIAL_INFO_INDEX);
                }
                break;
            case R.id.dial_img:
                // TODO
                showDialNumberDialog();
                break;
            default:
                break;
        }
    }

    private void doClickTitleRight() {
        // 拨号详情tab页
        if (currentIndex == DIAL_INFO_INDEX) {
            if (!isDialInfoHasData()) {
                return;
            }

            showDeleteDialDialog();

            return;
        }

        if (isStranger) {
            showAddStrangerDialog();
            return;
        }
        // 联系人信息页
        // 合家亲联系人
        if (isAppContact()) {
            showEditContactDialog();
            return;
        }
        // 通讯录联系人
        showAddContactDialog();

    }


    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_SUCCESS_MSG_ID:
                showToast(R.string.contact_info_add_contact_success_toast);
                break;
            case BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_FAILED_MSG_ID:
                showToast(R.string.contact_info_add_contact_failed_toast);
                this.finish();
                break;
            case BussinessConstants.ContactMsgID.DEL_APP_CONTACTS_SUCCESS_MSG_ID:
                showToast(R.string.contact_info_del_contact_success_toast);
                this.finish();
                break;
            case BussinessConstants.ContactMsgID.DEL_APP_CONTACTS_FAILED_MSG_ID:
                showToast(R.string.contact_info_del_contact_failed_toast);
                break;
            case BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_SUCCESS_MSG_ID:
                showToast(R.string.contact_info_edit_contact_success_toast);
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
            default:
                break;
        }
    }

    private void showDeleteDialDialog() {

        final DelCallRecordDialog delCallRecordDialog = new DelCallRecordDialog(this, R.style.CalendarDialog);
        Window window = delCallRecordDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        delCallRecordDialog.setCancelable(true);
        delCallRecordDialog.show();

        delCallRecordDialog.delLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 拨号逻辑接口删除通话记录
                contactsLogic.deleteContactCallRecords(mContactsInfo);

                delCallRecordDialog.dismiss();
            }
        });

        delCallRecordDialog.cancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delCallRecordDialog.dismiss();
            }
        });
    }

    private void showAddStrangerDialog() {
        final AddContactDialog addContactDialog = new AddContactDialog(this, R.style.CalendarDialog);
        Window window = addContactDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        addContactDialog.setCancelable(true);
        addContactDialog.show();

        addContactDialog.addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactDialog.dismiss();
                Intent intent = new Intent(ContactInfoActivity.this, ModifyContactActivity.class);
                intent.putExtra(BussinessConstants.Contact.INTENT_CONTACT_NUMBER_KEY, mContactsInfo.getNumberLst().get(0).getNumber());
                startActivity(intent);
            }
        });

        addContactDialog.cancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactDialog.dismiss();
            }
        });
    }

    private void showAddContactDialog() {
        final AddContactDialog addContactDialog = new AddContactDialog(this, R.style.CalendarDialog);
        Window window = addContactDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        addContactDialog.setCancelable(true);
        addContactDialog.show();

        addContactDialog.addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsLogic.addAppContact(mContactsInfo);
                addContactDialog.dismiss();
            }
        });

        addContactDialog.cancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactDialog.dismiss();
            }
        });
    }


    private void showEditContactDialog() {
        final EditContactDialog editContactDialog = new EditContactDialog(this, R.style.CalendarDialog);
        Window window = editContactDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        editContactDialog.setCancelable(true);
        editContactDialog.show();

        editContactDialog.editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editContactDialog.dismiss();
                Intent intent = new Intent(ContactInfoActivity.this, ModifyContactActivity.class);
                intent.putExtra(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY, mContactsInfo);
                startActivityForResult(intent, REQUEST_CODE_EDIT_CONTACT);
            }
        });

        editContactDialog.delLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editContactDialog.dismiss();
                contactsLogic.deleteAppContact(mContactsInfo.getContactId());
            }
        });

        editContactDialog.cancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editContactDialog.dismiss();
            }
        });


    }


    private void showDialNumberDialog() {
        final DialNumberDialog dialNumberDialog = new DialNumberDialog(this, R.style.CalendarDialog
                , mContactsInfo, ((IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class)),
                ((IContactsLogic) super.getLogicByInterfaceClass(IContactsLogic.class)), true);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_EDIT_CONTACT:
                if (null != data) {
                    ContactsInfo newContactsInfo = (ContactsInfo) data.getSerializableExtra(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY);
                    if (null != newContactsInfo) {
                        mContactsInfo = newContactsInfo;
                        showContactData();
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean isAppContact() {
        return mContactsInfo.getContactMode() == ContactsInfo.ContactMode.app;
    }

    //手动设置ViewPager要显示的视图
    private void switchFragment(int toIndex) {
        mViewPager.setCurrentItem(toIndex, true);
    }

    /**
     * 定义自己的ViewPager适配器。
     */
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        /**
         * 每次更新完成ViewPager的内容后，调用该接口，此处复写主要是为了让导航按钮上层的覆盖层能够动态的移动
         */
        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);//这句话要放在最前面，否则会报错
            //获取当前的视图是位于ViewGroup的第几个位置，用来更新对应的覆盖层所在的位置
            int currentItem = mViewPager.getCurrentItem();
            if (currentItem == currentIndex) {
                return;
            }

            showViewByCurIndex(currentItem);
            currentIndex = mViewPager.getCurrentItem();
        }

    }

    private void showViewByCurIndex(int currentItem) {
        if (currentItem == CONTACT_INFO_INDEX) {
            mContactInfoIcon.setImageResource(R.mipmap.icon_personal_data_pre);
            mContactInfoSelected.setVisibility(View.VISIBLE);
            mContactInfoUnSelected.setVisibility(View.GONE);
            mDialInfoIcon.setImageResource(R.mipmap.icon_call_record_nor);
            mDialInfoSelected.setVisibility(View.GONE);
            mDialInfoUnSelected.setVisibility(View.VISIBLE);
            titleLayout.rightBtn.setImageResource(isAppContact()
                    ? R.mipmap.title_icon_more_nor : R.mipmap.title_icon_add_nor);
        } else if (currentItem == DIAL_INFO_INDEX) {
            mContactInfoIcon.setImageResource(R.mipmap.icon_personal_data_nor);
            mContactInfoSelected.setVisibility(View.GONE);
            mContactInfoUnSelected.setVisibility(View.VISIBLE);
            mDialInfoIcon.setImageResource(R.mipmap.icon_call_record_pre);
            mDialInfoSelected.setVisibility(View.VISIBLE);
            mDialInfoUnSelected.setVisibility(View.GONE);
            titleLayout.rightBtn.setImageResource(isDialInfoHasData() ? R.mipmap.title_icon_delete_nor
                    : R.mipmap.title_icon_delete_dis);
        }
    }

    private boolean isDialInfoHasData() {
        DialInfoFragment dialInfoFragment = (DialInfoFragment) fragmentList.get(DIAL_INFO_INDEX);
        return dialInfoFragment.hasData();
    }
}
