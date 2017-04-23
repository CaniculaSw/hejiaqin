package com.chinamobile.hejiaqin.business.ui.basic;


import android.app.Activity;

import com.customer.framework.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/28.
 */
public class MyActivityManager {

    private static MyActivityManager sInstance = new MyActivityManager();

    private String currentActivityName;

    private List<Activity> activityArrayList = new ArrayList<Activity>();

    public static MyActivityManager getInstance() {
        return sInstance;
    }

    public void setCurrentActivityName(String activityName) {
        this.currentActivityName = activityName;
    }

    public boolean isCurrentActity(String activityName) {
        return activityName.equals(currentActivityName);
    }
    /***/
    public void addActivity(Activity activity) {
        activityArrayList.add(activity);
    }
    /***/
    public void removeActivity(Activity activity) {
        activityArrayList.remove(activity);
    }
    /***/
    public void finishAllActivity(String execptActivityName) {
        for (Activity activity : activityArrayList) {
            LogUtil.d("MyActivityManager", activity.getClass().getName());
            if (execptActivityName == null || !execptActivityName.equals(activity.getClass().getName())) {
                activity.finish();
                LogUtil.d("MyActivityManager finish", activity.getClass().getName());
            }
        }
    }
    /***/
    public void finishActivitys(String[] needFinishActivityNames) {
        for (Activity activity : activityArrayList) {
            LogUtil.d("MyActivityManager", activity.getClass().getName());
            for(int i=0;i<needFinishActivityNames.length;i++)
            {
                if (needFinishActivityNames[i] != null && needFinishActivityNames[i].equals(activity.getClass().getName())) {
                    activity.finish();
                    LogUtil.d("MyActivityManager finish", activity.getClass().getName());
                }
            }

        }
    }


}
