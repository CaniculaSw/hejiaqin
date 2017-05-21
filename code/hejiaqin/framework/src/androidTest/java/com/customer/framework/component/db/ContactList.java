package com.customer.framework.component.db;

import android.provider.ContactsContract;

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

    /***/
    public List<ContactsInfo> get() {
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        contactsInfoList.addAll(contactsInfoMap.values());
        return contactsInfoList;
    }

    /***/
    public void clear() {
        contactsInfoMap.clear();
    }

    /***/
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

    /***/
    public void addAppContact(ContactBean contactBean) {
        if (null == contactBean || StringUtil.isNullOrEmpty(contactBean.getName())
                || StringUtil.isNullOrEmpty(contactBean.getPhone())) {
            return;
        }

        ContactsInfo tmpContactsInfo = contactsInfoMap.get(contactBean.getContactId());
        if (null == tmpContactsInfo) {
            tmpContactsInfo = convert(contactBean);
            if (null == tmpContactsInfo) {
                return;
            }
            contactsInfoMap.put(contactBean.getContactId(), tmpContactsInfo);
            return;
        }

        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setType(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        numberInfo.setNumber(contactBean.getPhone());
        tmpContactsInfo.addNumber(numberInfo);
        contactsInfoMap.put(contactBean.getContactId(), tmpContactsInfo);
    }

    /***/
    public static ContactsInfo convert(ContactBean contactBean) {
        if (null == contactBean || null == contactBean.getContactId()
                || null == contactBean.getName() || null == contactBean.getPhone()) {
            return null;
        }

        ContactsInfo tmpContactsInfo = new ContactsInfo();
        tmpContactsInfo.setContactId(contactBean.getContactId());
        tmpContactsInfo.setName(contactBean.getName());

        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setType(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        numberInfo.setNumber(contactBean.getPhone());
        tmpContactsInfo.addNumber(numberInfo);

        tmpContactsInfo.setContactMode(ContactsInfo.ContactMode.app);
        tmpContactsInfo.setPhotoLg(contactBean.getPhotoLg());
        tmpContactsInfo.setPhotoSm(contactBean.getPhotoSm());
        return tmpContactsInfo;
    }

}
