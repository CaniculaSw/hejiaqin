package com.chinamobile.hejiaqin.business.model.login.req;

import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * Created by eshaohu on 16/9/12.
 */
public class TvLoginInfo implements ReqBody {
    private String tvId;
    private String code;
//    private String tvToken;

//    public String getTvToken() {
//        return tvToken;
//    }
//
//    public void setTvToken(String tvToken) {
//        this.tvToken = tvToken;
//    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTvId() {
        return tvId;
    }

    public void setTvId(String tvId) {
        this.tvId = tvId;
    }

    @Override
    public String toBody() {
        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("tvId", getTvId());
        reqBody.add("code", getCode());
        return reqBody.toBody();
    }
}
