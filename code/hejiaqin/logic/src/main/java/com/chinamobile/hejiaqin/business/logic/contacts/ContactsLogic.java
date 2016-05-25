package com.chinamobile.hejiaqin.business.logic.contacts;

import com.chinamobile.hejiaqin.business.BussinessConstants;
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
                ContactsInfoManager.getInstance().sortContactsInfoLst(contactsInfoList);
                sendMessage(BussinessConstants.ContactMsgID.GET_LOCAL_CONTACTS_SUCCESS_MSG_ID, contactsInfoList);
            }
        });
    }
}
