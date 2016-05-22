package com.chinamobile.hejiaqin.business.manager;

import android.content.Context;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.customer.framework.component.storage.StorageMgr;

import java.util.HashMap;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/17.
 */
public class CourseCacheManager {

    public static void saveCoursePracticeToLoacl(Context context,int index, String text) {
        HashMap map = new HashMap();
        map.put(BussinessConstants.Courses.COURSE_PRACTICE_SELECT_INDEX, index);
        map.put(BussinessConstants.Courses.COURSE_PRACTICE_SELECT_TEXT, text);
        StorageMgr.getInstance().getSharedPStorage(context).save(map);
    }

    public static void saveCourseHealthcareToLoacl(Context context,int index, String text) {
        HashMap map = new HashMap();
        map.put(BussinessConstants.Courses.COURSE_HEALTHCARE_SELECT_INDEX, index);
        map.put(BussinessConstants.Courses.COURSE_HEALTHCARE_SELECT_TEXT, text);
        StorageMgr.getInstance().getSharedPStorage(context).save(map);
    }
}
