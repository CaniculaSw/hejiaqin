package com.chinamobile.hejiaqin.business.model.contacts;

import com.chinamobile.hejiaqin.business.BussinessConstants;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class ContactsInfoTest extends TestCase {

    public void testGetContactId() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setContactId("abc");
        assertEquals("abc", contactsInfo.getContactId());
    }

    public void testSetContactId() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setContactId("abc");
        assertEquals("abc", contactsInfo.getContactId());
    }

    public void testGetName() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setName("abc");
        assertEquals("abc", contactsInfo.getName());
    }

    public void testSetName() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setName("abc");
        assertEquals("abc", contactsInfo.getName());
    }

    public void testGetContactMode() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setContactMode(ContactsInfo.ContactMode.app);
        assertEquals(ContactsInfo.ContactMode.app, contactsInfo.getContactMode());
    }

    public void testSetContactMode() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setContactMode(ContactsInfo.ContactMode.app);
        assertEquals(ContactsInfo.ContactMode.app, contactsInfo.getContactMode());
    }

    public void testGetNumberLst() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.addNumber(new NumberInfo());
        assertNotNull(contactsInfo.getNumberLst());
    }

    public void testGetPhone() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        assertNull(contactsInfo.getPhone());

        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("123");
        contactsInfo.addNumber(numberInfo);
        assertNotNull(contactsInfo.getPhone());
    }

    public void testGetPhotoLg() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setPhotoLg(BussinessConstants.ServerInfo.HTTP_ADDRESS);
        assertEquals(BussinessConstants.ServerInfo.HTTP_ADDRESS, contactsInfo.getPhotoLg());
    }

    public void testSetPhotoLg() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setPhotoLg(BussinessConstants.ServerInfo.HTTP_ADDRESS);
        assertEquals(BussinessConstants.ServerInfo.HTTP_ADDRESS, contactsInfo.getPhotoLg());
    }

    public void testGetPhotoSm() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setPhotoSm(BussinessConstants.ServerInfo.HTTP_ADDRESS);
        assertEquals(BussinessConstants.ServerInfo.HTTP_ADDRESS, contactsInfo.getPhotoSm());
    }

    public void testSetPhotoSm() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setPhotoSm(BussinessConstants.ServerInfo.HTTP_ADDRESS);
        assertEquals(BussinessConstants.ServerInfo.HTTP_ADDRESS, contactsInfo.getPhotoSm());
    }

    public void testAddNumber() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("123");
        contactsInfo.addNumber(numberInfo);
        assertNotNull(contactsInfo.getPhone());
    }

    public void testGetNameInPinyin() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        assertEquals("", contactsInfo.getNameInPinyin());
    }

    public void testGetSearchUnit() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        assertNull(contactsInfo.getSearchUnit());

        PinyinUnit pinyinUnit = new PinyinUnit();
        contactsInfo.genSearchUnit(pinyinUnit);
        assertNotNull(contactsInfo.getSearchUnit());
    }

    public void testGenSearchUnit() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        assertNull(contactsInfo.getSearchUnit());

        PinyinUnit pinyinUnit = new PinyinUnit();
        contactsInfo.genSearchUnit(pinyinUnit);
        assertNotNull(contactsInfo.getSearchUnit());
    }

    public void testGetGroupName() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.getGroupName();
    }

    public void testIsMatch() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.isMatch("");
    }

    public void testToString() throws Exception {
        ContactsInfo contactsInfo = new ContactsInfo();
        assertNotNull(contactsInfo.toString());
    }
}