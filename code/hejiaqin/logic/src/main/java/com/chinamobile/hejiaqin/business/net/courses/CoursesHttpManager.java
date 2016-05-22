package com.chinamobile.hejiaqin.business.net.courses;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.PageableInfo;
import com.chinamobile.hejiaqin.business.model.RequestResult;
import com.chinamobile.hejiaqin.business.model.courses.LectureInfo;
import com.chinamobile.hejiaqin.business.model.courses.PracticeInfo;
import com.chinamobile.hejiaqin.business.net.AbsHttpManager;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
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
public class CoursesHttpManager extends AbsHttpManager {

    private static final String TAG = "CoursesHttpManager";

    private final int action_base = 0;
    private final int find_all_training_course_action = action_base + 1;
    private final int find_recommend_training_course_action = action_base + 2;
    private final int find_all_rehabilitation_course_action = action_base + 3;
    private final int find_recommend_rehabilitation_course_action = action_base + 4;
    private final int find_all_lecture_course_action = action_base + 5;
    private final int find_lecture_detail_course_action = action_base + 6;

    /**
     * 请求的 Action
     */
    private int mAction;
    private ReqBody mData;
    private Context mContext;

    public CoursesHttpManager(Context context) {
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
            case find_all_training_course_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/course/all";
                break;
            case find_recommend_training_course_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/course/recommend";
                break;
            case find_all_rehabilitation_course_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/rehabilitation/all";
                break;
            case find_recommend_rehabilitation_course_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/rehabilitation/recommend";
                break;
            case find_all_lecture_course_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/lecture/list";
                break;
            case find_lecture_detail_course_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/lecture/detail";
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

    @Override
    protected NetRequest.RequestMethod getRequestMethod() {
        NetRequest.RequestMethod method = NetRequest.RequestMethod.GET;
        switch (this.mAction) {
            case find_all_training_course_action:
                method = NetRequest.RequestMethod.POST;
                break;
            case find_recommend_training_course_action:
                method = NetRequest.RequestMethod.POST;
                break;
            case find_all_rehabilitation_course_action:
                method = NetRequest.RequestMethod.POST;
                break;
            case find_recommend_rehabilitation_course_action:
                method = NetRequest.RequestMethod.POST;
                break;
            case find_all_lecture_course_action:
            case find_lecture_detail_course_action:
                method = NetRequest.RequestMethod.POST;
                break;
            default:
                break;
        }
        return method;
    }

    @Override
    protected Object handleResponse(NetResponse response) {
        Object object = null;
        if (BussinessConstants.HttpCommonCode.COMMON_SUCCESS_CODE.equals(response.getResultCode())) {
            try {
                JSONObject rootJsonObj = new JSONObject(response.getData());
                String data = rootJsonObj.get("data").toString();
                Gson gson = new Gson();

                PageableInfo pageableInfo = null;

                List resultDataList;
                switch (this.mAction) {
                    case find_all_training_course_action:
                    case find_recommend_training_course_action:
                    case find_all_rehabilitation_course_action:
                    case find_recommend_rehabilitation_course_action:
                        resultDataList = gson.fromJson(data, new TypeToken<List<PracticeInfo>>(){}.getType());
                        pageableInfo = gson.fromJson(rootJsonObj.get("pageInfo").toString(), PageableInfo.class);
                        object = new RequestResult(resultDataList, pageableInfo);
                        break;
                    case find_all_lecture_course_action:
                        resultDataList = gson.fromJson(data, new TypeToken<List<LectureInfo>>(){}.getType());
                        pageableInfo = gson.fromJson(rootJsonObj.get("pageInfo").toString(), PageableInfo.class);
                        object = new RequestResult(resultDataList, pageableInfo);
                        break;
                    case find_lecture_detail_course_action:
                        object = gson.fromJson(data,LectureInfo.class);
                        break;
                    default:
                        break;
                }
            } catch (JSONException ex) {
                Logger.e(TAG, ex.toString());
            }
        }
        return object;
    }

    /**
     * 获取全部练习课程
     *
     * @param invoker
     * @param callBack
     */
    public void findAllTrainingCourses(final Object invoker, final ReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = find_all_training_course_action;
        this.mData = reqBody;
        send(invoker, callBack);
    }

    /**
     * 获取推荐练习课程
     *
     * @param invoker
     * @param callBack
     */
    public void findRecommendTrainingCourses(final Object invoker, final ReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = find_recommend_training_course_action;
        this.mData = reqBody;
        send(invoker, callBack);
    }

    /**
     * 获取全部康复课程
     *
     * @param invoker
     * @param callBack
     */
    public void findAllRehabilitationCourses(final Object invoker, final ReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = find_all_rehabilitation_course_action;
        this.mData = reqBody;
        send(invoker, callBack);
    }

    /**
     * 获取推荐康复课程
     *
     * @param invoker
     * @param callBack
     */
    public void findRecommendRehabilitationCourses(final Object invoker, final ReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = find_recommend_rehabilitation_course_action;
        this.mData = reqBody;
        send(invoker, callBack);
    }

    /**
     * 获取全部康兮讲堂课程
     *
     * @param invoker
     * @param callBack
     */
    public void findAllLectureCourses(final Object invoker, final ReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = find_all_lecture_course_action;
        this.mData = reqBody;
        send(invoker, callBack);
    }

    /**
     * 获取康兮讲堂明细
     * @param invoker
     * @param reqBody
     * @param callBack
     */
    public void lectureCourseDetail(final Object invoker, final ReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = find_lecture_detail_course_action;
        this.mData = reqBody;
        send(invoker, callBack);
    }
}
