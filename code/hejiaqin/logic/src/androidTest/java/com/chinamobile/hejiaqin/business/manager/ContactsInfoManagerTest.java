package com.chinamobile.hejiaqin.business.manager;

import android.content.Context;
import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class ContactsInfoManagerTest extends AndroidTestCase {

    public void testSortContactsInfoLst() throws Exception {
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        ContactsInfo contactsInfo1 = new ContactsInfo();
        contactsInfo1.setContactId("001");
        contactsInfo1.setName("cContact");
        contactsInfo1.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfoList.add(contactsInfo1);

        ContactsInfo contactsInfo2 = new ContactsInfo();
        contactsInfo2.setContactId("002");
        contactsInfo2.setName("bContact");
        contactsInfo2.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfoList.add(contactsInfo2);

        ContactsInfo contactsInfo3 = new ContactsInfo();
        contactsInfo3.setContactId("003");
        contactsInfo3.setName("aContact");
        contactsInfo3.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfoList.add(contactsInfo3);

        ContactsInfo contactsInfo4 = new ContactsInfo();
        contactsInfo4.setContactId("004");
        contactsInfo4.setName("dContact");
        contactsInfo4.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfoList.add(contactsInfo4);

        Context context = getContext();
        assertNotNull(context);
        ContactsInfoManager.getInstance().sortContactsInfoLst(context, contactsInfoList);

        assertEquals("aContact", contactsInfoList.get(0).getName());
        assertEquals("bContact", contactsInfoList.get(1).getName());
        assertEquals("cContact", contactsInfoList.get(2).getName());
        assertEquals("dContact", contactsInfoList.get(3).getName());
    }

    public void testCacheLocalContactInfo() throws Exception {
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        ContactsInfo contactsInfo1 = new ContactsInfo();
        contactsInfo1.setContactId("001");
        contactsInfo1.setName("contact1");
        contactsInfo1.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfoList.add(contactsInfo1);

        ContactsInfo contactsInfo2 = new ContactsInfo();
        contactsInfo2.setContactId("002");
        contactsInfo2.setName("contact2");
        contactsInfo2.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfoList.add(contactsInfo2);

        ContactsInfo contactsInfo3 = new ContactsInfo();
        contactsInfo3.setContactId("003");
        contactsInfo3.setName("contact");
        contactsInfo3.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfoList.add(contactsInfo3);

        ContactsInfo contactsInfo4 = new ContactsInfo();
        contactsInfo4.setContactId("004");
        contactsInfo4.setName("contact4");
        contactsInfo4.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfoList.add(contactsInfo4);
        ContactsInfoManager.getInstance().cacheLocalContactInfo(contactsInfoList);

        List<ContactsInfo> cachedContactsInfoList = ContactsInfoManager.getInstance().getCachedLocalContactInfo();
        assertNotNull(cachedContactsInfoList);
        assertEquals(cachedContactsInfoList.size(), 4);
    }

    public void testCacheAppContactInfo() throws Exception {
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        ContactsInfo contactsInfo1 = new ContactsInfo();
        contactsInfo1.setContactId("001");
        contactsInfo1.setName("contact1");
        contactsInfo1.setContactMode(ContactsInfo.ContactMode.app);
        contactsInfoList.add(contactsInfo1);

        ContactsInfo contactsInfo2 = new ContactsInfo();
        contactsInfo2.setContactId("002");
        contactsInfo2.setName("contact2");
        contactsInfo2.setContactMode(ContactsInfo.ContactMode.app);
        contactsInfoList.add(contactsInfo2);

        ContactsInfo contactsInfo3 = new ContactsInfo();
        contactsInfo3.setContactId("003");
        contactsInfo3.setName("contact");
        contactsInfo3.setContactMode(ContactsInfo.ContactMode.app);
        contactsInfoList.add(contactsInfo3);

        ContactsInfo contactsInfo4 = new ContactsInfo();
        contactsInfo4.setContactId("004");
        contactsInfo4.setName("contact4");
        contactsInfo4.setContactMode(ContactsInfo.ContactMode.app);
        contactsInfoList.add(contactsInfo4);
        ContactsInfoManager.getInstance().cacheAppContactInfo(contactsInfoList);

        List<ContactsInfo> cachedContactsInfoList = ContactsInfoManager.getInstance().getCachedAppContactInfo();
        assertNotNull(cachedContactsInfoList);
        assertEquals(cachedContactsInfoList.size(), 4);
    }


    public void testSearchContactsInfoLst() throws Exception {
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        ContactsInfo contactsInfo1 = new ContactsInfo();
        contactsInfo1.setContactId("001");
        contactsInfo1.setName("cContact");
        contactsInfo1.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfoList.add(contactsInfo1);

        ContactsInfo contactsInfo2 = new ContactsInfo();
        contactsInfo2.setContactId("002");
        contactsInfo2.setName("bContact");
        contactsInfo2.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfoList.add(contactsInfo2);

        ContactsInfo contactsInfo3 = new ContactsInfo();
        contactsInfo3.setContactId("003");
        contactsInfo3.setName("aContact");
        contactsInfo3.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfoList.add(contactsInfo3);

        ContactsInfo contactsInfo4 = new ContactsInfo();
        contactsInfo4.setContactId("004");
        contactsInfo4.setName("dContact");
        contactsInfo4.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfoList.add(contactsInfo4);

        Context context = getContext();
        assertNotNull(context);
        ContactsInfoManager.getInstance().sortContactsInfoLst(context, contactsInfoList);

        List<ContactsInfo> hitContactsInfoList
                = ContactsInfoManager.getInstance().searchContactsInfoLst(contactsInfoList, "d");

        assertNotNull(hitContactsInfoList);
        assertEquals(hitContactsInfoList.size(), 1);
        assertEquals("dContact", hitContactsInfoList.get(0).getName());
    }
}