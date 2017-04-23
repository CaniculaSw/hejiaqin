package com.chinamobile.hejiaqin.business.net;

import android.text.TextUtils;

import com.customer.framework.component.net.message.BasicNameValuePair;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/6/21.
 */
public class NVPReqBody implements ReqBody {
    private List<String> list = new ArrayList<String>();
    /***/
    public void add(String name,String value)
    {
        if(TextUtils.isEmpty(name) || value ==null)
        {
            return;
        }
        list.add(new BasicNameValuePair(name,value).toString());
    }


    @Override
    public String toBody() {
        return StringUtil.listToString(list,"&");
    }
}
