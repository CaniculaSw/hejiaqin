package com.customer.framework.component.net;

import com.customer.framework.component.net.NetRequest.RequestMethod;
import com.customer.framework.component.net.NetResponse.ResponseCode;
import com.customer.framework.utils.FileUtil;
import com.customer.framework.utils.LogUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/***/
public class NetUrlConnection {
    /**
     * 打印日志标示
     */
    private static final String TAG = "HttpUrlConnection";
    private static final String BOUNDARY = "----hejiaqinapplicationrequestboundary";
    /**
     * 是否自动重定向
     */
    public static final boolean IS_FOLLOW_REDIRECTS = true;

    /**
     * 主机检查信任
     */
    private static HostnameVerifier hostNameVerifier = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * 通过HttpURLConnection进行连接建立
     *
     * @param request 请求对象
     * @return 响应对象
     */
    public static NetResponse connect(NetRequest request) {
        //创建返回对象
        NetResponse response = new NetResponse();
        response.setRequest(request);

        HttpURLConnection httpConn = null;

        System.setProperty("http.keepAlive", "false");
        GZIPOutputStream gzos = null;
        OutputStream os = null;
        try {
            LogUtil.i(TAG, "request url : " + request.getUrl());
            URL url = new URL(request.getUrl());
            if (!"https".equals(url.getProtocol().toLowerCase())) {
                httpConn = (HttpURLConnection) url.openConnection();
            } else {
                httpConn = getHttpsConn(url, request);
            }
            httpConn.setInstanceFollowRedirects(IS_FOLLOW_REDIRECTS);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestProperty("Charset", "UTF-8");
            setRequestMethod(request, httpConn);
            setRequestProperty(request, httpConn);
            httpConn.setConnectTimeout(request.getConnectionTimeOut());
            httpConn.setReadTimeout(request.getReadTOut());
            if (request.getBody() != null) {
                LogUtil.i(TAG, "request body : \n" + request.getBody());
                byte[] data = request.getBody().getBytes("UTF-8");

                os = httpConn.getOutputStream();
                if (request.isGZip()) {
                    gzos = new GZIPOutputStream(os);
                    gzos.write(data);
                    gzos.flush();
                } else {
                    os.write(data);
                    os.flush();
                }

            }
            int responseCode = initResponseCode(response, httpConn);
            switch (responseCode) {
                case HttpURLConnection.HTTP_BAD_REQUEST:
                case HttpURLConnection.HTTP_FORBIDDEN:
                case HttpURLConnection.HTTP_NOT_FOUND:
                case HttpURLConnection.HTTP_CONFLICT:
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_CREATED:
                    setResponseData(request, response, httpConn, false);
                    LogUtil.w(TAG, "Response Code:" + responseCode + ",URL=" + request.getUrl());
                    break;
                default:
                    response.setResponseCode(ResponseCode.Failed);
                    response.setData(Integer.toString(responseCode));
                    break;
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            response.setResponseCode(ResponseCode.Timeout);
        } catch (ConnectException e) {
            LogUtil.e(TAG, "HttpConnector exception!");
            response.setResponseCode(ResponseCode.NetworkError);
        } catch (SocketException se) {
            response.setResponseCode(ResponseCode.NetworkError);
        } catch (MalformedURLException e) {
            response.setResponseCode(ResponseCode.ParamError);
        } catch (IOException e) {
            if (httpConn != null) {
                try {
                    int responseCode = initResponseCode(response, httpConn);
                    if (HttpURLConnection.HTTP_UNAUTHORIZED == responseCode
                            || HttpURLConnection.HTTP_FORBIDDEN == responseCode
                            || HttpURLConnection.HTTP_BAD_REQUEST == responseCode
                            || HttpURLConnection.HTTP_NOT_FOUND == responseCode
                            || HttpURLConnection.HTTP_CONFLICT == responseCode
                            || HttpURLConnection.HTTP_INTERNAL_ERROR == responseCode) {
                        setResponseData(request, response, httpConn, true);
                    }
                    LogUtil.i(TAG, "IOException getrespCode:" + responseCode);
                } catch (IOException ex) {
                    response.setResponseCode(ResponseCode.Failed);
                } finally {
                    httpConn.disconnect();
                }
                LogUtil.i(TAG, "HttpConnector IOException ......!");
            }
        } finally {
            if (httpConn != null) {
                try {
                    httpConn.disconnect();
                } catch (Exception e) {
                    LogUtil.e(TAG, "httpConn disconnect exception!");
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    LogUtil.e(TAG, "  os.close(); exception!");
                }
            }
            if (gzos != null) {
                try {
                    gzos.close();
                } catch (Exception e) {
                    LogUtil.e(TAG, "  gzos.close(); exception!");
                }
            }
        }
        LogUtil.i(TAG, "Response Code :  " + response.getResponseCode());
        return response;
    }

    /**
     * 通过HttpURLConnection进行连接建立
     *
     * @param request 请求对象
     * @return 响应对象
     */
    public static NetResponse upload(NetRequest request) {
        final String LINE_FEED = "\r\n";
        final String TWO_HYPHENS = "--";
        //创建返回对象
        NetResponse response = new NetResponse();
        response.setRequest(request);

        HttpURLConnection httpConn = null;

        //        System.setProperty("http.keepAlive", "false");
        GZIPOutputStream gzos = null;
        DataOutputStream os = null;
        try {
            LogUtil.i(TAG, "request url : " + request.getUrl());
            URL url = new URL(request.getUrl());
            if (!"https".equals(url.getProtocol().toLowerCase())) {
                httpConn = (HttpURLConnection) url.openConnection();
            } else {
                httpConn = getHttpsConn(url, request);
            }
            httpConn.setInstanceFollowRedirects(IS_FOLLOW_REDIRECTS);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Charset", "UTF-8");
            setRequestMethod(request, httpConn);
            setRequestProperty(request, httpConn);
            httpConn.setConnectTimeout(request.getConnectionTimeOut());
            httpConn.setReadTimeout(request.getReadTOut());
            if (request.getBody() != null) {
                os = new DataOutputStream(httpConn.getOutputStream());
                LogUtil.i(TAG, "request body : \n" + request.getBody());
                String[] bodyParameter = request.getBody().split("&");
                for (String body : bodyParameter) {
                    String[] map = body.split("=");
                    LogUtil.i(TAG, "Handle body parameter: " + map[0] + "= " + map[1]);
                    if ("file".equals(map[0])) {
                        File file = FileUtil.getFileByPath(map[1]);
                        StringBuffer sb = new StringBuffer();
                        sb.append(TWO_HYPHENS + BOUNDARY + LINE_FEED);
                        sb.append("Content-Disposition: form-data; name=\"" + map[0]
                                + "\"; filename=\"" + file.getName() + LINE_FEED);
                        //                        os.writeBytes("Content-Type: " +  FileUtil.getMIMEType(file.getName()) + LINE_FEED);
                        sb.append("Content-Type: application/octet-stream" + LINE_FEED);
                        //                        os.writeBytes("Content-Transfer-Encoding: binary" + LINE_FEED);
                        sb.append(LINE_FEED);
                        os.write(sb.toString().getBytes("UTF-8"));
                        int count = 0;
                        FileInputStream inputStream = null;
                        try {
                            inputStream = new FileInputStream(file);
                            int bytesAvailable = inputStream.available();
                            int maxBufferSize = 1024;
                            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            byte[] buffer = new byte[bufferSize];

                            // read file and write it into form...
                            int bytesRead = inputStream.read(buffer, 0, bufferSize);
                            while (bytesRead > 0) {
                                count += bytesRead;
                                os.write(buffer, 0, bufferSize);
                                bytesAvailable = inputStream.available();
                                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                bytesRead = inputStream.read(buffer, 0, bufferSize);
                            }
                        } catch (Exception e) {
                            LogUtil.d(TAG, e.getMessage());
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        }

                        LogUtil.i(TAG, "Writted byte is: " + count);
                        LogUtil.i(TAG, "The length of file is: " + file.length());
                        os.write(LINE_FEED.getBytes("UTF-8"));
                    } else {
                        StringBuffer sb = new StringBuffer();
                        sb.append(TWO_HYPHENS + BOUNDARY + LINE_FEED);
                        sb.append("Content-Disposition: form-data; name=\"" + map[0] + "\""
                                + LINE_FEED);
                        sb.append(LINE_FEED);
                        sb.append(map[1] + LINE_FEED);
                        os.write(sb.toString().getBytes("UTF-8"));
                    }
                }
                os.write((TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_FEED).getBytes("UTF-8"));
                os.write(LINE_FEED.getBytes("UTF-8"));
                os.flush();
            }
            int responseCode = initResponseCode(response, httpConn);
            switch (responseCode) {
                case HttpURLConnection.HTTP_BAD_REQUEST:
                case HttpURLConnection.HTTP_FORBIDDEN:
                case HttpURLConnection.HTTP_NOT_FOUND:
                case HttpURLConnection.HTTP_CONFLICT:
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                case HttpURLConnection.HTTP_OK:
                case HttpURLConnection.HTTP_CREATED:
                    setResponseData(request, response, httpConn, false);
                    LogUtil.w(TAG, "Response Code:" + responseCode + ",URL=" + request.getUrl());
                    break;
                default:
                    response.setResponseCode(ResponseCode.Failed);
                    response.setData(Integer.toString(responseCode));
                    break;
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            response.setResponseCode(ResponseCode.Timeout);
        } catch (ConnectException e) {
            LogUtil.e(TAG, "HttpConnector exception!");
            response.setResponseCode(ResponseCode.NetworkError);
        } catch (SocketException se) {
            response.setResponseCode(ResponseCode.NetworkError);
        } catch (MalformedURLException e) {
            response.setResponseCode(ResponseCode.ParamError);
        } catch (IOException e) {
            if (httpConn != null) {
                try {
                    int responseCode = initResponseCode(response, httpConn);
                    if (HttpURLConnection.HTTP_UNAUTHORIZED == responseCode
                            || HttpURLConnection.HTTP_FORBIDDEN == responseCode
                            || HttpURLConnection.HTTP_BAD_REQUEST == responseCode
                            || HttpURLConnection.HTTP_NOT_FOUND == responseCode
                            || HttpURLConnection.HTTP_CONFLICT == responseCode
                            || HttpURLConnection.HTTP_INTERNAL_ERROR == responseCode) {
                        setResponseData(request, response, httpConn, true);
                    }
                    LogUtil.i(TAG, "IOException getrespCode:" + responseCode);
                } catch (IOException ex) {
                    response.setResponseCode(ResponseCode.Failed);
                } finally {
                    httpConn.disconnect();
                }
                LogUtil.i(TAG, "HttpConnector IOException ......!");
            }
        } finally {
            if (httpConn != null) {
                try {
                    httpConn.disconnect();
                } catch (Exception e) {
                    LogUtil.e(TAG, "httpConn disconnect exception!");
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    LogUtil.e(TAG, "  os.close(); exception!");
                }
            }
            if (gzos != null) {
                try {
                    gzos.close();
                } catch (Exception e) {
                    LogUtil.e(TAG, "  gzos.close(); exception!");
                }
            }
        }
        LogUtil.i(TAG, "Response Code :  " + response.getResponseCode());
        return response;
    }

    /**
     * 读取http响应的数据
     *
     * @param request  请求
     * @param response 响应
     * @param httpConn HttpUrlConnection连接
     * @throws IOException
     * @throws IOException
     */
    private static void setResponseData(NetRequest request, NetResponse response,
                                        HttpURLConnection httpConn, boolean isError)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        InputStream is = null;
        InputStream bis = null;
        InputStream gzis = null;
        String contentType = null;
        try {
            if (isError) {
                is = httpConn.getErrorStream();
            }
            if (null == is) {
                is = httpConn.getInputStream();
            }

            if ("gzip".equals(httpConn.getHeaderField("Content-Encoding"))) {
                gzis = new GZIPInputStream(is);
            } else {
                bis = new BufferedInputStream(is);
                bis.mark(2);
                byte[] header = new byte[2];
                int result = bis.read(header);
                bis.reset();

                if (2 == result && ((header[0] & 0xFF) | ((header[1] & 0xFF) << 8)) == 0x8b1f) {
                    gzis = new GZIPInputStream(bis);
                } else {
                    gzis = bis;
                    bis = null;
                }
            }
            int length = gzis.read(bytes);
            while (length != -1) {
                os.write(bytes, 0, length);
                length = gzis.read(bytes);
            }
            bytes = os.toByteArray();
            if (request.isNeedByte()) {
                response.setByteData(bytes);
            } else {
                response.setData(new String(bytes, "UTF-8"));
            }

            if (!request.isNeedByte()) {
                contentType = httpConn.getContentType();
                if (null != contentType) {
                    response.setResponseContentType(stringToContentType(contentType));
                }
                LogUtil.i(TAG, "response data : " + response.getData());
            } else {
                LogUtil.i(TAG, "response data : [ is byte data ]");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (gzis != null) {
                try {
                    gzis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != bis) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过HTTP的响应码，设置BaseResponse的响应参数
     *
     * @param response 响应
     * @param httpConn 连接
     * @return 返回HTTP响应码
     * @throws IOException
     */
    private static int initResponseCode(NetResponse response, HttpURLConnection httpConn)
            throws IOException {
        int responseCode = httpConn.getResponseCode();
        LogUtil.i(TAG, "Response Code[" + responseCode + "]");
        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
            case HttpURLConnection.HTTP_CREATED:
                response.setResponseCode(ResponseCode.Succeed);
                break;
            case HttpURLConnection.HTTP_BAD_REQUEST:
                response.setResponseCode(ResponseCode.BadRequest);
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                response.setResponseCode(ResponseCode.UnAuthorized);
                break;
            case HttpURLConnection.HTTP_FORBIDDEN:
                response.setResponseCode(ResponseCode.Forbidden);
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                response.setResponseCode(ResponseCode.NotFound);
                break;
            case HttpURLConnection.HTTP_CONFLICT:
                response.setResponseCode(ResponseCode.Conflict);
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                response.setResponseCode(ResponseCode.InternalError);
                break;
            default:
                response.setResponseCode(ResponseCode.Failed);
                break;
        }
        return responseCode;
    }

    /**
     * 设置HTTP请求头域内容
     *
     * @param request  请求
     * @param httpConn HttpURLConnection
     */
    private static void setRequestProperty(NetRequest request, HttpURLConnection httpConn) {
        if (request.getContentType() == NetRequest.ContentType.XML) {
            httpConn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        } else if (request.getContentType() == NetRequest.ContentType.JSON) {
            httpConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        } else if (request.getContentType() == NetRequest.ContentType.FORM_URLENCODED) {
            httpConn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
        } else if (request.getContentType() == NetRequest.ContentType.FORM_DATA) {
            httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        }

        if (request.isGZip()) {
            httpConn.setRequestProperty("Accept-Encoding", "gzip");
            if (null != request.getBody()) {
                httpConn.setRequestProperty("Content-Encoding", "gzip");
            }
        }
        // set request property
        if (request.getRequestProperties() != null) {
            for (NameValuePair nameValuePair : request.getRequestProperties()) {
                LogUtil.i(TAG, nameValuePair.getName() + ":" + nameValuePair.getValue());
                httpConn.setRequestProperty(nameValuePair.getName(), nameValuePair.getValue());
            }
        }
    }

    /**
     * 设置HTTP的请求方法
     *
     * @param request  请求
     * @param httpConn 请求连接
     * @throws ProtocolException
     */
    private static void setRequestMethod(NetRequest request, HttpURLConnection httpConn)
            throws ProtocolException {
        if (request.getRequestMethod() == RequestMethod.GET) {
            httpConn.setRequestMethod("GET");
        } else if (request.getRequestMethod() == RequestMethod.POST) {
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
        } else if (request.getRequestMethod() == RequestMethod.PUT) {
            httpConn.setRequestMethod("PUT");
            httpConn.setDoOutput(true);
        } else if (request.getRequestMethod() == RequestMethod.DELETE) {
            httpConn.setRequestMethod("DELETE");
        }
    }

    /**
     * 打开https连接<BR>
     * 信任所有主机
     *
     * @param url     连接的URL
     * @param request 请求
     * @return https 连接
     * @throws IOException 打开异常
     */
    private static HttpURLConnection getHttpsConn(URL url, NetRequest request) throws IOException {
        if (request != null && request.isTrustAll()) {
            trustAllHosts();
        }
        HttpsURLConnection https = (HttpsURLConnection) url.openConnection();

        if (request != null && request.isTrustAll()) {
            // 设置始终信任
            https.setHostnameVerifier(hostNameVerifier);
        }
        return https;
    }

    /**
     * 信任所有主机-对于任何证书都不做检查
     */
    private static void trustAllHosts() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }
        } };
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static NetRequest.ContentType stringToContentType(String contentType) {
        if (contentType.contains("application/json")) {
            return NetRequest.ContentType.JSON;
        } else if (contentType.contains("application/xml")) {
            return NetRequest.ContentType.XML;
        } else if (contentType.contains("application/x-www-form-urlencoded")) {
            return NetRequest.ContentType.FORM_URLENCODED;
        } else if (contentType.contains("multipart/form-data")) {
            return NetRequest.ContentType.FORM_DATA;
        }
        LogUtil.e(TAG, "Unhandle content type string: " + contentType);
        return NetRequest.ContentType.UNKNOWN;
    }
}
