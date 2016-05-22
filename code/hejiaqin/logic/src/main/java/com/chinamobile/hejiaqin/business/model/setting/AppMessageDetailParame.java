package com.chinamobile.hejiaqin.business.model.setting;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/9.
 */
public class AppMessageDetailParame implements ReqBody {


    private String messageid;

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    @Override
    public String toBody() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
