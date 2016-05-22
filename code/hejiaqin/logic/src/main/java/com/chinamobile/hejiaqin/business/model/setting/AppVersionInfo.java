package com.chinamobile.hejiaqin.business.model.setting;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/28.
 */
public class AppVersionInfo {

    private String id;

    private double versionCode;

    private String versionName;

    private String memo;

    private String downloadpath;

    private String publishtime;

    private String mustUpdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(double versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDownloadpath() {
        return downloadpath;
    }

    public void setDownloadpath(String downloadpath) {
        this.downloadpath = downloadpath;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public String getMustUpdate() {
        return mustUpdate;
    }

    public void setMustUpdate(String mustUpdate) {
        this.mustUpdate = mustUpdate;
    }
}
