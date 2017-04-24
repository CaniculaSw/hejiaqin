package com.chinamobile.hejiaqin.business.net;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.customer.framework.component.net.INetCallBack;
import com.customer.framework.component.net.NameValuePair;
import com.customer.framework.component.net.NetOption;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.component.net.message.BasicNameValuePair;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.cryptor.AES;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/***/
public abstract class NetOptionWithToken extends NetOption {

    private static final String TAG = "NetOptionWithToken";
    private static boolean isRefreshTokening = false;
    private static List<NetOptionWithToken> list = new ArrayList<NetOptionWithToken>();
    protected ReqBody mData;
    private INetCallBack netCallBack;

    @Override
    public void send(final INetCallBack httpCallback) {

        if (isNeedToken()) {
            sendWithToken(httpCallback);
        } else {
            super.send(httpCallback);
        }
    }

    /**
     * 发送Http请求
     *
     * @param httpCallback 回调对象
     */
    private synchronized void sendWithToken(final INetCallBack httpCallback) {
        //如果没有即将超期并且没有正在发送刷新TOKEN请求，直接发送网络请求
        if (needRefresh()) {
            if (!isRefreshTokening) {
                isRefreshTokening = true;
                new TokenRefreshNetOption(getContext()).send(new INetCallBack() {
                    @Override
                    public void onResult(NetResponse response) {
                        //保存token
                        isRefreshTokening = false;
                        UserInfo info = (UserInfo) StorageMgr.getInstance().getMemStorage()
                                .getObject(BussinessConstants.Login.USER_INFO_KEY);
                        if (response.getResultCode().equals("0") && info != null) {
                            UserInfo newInfo = (UserInfo) response.getObj(); //gson.fromJson(rootJsonObj.get("data").toString(),UserInfo.class);
                            info.setToken(newInfo.getToken());
                            info.setTokenExpire(newInfo.getTokenExpire());
                            long now = new Date().getTime();
                            UserInfoCacheManager.saveUserToMem(getContext(), info, now);
                            UserInfoCacheManager.saveUserToLoacl(getContext(), info, now);
                        } else {
                            LogUtil.w(TAG, "refresh token failed.");
                        }
                        //无论是否成功都不影响业务请求的发送
                        NetOptionWithToken.super.send(httpCallback);
                        //发送缓存的请求
                        for (NetOptionWithToken netOptionWithToken : list) {
                            netOptionWithToken.send(netOptionWithToken.netCallBack);
                            list.remove(netOptionWithToken);
                        }
                    }
                });
            } else {
                //缓存未发送的请求
                netCallBack = httpCallback;
                list.add(this);
            }
        } else {
            super.send(httpCallback);
        }
    }

    @Override
    protected List<NameValuePair> getRequestProperties() {
        List<NameValuePair> properties = null;
        if (isNeedToken()) {
            properties = new ArrayList<NameValuePair>();
            UserInfo info = (UserInfo) StorageMgr.getInstance().getMemStorage()
                    .getObject(BussinessConstants.Login.USER_INFO_KEY);
            if (info != null && info.getToken() != null) {
                String unique = UUID.randomUUID().toString();
                properties.add(new BasicNameValuePair(
                        BussinessConstants.HttpHeaderInfo.HEADER_TOKENID, AES.encrypt(
                                info.getToken(), unique.substring(0, 8))));
                properties.add(new BasicNameValuePair(
                        BussinessConstants.HttpHeaderInfo.HEADER_UNIQ, unique));
            }
        }
        return properties;
    }

    @Override
    protected String getBody() {
        String body = null;
        if (mData != null) {
            if (mData instanceof ReqToken) {
                UserInfo info = (UserInfo) StorageMgr.getInstance().getMemStorage()
                        .getObject(BussinessConstants.Login.USER_INFO_KEY);
                if (info != null && info.getToken() != null) {
                    ((ReqToken) mData).setToken(info.getToken());
                }
            }
            body = mData.toBody();
        }
        return body;
    }

    protected abstract boolean isNeedToken();

    private boolean needRefresh() {
        String infoCache = StorageMgr.getInstance().getSharedPStorage(getContext())
                .getString(BussinessConstants.Login.USER_INFO_KEY);
        if (infoCache == null) {
            return false;
        }
        UserInfo info = new Gson().fromJson(infoCache, UserInfo.class);
        String tokenExpire = info.getTokenExpire();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long expire = 0;
        long tokenDate = StorageMgr.getInstance().getSharedPStorage(getContext())
                .getLong(BussinessConstants.Login.TOKEN_DATE);
        try {
            expire = sdf.parse(tokenExpire).getTime() - tokenDate;
        } catch (ParseException e) {
            LogUtil.e(TAG, e);
        }
        //永不过期
        if (expire == -1) {
            return false;
        }
        LogUtil.i(TAG, "the eipire time is: " + expire + ";the tokenDate is: " + tokenExpire);
        if (tokenDate != Long.MIN_VALUE) {
            Date now = new Date();
            long value = now.getTime() - tokenDate;
            //在有效期内一半时间内刷新TOKEN
            if (value > expire / 2) {
                return true;
            }
        }
        return false;
    }
}
