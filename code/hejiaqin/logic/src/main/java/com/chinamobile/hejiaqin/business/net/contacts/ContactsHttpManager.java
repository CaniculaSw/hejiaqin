package com.chinamobile.hejiaqin.business.net.contacts;

import android.content.Context;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.net.AbsHttpManager;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.MapStrReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.customer.framework.component.net.NetRequest;
import com.customer.framework.component.net.NetResponse;

/**
 * Created by yupeng on 6/9/16.
 */
public class ContactsHttpManager extends AbsHttpManager {

    /**
     * 打印标志
     */
    private static final String TAG = "ContactsHttpManager";

    public enum Action {
        // 获取联系人列表
        list,
        // 添加联系人
        add,
        // 批量添加联系人
        batchAdd,
        // 修改联系人
        update,
        // 删除联系人
        delete
    }

    private Context mContext;

    private Action mAction;

    private ReqBody mData;

    public ContactsHttpManager(Context context) {
        this.mContext = context;
    }

    @Override
    protected Context getContext() {
        return mContext;
    }

    /**
     * 获取请求的URL
     *
     * @return 返回请求URL
     */
    @Override
    protected String getUrl() {
        String url = null;
        switch (mAction) {
            case list:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/contact/list";
                break;
            case add:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/contact/add";
                break;
            case batchAdd:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/contact/add";
                break;
            case update:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/contact/update";
                break;
            case delete:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/contact/delete";
                break;
        }
        return url;
    }

    /**
     * 获取请求要携带的消息体
     *
     * @return 返回消息体
     */
    @Override
    protected String getBody() {
        String body = null;
        if (mData != null) {
            body = mData.toBody();
        }
        return body;
    }

    /**
     * 请求method类型<BR>
     *
     * @return 默认为GET请求
     */
    protected NetRequest.RequestMethod getRequestMethod() {
        NetRequest.RequestMethod method = NetRequest.RequestMethod.GET;
        switch (this.mAction) {
            case list:
            case add:
            case batchAdd:
            case update:
            case delete:
                method = NetRequest.RequestMethod.POST;
                break;
            default:
                break;
        }
        return method;
    }

    /**
     * 处理响应
     *
     * @param response 收到的响应
     * @return 返回解析对象
     */
    @Override
    protected Object handleResponse(NetResponse response) {
        return null;
    }

    // 查询联系人列表
    public void list(final Object invoker, final MapStrReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = Action.list;
        this.mData = reqBody;
        send(invoker, callBack);
    }

    // 添加联系人
    public void add(final Object invoker, final MapStrReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = Action.add;
        this.mData = reqBody;
        send(invoker, callBack);
    }

    // 批量添加联系人
    public void batchAdd(final Object invoker, final MapStrReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = Action.batchAdd;
        this.mData = reqBody;
        send(invoker, callBack);
    }

    // 更新联系人
    public void update(final Object invoker, final MapStrReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = Action.update;
        this.mData = reqBody;
        send(invoker, callBack);
    }

    // 删除联系人
    public void delete(final Object invoker, final MapStrReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = Action.delete;
        this.mData = reqBody;
        send(invoker, callBack);
    }
}
