package com.chinamobile.hejiaqin.business.logic.setting;

import com.chinamobile.hejiaqin.business.model.setting.AppMessageDetailParame;
import com.chinamobile.hejiaqin.business.model.setting.UserSettingInfo;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageParame;
/**
 * Created by zhanggj on 2016/4/25.
 */
public interface ISettingLogic {

    /**
     * @param invoker 调用者(用来区分登录后调用还是setting里面的调用)
     */
    void getLastVersion(Object invoker);

    /**
     * 获取关于信息
     */
    void getAbout();


    /**
     * 获取消息
     * @param actionId 调用方
     * @param parame 获取消息需要的参数（queryType、current_page 必填）
     */
    void getMessage(int actionId, AppMessageParame parame);

    /**
     * 删除消息
     * @param id
     */
    void delMessage(String id);


    void getMessageDetail(AppMessageDetailParame parame);

    /**
     * 帮助问题列表
     */
    void helpQuestionList(int pageSize, int currentPageNo,String lastId);

    /**
     * 获取服务
     */
    void downloadService();

    /**
     * 获取用户设置
     */
    void loadingUserSetting();

    /**
     * 修改用户设置
     */
    void amendUserSetting(UserSettingInfo settingInfo);

    /**
     * 提交用户反馈
     */
    void submitUserFeedback(String feedbackContent);

}
