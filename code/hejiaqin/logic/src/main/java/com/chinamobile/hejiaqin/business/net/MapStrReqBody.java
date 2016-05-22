package com.chinamobile.hejiaqin.business.net;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/28.
 */
public class MapStrReqBody implements ReqBody{
    private Map<String,String> map = new HashMap<String,String>();

    public void add(String name,String value)
    {
        if(TextUtils.isEmpty(name) || value ==null)
        {
            return;
        }
        map.put(name,value);
    }

    @Override
    public String toBody() {
        if(map.size()==0)
        {
            return "";
        }
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
