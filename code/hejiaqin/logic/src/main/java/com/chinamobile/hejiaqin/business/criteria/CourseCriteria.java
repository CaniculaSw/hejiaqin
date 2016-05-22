package com.chinamobile.hejiaqin.business.criteria;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * Created by Xiadong on 2016/5/17.
 */
public class CourseCriteria extends PageableCriteria implements ReqBody {

    /**
     * 课程ID
     */
    private String courseId;

    /**
     * 课程类型
     */
    private String movementType;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    @Override
    public String toBody() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
