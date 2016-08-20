package com.chinamobile.hejiaqin.business.model.login.req;

import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * 登录信息
 * hejiaqin Version 001
 * author:
 * Created: 2016/4/8.
 */
public class LoginInfo implements ReqBody {

    //登录ID  手机号码
    private String phone;

    //用户密码
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toBody() {
        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("phone", getPhone());
        reqBody.add("password", getPassword());
        return reqBody.toBody();
    }
}
