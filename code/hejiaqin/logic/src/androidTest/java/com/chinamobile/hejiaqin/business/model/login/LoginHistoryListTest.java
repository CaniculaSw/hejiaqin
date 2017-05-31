package com.chinamobile.hejiaqin.business.model.login;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class LoginHistoryListTest extends TestCase {

    public void testGetHistories() throws Exception {
        LoginHistoryList loginHistoryList = new LoginHistoryList();

        List<LoginHistory> loginHistories = new ArrayList<>();
        loginHistoryList.setHistories(loginHistories);
        assertEquals(loginHistories, loginHistoryList.getHistories());
    }

    public void testSetHistories() throws Exception {
        LoginHistoryList loginHistoryList = new LoginHistoryList();

        List<LoginHistory> loginHistories = new ArrayList<>();
        loginHistoryList.setHistories(loginHistories);
        assertEquals(loginHistories, loginHistoryList.getHistories());
    }
}