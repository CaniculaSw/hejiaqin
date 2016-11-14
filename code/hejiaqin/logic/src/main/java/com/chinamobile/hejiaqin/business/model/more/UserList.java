package com.chinamobile.hejiaqin.business.model.more;


import com.chinamobile.hejiaqin.business.model.login.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/11/14.
 */
public class UserList {
    private List<UserInfo> users = new ArrayList<UserInfo>();

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        if (users != null)
            this.users.addAll(users);
    }
}
