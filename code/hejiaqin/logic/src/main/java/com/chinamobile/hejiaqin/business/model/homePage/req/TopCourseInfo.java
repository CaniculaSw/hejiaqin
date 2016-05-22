package com.chinamobile.hejiaqin.business.model.homePage.req;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * 登录信息
 * Kangxi Version 001
 * author: zhanggj
 * Created: 2016/4/8.
 */
public class TopCourseInfo implements ReqBody{

    //课程/任务ID
    private String id;

    //1置顶，0不置顶
    private String placedTop;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlacedTop() {
        return placedTop;
    }

    public void setPlacedTop(String placedTop) {
        this.placedTop = placedTop;
    }

    @Override
    public String toBody() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
