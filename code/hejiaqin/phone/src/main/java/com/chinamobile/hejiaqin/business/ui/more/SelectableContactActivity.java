package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;
import com.chinamobile.hejiaqin.business.ui.more.adapter.SelectContactAdapter;
import com.chinamobile.hejiaqin.business.utils.CaaSUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by eshaohu on 16/6/6.
 */
public class SelectableContactActivity extends BasicActivity implements View.OnClickListener {

    private static final String TAG = "SelectableContactActivity";

    private String tvAccount;
    private HeaderView mHeaderView;
    private Context mContext;
    private StickyListHeadersListView mContactListView;
    private SelectContactAdapter adapter;
    private IContactsLogic contactsLogic;
    private ISettingLogic settingLogic;
    private TextView mSelectAll;
    private TextView mSelectCount;
    private int mSelectedContactNum;

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID:
                List<ContactsInfo> contactsInfoList = (List<ContactsInfo>) msg.obj;
                adapter.setData(contactsInfoList);
                break;
            case BussinessConstants.SettingMsgID.CONTACT_CHECKED_STATED_CHANGED:
                updateSelectedHint(msg.arg1);
                if (msg.arg1 == msg.arg2) {
                    mSelectAll.setText(getString(R.string.more_cancle_select_all));
                } else {
                    if (mSelectAll.getText().equals(getString(R.string.more_cancle_select_all))) {
                        mSelectAll.setText(getString(R.string.more_select_all));
                    }
                }
                break;
            case BussinessConstants.SettingMsgID.SEND_CONTACT_RESPOND_SUCCESS:
                doBack();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_contact;
    }

    @Override
    protected void initView() {
        mContext = getApplicationContext();
        mHeaderView = (HeaderView) findViewById(R.id.more_select_contact_header);
        mHeaderView.title.setText(R.string.more_choose_contact_in_list);
        mHeaderView.backImageView.setImageResource(R.mipmap.title_icon_close);
        mHeaderView.rightBtn.setImageResource(R.mipmap.title_icon_check_nor);

        mContactListView = (StickyListHeadersListView) findViewById(R.id.list);
        adapter = new SelectContactAdapter(this, getHandler());
        mContactListView.setAdapter(adapter);
        mSelectCount = (TextView) findViewById(R.id.more_chosen);
        mSelectAll = (TextView) findViewById(R.id.more_select_all);
    }

    @Override
    protected void initDate() {
        contactsLogic.fetchAppContactLst();
        mSelectedContactNum = 0;
        updateSelectedHint(mSelectedContactNum);
        Intent intent = getIntent();
        tvAccount = intent.getStringExtra("tvAccount");
    }

    @Override
    protected void initListener() {
        mHeaderView.backImageView.setClickable(true);
        mHeaderView.backImageView.setOnClickListener(this);
        mHeaderView.rightBtn.setClickable(true);
        mHeaderView.rightBtn.setOnClickListener(this);
        mSelectAll.setClickable(true);
        mSelectAll.setOnClickListener(this);
    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) this.getLogicByInterfaceClass(IContactsLogic.class);
        settingLogic = (ISettingLogic) getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_select_all:
                adapter.selectAll(mSelectAll.getText().equals(getString(R.string.more_select_all)));
                break;
            case R.id.back_iv:
                doBack();
                break;
            case R.id.right_btn:
                sendContacts();
                break;
            default:
                break;
        }
    }

    private void sendContacts() {
        mHeaderView.rightBtn.setClickable(false);
        Set<ContactsInfo> contacts = adapter.getSelectedContactSet();
        if (contacts.size() == 0) {
            return;
        }
        settingLogic.sendContact(tvAccount, CaaSUtil.OpCode.SEND_CONTACT, getParam(contacts));
        mHeaderView.rightBtn.setClickable(true);
        showToast("正在发送", Toast.LENGTH_SHORT, null);
    }

    private Map<String, String> getParam(Set<ContactsInfo> contacts) {
        Map<String, String> params = new HashMap<String, String>();
        StringBuilder names = new StringBuilder();
        StringBuilder numbers = new StringBuilder();
        Iterator<ContactsInfo> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            ContactsInfo contact = iterator.next();
            names.append(contact.getName()).append(";");
            numbers.append(contact.getPhone()).append(";");
        }
        names.deleteCharAt(names.lastIndexOf(";"));
        numbers.deleteCharAt(numbers.lastIndexOf(";"));

        params.put("Param1", names.toString());
        params.put("Param2", numbers.toString());
        params.put("Param3", UserInfoCacheManager.getUserInfo(getApplicationContext()).getPhone());

        return params;
    }

    private void updateSelectedHint(int num) {
        mSelectCount.setText(getString(R.string.more_chosen, num));
    }
}
