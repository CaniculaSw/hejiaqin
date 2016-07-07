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

    // 获取通话的时间
    public String getDialTime() {
        return "15:30";
    }

    // 获取通话的时长
    public String getDialDuration() {
        return "18分12秒";
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
