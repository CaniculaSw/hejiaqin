package com.chinamobile.hejiaqin.business.model.login.req;

import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * Created by eshaohu on 16/6/23.
 */
public class UpdatePhotoReq implements ReqBody {
    String file;
    String token;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toBody() {
        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("token",getToken());
        reqBody.add("file",getFile());
        return reqBody.toBody();
   }
        // These strings are sent in the request body. They provide information about the file being uploaded
        // This is the standard format for a multipart request
//        StringBuffer requestBody = new StringBuffer();
//
//        String contentDisposition = null;
//        if (null != getFileName()) {
//            File file = new File(getFileName());
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
//
//    }

}
