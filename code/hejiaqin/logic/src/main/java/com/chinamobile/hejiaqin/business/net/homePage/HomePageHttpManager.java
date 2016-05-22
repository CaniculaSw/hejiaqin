package com.chinamobile.hejiaqin.business.net.homePage;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.homePage.HomeForumInfo;
import com.chinamobile.hejiaqin.business.model.homePage.MyHealthInfo;
import com.chinamobile.hejiaqin.business.model.homePage.MyPracticeInfo;
import com.chinamobile.hejiaqin.business.model.homePage.req.TopCourseInfo;
import com.chinamobile.hejiaqin.business.net.AbsHttpManager;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.MapStrReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.customer.framework.component.log.Logger;
import com.customer.framework.component.net.NameValuePair;
import com.customer.framework.component.net.NetRequest;
import com.customer.framework.component.net.NetResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zhanggj on 2016/4/25.
 */
public class HomePageHttpManager extends AbsHttpManager {
    /**
     * 打印标志
     */
    private static final String TAG = "HomePageHttpManager";

    private final int action_base = 0;
    //1练习；2保健
    private static final String COURSE_CATEGORY_PRACTICE = "1";
    //1练习；2保健
    private static final String COURSE_CATEGORY_CARE = "2";
    /**
     * 获取首页我的健康信息
     */
    private final int get_my_health_action = action_base + 1;
    /**
     * 首页我的练习课程
     */
    private final int get_my_practice_action = action_base + 2;
    /**
     * 首页我的康复保健
     */
    private final int get_my_care_action = action_base + 3;
    /**
     * 获取康兮讲堂
     */
    private final int get_index_lecture_action = action_base + 4;
    /**
     * 置顶、取消置顶
     */
    private final int do_topcourse_action = action_base + 5;

    private Context mContext;
    /**
     * 请求action
     */
    private int mAction;

    private ReqBody mData;

    public HomePageHttpManager(Context context)
    {
        this.mContext =context;
    }

    @Override
    protected Context getContext()
    {
        return  this.mContext;
    }
    @Override
    protected String getUrl() {
        String url = null;
        switch (this.mAction) {
            case get_my_health_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/index/myhealth";
                break;
            case get_my_practice_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/index/mypractice";
                break;
            case get_my_care_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/index/mypractice";
                break;
            case get_index_lecture_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/index/lecture";
                break;
            case do_topcourse_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/index/topcourse";
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
     * @return 请求
     */
    protected NetRequest.RequestMethod getRequestMethod() {
        NetRequest.RequestMethod method = NetRequest.RequestMethod.GET;
        switch (this.mAction) {
            case get_my_practice_action:
            case get_my_care_action:
            case do_topcourse_action:
                method = NetRequest.RequestMethod.POST;
                break;
            default:
                break;
        }
        return method;
    }

    @Override
    protected List<NameValuePair> getRequestProperties() {
        List<NameValuePair> properties = super.getRequestProperties();
        return properties;
    }

    @Override
    protected Object handleResponse(NetResponse response) {
        Object obj = null;
        if (BussinessConstants.HttpCommonCode.COMMON_SUCCESS_CODE.equals(response.getResultCode())) {
            try {
                JSONObject rootJsonObj = new JSONObject(response.getData());
                if(!rootJsonObj.has("data"))
                {
                    return obj;
                }
                String data = rootJsonObj.get("data").toString();
                Gson gson = new Gson();
                switch (this.mAction) {
                    case get_my_health_action:
                        obj = gson.fromJson(data, MyHealthInfo.class);
                        break;
                    case get_my_practice_action:
                        obj = gson.fromJson(data, new TypeToken<List<MyPracticeInfo>>(){}.getType());
                        break;
                    case get_my_care_action:
                        obj = gson.fromJson(data, new TypeToken<List<MyPracticeInfo>>(){}.getType());
                        break;
                    case get_index_lecture_action:
                        obj = gson.fromJson(data, new TypeToken<List<HomeForumInfo>>(){}.getType());
                        break;
                    case do_topcourse_action:
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
     * 获取我的健康信息
     *
     * @param invoker  调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param callBack 回调监听
     */
    public void getMyHealth(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = get_my_health_action;
        send(invoker, callBack);
    }

    /**
     * 首页我的练习课程
     */
    public void getMyPractice(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = get_my_practice_action;
        MapStrReqBody reqBody = new MapStrReqBody();
        reqBody.add("courseCategory", COURSE_CATEGORY_PRACTICE);
        this.mData = reqBody;
        send(invoker, callBack);
    }

    /**
     * 首页我的康复保健
     */
    public void getMyCare(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = get_my_care_action;
        MapStrReqBody reqBody = new MapStrReqBody();
        //TODO TEST
        reqBody.add("courseCategory", COURSE_CATEGORY_PRACTICE);
        //reqBody.add("courseCategory", COURSE_CATEGORY_CARE);
        this.mData = reqBody;
        send(invoker, callBack);
    }

    /**
     * 获取康兮讲堂
     */
    public void getIndexLecture(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = get_index_lecture_action;
        send(invoker, callBack);
    }

    /**
     * 置顶、取消置顶
     */
    public void doTopCourseAction(final Object invoker, TopCourseInfo info, final IHttpCallBack callBack) {
        this.mAction = do_topcourse_action;
        this.mData = info;
        send(invoker, callBack);
    }
}
