package com.chinamobile.hejiaqin.business.net.healthBank;

import android.content.Context;

import com.chinamobile.hejiaqin.business.net.AbsHttpManager;
import com.customer.framework.component.net.NameValuePair;
import com.customer.framework.component.net.NetResponse;

import java.util.List;

/**
 * Created by zhanggj on 2016/4/25.
 */
public class HealthHttpManager extends AbsHttpManager {

    private Context mContext;

    public HealthHttpManager(Context context)
    {
        this.mContext =context;
    }

    @Override
    protected Context getContext()
    {
        return  this.mContext;
    }

    @Override
    protected String getUrl() {
        return null;
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
}
