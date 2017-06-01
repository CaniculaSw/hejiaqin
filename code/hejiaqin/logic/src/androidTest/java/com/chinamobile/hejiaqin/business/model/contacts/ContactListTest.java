package com.chinamobile.hejiaqin.business.model.contacts;

import com.chinamobile.hejiaqin.business.model.contacts.rsp.ContactBean;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class ContactListTest extends TestCase {

    public void testGet() throws Exception {
        ContactList contactList = new ContactList();
        assertNotNull(contactList.get());
    }

    public void testClear() throws Exception {
        ContactList contactList = new ContactList();
        contactList.clear();
        assertNotNull(contactList.get());
    }

    public void testAddLocalContact() throws Exception {
        ContactList contactList = new ContactList();
        contactList.addLocalContact("abc", new NumberInfo());
        assertNotNull(contactList.get());
    }

    public void testAddAppContact() throws Exception {
        ContactList contactList = new ContactList();
        contactList.addAppContact(new ContactBean());
        assertNotNull(contactList.get());
    }

    public void testConvert() throws Exception {
        ContactList contactList = new ContactList();
        contactList.convert(new ContactBean());
        assertNotNull(contactList.get());
    }
}