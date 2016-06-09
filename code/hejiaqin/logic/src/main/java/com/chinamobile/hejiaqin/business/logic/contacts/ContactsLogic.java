package com.chinamobile.hejiaqin.business.logic.contacts;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.dbApdater.ContactsDbAdapter;
import com.chinamobile.hejiaqin.business.manager.ContactsInfoManager;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.customer.framework.component.ThreadPool.ThreadPoolUtil;
import com.customer.framework.component.ThreadPool.ThreadTask;
import com.customer.framework.logic.LogicImp;

import java.util.List;

/**
 * Created by th on 5/24/16.
 */
public class ContactsLogic extends LogicImp implements IContactsLogic {
    @Override
    public void fetchLocalContactLst() {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {
                List<ContactsInfo> contactsInfoList = ContactsInfoManager.getInstance().getLocalContactLst(getContext());
                ContactsInfoManager.getInstance().sortContactsInfoLst(getContext(), contactsInfoList);
                ContactsInfoManager.getInstance().cacheLocalContactInfo(contactsInfoList);


                sendMessage(BussinessConstants.ContactMsgID.GET_LOCAL_CONTACTS_SUCCESS_MSG_ID, contactsInfoList);
            }
        });
    }


    @Override
    public void fetchAppContactLst() {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {
                // 从网络获取联系人数据 TODO

                // 从数据库获取联系人数据

                // 伪造数据,用于测试,先从本地联系人中获取 TODO
                ContactsDbAdapter.getInstance(getContext(), "aaa").delAll();
                List<ContactsInfo> contactsInfoList = ContactsInfoManager.getInstance().getLocalContactLst(getContext());
                ContactsInfoManager.getInstance().sortContactsInfoLst(getContext(), contactsInfoList);
                ContactsDbAdapter.getInstance(getContext(), "aaa").add(contactsInfoList);

                List<ContactsInfo> newContactsInfoList = ContactsDbAdapter.getInstance(getContext(), "aaa").queryAll();
                sendMessage(BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID, newContactsInfoList);
            }
        });
    }

    @Override
    public List<ContactsInfo> getCacheLocalContactLst() {
        return ContactsInfoManager.getInstance().getCachedLocalContactInfo();
    }

    @Override
    public void searchLocalContactLst(String input) {
        List<ContactsInfo> contactsInfoList = getCacheLocalContactLst();
        sendMessage(BussinessConstants.ContactMsgID.SEARCH_LOCAL_CONTACTS_SUCCESS_MSG_ID, ContactsInfoManager.getInstance().searchContactsInfoLst(contactsInfoList, input));
    }
}
