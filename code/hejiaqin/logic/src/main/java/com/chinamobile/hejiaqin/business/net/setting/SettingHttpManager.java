package com.chinamobile.hejiaqin.business.net.setting;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.setting.AppAboutInfo;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageDetailInfo;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageDetailParame;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageInfo;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageParame;
import com.chinamobile.hejiaqin.business.model.setting.AppVersionInfo;
import com.chinamobile.hejiaqin.business.model.setting.PageInfo;
import com.chinamobile.hejiaqin.business.model.setting.PageInfoObject;
import com.chinamobile.hejiaqin.business.model.setting.UserSettingInfo;
import com.chinamobile.hejiaqin.business.model.setting.UserQuestionInfo;
import com.chinamobile.hejiaqin.business.net.AbsHttpManager;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.MapStrReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.customer.framework.component.log.Logger;
import com.customer.framework.component.net.NetRequest;
import com.customer.framework.component.net.NetResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zhanggj on 2016/4/25.
 */
public class SettingHttpManager extends AbsHttpManager {

    /**
     * 打印标志
     */
    private static final String TAG = "SettingHttpManager";

    private final int action_base = 0;
    /**
     * 检测最新
     */
    private final int get_last_version_action = action_base + 1;

    /**
     * 关于
     */
    private final int get_about_action = action_base + 2;

    /**
     * 获取消息
     */
    private final int get_message_action = action_base + 3;


    /**
     * 帮助
     */
    private final int help_question_action = action_base + 4;

    /**
     * get service
     */
    private final int get_service_content_action = action_base + 5;

    /**
     * 加载设置
     */
    private final int loading_user_setting_action = action_base + 6;

    /**
     * 修改设置
     */
    private final int amend_user_setting_action = action_base + 7;

    /**
     * 提交反馈
     */
    private final int submit_user_feedback_action = action_base + 8;

    /**
     * 获取详情
     */
    private final int get_message_detail_action = action_base + 9;

    /**
     * 删除消息
     */
    private final int del_message_action = action_base + 10;


    /**
     * 请求action
     */
    private int mAction;

    private ReqBody mData;

    private Context mContext;

    public SettingHttpManager(Context context) {
        this.mContext = context;
    }

    @Override
    protected Context getContext() {
        return this.mContext;
    }

