package com.chinamobile.hejiaqin.business.logic.contacts;

import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;

import java.util.List;

/**
 * Created by th on 5/24/16.
 */
public interface IContactsLogic {
    void fetchLocalContactLst();

    List<ContactsInfo> getCacheLocalContactLst();

    void searchLocalContactLst(String input);

    void fetchAppContactLst();

    void addAppContact(String name, String number, byte[] photo);

    void batchAddAppContacts();

    void updateAppContact(String contactId, String name, String number, byte[] photo);
}
