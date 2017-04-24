package com.chinamobile.hejiaqin.business.model.more.req;

import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.chinamobile.hejiaqin.business.net.ReqToken;

/**
 * Created by eshaohu on 16/10/3.
 */

public class GetDeviceListReq extends ReqToken implements ReqBody {
    @Override
    public String toBody() {
        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("token", getToken());
        return reqBody.toBody();
    }
}
