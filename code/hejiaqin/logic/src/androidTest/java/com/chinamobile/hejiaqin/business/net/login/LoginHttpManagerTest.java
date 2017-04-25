package com.chinamobile.hejiaqin.business.net.login;

import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.TestConfig;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.customer.framework.component.net.NetResponse;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class LoginHttpManagerTest extends AndroidTestCase {
    private volatile boolean waitingFlag = true;

    public void testGetVerifyCode() throws Exception {

    }

    public void testGetResetPasswordCode() throws Exception {

    }

    public void testCheckVerifyCode() throws Exception {

    }

    public void testCheckResetPasswordCode() throws Exception {

    }

    public void testRegisterSecondStep() throws Exception {

    }

    public void testLogin() throws Exception {
        LoginInfo loginInfo = TestConfig.getInstance().getLoginInfo();
        new LoginHttpManager(getContext()).login(null, loginInfo, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                UserInfo userInfo = (UserInfo) obj;
                assertNotNull(userInfo);

                Date now = new Date();
                UserInfoCacheManager.saveUserToMem(getContext(), userInfo, now.getTime());
                UserInfoCacheManager.saveUserToLoacl(getContext(), userInfo, now.getTime());
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("login failed: " + code + ", " + desc, false);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("login failed, network error " + errorCode, false);
                cancelWait();
            }
        });

        syncWait();

    }

    public void testTvLogin() throws Exception {

    }

    public void testCheckTvAccount() throws Exception {

    }

    public void testLogout() throws Exception {

    }

    public void testUpdatePassword() throws Exception {

    }

    public void testUpdatePhoto() throws Exception {

    }

    public void testFeedBack() throws Exception {

    }

    public void testGetUserInfo() throws Exception {

    }

    private void syncWait() {
        waitingFlag = true;
        while (waitingFlag) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
        }
    }

    private void cancelWait() {
        waitingFlag = false;
    }
}