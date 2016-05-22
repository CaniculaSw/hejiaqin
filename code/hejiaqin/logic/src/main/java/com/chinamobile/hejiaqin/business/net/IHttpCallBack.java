package com.chinamobile.hejiaqin.business.net;

import com.customer.framework.component.net.NetResponse;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/14.
 */
public interface IHttpCallBack {
    /**
     * 网络请求成功响应
     * @param invoker 调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param obj 服务器响应解析后的对象
     */
    public void onSuccessful(final Object invoker,Object obj);

    /**
     * 网络请求业务失败响应
     * @param invoker 调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param code 业务错误码
     * @param desc 业务错误描述
     */
    public void onFailure(final Object invoker,String code, String desc);

    /**
     * 网络请求网络失败响应
     * @param errorCode 网络错误码
     */
    public void onNetWorkError(NetResponse.ResponseCode errorCode);
}

