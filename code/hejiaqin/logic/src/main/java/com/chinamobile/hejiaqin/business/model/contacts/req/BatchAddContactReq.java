package com.chinamobile.hejiaqin.business.model.contacts.req;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.chinamobile.hejiaqin.business.net.ReqToken;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public class BatchAddContactReq extends ReqToken implements ReqBody {

    private String contactJson;

    @Override
    public String toBody() {
        // These strings are sent in the request body. They provide information about the file being uploaded
        // This is the standard format for a multipart request
        StringBuffer requestBody = new StringBuffer();

        String contentDisposition = null;
        contentDisposition = "Content-Disposition: form-data; name=\"contactJson\"";
        requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY);
        requestBody.append("\r\n");
        requestBody.append(contentDisposition);
        requestBody.append("\r\n\r\n");
        requestBody.append(getContactJson());
        requestBody.append("\r\n");

        contentDisposition = "Content-Disposition: form-data; name=\"token\"";
        requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY);
        requestBody.append("\r\n");
        requestBody.append(contentDisposition);
        requestBody.append("\r\n\r\n");
        requestBody.append(getToken());
        requestBody.append("\r\n");

        requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY).append("--");
        requestBody.append("\r\n\r\n");

        return requestBody.toString();
    }

    public String getContactJson() {
        return contactJson;
    }

    public void setContactJson(String contactJson) {
        this.contactJson = contactJson;
    }
}
