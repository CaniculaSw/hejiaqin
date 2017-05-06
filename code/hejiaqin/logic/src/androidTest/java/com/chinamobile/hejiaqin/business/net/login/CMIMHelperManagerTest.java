package com.chinamobile.hejiaqin.business.net.login;

import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.TestConfig;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.utils.LogUtil;
import com.littlec.sdk.manager.CMIMHelper;
import com.littlec.sdk.utils.CMChatListener;


import java.util.Date;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class CMIMHelperManagerTest extends AndroidTestCase {
    private volatile boolean waitingFlag = true;

    public void testDoLogin() throws Exception {
        LoginInfo loginInfo = TestConfig.getInstance().getLoginInfo();
        new LoginHttpManager(getContext()).login(null, loginInfo, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                UserInfo userInfo = (UserInfo) obj;
                assertNotNull(userInfo);

                CMIMHelper.getCmAccountManager().doLogin(userInfo.getImAccount(), userInfo.getImPassword(),
                        new CMChatListener.OnCMListener() {
                            @Override
                            public void onSuccess() {
                                cancelWait();
                            }

                            @Override
                            public void onFailed(String s) {
                                assertTrue("login failed: " + s, false);
                                cancelWait();
                            }
                        });
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