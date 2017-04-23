package com.chinamobile.hejiaqin.business.net;

import android.content.Context;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.customer.framework.component.net.INetCallBack;
import com.customer.framework.component.net.NameValuePair;
import com.customer.framework.component.net.NetOption;
import com.customer.framework.component.net.NetRequest;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.utils.LogUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
/***/
public class TokenRefreshNetOption extends NetOption {

    private String tokenRefreshUrl = "/user/refreshToken";
    private Context mContext;

    public TokenRefreshNetOption(Context context){
        this.mContext = context;
    }

    @Override
    protected String getUrl() {
        return BussinessConstants.ServerInfo.HTTP_ADDRESS + tokenRefreshUrl;
    }

    @Override
    protected String getBody() {
        NVPWithTokenReqBody reqBody = new NVPWithTokenReqBody();
        String infoStr =  StorageMgr.getInstance().getSharedPStorage(getContext()).getString(BussinessConstants.Login.USER_INFO_KEY);
        if (infoStr != null) {
            Gson gson = new Gson();
            UserInfo info = gson.fromJson(infoStr,UserInfo.class);
            reqBody.setToken(info.getToken());
        }
        return reqBody.toBody();
    }

    @Override
    protected NetRequest.RequestMethod getRequestMethod() {
        return NetRequest.RequestMethod.POST;
    }

    @Override
    protected NetRequest.ContentType getContentType() {
        return NetRequest.ContentType.FORM_URLENCODED;
    }

    @Override
    protected List<NameValuePair> getRequestProperties() {
        return null;
    }

    @Override
    protected Object handleResponse(NetResponse response) {
        Object obj = null;
        if (BussinessConstants.HttpCommonCode.COMMON_SUCCESS_CODE.equals(response.getResultCode())){
            try {
                String data = "";
                JSONObject rootJsonObj = new JSONObject(response.getData());
                Gson gson = new Gson();
                data = rootJsonObj.get("data").toString();
                obj = gson.fromJson(data, UserInfo.class);
            }catch (JSONException e) {
                LogUtil.e("TokenRefreshNetOption", e.toString());
            }
        }
        return obj;
    }

    @Override
    public Context getContext() {
        return mContext;
    }
    /***/
    public void send(final INetCallBack httpCallback) {
        super.send(httpCallback);
    }
}
