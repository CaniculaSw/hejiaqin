package com.chinamobile.hejiaqin.business.model.login.req;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/15.
 */
public class VerifyInfo implements ReqBody {
    //登录ID  手机号码
    private String phone;
    //验证码
    private String verifyCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
    @Override
    public String toBody() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
