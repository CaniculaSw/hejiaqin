package com.chinamobile.hejiaqin.business.model.more;

import com.chinamobile.hejiaqin.business.model.login.UserInfo;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class UserListTest extends TestCase {

    public void testGetUsers() throws Exception {
        UserList userList = new UserList();
        List<UserInfo> userInfoList = new ArrayList<>();
        userList.setUsers(userInfoList);
        assertEquals(userInfoList, userList.getUsers());
    }

    public void testSetUsers() throws Exception {
        UserList userList = new UserList();
        List<UserInfo> userInfoList = new ArrayList<>();
        userList.setUsers(userInfoList);
        assertEquals(userInfoList, userList.getUsers());
    }
}