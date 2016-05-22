package com.chinamobile.hejiaqin.business.model.homePage;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/5/11.
 */
public class MyPracticeInfo {

    //课程ID
    private String id;
    //课程名称
    private String title;
    //一周练习次数（推荐课程无此参数）
    private long weeknum;
    //一周练习次数（推荐课程无此参数）
    private long totalnum;
    //分数
    private long score;
    //1我的练习；2推荐课程
    private String type;
    //本课程的参与人数
    private long participants;
    //运动类型 1有氧；2力量；3瑜伽；4国术；
    private String movementType;
    //课程等级 有氧、力量无此字段；瑜伽和国术显示等级
    private String grade;
    //是否已经置顶
    private String practiceDate;

    private String placedTop;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public long getParticipants() {
        return participants;
    }

    public void setParticipants(long participants) {
        this.participants = participants;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlacedTop() {
        return placedTop;
    }

    public void setPlacedTop(String placedTop) {
        this.placedTop = placedTop;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

    public String getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(String practiceDate) {
        this.practiceDate = practiceDate;
    }

    public long getWeeknum() {
        return weeknum;
    }

    public void setWeeknum(long weeknum) {
        this.weeknum = weeknum;
    }

    public long getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(long totalnum) {
        this.totalnum = totalnum;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
