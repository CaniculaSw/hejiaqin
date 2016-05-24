package com.chinamobile.hejiaqin.business.ui.contact.fragment;

import android.os.Message;
import android.view.View;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;
import com.chinamobile.hejiaqin.business.ui.contact.adapter.SysContactAdapter;

import java.util.List;

/**
 *
 *
 */
public class SysContactListFragment extends BasicFragment {
    private IContactsLogic contactsLogic;
    private SysContactAdapter adapter;

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.GET_LOCAL_CONTACTS_SUCCESS_MSG_ID:
                List<ContactsInfo> contactsInfoList = (List<ContactsInfo>) msg.obj;
                adapter.setData(contactsInfoList);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sys_contact_list;
    }

    @Override
    protected void initView(View view) {
        StickyListHeadersListView stickyList = (StickyListHeadersListView) view.findViewById(R.id.list);
        adapter = new SysContactAdapter(this.getContext());
        stickyList.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        contactsLogic.fetchLocalContactLst();
    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) this.getLogicByInterfaceClass(IContactsLogic.class);
    }
}
