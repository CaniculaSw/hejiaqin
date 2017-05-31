package com.chinamobile.hejiaqin.business.model.login;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class UserInfoTest extends TestCase {

    public void testGetUserId() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("123");
        assertEquals("123", userInfo.getUserId());
    }

    public void testSetUserId() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("123");
        assertEquals("123", userInfo.getUserId());
    }

    public void testGetUserName() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("123");
        assertEquals("123", userInfo.getUserName());
    }

    public void testSetUserName() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("123");
        assertEquals("123", userInfo.getUserName());
    }

    public void testGetPhone() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone("123");
        assertEquals("123", userInfo.getPhone());
    }

    public void testSetPhone() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone("123");
        assertEquals("123", userInfo.getPhone());
    }

    public void testGetSdkAccount() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setSdkAccount("123");
        assertEquals("123", userInfo.getSdkAccount());
    }

    public void testSetSdkAccount() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setSdkAccount("123");
        assertEquals("123", userInfo.getSdkAccount());
    }

    public void testGetSdkPassword() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setSdkPassword("123");
        assertEquals("123", userInfo.getSdkPassword());
    }

    public void testSetSdkPassword() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setSdkPassword("123");
        assertEquals("123", userInfo.getSdkPassword());
    }

    public void testGetImAccount() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setImAccount("123");
        assertEquals("123", userInfo.getImAccount());
    }

    public void testSetImAccount() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setImAccount("123");
        assertEquals("123", userInfo.getImAccount());
    }

    public void testGetImPassword() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setImPassword("123");
        assertEquals("123", userInfo.getImPassword());
    }

    public void testSetImPassword() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setImPassword("123");
        assertEquals("123", userInfo.getImPassword());
    }

    public void testGetToken() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setToken("123");
        assertEquals("123", userInfo.getToken());
    }

    public void testSetToken() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setToken("123");
        assertEquals("123", userInfo.getToken());
    }

    public void testGetTokenExpire() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setTokenExpire("123");
        assertEquals("123", userInfo.getTokenExpire());
    }

    public void testSetTokenExpire() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setTokenExpire("123");
        assertEquals("123", userInfo.getTokenExpire());
    }

    public void testGetType() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setType("123");
        assertEquals("123", userInfo.getType());
    }

    public void testSetType() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setType("123");
        assertEquals("123", userInfo.getType());
    }

    public void testGetPhotoLg() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhotoLg("123");
        assertEquals("123", userInfo.getPhotoLg());
    }

    public void testSetPhotoLg() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhotoLg("123");
        assertEquals("123", userInfo.getPhotoLg());
    }

    public void testGetPhotoSm() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhotoSm("123");
        assertEquals("123", userInfo.getPhotoSm());
    }

    public void testSetPhotoSm() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhotoSm("123");
        assertEquals("123", userInfo.getPhotoSm());
    }

    public void testGetTvAccount() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setTvAccount("123");
        assertEquals("123", userInfo.getTvAccount());
    }

    public void testSetTvAccount() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setTvAccount("123");
        assertEquals("123", userInfo.getTvAccount());
    }

    public void testGetName() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("123");
        assertEquals("123", userInfo.getName());
    }

    public void testSetName() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("123");
        assertEquals("123", userInfo.getName());
    }

    public void testToString() throws Exception {
        UserInfo userInfo = new UserInfo();
        assertNotNull(userInfo.toString());
    }
}