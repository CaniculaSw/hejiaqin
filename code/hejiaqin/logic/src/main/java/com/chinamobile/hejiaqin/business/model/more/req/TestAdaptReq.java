package com.chinamobile.hejiaqin.business.model.more.req;

import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * Created by eshaohu on 17/1/12.
 */
public class TestAdaptReq implements ReqBody {
    @Override
    public String toBody() {
        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("device",android.os.Build.DEVICE);
        reqBody.add("model",android.os.Build.MODEL);
        reqBody.add("rom",android.os.Build.VERSION.RELEASE);

        return reqBody.toBody();
    }
}
