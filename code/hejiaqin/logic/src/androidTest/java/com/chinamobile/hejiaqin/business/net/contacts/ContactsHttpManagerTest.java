package com.chinamobile.hejiaqin.business.net.contacts;

import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.TestConfig;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.contacts.req.AddContactReq;
import com.chinamobile.hejiaqin.business.model.contacts.req.SimpleContactInfo;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.NVPWithTokenReqBody;
import com.chinamobile.hejiaqin.business.net.login.LoginHttpManager;
import com.customer.framework.component.net.NetResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class ContactsHttpManagerTest extends AndroidTestCase {

    private volatile boolean waitingFlag = true;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // 优先进行登录
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

    public void testList() throws Exception {
        NVPWithTokenReqBody reqBody = new NVPWithTokenReqBody();

        new ContactsHttpManager(getContext()).list(null, reqBody, new IHttpCallBack() {
            /**
             * 网络请求成功响应
             *
             * @param invoker 调用者(用来区分不同的调用场景，差异化实现回调逻辑)
             * @param obj     服务器响应解析后的对象
             */
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertNotNull(obj);
                assertTrue(true);
                // TODO 暂时不对数据进行校验，这和测试账号下的实际联系人相关
                cancelWait();
            }

            /**
             * 网络请求业务失败响应
             *
             * @param invoker 调用者(用来区分不同的调用场景，差异化实现回调逻辑)
             * @param code    业务错误码
             * @param desc    业务错误描述
             */
            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("failed: " + code + ", " + desc, false);
                cancelWait();
            }

            /**
             * 网络请求网络失败响应
             *
             * @param errorCode 网络错误码
             */
            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("failed, network error " + errorCode, false);
                cancelWait();
            }
        });
        syncWait();
    }

    public void testAdd() throws Exception {

        AddContactReq request = new AddContactReq();
        request.setName("李广");
        request.setPhone("15098765432");
        request.setFile(null);

        new ContactsHttpManager(getContext()).add(null, request, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertNotNull(obj);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("failed: " + code + ", " + desc, false);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("failed, network error " + errorCode, false);
                cancelWait();
            }
        });
        syncWait();
    }

    public void testBatchAdd() throws Exception {
        List<SimpleContactInfo> simpleContactInfoList = new ArrayList<SimpleContactInfo>();
        for (int i = 0; i < 3; i++) {
            SimpleContactInfo simpleContactInfo = new SimpleContactInfo();
            simpleContactInfo.setName("赵四" + i);
            simpleContactInfo.setPhone("1501234123" + i);
            simpleContactInfoList.add(simpleContactInfo);
        }

        Gson gson = new Gson();
        String contactJson = gson.toJson(simpleContactInfoList,
                new TypeToken<List<SimpleContactInfo>>() {
                }.getType());

        NVPWithTokenReqBody reqBody = new NVPWithTokenReqBody();
        reqBody.add("contactJson", contactJson);

        new ContactsHttpManager(getContext()).batchAdd(null, reqBody, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertNotNull(obj);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("failed: " + code + ", " + desc, false);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("failed, network error " + errorCode, false);
                cancelWait();
            }
        });
        syncWait();
    }

    public void testUpdate() throws Exception {

    }

    public void testDelete() throws Exception {

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