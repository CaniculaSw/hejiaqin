package com.chinamobile.hejiaqin.business.ui.basic;


import android.app.Activity;

import com.customer.framework.component.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/28.
 */
public class MyActivityManager {

    private static MyActivityManager sInstance = new MyActivityManager();

    private String currentActivityName;

    private List<Activity> ActivityList = new ArrayList<Activity>();

    public static MyActivityManager getInstance() {
        return sInstance;
    }

    public void setCurrentActivityName(String activityName) {
        this.currentActivityName = activityName;
    }

    public boolean isCurrentActity(String activityName) {
        return activityName.equals(currentActivityName);
    }

    public void AddActivity(Activity activity) {
        ActivityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        ActivityList.remove(activity);
    }

    public void finishAllActivity(String execptActivityName) {
        for (Activity activity : ActivityList) {
            Logger.d("MyActivityManager", activity.getClass().getName());
            if (execptActivityName == null || !execptActivityName.equals(activity.getClass().getName())) {
                activity.finish();
                Logger.d("MyActivityManager finish", activity.getClass().getName());
            }
        }
    }

    public void finishActivitys(String[] needFinishActivityNames) {
        for (Activity activity : ActivityList) {
            Logger.d("MyActivityManager", activity.getClass().getName());
            for(int i=0;i<needFinishActivityNames.length;i++)
            {
                if (needFinishActivityNames[i] != null && needFinishActivityNames[i].equals(activity.getClass().getName())) {
                    activity.finish();
                    Logger.d("MyActivityManager finish", activity.getClass().getName());
                }
            }

        }
    }


}
