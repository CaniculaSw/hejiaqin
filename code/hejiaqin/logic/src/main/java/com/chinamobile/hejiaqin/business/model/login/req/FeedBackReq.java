package com.chinamobile.hejiaqin.business.model.login.req;

import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.chinamobile.hejiaqin.business.net.ReqToken;

/**
 * Created by eshaohu on 16/6/26.
 */
public class FeedBackReq extends ReqToken implements ReqBody {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toBody() {
        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("token",getToken());
        reqBody.add("content",getContent());

        return reqBody.toBody();
    }
}
