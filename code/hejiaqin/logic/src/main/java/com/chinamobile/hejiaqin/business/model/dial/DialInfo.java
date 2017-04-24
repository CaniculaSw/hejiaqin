package com.chinamobile.hejiaqin.business.model.dial;

/**
 * Created by Administrator on 2016/7/3 0003.
 */
public class DialInfo {
    public enum Type {
        // 呼出
        out,
        // 呼入
        in,
        // 未接
        missed,
        // 拒接
        reject
    }

    private Type type;

    private String dialTime;

    private String dialDuration;

    private String dialDay;

    // 获取通话的时间
    public String getDialTime() {
        return dialTime;
    }

    // 获取通话的时长
    public String getDialDuration() {
        return dialDuration;
    }

    public Type getType() {
        return type;
    }

    public void setDialTime(String dialTime) {
        this.dialTime = dialTime;
    }

    public void setDialDuration(String dialDuration) {
        this.dialDuration = dialDuration;
    }

    public String getDialDay() {
        return dialDay;
    }

    public void setDialDay(String dialDay) {
        this.dialDay = dialDay;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /***/
    public static Type convertType(int type) {
        switch (type) {
            case CallRecord.TYPE_VIDEO_INCOMING:
                return Type.in;
            case CallRecord.TYPE_VIDEO_MISSING:
                return Type.missed;
            case CallRecord.TYPE_VIDEO_OUTGOING:
                return Type.out;
            case CallRecord.TYPE_VIDEO_REJECT:
                return Type.reject;
            default:
                return Type.out;
        }
    }
}
