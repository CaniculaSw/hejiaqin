package com.customer.framework.utils;

/**
 * desc:
 * version 001
 * author:
 * Created: 2016/6/6.
 */
public class TimeUtil {

    /***/
    public static String secToTime(int time) {
        String timeStr = null;
        int minute = 0;
        int second = 0;
        if (time <= 0){
            return "00:00";
        } else {
            minute = time / 60;
            second = time % 60;
            timeStr = unitFormat(minute) + ":" + unitFormat(second);
        }
        return timeStr;
    }
    /***/
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }
    /***/
    public static String disToTime(int time) {
        String timeStr = "";
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "0\"";
        } else {
            minute = time / 60;
            second = time % 60;
            if(minute>0) {
                timeStr = minute+"\'";
            }
            if(second>0) {
                timeStr = timeStr + second + "\"";
            }
        }
        return timeStr;
    }
    /***/
    public static String disToTimeWithLanuage(int time) {
        String timeStr = "";
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "0秒";
        } else {
            minute = time / 60;
            second = time % 60;
            if(minute>0) {
                if(second>0) {
                    timeStr = minute + "分";
                }else {
                    timeStr = minute + "分钟";
                }
            }
            if(second>0) {
                timeStr = timeStr + second + "秒";
            }
        }
        return timeStr;
    }

}
