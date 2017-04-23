package com.chinamobile.hejiaqin.business.net;

import android.text.TextUtils;

import com.customer.framework.component.net.message.BasicNameValuePair;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/7/17.
 */
public class NVPWithTokenReqBody extends ReqToken implements ReqBody {
    private List<String> list = null;
    /***/
    public void add(String name,String value)
    {
        if(TextUtils.isEmpty(name) || value ==null)
        {
            return;
        }
        list.add(new BasicNameValuePair(name,value).toString());
    }

    public NVPWithTokenReqBody(){
        list = new ArrayList<String>();
    }
    @Override
    public String toBody() {
        add("token",getToken());
        return StringUtil.listToString(list,"&");
    }
}
