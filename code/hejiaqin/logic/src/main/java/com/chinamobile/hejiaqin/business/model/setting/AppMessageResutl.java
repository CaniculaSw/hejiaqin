package com.chinamobile.hejiaqin.business.model.setting;

import com.customer.framework.component.net.NetResponse;

import java.util.List;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/10.
 */
public class AppMessageResutl {


    private int actionId;
    private int resultId;
    private String errorMsg;
    private NetResponse.ResponseCode errorCode;
    private AppMessageParame parame;
    private List<AppMessageInfo> msgData;
    private PageInfo pageInfo;

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
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

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public List<AppMessageInfo> getMsgData() {
        return msgData;
    }

    public void setMsgData(List<AppMessageInfo> msgData) {
        this.msgData = msgData;
    }

    public AppMessageParame getParame() {
        return parame;
    }

    public void setParame(AppMessageParame parame) {
        this.parame = parame;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
