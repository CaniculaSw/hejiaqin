package com.chinamobile.hejiaqin.business.model.login.req;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * 登录信息
 * Kangxi Version 001
 * author: zhanggj
 * Created: 2016/4/8.
 */
public class LoginInfo implements ReqBody{

    //登录ID  手机号码
    private String loginid;

    //用户密码
    private String pwd;

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toBody() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
