package com.chinamobile.hejiaqin.business.model.more;

/**
 * Created by eshaohu on 16/5/28.
 */
public class SystemMessage {

    private String title;

    private String date;

    private String msgBody;

    private String id;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
