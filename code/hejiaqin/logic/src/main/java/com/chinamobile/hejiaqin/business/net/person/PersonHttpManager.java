package com.chinamobile.hejiaqin.business.net.person;

import android.content.Context;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.person.PersonInfo;
import com.chinamobile.hejiaqin.business.model.person.PersonalDocument;
import com.chinamobile.hejiaqin.business.model.person.PhysiologyInfo;
import com.chinamobile.hejiaqin.business.model.person.Preference;
import com.chinamobile.hejiaqin.business.net.AbsHttpManager;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.customer.framework.component.log.Logger;
import com.customer.framework.component.net.NameValuePair;
import com.customer.framework.component.net.NetRequest;
import com.customer.framework.component.net.NetResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/4/28.
 */
public class PersonHttpManager extends AbsHttpManager {

    /**
     * 打印标志
     */
    private static final String TAG = "PersonHttpManager";

    private final int action_base = 0;
    /**
     * 查询个人资料
     */
    private final int get_perason_info_action = action_base + 1;

    /**
     * 获取生理数据
     */
    private final int get_physiology_info_action = action_base + 2;
    //修改个人信息
    private final int change_personal_doc_action = action_base + 3;
    private final int change_physiology_info_action = action_base + 4;

    private final int upload_user_avatar_action = action_base + 5;
    //运动偏好的修改
    private final int chage_sports_prefer_action = action_base + 6;
    /**
     * 请求action
     */
    private int mAction;
    private Context mContext;
    private ReqBody mData;

    public PersonHttpManager(Context context) {
        this.mContext = context;
    }


    /**
     * 查询个人资料方法
     *
     * @param invoker  调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param callBack 回调监听
     */
    public void loadPersonInfo(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = get_perason_info_action;
        send(invoker, callBack);
    }

    public void loadPhysiologyInfo(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = get_physiology_info_action;
        send(invoker, callBack);
    }

    /**
     * 修改个人信息
     */
    public void changePersonalDoc(final Object invoker, final PersonalDocument personalInfo, final IHttpCallBack callBack) {
        this.mAction = change_personal_doc_action;
        this.mData = personalInfo;
        send(invoker, callBack);
    }

    /**
     * 生理数据的修改
     */
    public void changePhysiologyInfo(final Object invoker, final PhysiologyInfo physiologyInfo, final IHttpCallBack callBack) {
        this.mAction = change_physiology_info_action;
        this.mData = physiologyInfo;
        send(invoker, callBack);
    }

    /**
     * 上传用户图像
     */
    public void uploadUserAvatarImage(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = upload_user_avatar_action;
        send(invoker, callBack);
    }

    public void changeSportsPrefers(final Object invoker, final Preference preference, final IHttpCallBack callBack) {
        this.mAction = chage_sports_prefer_action;
        this.mData = preference;
        send(invoker, callBack);
    }

    @Override
    protected Context getContext() {
        return this.mContext;
    }

    @Override
    protected String getUrl() {
        String url = null;
        switch (this.mAction) {
            case get_perason_info_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/baseinfo";
                break;
            case get_physiology_info_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/physiology";
                break;
            case change_personal_doc_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/baseinfo";
                break;
            case change_physiology_info_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/physiology";
                break;
            case upload_user_avatar_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/uploadAvatar";
                break;
            case chage_sports_prefer_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/prefers";
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

    @Override
    protected Object handleResponse(NetResponse response) {
        Object obj = null;
        if (BussinessConstants.HttpCommonCode.COMMON_SUCCESS_CODE.equals(response.getResultCode())) {
            try {
                JSONObject rootJsonObj = new JSONObject(response.getData());
                String data = rootJsonObj.get("data").toString();
                Gson gson = new Gson();
                switch (this.mAction) {
                    case get_perason_info_action:
                        obj = gson.fromJson(data, PersonInfo.class);
                        break;
                    case get_physiology_info_action:
                        obj = gson.fromJson(data, PhysiologyInfo.class);
                        break;
                    case change_personal_doc_action:
                        obj = gson.fromJson(data, PersonalDocument.class);
                        break;
                    case change_physiology_info_action:
                        obj = gson.fromJson(data, PhysiologyInfo.class);
                        break;
                    case upload_user_avatar_action:
                        break;
                    case chage_sports_prefer_action:
                        obj = gson.fromJson(data, Preference.class);
                        break;
                }
            } catch (JSONException e) {
                Logger.e(TAG, e.toString());
            }
        }
        return obj;
    }

    /**
     * 请求method类型<BR>
     *
     * @return 默认为GET请求
     */
    protected NetRequest.RequestMethod getRequestMethod() {
        NetRequest.RequestMethod method = NetRequest.RequestMethod.GET;
        switch (this.mAction) {
            case change_personal_doc_action:
                method = NetRequest.RequestMethod.POST;
                break;
            case change_physiology_info_action:
                method = NetRequest.RequestMethod.POST;
                break;
            case upload_user_avatar_action:
                method = NetRequest.RequestMethod.POST;
                break;
            case chage_sports_prefer_action:
                method = NetRequest.RequestMethod.POST;
                break;
        }
        return method;
    }

    @Override
    protected List<NameValuePair> getRequestProperties() {
        List<NameValuePair> properties = super.getRequestProperties();
        if (properties == null) {
            properties = new ArrayList<NameValuePair>();
        }
        switch (this.mAction) {
            case get_perason_info_action:
                //不需要添加属性，直接通过TOKEN就可以了
                break;
        }
        return properties;
    }
}
