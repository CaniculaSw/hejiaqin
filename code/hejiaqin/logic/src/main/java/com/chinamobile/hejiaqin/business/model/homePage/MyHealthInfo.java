package com.chinamobile.hejiaqin.business.model.homePage;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/5/11.
 */
public class MyHealthInfo {
    private String id;
    private String name;
    private String loginid;
    //成长值，整数
    private long growup;
    //成长值在所有练习人数中的比例，字符
    private String ranking;
    //本周锻炼次数，整数
    private long weeknum;
    //本日锻炼次数，整数
    private long nownum;
    //健康信息描述
    private String describe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public long getGrowup() {
        return growup;
    }

    public void setGrowup(long growup) {
        this.growup = growup;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public long getWeeknum() {
        return weeknum;
    }

    public void setWeeknum(long weeknum) {
        this.weeknum = weeknum;
    }

    public long getNownum() {
        return nownum;
    }

    public void setNownum(long nownum) {
        this.nownum = nownum;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
