package com.chinamobile.hejiaqin.business.model.contacts.req;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class EditContactReqTest extends TestCase {

    public void testToBody() throws Exception {
        EditContactReq editContactReq = new EditContactReq();
        assertNotNull(editContactReq.toBody());
    }

    public void testGetFile() throws Exception {
        EditContactReq editContactReq = new EditContactReq();
        editContactReq.setFile("123");
        assertEquals("123", editContactReq.getFile());
    }

    public void testSetFile() throws Exception {
        EditContactReq editContactReq = new EditContactReq();
        editContactReq.setFile("123");
        assertEquals("123", editContactReq.getFile());
    }

    public void testGetPhone() throws Exception {
        EditContactReq editContactReq = new EditContactReq();
        editContactReq.setPhone("123");
        assertEquals("123", editContactReq.getPhone());
    }

    public void testSetPhone() throws Exception {
        EditContactReq editContactReq = new EditContactReq();
        editContactReq.setPhone("123");
        assertEquals("123", editContactReq.getPhone());
    }

    public void testGetName() throws Exception {
        EditContactReq editContactReq = new EditContactReq();
        editContactReq.setName("123");
        assertEquals("123", editContactReq.getName());
    }

    public void testSetName() throws Exception {
        EditContactReq editContactReq = new EditContactReq();
        editContactReq.setName("123");
        assertEquals("123", editContactReq.getName());
    }

    public void testGetContactId() throws Exception {
        EditContactReq editContactReq = new EditContactReq();
        editContactReq.setContactId("123");
        assertEquals("123", editContactReq.getContactId());
    }

    public void testSetContactId() throws Exception {
        EditContactReq editContactReq = new EditContactReq();
        editContactReq.setContactId("123");
        assertEquals("123", editContactReq.getContactId());
    }
}