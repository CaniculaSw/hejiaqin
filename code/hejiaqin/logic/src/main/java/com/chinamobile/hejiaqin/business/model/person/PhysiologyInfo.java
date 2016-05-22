package com.chinamobile.hejiaqin.business.model.person;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/29.
 */
public class PhysiologyInfo implements ReqBody {
    private Long height;

    private Long weight;

    private Long gi;

    private Long cholesterol;

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getGi() {
        return gi;
    }

    public void setGi(Long gi) {
        this.gi = gi;
    }

    public Long getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(Long cholesterol) {
        this.cholesterol = cholesterol;
    }

    @Override
    public String toBody() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
