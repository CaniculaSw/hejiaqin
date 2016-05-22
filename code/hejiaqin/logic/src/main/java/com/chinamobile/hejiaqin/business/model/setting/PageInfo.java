package com.chinamobile.hejiaqin.business.model.setting;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/13.
 */
public class PageInfo {

    private String currentPageno;
    private String totalRows;
    private String eachPageRows;
    private String totalPages;

    public String getCurrentPageno() {
        return currentPageno;
    }

    public void setCurrentPageno(String currentPageno) {
        this.currentPageno = currentPageno;
    }

    public String getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(String totalRows) {
        this.totalRows = totalRows;
    }

    public String getEachPageRows() {
        return eachPageRows;
    }

    public void setEachPageRows(String eachPageRows) {
        this.eachPageRows = eachPageRows;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

}
