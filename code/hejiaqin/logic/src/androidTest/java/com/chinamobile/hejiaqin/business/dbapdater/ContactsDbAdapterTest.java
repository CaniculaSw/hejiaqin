package com.chinamobile.hejiaqin.business.dbapdater;

import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/6 0006.
 */
public class ContactsDbAdapterTest extends AndroidTestCase {

    String userId = "123";

    public void testGetInstance() throws Exception {
        ContactsDbAdapter dbAdapter = ContactsDbAdapter.getInstance(getContext(),
                userId);
        assertNotNull(dbAdapter);
    }

    public void testAdd() throws Exception {
        ContactsDbAdapter dbAdapter = ContactsDbAdapter.getInstance(getContext(),
                userId);
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setName("abc");
        contactsInfo.setContactId("111");
        contactsInfo.setContactMode(ContactsInfo.ContactMode.system);
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("13800001111");
        contactsInfo.addNumber(numberInfo);
        contactsInfoList.add(contactsInfo);
        dbAdapter.add(contactsInfoList);

        assertNotNull(dbAdapter.queryAll());
    }

    public void testUpdate() throws Exception {
        ContactsDbAdapter dbAdapter = ContactsDbAdapter.getInstance(getContext(),
                userId);
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setName("abc");
        contactsInfo.setContactId("111");
        contactsInfo.setContactMode(ContactsInfo.ContactMode.system);
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("13800001111");
        contactsInfo.addNumber(numberInfo);
        contactsInfoList.add(contactsInfo);
        dbAdapter.add(contactsInfoList);

        contactsInfoList = dbAdapter.queryAll();
        assertNotNull(contactsInfoList);
        assertFalse(contactsInfoList.isEmpty());

        ContactsInfo newContactInfo = contactsInfoList.get(0);
        assertEquals(newContactInfo.getName(), "abc");

        contactsInfo.setName("bbb");
        dbAdapter.update(contactsInfo);

        contactsInfoList = dbAdapter.queryAll();
        assertNotNull(contactsInfoList);
        assertFalse(contactsInfoList.isEmpty());
        newContactInfo = contactsInfoList.get(0);
        assertEquals(newContactInfo.getName(), "bbb");

    }

    public void testQueryAll() throws Exception {
        ContactsDbAdapter dbAdapter = ContactsDbAdapter.getInstance(getContext(),
                userId);
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setName("abc");
        contactsInfo.setContactId("111");
        contactsInfo.setContactMode(ContactsInfo.ContactMode.system);
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("13800001111");
        contactsInfo.addNumber(numberInfo);
        contactsInfoList.add(contactsInfo);
        dbAdapter.add(contactsInfoList);

        assertNotNull(dbAdapter.queryAll());
    }

    public void testDelByContactId() throws Exception {
        ContactsDbAdapter dbAdapter = ContactsDbAdapter.getInstance(getContext(),
                userId);
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setName("abc");
        contactsInfo.setContactId("111");
        contactsInfo.setContactMode(ContactsInfo.ContactMode.system);
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("13800001111");
        contactsInfo.addNumber(numberInfo);
        contactsInfoList.add(contactsInfo);
        dbAdapter.add(contactsInfoList);

        assertNotNull(dbAdapter.queryAll());

        dbAdapter.delByContactId("111");
        assertTrue(dbAdapter.queryAll().isEmpty());
    }

    public void testDelAll() throws Exception {
        ContactsDbAdapter dbAdapter = ContactsDbAdapter.getInstance(getContext(),
                userId);
        assertNotNull(dbAdapter);
        dbAdapter.delAll();

        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setName("abc");
        contactsInfo.setContactId("111");
        contactsInfo.setContactMode(ContactsInfo.ContactMode.system);
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("13800001111");
        contactsInfo.addNumber(numberInfo);
        contactsInfoList.add(contactsInfo);
        dbAdapter.add(contactsInfoList);

        assertNotNull(dbAdapter.queryAll());

        dbAdapter.delAll();
        assertTrue(dbAdapter.queryAll().isEmpty());
    }

    public void testGetDbHelper() throws Exception {
        ContactsDbAdapter dbAdapter = ContactsDbAdapter.getInstance(getContext(),
                userId);
        assertNotNull(dbAdapter);
        assertNotNull(dbAdapter.getDbHelper());
    }
}