package com.chinamobile.hejiaqin.business.model.more.req;

import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.chinamobile.hejiaqin.business.net.ReqToken;

/**
 * Created by eshaohu on 16/11/12.
 */
public class SaveBindRequest extends ReqToken implements ReqBody {
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toBody() {
        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("token", getToken());
        reqBody.add("phone", getPhone());
        return reqBody.toBody();
    }
}
