package com.chinamobile.hejiaqin.business.model.contacts;

import android.provider.ContactsContract;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class NumberInfoTest extends TestCase {

    public void testGetNumber() throws Exception {
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("aaa");
        assertEquals("aaa", numberInfo.getNumber());
    }

    public void testSetNumber() throws Exception {
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("aaa");
        assertEquals("aaa", numberInfo.getNumber());
    }

    public void testGetNumberNoCountryCode() throws Exception {
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("aaa");
        assertNotNull(numberInfo.getNumberNoCountryCode());
    }

    public void testGetType() throws Exception {
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setType(1);
        assertEquals(1, numberInfo.getType());
    }

    public void testSetType() throws Exception {
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setType(1);
        assertEquals(1, numberInfo.getType());
    }

    public void testGetDesc() throws Exception {
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setType(ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
        assertEquals("家庭电话", numberInfo.getDesc());
        numberInfo.setType(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        assertEquals("移动电话", numberInfo.getDesc());
    }

    public void testIsMatch() throws Exception {
        NumberInfo numberInfo = new NumberInfo();
        assertTrue(numberInfo.isMatch(""));
        numberInfo.setNumber("abc");
        assertTrue(numberInfo.isMatch("a"));
        assertFalse(numberInfo.isMatch("d"));
    }

    public void testToString() throws Exception {
        NumberInfo numberInfo = new NumberInfo();
        assertNotNull(numberInfo.toString());
    }
}