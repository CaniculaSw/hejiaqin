package com.chinamobile.hejiaqin.business.net.setting;

import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.login.RespondInfo;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.more.UserList;
import com.chinamobile.hejiaqin.business.model.more.VersionInfo;
import com.chinamobile.hejiaqin.business.model.more.req.GetBindListReq;
import com.chinamobile.hejiaqin.business.model.more.req.GetDeviceListReq;
import com.chinamobile.hejiaqin.business.model.more.req.SaveBindRequest;
import com.chinamobile.hejiaqin.business.model.more.req.TestAdaptReq;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.XmlParseUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class SettingHttpmanagerTest extends AndroidTestCase {
    private volatile boolean waitingFlag = true;

    public void testCheckVersion() throws Exception {
        new SettingHttpmanager(getContext()).checkVersion(null, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("check version failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("check version failed, network error " + errorCode, true);
                cancelWait();
            }
        });

        syncWait();
    }

    public void testGetDeviceList() throws Exception {
        final GetDeviceListReq reqBody = new GetDeviceListReq();
        new SettingHttpmanager(getContext()).getDeviceList(null, reqBody, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("get device list failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("get device list failed, network error " + errorCode, true);
                cancelWait();
            }
        });

        syncWait();
    }

    public void testGetBindList() throws Exception {
        final GetBindListReq reqBody = new GetBindListReq();
        new SettingHttpmanager(getContext()).getBindList(null, reqBody, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("get bind list failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("get bind list failed, network error " + errorCode, true);
                cancelWait();
            }
        });

        syncWait();
    }

    public void testSaveBindRequest() throws Exception {
        SaveBindRequest req = new SaveBindRequest();
        // TODO
        req.setPhone(XmlParseUtil.getElemString("aaa", "Phone"));
        new SettingHttpmanager(getContext()).saveBindRequest(null, req, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("save bind list failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("save bind list failed, network error " + errorCode, true);
                cancelWait();
            }
        });

        syncWait();
    }

    public void testTestAdapt() throws Exception {
        final TestAdaptReq req = new TestAdaptReq();
        req.setVersion(UserInfoCacheManager.getSoftware(getContext()));
        new SettingHttpmanager(getContext()).testAdapt(null, req, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                assertTrue("onSuccessful: " + invoker + ", " + obj, true);
                cancelWait();
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                assertTrue("test adapt list failed: " + code + ", " + desc, true);
                cancelWait();
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                assertTrue("test adapt list failed, network error " + errorCode, true);
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