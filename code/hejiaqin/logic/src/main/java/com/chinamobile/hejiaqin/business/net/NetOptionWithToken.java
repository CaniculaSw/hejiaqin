package com.chinamobile.hejiaqin.business.net;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.customer.framework.component.net.INetCallBack;
import com.customer.framework.component.net.NameValuePair;
import com.customer.framework.component.net.NetOption;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.component.net.message.BasicNameValuePair;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.cryptor.AES;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/14.
 */
public abstract class NetOptionWithToken extends NetOption {

    private static final String TAG = "NetOptionWithToken";
    private static boolean isRefreshTokening = false;

    @Override
    public void send(final INetCallBack httpCallback) {

        if (isNeedToken()) {
            sendWithToken(httpCallback);
        } else {
            super.send(httpCallback);
        }
    }

    @Override
    public void uploadDirect(final INetCallBack httpCallback) {
        if (isNeedToken()) {
            uploadWithToken(httpCallback);
        } else {
            super.uploadDirect(httpCallback);
        }
    }

    /**
     * 发送Http请求
     *
     * @param httpCallback 回调对象
     */
    private synchronized void sendWithToken(final INetCallBack httpCallback) {
        //TODO:如果没有即将超期并且没有正在发送刷新TOKEN请求，直接发送网络请求
        if (needRefresh() && !isRefreshTokening) {
            isRefreshTokening = true;
            new TokenRefreshNetOption().send(new INetCallBack() {
                @Override
                public void onResult(NetResponse response) {
                    //保存token
                    isRefreshTokening = false;
                    //TODO 成功了则刷新本地Token数据
                    //无论是否成功都不影响业务请求的发送
                    NetOptionWithToken.super.send(httpCallback);
                }
            });
        } else {
            super.send(httpCallback);
        }
    }

    private synchronized void uploadWithToken(final INetCallBack httpCallback) {
        //TODO:如果没有即将超期并且没有正在发送刷新TOKEN请求，直接发送网络请求
        if (needRefresh() && !isRefreshTokening) {
            isRefreshTokening = true;
            new TokenRefreshNetOption().send(new INetCallBack() {
                @Override
                public void onResult(NetResponse response) {
                    //保存token
                    isRefreshTokening = false;
                    //TODO 成功了则刷新本地Token数据
                    //无论是否成功都不影响业务请求的发送
                    NetOptionWithToken.super.uploadDirect(httpCallback);
                }
            });
        } else {
            super.uploadDirect(httpCallback);
        }
    }

    @Override
    protected List<NameValuePair> getRequestProperties() {
        List<NameValuePair> properties = null;
        if (isNeedToken()) {
            properties = new ArrayList<NameValuePair>();
            UserInfo info = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
            if (info != null && info.getToken() != null) {
                String unique = UUID.randomUUID().toString();
                properties.add(new BasicNameValuePair(BussinessConstants.HttpHeaderInfo.HEADER_TOKENID, AES.encrypt(info.getToken(), unique.substring(0, 8))));
                properties.add(new BasicNameValuePair(BussinessConstants.HttpHeaderInfo.HEADER_UNIQ, unique));
            }
        }
        return properties;
    }

    protected abstract boolean isNeedToken();

    private boolean needRefresh() {
        UserInfo info = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
        if (info == null) {
            return false;
        }
        String tokenExpire = info.getTokenExpire();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long expire = 0;
        long tokenDate = StorageMgr.getInstance().getMemStorage().getLong(BussinessConstants.Login.TOKEN_DATE);
        try {
            expire = sdf.parse(tokenExpire).getTime() - tokenDate;
        } catch (ParseException e) {
            LogUtil.e(TAG, e);
        }
        //永不过期
        if (expire == -1) {
            return false;
        }

        if (tokenDate != Long.MIN_VALUE) {
            Date now = new Date();
            long value = now.getTime() - tokenDate;
            //在有效期内一半时间内刷新TOKEN
            if (value > expire / 2 && value < expire) {
                return true;
            }
        }
        return false;
    }

}
