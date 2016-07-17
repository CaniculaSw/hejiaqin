package com.chinamobile.hejiaqin.business.net.setting;

import android.content.Context;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.more.VersionInfo;
import com.chinamobile.hejiaqin.business.net.AbsHttpManager;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.customer.framework.component.net.NameValuePair;
import com.customer.framework.component.net.NetRequest;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.utils.LogUtil;
import com.google.gson.Gson;

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
                if (rootJsonObj.has("data")){
                    data = rootJsonObj.get("data").toString();
                }
                Gson gson = new Gson();
                switch (this.mAction) {
                    case check_android_version:
                        obj = gson.fromJson(data, VersionInfo.class);
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

    public void checkVersion(final Object invoker, final IHttpCallBack callBack){
        this.mAction = check_android_version;
        this.mData = null;
        send(invoker, callBack);
    }
}
