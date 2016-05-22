package com.chinamobile.hejiaqin.business.logic.courses;

import com.chinamobile.hejiaqin.business.criteria.CourseCriteria;

/**
 * Created by zhanggj on 2016/4/25.
 */
public interface ICoursesLogic {

    /**
     * 获取练习课程(可根据课程类型获取), 如果课程类型是null将返回所有类型的课程
     */
    void findAllTrainingCourses(int actionId, CourseCriteria criteria);

    /**
     * 获取推荐练习课程
     */
    void findRecommendTrainingCourses(int actionId, CourseCriteria criteria);

    /**
     * 练习课程Borg反馈
     */
    void borgFeedbackForTraining();

    /**
     * 课程评价
     */
    void courseEvaluation();

    /**
     * 获取全部保健康复课程
     */
    void findAllRehabilitationCourses(int actionId, CourseCriteria criteria);

    /**
     * 获取推荐保健康复课程
     */
    void findRecommendRehabilitationCourses(int actionId, CourseCriteria criteria);

    /**
     * 保健康复课程Borg反馈
     */
    void borgFeedbackForRehabilitation();


    /**
     * 获取全部的康兮讲堂课程
     */
    void findAllLectureCourses(int actionId, CourseCriteria criteria);

    /**
     * 讲堂课程的详情
     */
    void lectureCourseDetail(int id);

}
