package com.chinamobile.hejiaqin.business.logic.homePage;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.homePage.req.TopCourseInfo;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.homePage.HomePageHttpManager;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.logic.LogicImp;

/**
 * Created by zhanggj on 2016/4/25.
 */
public class HomePageLogic extends LogicImp implements IHomePageLogic {

    public void getMyHealth()
    {
        new HomePageHttpManager(getContext()).getMyHealth(null, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                HomePageLogic.this.sendMessage(BussinessConstants.HomePageMsgID.GET_MY_HEALTH_SUCCESS_MSG_ID, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                HomePageLogic.this.sendMessage(BussinessConstants.HomePageMsgID.GET_MY_HEALTH_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                HomePageLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    public void getMyPractice()
    {
        new HomePageHttpManager(getContext()).getMyPractice(null, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                HomePageLogic.this.sendMessage(BussinessConstants.HomePageMsgID.GET_MY_PRACTICE_SUCCESS_MSG_ID, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                HomePageLogic.this.sendMessage(BussinessConstants.HomePageMsgID.GET_MY_PRACTICE_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                HomePageLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    public void getMycare()
    {
        new HomePageHttpManager(getContext()).getMyCare(null, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                HomePageLogic.this.sendMessage(BussinessConstants.HomePageMsgID.GET_MY_CARE_SUCCESS_MSG_ID, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                HomePageLogic.this.sendMessage(BussinessConstants.HomePageMsgID.GET_MY_CARE_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                HomePageLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    public void getIndexLecture()
    {
        new HomePageHttpManager(getContext()).getIndexLecture(null, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                HomePageLogic.this.sendMessage(BussinessConstants.HomePageMsgID.GET_INDEX_LECTURE_SUCCESS_MSG_ID, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                HomePageLogic.this.sendMessage(BussinessConstants.HomePageMsgID.GET_INDEX_LECTURE_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                HomePageLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    public void doTopCourseAction(final TopCourseInfo info)
    {
        //页面先调整再发请求
        new HomePageHttpManager(getContext()).doTopCourseAction(null, info,new IHttpCallBack() {

        @Override
        public void onSuccessful (Object invoker, Object obj){
        }

        @Override
        public void onFailure (Object invoker, String code, String desc){
        }

        @Override
        public void onNetWorkError (NetResponse.ResponseCode errorCode){

        }
    });
    }

}
