package com.chinamobile.hejiaqin.business.ui.dial;

import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class DialHelperTest extends TestCase {
    public void testIsPhoneCall() {
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setName("abc");
        contactsInfo.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfo.setContactId("001");
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("137123412345");
        numberInfo.setType(1);
        contactsInfo.addNumber(numberInfo);
        assertTrue(DialHelper.getInstance().isPhoneCall(contactsInfo));
    }
}
