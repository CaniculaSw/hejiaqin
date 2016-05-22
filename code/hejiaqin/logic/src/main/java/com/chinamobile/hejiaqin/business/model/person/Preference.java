package com.chinamobile.hejiaqin.business.model.person;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * @author WBG
 * @desc prefernce choose
 * @time 2016/5/13.
 */
public class Preference implements ReqBody {
    private String prefer;

    public void setPrefer(String prefer) {
        this.prefer = prefer;
    }

    public String getPrefer() {
        return prefer;
    }

    @Override
    public String toBody() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
