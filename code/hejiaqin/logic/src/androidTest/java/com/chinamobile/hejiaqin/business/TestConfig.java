package com.chinamobile.hejiaqin.business;

import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class TestConfig {
    private static TestConfig ourInstance = new TestConfig();

    public static TestConfig getInstance() {
        return ourInstance;
    }

    private TestConfig() {
    }

    public LoginInfo getLoginInfo() {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setPhone("13512341234");
        loginInfo.setPassword("123456");
        return loginInfo;
    }

}
