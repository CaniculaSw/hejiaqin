package com.chinamobile.hejiaqin.business.net.login;

import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.TestConfig;
import com.chinamobile.hejiaqin.business.model.login.req.FeedBackReq;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.business.model.login.req.PasswordInfo;
import com.chinamobile.hejiaqin.business.model.login.req.RegisterSecondStepInfo;
import com.chinamobile.hejiaqin.business.model.login.req.TvLoginInfo;
import com.chinamobile.hejiaqin.business.model.login.req.VerifyInfo;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.NVPWithTokenReqBody;
import com.customer.framework.component.net.NetResponse;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class LoginHttpManagerTest extends AndroidTestCase {
    private volatile boolean waitingFlag = true;

    public void testGetVerifyCode() throws Exception {
        LoginInfo loginInfo = TestConfig.getInstance().getLoginInfo();

        final NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("phone", loginInfo.getPhone());
        new LoginHttpManager(getContext()).getVerifyCode(null, reqBody, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("get verify code failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("get verify code failed, network error " + errorCode, true);
                cancelWait();
            }
        });
        syncWait();


    }

    public void testGetResetPasswordCode() throws Exception {
        LoginInfo loginInfo = TestConfig.getInstance().getLoginInfo();

        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("phone", loginInfo.getPhone());
        new LoginHttpManager(getContext()).getResetPasswordCode(null, reqBody, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("get reset password code failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("get reset password code failed, network error " + errorCode, true);
                cancelWait();
            }
        });
        syncWait();
    }

    public void testCheckVerifyCode() throws Exception {
        LoginInfo loginInfo = TestConfig.getInstance().getLoginInfo();
        VerifyInfo info = new VerifyInfo();
        info.setPhone(loginInfo.getPhone());
        info.setVerifyCode(TestConfig.getInstance().getVerifyCode());
        new LoginHttpManager(getContext()).checkVerifyCode(null, info, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("check verify code failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("check verify code failed, network error " + errorCode, true);
                cancelWait();
            }
        });
        syncWait();
    }

    public void testCheckResetPasswordCode() throws Exception {
        LoginInfo loginInfo = TestConfig.getInstance().getLoginInfo();
        VerifyInfo info = new VerifyInfo();
        info.setPhone(loginInfo.getPhone());
        info.setVerifyCode(TestConfig.getInstance().getVerifyCode());

        new LoginHttpManager(getContext()).checkResetPasswordCode(null, info,
                new IHttpCallBack() {

                    @Override
                    public void onSuccessful(Object invoker, Object obj) {
                        assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                        cancelWait();
                    }

                    @Override
                    public void onFailure(Object invoker, String code, String desc) {
                        assertTrue("check reset password code failed: " + code + ", " + desc, true);
                        cancelWait();
                    }

                    @Override
                    public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                        assertTrue("check reset password code failed, network error " + errorCode, true);
                        cancelWait();
                    }
                });
        syncWait();
    }

    public void testRegisterSecondStep() throws Exception {
        RegisterSecondStepInfo registerSecondStepInfo = new RegisterSecondStepInfo();
        registerSecondStepInfo.setPhone("18712341234");
        registerSecondStepInfo.setCode(TestConfig.getInstance().getVerifyCode());
        registerSecondStepInfo.setPwd("123456");

        new LoginHttpManager(getContext()).registerSecondStep(null, registerSecondStepInfo, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("register second step failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("register second step failed, network error " + errorCode, true);
                cancelWait();
            }
        });
        syncWait();
    }

    public void testLogin() throws Exception {
        LoginInfo loginInfo = TestConfig.getInstance().getLoginInfo();
        new LoginHttpManager(getContext()).login(null, loginInfo, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("login failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("login failed, network error " + errorCode, true);
                cancelWait();
            }
        });

        syncWait();

    }

    public void testTvLogin() throws Exception {

        TvLoginInfo loginInfo = TestConfig.getInstance().getTvLoginInfo();

        new LoginHttpManager(getContext()).tvLogin(null, loginInfo, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("tv login failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("tv login failed, network error " + errorCode, true);
                cancelWait();
            }
        });

        syncWait();
    }

    public void testCheckTvAccount() throws Exception {
        TvLoginInfo loginInfo = TestConfig.getInstance().getTvLoginInfo();

        new LoginHttpManager(getContext()).checkTvAccount(null, loginInfo, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("check tv account failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("check tv account failed, network error " + errorCode, true);
                cancelWait();
            }
        });
        syncWait();
    }

    public void testLogout() throws Exception {
        new LoginHttpManager(getContext()).logout(null, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("logout failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("logout failed, network error " + errorCode, true);
                cancelWait();
            }
        });

        syncWait();
    }

    public void testUpdatePassword() throws Exception {
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setPassword("abcd123456");

        new LoginHttpManager(getContext()).updatePassword(null, passwordInfo, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("update password failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("update password failed, network error " + errorCode, true);
                cancelWait();
            }
        });

        syncWait();
    }

    public void testUpdatePhoto() throws Exception {

    }

    public void testFeedBack() throws Exception {
        FeedBackReq reqBody = new FeedBackReq();
        reqBody.setContent("i am feed back...");
        new LoginHttpManager(getContext()).feedBack(null, reqBody, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("feed back failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("feed back failed, network error " + errorCode, true);
                cancelWait();
            }
        });

        syncWait();
    }

    public void testGetUserInfo() throws Exception {
        NVPWithTokenReqBody reqBody = new NVPWithTokenReqBody();
        new LoginHttpManager(getContext()).getUserInfo(null, reqBody, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("get user info failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("get user info failed, network error " + errorCode, true);
                cancelWait();
            }
        });

        syncWait();
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