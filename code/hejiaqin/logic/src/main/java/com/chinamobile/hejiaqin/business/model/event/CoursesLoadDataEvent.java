package com.chinamobile.hejiaqin.business.model.event;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/17.
 */
public class CoursesLoadDataEvent {

    private int actionId;

    private Object obj;

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
