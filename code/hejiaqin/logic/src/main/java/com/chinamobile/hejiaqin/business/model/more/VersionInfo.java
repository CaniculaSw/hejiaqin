package com.chinamobile.hejiaqin.business.model.more;

/**
 * Created by eshaohu on 16/6/26.
 */
public class VersionInfo {
    private String versionCode;
    private String versionName;
    private String url;
    private String time;
    private int byForce;
    private String forceVersionCode;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getByForce() {
        return byForce;
    }

    public void setByForce(int byForce) {
        this.byForce = byForce;
    }

    public String getForceVersionCode() {
        return forceVersionCode;
    }

    public void setForceVersionCode(String forceVersionCode) {
        this.forceVersionCode = forceVersionCode;
    }
}
