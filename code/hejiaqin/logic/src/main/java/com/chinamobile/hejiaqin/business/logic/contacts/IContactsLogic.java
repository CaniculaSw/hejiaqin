package com.chinamobile.hejiaqin.business.logic.contacts;

import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;

import java.util.List;

/**
 * Created by th on 5/24/16.
 */
public interface IContactsLogic {
    /***/
    void fetchLocalContactLst();

    List<ContactsInfo> getCacheLocalContactLst();

    List<ContactsInfo> getCacheAppContactLst();

    /***/
    void searchLocalContactLst(String input, String invoker);

    /***/
    void searchAppContactLst(String input, String invoker);

    /***/
    void fetchAppContactLst();

    /***/
    void addAppContact(String name, String number, String photoFullPath);

    /***/
    void addAppContact(ContactsInfo contactsInfo);

    /***/
    void batchAddAppContacts(List<ContactsInfo> contactsInfoList);

    /***/
    void updateAppContact(String contactId, String name, String number, String photoFullPath);

    /***/
    void deleteAppContact(String contactId);

    /***/
    void queryContactCallRecords(ContactsInfo contactsInfo);

    /***/
    void deleteContactCallRecords(ContactsInfo contactsInfo);

    /***/
    boolean isAppContactExist(String phoneNumber);
}