    @Override
    protected String getUrl() {
        String url = null;
        switch (this.mAction) {
            case get_last_version_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/app/version/last";
                break;
            case get_about_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/app/about";
                break;
            case get_message_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/messages/list";
                break;
            case get_message_detail_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/messages/detail";
                break;
            case help_question_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/help/list";
                break;
            case get_service_content_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/app/agreement";
                break;
            case loading_user_setting_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/settings";
                break;
            case amend_user_setting_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/settings";
                break;
            case submit_user_feedback_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/help/ask";
                break;
            case del_message_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/messages/delete";
                break;
            default:
                break;
        }
        return url;
    }

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
            case get_message_action:
            case get_message_detail_action:
                method = NetRequest.RequestMethod.POST;
                break;
            case help_question_action:
                method = NetRequest.RequestMethod.POST;
                break;
            case amend_user_setting_action:
                method = NetRequest.RequestMethod.POST;
                break;
            case submit_user_feedback_action:
                method = NetRequest.RequestMethod.POST;
                break;
            case del_message_action:
                method = NetRequest.RequestMethod.POST;
                break;
        }
        return method;
    }

    @Override
    protected boolean isNeedToken() {
        boolean flag = true;
        switch (this.mAction) {
            case get_last_version_action:
                flag = false;
                break;
            case get_about_action:
                flag = false;
                break;
            case get_message_action:
                flag = true;
                break;
//            case help_question_action:
//                flag = false;
//                break;
            case get_service_content_action:
                flag = false;
                break;
        }
        return flag;
    }

    @Override
    protected Object handleResponse(NetResponse response) {
        Object obj = null;
        if (BussinessConstants.HttpCommonCode.COMMON_SUCCESS_CODE.equals(response.getResultCode())) {
            try {
                JSONObject rootJsonObj = new JSONObject(response.getData());
                String data = rootJsonObj.get("data").toString();
                String pageInfo;
                Gson gson = new Gson();
                switch (this.mAction) {
                    case get_last_version_action:
                        obj = gson.fromJson(data, AppVersionInfo.class);
                        break;
                    case del_message_action:
                        obj = null;
                        break;
                    case get_about_action:
                        obj = gson.fromJson(data, AppAboutInfo.class);
                        break;
                    case get_message_action:
                        pageInfo = rootJsonObj.get("pageInfo").toString();
                        List<AppMessageInfo> msg_obj1 = gson.fromJson(data, new TypeToken<List<AppMessageInfo>>(){}.getType());
                        PageInfo msg_obj2 = gson.fromJson(pageInfo, PageInfo.class);
                        PageInfoObject msg_infoObject = new PageInfoObject();
                        msg_infoObject.setPageData(msg_obj1);
                        msg_infoObject.setPageInfo(msg_obj2);
                        obj = msg_infoObject;
                        break;
                    case get_message_detail_action:
                        obj = gson.fromJson(data, AppMessageDetailInfo.class);
                        break;
                    case help_question_action:
                        pageInfo = rootJsonObj.get("pageInfo").toString();
                        List<UserQuestionInfo> que_obj1 = gson.fromJson(data, new TypeToken<List<UserQuestionInfo>>(){}.getType());
                        PageInfo que_obj2 = gson.fromJson(pageInfo, PageInfo.class);
                        PageInfoObject que_infoObject = new PageInfoObject();
                        que_infoObject.setPageData(que_obj1);
                        que_infoObject.setPageInfo(que_obj2);
                        obj = que_infoObject;
                        break;
                    case get_service_content_action:
                        obj = gson.fromJson(data, AppAboutInfo.class);
                        break;
                    case loading_user_setting_action:
                        obj = gson.fromJson(data, UserSettingInfo.class);
                        break;
                    case amend_user_setting_action:
                        break;
                    case submit_user_feedback_action:
                        break;
                    default:
                        break;
                }
            } catch (JSONException e) {
                Logger.e(TAG, e.toString());
            }
        }
        return obj;
    }

    /**
     * 检测最新
     *
     * @param invoker  调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param callBack 回调监听
     */
    public void getLastVersion(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = get_last_version_action;
        send(invoker, callBack);
    }


    /**
     * 获取关于
     *
     * @param invoker  调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param callBack 回调监听
     */
    public void getAbout(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = get_about_action;
        send(invoker, callBack);
    }

    /**
     * 帮助问题列表
     */
    public void helpQuestionList(final Object invoker, final MapStrReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = help_question_action;
        this.mData = reqBody;
        send(invoker,callBack);
    }

    public void getMessage(final Object invoker,final AppMessageParame parame,final IHttpCallBack callBack){
        this.mAction = get_message_action;
        this.mData = parame;
        send(invoker,callBack);
    }

    /**
     * 删除消息
     * @param invoker
     * @param reqBody
     * @param callBack
     */
    public void delMessage(final Object invoker,final MapStrReqBody reqBody,final IHttpCallBack callBack){
        this.mAction = del_message_action;
        this.mData = reqBody;
        send(invoker,callBack);
    }

    public void getMessageDetail(final Object invoker,final AppMessageDetailParame parame,final IHttpCallBack callBack){
        this.mAction = get_message_detail_action;
        this.mData = parame;
        send(invoker,callBack);
    }

    /**
     * 获取service
     * @param invoker  调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param callBack 回调监听
     */
    public void getServiceContent(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = get_service_content_action;
        send(invoker, callBack);
    }
    /**
     * 获取用户设置信息
     */
    public void loadingUserSetting(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = loading_user_setting_action;
        send(invoker, callBack);
    }

    /**
     * 修改用户设置信息
     */
    public void amendUserSetting(final Object invoker, final UserSettingInfo settingInfo, final IHttpCallBack callBack) {
        this.mAction = amend_user_setting_action;
        this.mData = settingInfo;
        send(invoker, callBack);
    }

    /**
     * 提交用户反馈
     */
    public void submitUserFeedback(final Object invoker, final MapStrReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = submit_user_feedback_action;
        this.mData = reqBody;
        send(invoker, callBack);
    }
}
