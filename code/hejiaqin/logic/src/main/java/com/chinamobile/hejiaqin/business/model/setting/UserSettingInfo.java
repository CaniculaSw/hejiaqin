package com.chinamobile.hejiaqin.business.model.setting;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * Created by Xiadong on 2016/5/10.
 */
public class UserSettingInfo implements ReqBody {

    private Integer autoDownloadIfWIFI;
    private Integer orderMessage;
    private Integer coach;

    public Integer getAutoDownloadIfWIFI() {
        return autoDownloadIfWIFI;
    }

    public void setAutoDownloadIfWIFI(Integer autoDownloadIfWIFI) {
        this.autoDownloadIfWIFI = autoDownloadIfWIFI;
    }

    public Integer getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(Integer orderMessage) {
        this.orderMessage = orderMessage;
    }

    public Integer getCoach() {
        return coach;
    }

    public void setCoach(Integer coach) {
        this.coach = coach;
    }

    @Override
    public String toBody() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
