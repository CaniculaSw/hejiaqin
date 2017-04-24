package com.chinamobile.hejiaqin.business.model.login;

/***/
public class UserInfo {

    private String name;
    private String tvAccount;
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

    public String getTvAccount() {
        return tvAccount;
    }

    public void setTvAccount(String tvAccount) {
        this.tvAccount = tvAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /***/
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("name:").append(name);
        builder.append("tvAccount:").append(tvAccount);
        builder.append("userId:").append(userId);
        builder.append("userName:").append(userName);
        builder.append("phone:").append(phone);

        builder.append("sdkAccount:").append(sdkAccount);
        builder.append("sdkPassword:").append(sdkPassword);
        builder.append("imAccount:").append(imAccount);
        builder.append("imPassword:").append(imPassword);

        builder.append("token:").append(token);
        builder.append("photoLg:").append(photoLg);
        builder.append("photoSm:").append(photoSm);
        builder.append("tokenExpire:").append(tokenExpire);
        builder.append("type:").append(type);

        return builder.toString();
    }
}
