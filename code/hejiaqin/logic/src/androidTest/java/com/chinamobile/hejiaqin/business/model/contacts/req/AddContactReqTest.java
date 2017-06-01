package com.chinamobile.hejiaqin.business.model.contacts.req;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class AddContactReqTest extends TestCase {

    public void testToBody() throws Exception {
        AddContactReq addContactReq = new AddContactReq();
        assertNotNull(addContactReq.toBody());
    }

    public void testGetFile() throws Exception {
        AddContactReq addContactReq = new AddContactReq();
        addContactReq.setFile("123");
        assertEquals("123", addContactReq.getFile());
    }

    public void testSetFile() throws Exception {
        AddContactReq addContactReq = new AddContactReq();
        addContactReq.setFile("123");
        assertEquals("123", addContactReq.getFile());
    }

    public void testGetPhone() throws Exception {
        AddContactReq addContactReq = new AddContactReq();
        addContactReq.setPhone("123");
        assertEquals("123", addContactReq.getPhone());
    }

    public void testSetPhone() throws Exception {
        AddContactReq addContactReq = new AddContactReq();
        addContactReq.setPhone("123");
        assertEquals("123", addContactReq.getPhone());
    }

    public void testGetName() throws Exception {
        AddContactReq addContactReq = new AddContactReq();
        addContactReq.setName("123");
        assertEquals("123", addContactReq.getName());
    }

    public void testSetName() throws Exception {
        AddContactReq addContactReq = new AddContactReq();
        addContactReq.setName("123");
        assertEquals("123", addContactReq.getName());
    }
}