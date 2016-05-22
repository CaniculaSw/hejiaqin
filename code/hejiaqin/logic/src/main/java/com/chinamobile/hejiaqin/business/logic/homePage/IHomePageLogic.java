package com.chinamobile.hejiaqin.business.logic.homePage;

import com.chinamobile.hejiaqin.business.model.homePage.req.TopCourseInfo;

/**
 * Created by zhanggj on 2016/4/25.
 */
public interface IHomePageLogic {

    /**
     * 获取首页我的健康信息
     */
    void getMyHealth();

    /**
     * 首页我的练习课程
     */
    void getMyPractice();

    /**
     * 首页我的康复保健
     */
    void getMycare();

    /**
     * 获取康兮讲堂
     */
    void getIndexLecture();

    /**
     * 置顶、取消置顶
     */
    void doTopCourseAction(TopCourseInfo info);

}
