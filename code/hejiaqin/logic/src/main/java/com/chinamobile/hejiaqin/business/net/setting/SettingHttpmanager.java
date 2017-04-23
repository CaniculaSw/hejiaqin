package com.chinamobile.hejiaqin.business.net.setting;

import android.content.Context;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.RespondInfo;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.more.VersionInfo;
import com.chinamobile.hejiaqin.business.model.more.req.GetBindListReq;
import com.chinamobile.hejiaqin.business.model.more.req.GetDeviceListReq;
import com.chinamobile.hejiaqin.business.model.more.req.SaveBindRequest;
import com.chinamobile.hejiaqin.business.model.more.req.TestAdaptReq;
import com.chinamobile.hejiaqin.business.net.AbsHttpManager;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.customer.framework.component.net.NameValuePair;
import com.customer.framework.component.net.NetRequest;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/6/26.
 */
public class SettingHttpmanager extends AbsHttpManager {
    /**
     * 打印标志
     */
    private static final String TAG = "SettingHttpmanager";

    private static final int ACTION_BASE = 0;

    private static final int CHECK_ANDROID_VERSION = ACTION_BASE + 1;
    private static final int GET_DEVICE_LIST = ACTION_BASE + 2;
    private static final int SAVE_BIND_REQUEST = ACTION_BASE + 3;
    private static final int GET_BIND_LIST = ACTION_BASE + 4;
    private static final int TEST_ADAPT = ACTION_BASE + 5;

    /**
     * 请求action
     */
    private int mAction;

    private Context mContext;

    public SettingHttpmanager(Context context) {
        this.mContext = context;
    }

    @Override
    protected Context getContext() {
        return this.mContext;
    }

    @Override
    protected String getUrl() {
        String url = null;
        switch (this.mAction) {
            case CHECK_ANDROID_VERSION:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/version/android";
                break;
            case GET_DEVICE_LIST:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/device/getDeviceList";
                break;
            case SAVE_BIND_REQUEST:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/device/bind";
                break;
            case GET_BIND_LIST:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/device/getBindList";
                break;
            case TEST_ADAPT:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/device/testAdapt";
                break;
            default:
                break;
        }
        return url;
    }

    /**
     * 请求method类型<BR>
     *
     * @return 默认为GET请求
     */
    protected NetRequest.RequestMethod getRequestMethod() {
        NetRequest.RequestMethod method = NetRequest.RequestMethod.GET;
        switch (this.mAction) {
            case CHECK_ANDROID_VERSION:
            case GET_DEVICE_LIST:
            case SAVE_BIND_REQUEST:
            case GET_BIND_LIST:
            case TEST_ADAPT:
                method = NetRequest.RequestMethod.POST;
                break;
            default:
                break;
        }
        return method;
    }

    @Override
    protected boolean isNeedToken() {
        boolean flag = true;
        switch (this.mAction) {
            case CHECK_ANDROID_VERSION:
            case TEST_ADAPT:
                flag = false;
                break;
            default:
                break;
        }
        return flag;
    }

    @Override
    protected List<NameValuePair> getRequestProperties() {
        List<NameValuePair> properties = super.getRequestProperties();
        if (properties == null) {
            properties = new ArrayList<NameValuePair>();
        }
        switch (this.mAction) {
            case CHECK_ANDROID_VERSION:
                //TODO
            case GET_DEVICE_LIST:
                //TODO
            case SAVE_BIND_REQUEST:
                //TODO
            case GET_BIND_LIST:
                //TODO
            case TEST_ADAPT:
                //TODO
            default:
                break;
        }
        return properties;
    }

    @Override
    protected NetRequest.ContentType getContentType() {
        return NetRequest.ContentType.FORM_URLENCODED;
    }


    @Override
    protected Object handleResponse(NetResponse response) {
        Object obj = null;
        if (BussinessConstants.HttpCommonCode.COMMON_SUCCESS_CODE.equals(response.getResultCode())) {
            try {
                String data = "";
                JSONObject rootJsonObj = new JSONObject(response.getData());
                if (rootJsonObj.has("data")) {
                    data = rootJsonObj.get("data").toString();
                } else if (rootJsonObj.has("dataList")) {
                    data = rootJsonObj.get("dataList").toString();
                }
                Gson gson = new Gson();
                switch (this.mAction) {
                    case CHECK_ANDROID_VERSION:
                        obj = gson.fromJson(data, VersionInfo.class);
                        break;
                    case GET_DEVICE_LIST:
                        obj = gson.fromJson(data, new TypeToken<List<UserInfo>>() {
                        }.getType());
                        break;
                    case GET_BIND_LIST:
                        obj = gson.fromJson(data, new TypeToken<List<UserInfo>>() {
                        }.getType());
                        break;
                    case SAVE_BIND_REQUEST:
                        break;
                    case TEST_ADAPT:
                        RespondInfo info = new RespondInfo();
                        info.setData(data);
                        if (rootJsonObj.has("msg")) {
                            info.setMsg(rootJsonObj.get("msg").toString());
                        }
                        if (rootJsonObj.has("code")) {
                            info.setCode(rootJsonObj.get("code").toString());
                        }
                        obj = info;
                        break;
                    default:
                        break;
                }

            } catch (JSONException e) {
                LogUtil.e(TAG, e.toString());
            }
        }
        return obj;
    }
    /***/
    public void checkVersion(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = CHECK_ANDROID_VERSION;
        this.mData = null;
        send(invoker, callBack);
    }
    /***/
    public void getDeviceList(final Object invoker, final GetDeviceListReq req, final IHttpCallBack callBack) {
        this.mAction = GET_DEVICE_LIST;
        this.mData = req;
        send(invoker, callBack);
    }
    /***/
    public void getBindList(final Object invoker, final GetBindListReq req, final IHttpCallBack callBack) {
        this.mAction = GET_BIND_LIST;
        this.mData = req;
        send(invoker, callBack);
    }
    /***/
    public void saveBindRequest(final Object invoker, final SaveBindRequest req, final IHttpCallBack callBack) {
        this.mAction = SAVE_BIND_REQUEST;
        this.mData = req;
        send(invoker, callBack);
    }
    /***/
    public void testAdapt(final Object invoker, final TestAdaptReq req, final IHttpCallBack callBack) {
        this.mAction = TEST_ADAPT;
        this.mData = req;
        send(invoker, callBack);
    }
}
