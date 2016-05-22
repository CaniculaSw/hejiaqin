package com.chinamobile.hejiaqin.business.model.courses;

import java.util.List;

/**
 * Created by wbg on 2016/5/17.
 */
public class PracticeInfo {
    private int id;
    private String title;
    private int participants;   //参加的人数
    private String signup;
    private String movementType;//课程类型
    private int joined;         //是否已参加
    private String coverImage;
    private List<RangeInfo> range;
    private List<ApparatusInfo> apparatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public String getSignup() {
        return signup;
    }

    public void setSignup(String signup) {
        this.signup = signup;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public int getJoined() {
        return joined;
    }

    public void setJoined(int joined) {
        this.joined = joined;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<RangeInfo> getRange() {
        return range;
    }

    public void setRange(List<RangeInfo> range) {
        this.range = range;
    }

    public List<ApparatusInfo> getApparatus() {
        return apparatus;
    }

    public void setApparatus(List<ApparatusInfo> apparatus) {
        this.apparatus = apparatus;
    }
}
