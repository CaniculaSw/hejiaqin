package com.chinamobile.hejiaqin.business.model.login.req;

import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
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
        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("phone", getPhone());
        reqBody.add("code", getVerifyCode());

        return reqBody.toBody();
    }
}
