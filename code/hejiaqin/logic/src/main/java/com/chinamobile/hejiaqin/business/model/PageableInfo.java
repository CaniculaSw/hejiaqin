package com.chinamobile.hejiaqin.business.model;

/**
 * Created by Xiadong on 2016/5/18.
 */
public class PageableInfo {

    /**
     * 当前行数
     */
    private int currentPageno;

    /**
     * 实际返回数据的数量
     */
    private int totalRows;

    /**
     * 每页显示数据的数量
     */
    private int eachPageRows;

    /**
     * 总页数
     */
    private int totalPages;

    public int getCurrentPageno() {
        return currentPageno;
    }

    public void setCurrentPageno(int currentPageno) {
        this.currentPageno = currentPageno;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getEachPageRows() {
        return eachPageRows;
    }

    public void setEachPageRows(int eachPageRows) {
        this.eachPageRows = eachPageRows;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
