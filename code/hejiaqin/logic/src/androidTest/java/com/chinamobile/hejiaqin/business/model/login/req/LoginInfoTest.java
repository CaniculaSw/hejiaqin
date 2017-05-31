package com.chinamobile.hejiaqin.business.model.login.req;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class LoginInfoTest extends TestCase {

    public void testGetPhone() throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setPhone("123");
        assertEquals("123", loginInfo.getPhone());
    }

    public void testSetPhone() throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setPhone("123");
        assertEquals("123", loginInfo.getPhone());
    }

    public void testGetPassword() throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setPassword("123");
        assertEquals("123", loginInfo.getPassword());
    }

    public void testSetPassword() throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setPassword("123");
        assertEquals("123", loginInfo.getPassword());
    }

    public void testToBody() throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setPhone("123");
        loginInfo.setPassword("123");
        assertNotNull(loginInfo.toBody());
    }
}