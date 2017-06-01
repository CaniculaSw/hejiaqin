package com.chinamobile.hejiaqin.business.model.contacts.rsp;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class ContactBeanTest extends TestCase {

    public void testGetContactId() throws Exception {
        ContactBean contactBean = new ContactBean();
        contactBean.setContactId("123");
        assertEquals("123", contactBean.getContactId());
    }

    public void testSetContactId() throws Exception {
        ContactBean contactBean = new ContactBean();
        contactBean.setContactId("123");
        assertEquals("123", contactBean.getContactId());
    }

    public void testGetName() throws Exception {
        ContactBean contactBean = new ContactBean();
        contactBean.setName("123");
        assertEquals("123", contactBean.getName());
    }

    public void testSetName() throws Exception {
        ContactBean contactBean = new ContactBean();
        contactBean.setName("123");
        assertEquals("123", contactBean.getName());
    }

    public void testGetPhone() throws Exception {
        ContactBean contactBean = new ContactBean();
        contactBean.setPhone("123");
        assertEquals("123", contactBean.getPhone());
    }

    public void testSetPhone() throws Exception {
        ContactBean contactBean = new ContactBean();
        contactBean.setPhone("123");
        assertEquals("123", contactBean.getPhone());
    }

    public void testGetPhotoLg() throws Exception {
        ContactBean contactBean = new ContactBean();
        contactBean.setPhotoLg("123");
        assertEquals("123", contactBean.getPhotoLg());
    }

    public void testSetPhotoLg() throws Exception {
        ContactBean contactBean = new ContactBean();
        contactBean.setPhotoLg("123");
        assertEquals("123", contactBean.getPhotoLg());
    }

    public void testGetPhotoSm() throws Exception {
        ContactBean contactBean = new ContactBean();
        contactBean.setPhotoSm("123");
        assertEquals("123", contactBean.getPhotoSm());
    }

    public void testSetPhotoSm() throws Exception {
        ContactBean contactBean = new ContactBean();
        contactBean.setPhotoSm("123");
        assertEquals("123", contactBean.getPhotoSm());
    }
}