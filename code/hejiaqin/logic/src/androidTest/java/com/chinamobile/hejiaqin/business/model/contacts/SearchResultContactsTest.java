package com.chinamobile.hejiaqin.business.model.contacts;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class SearchResultContactsTest extends TestCase {

    public void testGetInvoker() throws Exception {
        String invoker = "aaa";
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        SearchResultContacts searchResultContacts = new SearchResultContacts(invoker, contactsInfoList);
        assertEquals(invoker, searchResultContacts.getInvoker());
    }

    public void testGetContactsInfos() throws Exception {
        String invoker = "aaa";
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        SearchResultContacts searchResultContacts = new SearchResultContacts(invoker, contactsInfoList);
        assertEquals(contactsInfoList, searchResultContacts.getContactsInfos());
    }
}