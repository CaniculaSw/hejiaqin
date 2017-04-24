package com.chinamobile.hejiaqin.business.model.contacts;

import java.util.List;

/**
 * Created by  on 2016/7/25.
 */
public class SearchResultContacts {
    private String invoker;
    private List<ContactsInfo> contactsInfos;

    public SearchResultContacts(String invoker, List<ContactsInfo> contactsInfos) {
        this.invoker = invoker;
        this.contactsInfos = contactsInfos;
    }

    public String getInvoker() {
        return invoker;
    }

    public List<ContactsInfo> getContactsInfos() {
        return contactsInfos;
    }
}
