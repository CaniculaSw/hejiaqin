package com.chinamobile.hejiaqin.business.model.login;

/**
 * desc:用户登录后服务器返回的信息
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/14.
 */
public class UserInfo {

    private String userId;

    private String userName;

    private String phone;

    private String sdkAccount;

    private String sdkPassword;


    private String imAccount;

    private String imPassword;

    private String token;

    private String photoLg;

    private String photoSm;

    private String tokenExpire;

    private String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSdkAccount() {
        return sdkAccount;
    }

    public void setSdkAccount(String sdkAccount) {
        this.sdkAccount = sdkAccount;
    }

    public String getSdkPassword() {
        return sdkPassword;
    }

    public void setSdkPassword(String sdkPassword) {
        this.sdkPassword = sdkPassword;
    }

    public String getImAccount() {
        return imAccount;
    }

    public void setImAccount(String imAccount) {
        this.imAccount = imAccount;
    }

    public String getImPassword() {
        return imPassword;
    }

    public void setImPassword(String imPassword) {
        this.imPassword = imPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(String tokenExpire) {
        this.tokenExpire = tokenExpire;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhotoLg() {
        return photoLg;
    }

    public void setPhotoLg(String photoLg) {
        this.photoLg = photoLg;
    }

    public String getPhotoSm() {
        return photoSm;
    }

    public void setPhotoSm(String photoSm) {
        this.photoSm = photoSm;
    }

}
