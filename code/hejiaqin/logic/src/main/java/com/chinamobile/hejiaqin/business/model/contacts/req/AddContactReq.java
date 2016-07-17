package com.chinamobile.hejiaqin.business.model.contacts.req;

import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.chinamobile.hejiaqin.business.net.ReqToken;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public class AddContactReq extends ReqToken implements ReqBody {

    private String file;

    private String phone;

    private String name;

    @Override
    public String toBody() {
        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("token", getToken());
        reqBody.add("phone", getPhone());
        reqBody.add("name", getName());
        reqBody.add("file", getFile());
        return reqBody.toBody();

        // These strings are sent in the request body. They provide information about the file being uploaded
        // This is the standard format for a multipart request
//        StringBuffer requestBody = new StringBuffer();
//
//        String contentDisposition = null;
//        if (null != getFile()) {
//            File file = new File(getFile());
//            contentDisposition = "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"";
//            String contentType = "Content-Type: " + FileUtil.getMIMEType(file.getName());
//
//            requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY);
//            requestBody.append("\r\n");
//            requestBody.append(contentDisposition);
//            requestBody.append("\r\n");
//            requestBody.append(contentType);
//            requestBody.append("\r\n\r\n");
//            try {
//                requestBody.append(new String(FileUtil.fileToByte(file), "UTF-8"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            requestBody.append("\r\n");
//        }
//
//        contentDisposition = "Content-Disposition: form-data; name=\"phone\"";
//        requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY);
//        requestBody.append("\r\n");
//        requestBody.append(contentDisposition);
//        requestBody.append("\r\n\r\n");
//        requestBody.append(getPhone());
//        requestBody.append("\r\n");
//
//        contentDisposition = "Content-Disposition: form-data; name=\"name\"";
//        requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY);
//        requestBody.append("\r\n");
//        requestBody.append(contentDisposition);
//        requestBody.append("\r\n\r\n");
//        requestBody.append(getName());
//        requestBody.append("\r\n");
//
//        contentDisposition = "Content-Disposition: form-data; name=\"token\"";
//        requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY);
//        requestBody.append("\r\n");
//        requestBody.append(contentDisposition);
//        requestBody.append("\r\n\r\n");
//        requestBody.append(getToken());
//        requestBody.append("\r\n");
//
//        requestBody.append("--").append(BussinessConstants.HttpHeaderInfo.HEADER_BOUNDARY).append("--");
//        requestBody.append("\r\n\r\n");
//
//        return requestBody.toString();
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
