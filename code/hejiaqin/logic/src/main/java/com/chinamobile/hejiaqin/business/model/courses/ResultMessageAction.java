package com.chinamobile.hejiaqin.business.model.courses;

import com.customer.framework.component.net.NetResponse;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/18.
 */
public class ResultMessageAction {

    private int actionId;
    private int resultId;
    private Object criteria;//查询条件
    private NetResponse.ResponseCode errorCode;
    private String errorMsg;
    private Object resultData;

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public Object getCriteria() {
        return criteria;
    }

    public void setCriteria(Object criteria) {
        this.criteria = criteria;
    }

    public NetResponse.ResponseCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(NetResponse.ResponseCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }
}
