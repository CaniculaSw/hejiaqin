package com.chinamobile.hejiaqin.business.net.setting;

import android.content.Context;

import com.chinamobile.hejiaqin.business.BussinessConstants;
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

    private final int action_base = 0;

    private final int check_android_version = action_base + 1;
    private final int get_device_list = action_base + 2;
    private final int save_bind_request = action_base + 3;
    private final int get_bind_list = action_base + 4;
    private final int test_adapt = action_base + 5;

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
            case check_android_version:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/version/android";
                break;
            case get_device_list:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/device/getDeviceList";
                break;
            case save_bind_request:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/device/bind";
                break;
            case get_bind_list:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/device/getBindList";
                break;
            case test_adapt:
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
            case check_android_version:
            case get_device_list:
            case save_bind_request:
            case get_bind_list:
            case test_adapt:
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
            case check_android_version:
            case test_adapt:
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
            case check_android_version:
                //TODO
            case get_device_list:
                //TODO
            case save_bind_request:
                //TODO
            case get_bind_list:
                //TODO
            case test_adapt:
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
                    case check_android_version:
                        obj = gson.fromJson(data, VersionInfo.class);
                        break;
                    case get_device_list:
                        obj = gson.fromJson(data, new TypeToken<List<UserInfo>>() {
                        }.getType());
                        break;
                    case get_bind_list:
                        obj = gson.fromJson(data, new TypeToken<List<UserInfo>>() {
                        }.getType());
                        break;
                    case save_bind_request:
                        break;
                    case test_adapt:
                        obj = data;
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

    public void checkVersion(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = check_android_version;
        this.mData = null;
        send(invoker, callBack);
    }

    public void getDeviceList(final Object invoker, final GetDeviceListReq req, final IHttpCallBack callBack) {
        this.mAction = get_device_list;
        this.mData = req;
        send(invoker, callBack);
    }

    public void getBindList(final Object invoker, final GetBindListReq req, final IHttpCallBack callBack) {
        this.mAction = get_bind_list;
        this.mData = req;
        send(invoker, callBack);
    }

    public void saveBindRequest(final Object invoker, final SaveBindRequest req, final IHttpCallBack callBack) {
        this.mAction = save_bind_request;
        this.mData = req;
        send(invoker, callBack);
    }

    public void testAdapt(final Object invoker, final TestAdaptReq req, final IHttpCallBack callBack) {
        this.mAction = test_adapt;
        this.mData = req;
        send(invoker, callBack);
    }
}
