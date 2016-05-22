package com.chinamobile.hejiaqin.business.logic.courses;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.criteria.CourseCriteria;
import com.chinamobile.hejiaqin.business.model.courses.ResultMessageAction;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.MapStrReqBody;
import com.chinamobile.hejiaqin.business.net.courses.CoursesHttpManager;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.logic.LogicImp;

/**
 * Created by zhanggj on 2016/4/25.
 */
public class CoursesLogic extends LogicImp implements ICoursesLogic {

    @Override
    public void findAllTrainingCourses(final int actionId, final CourseCriteria criteria) {
        new CoursesHttpManager(getContext()).findAllTrainingCourses(null, criteria, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultData(obj);
                msgAction.setResultId(BussinessConstants.CoursesMsgID.FIND_ALL_TRAINING_COURSE_SUCCESS_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                CoursesLogic.this.sendMessage(BussinessConstants.CoursesMsgID.FIND_ALL_TRAINING_COURSE_SUCCESS_MSG_ID, msgAction);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultId(BussinessConstants.CoursesMsgID.FIND_ALL_TRAINING_COURSE_FAILED_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                msgAction.setErrorMsg(code);
                CoursesLogic.this.sendMessage(BussinessConstants.CoursesMsgID.FIND_ALL_TRAINING_COURSE_FAILED_MSG_ID, msgAction);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultId(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                msgAction.setErrorCode(errorCode);
                CoursesLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, msgAction);
            }
        });
    }

    @Override
    public void findRecommendTrainingCourses(final int actionId, final CourseCriteria criteria) {
        new CoursesHttpManager(getContext()).findRecommendTrainingCourses(null, criteria, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultData(obj);
                msgAction.setResultId(BussinessConstants.CoursesMsgID.FIND_ALL_TRAINING_COURSE_SUCCESS_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                CoursesLogic.this.sendMessage(BussinessConstants.CoursesMsgID.FIND_ALL_TRAINING_COURSE_SUCCESS_MSG_ID, msgAction);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultId(BussinessConstants.CoursesMsgID.FIND_ALL_TRAINING_COURSE_FAILED_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                msgAction.setErrorMsg(code);
                CoursesLogic.this.sendMessage(BussinessConstants.CoursesMsgID.FIND_ALL_TRAINING_COURSE_FAILED_MSG_ID, msgAction);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultId(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                msgAction.setErrorCode(errorCode);
                CoursesLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, msgAction);
            }
        });
    }

    @Override
    public void borgFeedbackForTraining() {

    }

    @Override
    public void courseEvaluation() {

    }

    @Override
    public void findAllRehabilitationCourses(final int actionId, final CourseCriteria criteria) {
        new CoursesHttpManager(getContext()).findAllRehabilitationCourses(null, criteria, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultData(obj);
                msgAction.setResultId(BussinessConstants.CoursesMsgID.FIND_ALL_REHABILITATION_COURSE_SUCCESS_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                CoursesLogic.this.sendMessage(BussinessConstants.CoursesMsgID.FIND_ALL_TRAINING_COURSE_SUCCESS_MSG_ID, msgAction);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultId(BussinessConstants.CoursesMsgID.FIND_ALL_REHABILITATION_COURSE_FAILED_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                msgAction.setErrorMsg(code);
                CoursesLogic.this.sendMessage(BussinessConstants.CoursesMsgID.FIND_ALL_REHABILITATION_COURSE_FAILED_MSG_ID, msgAction);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultId(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                msgAction.setErrorCode(errorCode);
                CoursesLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, msgAction);
            }
        });
    }

    @Override
    public void findRecommendRehabilitationCourses(final int actionId, final CourseCriteria criteria) {
        new CoursesHttpManager(getContext()).findRecommendRehabilitationCourses(null, criteria, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultData(obj);
                msgAction.setResultId(BussinessConstants.CoursesMsgID.FIND_RECOMMEND_REHABILITATION_COURSE_SUCCESS_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                CoursesLogic.this.sendMessage(BussinessConstants.CoursesMsgID.FIND_RECOMMEND_REHABILITATION_COURSE_SUCCESS_MSG_ID, msgAction);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultId(BussinessConstants.CoursesMsgID.FIND_RECOMMEND_REHABILITATION_COURSE_FAILED_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                msgAction.setErrorMsg(code);
                CoursesLogic.this.sendMessage(BussinessConstants.CoursesMsgID.FIND_RECOMMEND_REHABILITATION_COURSE_FAILED_MSG_ID, msgAction);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultId(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                msgAction.setErrorCode(errorCode);
                CoursesLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, msgAction);
            }
        });
    }

    @Override
    public void borgFeedbackForRehabilitation() {

    }

    @Override
    public void findAllLectureCourses(final int actionId, final CourseCriteria criteria) {
        new CoursesHttpManager(getContext()).findAllLectureCourses(null, criteria, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultData(obj);
                msgAction.setResultId(BussinessConstants.CoursesMsgID.FIND_ALL_LECTURE_COURSE_SUCCESS_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                CoursesLogic.this.sendMessage(BussinessConstants.CoursesMsgID.FIND_ALL_LECTURE_COURSE_SUCCESS_MSG_ID, msgAction);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultId(BussinessConstants.CoursesMsgID.FIND_ALL_LECTURE_COURSE_FAILED_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                msgAction.setErrorMsg(code);
                CoursesLogic.this.sendMessage(BussinessConstants.CoursesMsgID.FIND_ALL_LECTURE_COURSE_FAILED_MSG_ID, msgAction);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                ResultMessageAction msgAction = new ResultMessageAction();
                msgAction.setResultId(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID);
                msgAction.setCriteria(criteria);
                msgAction.setActionId(actionId);
                msgAction.setErrorCode(errorCode);
                CoursesLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, msgAction);
            }
        });
    }

    @Override
    public void lectureCourseDetail(final int id) {
        MapStrReqBody reqBody = new MapStrReqBody();
        reqBody.add("id", id + "");

        new CoursesHttpManager(getContext()).lectureCourseDetail(null, reqBody, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                CoursesLogic.this.sendMessage(BussinessConstants.CoursesMsgID.FIND_LECTURE_COURSE_DETAIL_SUCCESS_MSG_ID, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                CoursesLogic.this.sendMessage(BussinessConstants.CoursesMsgID.FIND_LECTURE_COURSE_DETAIL_FAILED_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                CoursesLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

}
