package com.chinamobile.hejiaqin.business.criteria;

/**
 * Created by Xiadong on 2016/5/17.
 */
public class PageableCriteria {

    /**
     * 当前页
     */
    private int currentPageno;

    /**
     * 每页显示的数据数量
     */
    private String eachPageRows;

    /**
     * 上一次查询的最后一条记录的ID
     */
    private String lastRecordId;

    public int getCurrentPageno() {
        return currentPageno;
    }

    public void setCurrentPageno(int currentPageno) {
        this.currentPageno = currentPageno;
    }

    public String getEachPageRows() {
        return eachPageRows;
    }

    public void setEachPageRows(String eachPageRows) {
        this.eachPageRows = eachPageRows;
    }

    public String getLastRecordId() {
        return lastRecordId;
    }

    public void setLastRecordId(String lastRecordId) {
        this.lastRecordId = lastRecordId;
    }
}
