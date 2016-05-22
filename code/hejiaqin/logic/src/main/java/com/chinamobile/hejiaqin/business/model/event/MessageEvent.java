package com.chinamobile.hejiaqin.business.model.event;

import com.chinamobile.hejiaqin.business.model.setting.AppMessageInfo;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/13.
 */
public class MessageEvent {

    private int type;               //1为复制，2为删除

    private AppMessageInfo messageInfo;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public AppMessageInfo getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(AppMessageInfo messageInfo) {
        this.messageInfo = messageInfo;
    }
}
