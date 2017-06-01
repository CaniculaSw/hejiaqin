package com.chinamobile.hejiaqin.business.logic.login;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.logic.contacts.ContactsLogic;
import com.chinamobile.hejiaqin.business.model.login.req.FeedBackReq;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.business.model.login.req.PasswordInfo;
import com.chinamobile.hejiaqin.business.model.login.req.RegisterSecondStepInfo;
import com.chinamobile.hejiaqin.business.model.login.req.TvLoginInfo;
import com.chinamobile.hejiaqin.business.model.login.req.UpdatePhotoReq;
import com.chinamobile.hejiaqin.business.model.login.req.VerifyInfo;
import com.chinamobile.hejiaqin.business.model.more.VersionInfo;
import com.chinamobile.hejiaqin.business.net.NVPWithTokenReqBody;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class LoginLogicTest extends AndroidTestCase {
    private volatile boolean waitingFlag = true;

    LoginLogic contactsLogic;
    Handler handler;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        contactsLogic = new LoginLogic();
        contactsLogic.init(getContext());
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                cancelWait();
            }
        };
        contactsLogic.addHandler(handler);
    }

    public void testGetVerifyCode() throws Exception {
        contactsLogic.getVerifyCode("123456");
        syncWait();
    }

    public void testGetResetPasswordCode() throws Exception {
        contactsLogic.getResetPasswordCode("123456");
        syncWait();
    }

    public void testCheckVerifyCode() throws Exception {
        VerifyInfo verifyInfo = new VerifyInfo();
        verifyInfo.setVerifyCode("123456");
        verifyInfo.setPhone("123456");
        contactsLogic.checkVerifyCode(verifyInfo);
        syncWait();
    }

    public void testCheckResetPasswordCode() throws Exception {
        VerifyInfo verifyInfo = new VerifyInfo();
        verifyInfo.setVerifyCode("123456");
        verifyInfo.setPhone("123456");
        contactsLogic.checkResetPasswordCode(verifyInfo);
        syncWait();
    }

    public void testRegisterSecondStep() throws Exception {
        RegisterSecondStepInfo registerSecondStepInfo = new RegisterSecondStepInfo();
        registerSecondStepInfo.setPhone("123456");
        registerSecondStepInfo.setPwd("123456");
        registerSecondStepInfo.setCode("123456");
        contactsLogic.registerSecondStep(registerSecondStepInfo);
        syncWait();
    }

    public void testLogin() throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setPhone("123456");
        loginInfo.setPassword("123456");
        contactsLogic.login(loginInfo);
        syncWait();
    }

    public void testTvLogin() throws Exception {
        TvLoginInfo loginInfo = new TvLoginInfo();
        loginInfo.setTvId("123456");
        loginInfo.setTvToken("123456");
        contactsLogic.tvLogin(loginInfo);
        syncWait();
    }

    public void testCheckTvAccount() throws Exception {
        TvLoginInfo loginInfo = new TvLoginInfo();
        loginInfo.setTvId("123456");
        loginInfo.setTvToken("123456");
        contactsLogic.checkTvAccount(loginInfo);
        syncWait();
    }

    public void testLogout() throws Exception {
        contactsLogic.logout();
    }

    public void testHasLogined() throws Exception {
        contactsLogic.hasLogined();
    }

    public void testLoadUserFromLocal() throws Exception {
        contactsLogic.loadUserFromLocal();
    }

    public void testLoadHistoryFromLocal() throws Exception {
        contactsLogic.loadHistoryFromLocal();
    }

    public void testEncryPassword() throws Exception {
        contactsLogic.encryPassword("123456");
    }

    public void testGetLoginHistory() throws Exception {
        contactsLogic.getLoginHistory("123456");
    }

    public void testGetUserInfo() throws Exception {
        NVPWithTokenReqBody nvpWithTokenReqBody = new NVPWithTokenReqBody();
        contactsLogic.getUserInfo(nvpWithTokenReqBody);
        syncWait();
    }

    public void testUpdatePassword() throws Exception {
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setPassword("123456");
        passwordInfo.setResetToken("123456");
        contactsLogic.updatePassword(passwordInfo);
        syncWait();
    }

    public void testInitCMIMSdk() throws Exception {
        try {
            contactsLogic.initCMIMSdk();
        } catch (Exception e) {

        }
    }

    public void testUpdatePhoto() throws Exception {
        UpdatePhotoReq updatePhotoReq = new UpdatePhotoReq();
        updatePhotoReq.setFile("123");
        contactsLogic.updatePhoto(updatePhotoReq);
        syncWait();
    }

    public void testFeedBack() throws Exception {
        FeedBackReq feedBackReq = new FeedBackReq();
        feedBackReq.setContent("123456778");
        contactsLogic.feedBack(feedBackReq);
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