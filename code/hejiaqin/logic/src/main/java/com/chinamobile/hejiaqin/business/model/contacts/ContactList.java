package com.chinamobile.hejiaqin.business.model.contacts;

import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 联系人列表模型
 */
public class ContactList {
    Map<String, ContactsInfo> contactsInfoMap = new ConcurrentHashMap<>();

    public List<ContactsInfo> get() {
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        contactsInfoList.addAll(contactsInfoMap.values());
        return contactsInfoList;
    }

    public void clear() {
        contactsInfoMap.clear();
    }

    public void addLocalContact(String name, NumberInfo numberInfo) {
        if (StringUtil.isNullOrEmpty(name) || null == numberInfo || null == numberInfo.getNumber()) {
            return;
        }

        ContactsInfo tmpContactsInfo = contactsInfoMap.get(name);
        if (null == tmpContactsInfo) {
            tmpContactsInfo = new ContactsInfo();
            tmpContactsInfo.setName(name);
            tmpContactsInfo.addNumber(numberInfo);
            tmpContactsInfo.setContactMode(ContactsInfo.ContactMode.system);
            contactsInfoMap.put(name, tmpContactsInfo);
            return;
        }

        tmpContactsInfo.addNumber(numberInfo);
        contactsInfoMap.put(name, tmpContactsInfo);
    }
}
