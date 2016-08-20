package com.chinamobile.hejiaqin.business.net;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.utils.SysInfoUtil;
import com.customer.framework.component.net.INetCallBack;
import com.customer.framework.component.net.NameValuePair;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.component.net.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/15.
 */
public abstract class AbsHttpManager extends NetOptionWithToken {


    protected void send(final Object invoker, final IHttpCallBack callBack) {

        super.send(new INetCallBack() {
            @Override
            public void onResult(NetResponse response) {
                if (response.getResponseCode() == NetResponse.ResponseCode.Succeed) {
                    if (BussinessConstants.HttpCommonCode.COMMON_SUCCESS_CODE.equals(response.getResultCode())) {
                        callBack.onSuccessful(invoker, response.getObj());
                    } else {
                        callBack.onFailure(invoker, response.getResultCode(), response.getResultDesc());
                    }
                } else {
                    callBack.onNetWorkError(response.getResponseCode());
                }
            }
        });
    }

    @Override
    protected boolean isNeedToken() {
        return true;
    }

    @Override
    protected List<NameValuePair> getRequestProperties() {
        List<NameValuePair> properties = super.getRequestProperties();
        if (properties == null) {
            properties = new ArrayList<NameValuePair>();
        }

        //增加IMEI值(使用IMEI+MAC地址)
        properties.add(new BasicNameValuePair(BussinessConstants.HttpHeaderInfo.HEADER_IMEI, SysInfoUtil.getAppUniqueId(getContext())));
        //增加类型
        properties.add(new BasicNameValuePair(BussinessConstants.HttpHeaderInfo.HEADER_MOBILE_TYPE, BussinessConstants.CommonInfo.MOBILE_TYPE_ANDROID));
        //增加名称
        properties.add(new BasicNameValuePair(BussinessConstants.HttpHeaderInfo.HEADER_MOBILE_NAME, SysInfoUtil.getModel()));
        //增加系统版本
        properties.add(new BasicNameValuePair(BussinessConstants.HttpHeaderInfo.HEADER_MOBILE_VERSION, SysInfoUtil.getOsRelease()));
        return properties;
    }

}
