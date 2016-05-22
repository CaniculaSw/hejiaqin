package com.chinamobile.hejiaqin.business.model;

/**
 * Created by Xiadong on 2016/5/18.
 */
public class RequestResult {

    private Object result;
    private PageableInfo pageableInfo;

    public RequestResult(Object result, PageableInfo pageableInfo) {
        this.result = result;
        this.pageableInfo = pageableInfo;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public PageableInfo getPageableInfo() {
        return pageableInfo;
    }

    public void setPageableInfo(PageableInfo pageableInfo) {
        this.pageableInfo = pageableInfo;
    }
}
