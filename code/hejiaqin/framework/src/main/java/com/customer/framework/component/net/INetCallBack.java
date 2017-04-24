package com.customer.framework.component.net;

/***/
public interface INetCallBack {
    /**
     * UI请求数据后回调函数<BR>
     *
     * @param response Response
     */
    void onResult(NetResponse response);
}
