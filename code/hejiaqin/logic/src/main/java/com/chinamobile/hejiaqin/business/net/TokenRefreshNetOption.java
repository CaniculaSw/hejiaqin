package com.chinamobile.hejiaqin.business.net;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.customer.framework.component.net.INetCallBack;
import com.customer.framework.component.net.NameValuePair;
import com.customer.framework.component.net.NetOption;
import com.customer.framework.component.net.NetResponse;

import java.util.List;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/14.
 */
public class TokenRefreshNetOption extends NetOption {

    private String tokenRefreshUrl = "";

    @Override
    protected String getUrl() {
        return BussinessConstants.ServerInfo.HTTP_ADDRESS + tokenRefreshUrl;
    }

    @Override
    protected String getBody() {
        return null;
    }

    @Override
    protected List<NameValuePair> getRequestProperties() {
        return null;
    }

    @Override
    protected Object handleResponse(NetResponse response) {
        return null;
    }

    public void send(final INetCallBack httpCallback) {
        super.send(httpCallback);
    }
}
