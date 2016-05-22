package com.chinamobile.hejiaqin.business.logic.setting;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageDetailParame;
import com.chinamobile.hejiaqin.business.model.setting.PageInfoObject;
import com.chinamobile.hejiaqin.business.model.setting.UserSettingInfo;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageInfo;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageParame;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageResutl;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.MapStrReqBody;
import com.chinamobile.hejiaqin.business.net.setting.SettingHttpManager;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.logic.LogicImp;

import java.util.List;

/**
 * Created by zhanggj on 2016/4/25.
 */
public class SettingLogic extends LogicImp implements ISettingLogic {

    @Override
    public void getLastVersion(Object invoker) {
        new SettingHttpManager(getContext()).getLastVersion(invoker, new IHttpCallBack() {
                    @Override
                    public void onSuccessful(Object invoker, Object obj) {
                        if (invoker != null && BussinessConstants.Setting.GET_VERSION_AFTER_LOGIN.equals((String) invoker)) {
                            SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_VERSION_AFTER_LOGIN_SUCCESS_MSG_ID, obj);
                        } else {
                            SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_VERSION_SUCCESS_MSG_ID, obj);
                        }
                    }

                    @Override
                    public void onFailure(Object invoker, String code, String desc) {
                        if (invoker != null && BussinessConstants.Setting.GET_VERSION_AFTER_LOGIN.equals((String) invoker)) {
                            SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_VERSION_AFTER_LOGIN_FAIL_MSG_ID, code);
                        } else {
                            SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_VERSION_FAIL_MSG_ID, code);
                        }
                    }

                    @Override
                    public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                        SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
                    }
                }
        );
    }


    @Override
    public void getAbout() {
        new SettingHttpManager(getContext()).getAbout(null, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_ABOUT_SUCCESS_MSG_ID, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_ABOUT_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void getMessage(final int actionId, final AppMessageParame parame) {
        new SettingHttpManager(getContext()).getMessage(null, parame, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                PageInfoObject infoObject = (PageInfoObject) obj;
                List<AppMessageInfo> msgData = (List<AppMessageInfo>) infoObject.getPageData();
                AppMessageResutl resutl = new AppMessageResutl();
                resutl.setParame(parame);
                resutl.setActionId(actionId);
                resutl.setMsgData(msgData);
                resutl.setPageInfo(infoObject.getPageInfo());
                resutl.setResultId(BussinessConstants.SettingMsgID.GET_MESSAGE_SUCCESS_MSG_ID);
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_MESSAGE_SUCCESS_MSG_ID, resutl);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                AppMessageResutl resutl = new AppMessageResutl();
                resutl.setParame(parame);
                resutl.setActionId(actionId);
                resutl.setErrorMsg(code);
                resutl.setResultId(BussinessConstants.SettingMsgID.GET_MESSAGE_FAIL_MSG_ID);
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_MESSAGE_FAIL_MSG_ID, resutl);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                AppMessageResutl resutl = new AppMessageResutl();
                resutl.setParame(parame);
                resutl.setActionId(actionId);
                resutl.setErrorCode(errorCode);
                resutl.setResultId(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID);
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, resutl);
            }
        });
    }

    @Override
    public void delMessage(final String id) {
        MapStrReqBody reqBody = new MapStrReqBody();
        reqBody.add("messageid", id);

        new SettingHttpManager(getContext()).delMessage(null,reqBody, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.DEL_MESSAGE_SUCCESS_MSG_ID, id);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.DEL_MESSAGE_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void getMessageDetail(AppMessageDetailParame parame) {
        new SettingHttpManager(getContext()).getMessageDetail(null, parame, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_MESSAGE_DETAIL_SUCCESS_MSG_ID, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_MESSAGE_DETAIL_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void helpQuestionList(int pageSize, int currentPageNo,String lastId) {
        MapStrReqBody reqBody = new MapStrReqBody();
        reqBody.add("records_onepage", String.valueOf(pageSize));
        reqBody.add("current_page", String.valueOf(currentPageNo));
        reqBody.add("last_record_id", lastId);
        new SettingHttpManager(getContext()).helpQuestionList(null, reqBody, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.HELP_QUESTION_LIST_SUCCESS_MSG_ID, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.HELP_QUESTION_LIST_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    /**
     * 获取服务
     */
    @Override
    public void downloadService() {
        new SettingHttpManager(getContext()).getServiceContent(null, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_SERVICE_CONTENT_SUCCESS_MSG_ID, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_SERVICE_FAIL_CONTENT_MSG_ID, code);

            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void loadingUserSetting() {
        new SettingHttpManager(getContext()).loadingUserSetting(null, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.LOAD_USER_SETTING_SUCCESS_MSG_ID, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.LOAD_USER_SETTING_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void amendUserSetting(UserSettingInfo settingInfo) {
        new SettingHttpManager(getContext()).amendUserSetting(null, settingInfo, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.AMEND_USER_SETTING_SUCCESS_MSG_ID, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.AMEND_USER_SETTING_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void submitUserFeedback(String feedbackContent) {
        MapStrReqBody reqBody = new MapStrReqBody();
        reqBody.add("content", feedbackContent);
        new SettingHttpManager(getContext()).submitUserFeedback(null, reqBody, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                SettingLogic.this.sendEmptyMessage(BussinessConstants.SettingMsgID.SUBMIT_USER_FEEDBACK_SUCCESS_MSG_ID);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.SUBMIT_USER_FEEDBACK_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }
}
