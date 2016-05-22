package com.chinamobile.hejiaqin.business.model.setting;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/9.
 */
public class AppAboutInfo {
    private String content;
    private String contentUrl;
    private String email;
    private String tel;

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

}
