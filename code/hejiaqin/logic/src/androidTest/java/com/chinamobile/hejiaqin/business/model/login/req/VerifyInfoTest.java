package com.chinamobile.hejiaqin.business.model.login.req;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class VerifyInfoTest extends TestCase {

    public void testGetPhone() throws Exception {
        VerifyInfo verifyInfo = new VerifyInfo();
        verifyInfo.setPhone("123");
        assertEquals("123", verifyInfo.getPhone());
    }

    public void testSetPhone() throws Exception {
        VerifyInfo verifyInfo = new VerifyInfo();
        verifyInfo.setPhone("123");
        assertEquals("123", verifyInfo.getPhone());
    }

    public void testGetVerifyCode() throws Exception {
        VerifyInfo verifyInfo = new VerifyInfo();
        verifyInfo.setVerifyCode("123");
        assertEquals("123", verifyInfo.getVerifyCode());
    }

    public void testSetVerifyCode() throws Exception {
        VerifyInfo verifyInfo = new VerifyInfo();
        verifyInfo.setVerifyCode("123");
        assertEquals("123", verifyInfo.getVerifyCode());
    }

    public void testToBody() throws Exception {
        VerifyInfo verifyInfo = new VerifyInfo();
        verifyInfo.setVerifyCode("123");
        assertNotNull(verifyInfo.toBody());
    }
}