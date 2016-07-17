package com.chinamobile.hejiaqin.business.model.dial;

/**
 * Created by zhanggj on 2016/7/17.
 */
public class CallLog {
    public static final int TYPE_AUDIO_INCOMING = 1;
    public static final int TYPE_AUDIO_OUTGOING = 2;
    public static final int TYPE_AUDIO_MISSING = 3;
    public static final int TYPE_VIDEO_INCOMING = 4;
    public static final int TYPE_VIDEO_OUTGOING = 5;
    public static final int TYPE_VIDEO_MISSING = 6;

    private String dbId;
    private String peerNumber;
    private String peerame;
    private String peerHeaderImage;
    private String beginTime;
    private String duration;
    private int type;
    private boolean read;

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getPeerNumber() {
        return peerNumber;
    }

    public void setPeerNumber(String peerNumber) {
        this.peerNumber = peerNumber;
    }

    public String getPeerame() {
        return peerame;
    }

    public void setPeerame(String peerame) {
        this.peerame = peerame;
    }

    public String getPeerHeaderImage() {
        return peerHeaderImage;
    }

    public void setPeerHeaderImage(String peerHeaderImage) {
        this.peerHeaderImage = peerHeaderImage;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
