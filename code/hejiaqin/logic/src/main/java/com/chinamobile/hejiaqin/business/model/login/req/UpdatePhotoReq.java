package com.chinamobile.hejiaqin.business.model.login.req;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.customer.framework.utils.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by eshaohu on 16/6/23.
 */
public class UpdatePhotoReq implements ReqBody {
    String fileName;
    String token;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toBody() {
        // These strings are sent in the request body. They provide information about the file being uploaded
        File file = new File(getFileName());

        // This is the standard format for a multipart request
        StringBuffer requestBody = new StringBuffer();
//
        String contentDisposition = "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"";
        String contentType = "Content-Type: "+ FileUtil.getMIMEType(file.getName());

        requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY);
        requestBody.append("\r\n");
        requestBody.append(contentDisposition);
        requestBody.append("\r\n");
        requestBody.append(contentType);
        requestBody.append("\r\n\r\n");
        try {
            requestBody.append(new String(FileUtil.fileToByte(file),"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestBody.append("\r\n");

        contentDisposition = "Content-Disposition: form-data; name=\"token\"";
        requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY);
        requestBody.append("\r\n");
        requestBody.append(contentDisposition);
        requestBody.append("\r\n\r\n");
        requestBody.append(getToken());
        requestBody.append("\r\n");

//        contentDisposition = "Content-Disposition: form-data; name=\"phone\"";
//        requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY);
//        requestBody.append("\r\n");
//        requestBody.append(contentDisposition);
//        requestBody.append("\r\n\r\n");
//        requestBody.append("13772222222");
//        requestBody.append("\r\n");
//
//        contentDisposition = "Content-Disposition: form-data; name=\"name\"";
//        requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY);
//        requestBody.append("\r\n");
//        requestBody.append(contentDisposition);
//        requestBody.append("\r\n\r\n");
//        requestBody.append("Tim Huang");
//        requestBody.append("\r\n");

        requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY).append("--");
        requestBody.append("\r\n\r\n");

        return requestBody.toString();

    }

}
