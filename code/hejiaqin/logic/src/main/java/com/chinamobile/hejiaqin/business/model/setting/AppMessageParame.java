package com.chinamobile.hejiaqin.business.model.setting;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/9.
 */
public class AppMessageParame implements ReqBody {

    private int queryType;              //1已读/0未读/2全部
    private int records_onepage = 15;   //每页多少条数据，默认15条
    private int current_page;           //第几
    private String last_record_id;      //最后一页

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public int getRecords_onepage() {
        return records_onepage;
    }

    public void setRecords_onepage(int records_onepage) {
        this.records_onepage = records_onepage;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public String getLast_record_id() {
        return last_record_id;
    }

    public void setLast_record_id(String last_record_id) {
        this.last_record_id = last_record_id;
    }

    @Override
    public String toBody() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
