package com.chinamobile.hejiaqin.business.model.contacts.req;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class SimpleContactInfoTest extends TestCase {

    public void testGetName() throws Exception {
        SimpleContactInfo simpleContactInfo = new SimpleContactInfo();
        simpleContactInfo.setName("123");
        assertEquals("123", simpleContactInfo.getName());
    }

    public void testSetName() throws Exception {
        SimpleContactInfo simpleContactInfo = new SimpleContactInfo();
        simpleContactInfo.setName("123");
        assertEquals("123", simpleContactInfo.getName());
    }

    public void testGetPhone() throws Exception {
        SimpleContactInfo simpleContactInfo = new SimpleContactInfo();
        simpleContactInfo.setPhone("123");
        assertEquals("123", simpleContactInfo.getPhone());
    }

    public void testSetPhone() throws Exception {
        SimpleContactInfo simpleContactInfo = new SimpleContactInfo();
        simpleContactInfo.setPhone("123");
        assertEquals("123", simpleContactInfo.getPhone());
    }
}