package com.chinamobile.hejiaqin.business.model.login;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class LoginHistoryTest extends TestCase {

    public void testGetAvatar() throws Exception {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setAvatar("123");
        assertEquals("123", loginHistory.getAvatar());
    }

    public void testSetAvatar() throws Exception {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setAvatar("123");
        assertEquals("123", loginHistory.getAvatar());
    }

    public void testGetLoginid() throws Exception {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setLoginid("123");
        assertEquals("123", loginHistory.getLoginid());
    }

    public void testSetLoginid() throws Exception {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setLoginid("123");
        assertEquals("123", loginHistory.getLoginid());
    }
}