package com.chinamobile.hejiaqin.business.model.more.req;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class SaveBindRequestTest extends TestCase {

    public void testGetPhone() throws Exception {
        SaveBindRequest saveBindRequest = new SaveBindRequest();
        saveBindRequest.setPhone("123");
        assertEquals("123", saveBindRequest.getPhone());
    }

    public void testSetPhone() throws Exception {
        SaveBindRequest saveBindRequest = new SaveBindRequest();
        saveBindRequest.setPhone("123");
        assertEquals("123", saveBindRequest.getPhone());
    }

    public void testToBody() throws Exception {
        SaveBindRequest saveBindRequest = new SaveBindRequest();
        saveBindRequest.setPhone("123");
        assertNotNull(saveBindRequest.toBody());
    }
}