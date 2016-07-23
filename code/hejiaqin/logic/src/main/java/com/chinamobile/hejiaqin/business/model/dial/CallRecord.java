package com.chinamobile.hejiaqin.business.model.dial;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.customer.framework.component.time.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhanggj on 2016/7/17.
 */
public class CallRecord {
    public static final int TYPE_AUDIO_INCOMING = 1;
    public static final int TYPE_AUDIO_OUTGOING = 2;
    public static final int TYPE_AUDIO_MISSING = 3;
    public static final int TYPE_VIDEO_INCOMING = 4;
    public static final int TYPE_VIDEO_OUTGOING = 5;
    public static final int TYPE_VIDEO_MISSING = 6;
    public static final int TYPE_VIDEO_REJECT = 7;

    public static final int INTO_FLAG_HEJIAQING = 1;
    public static final int INTO_FLAG_SYSTEM = 2;

    private String id;
    private String recordId;
    private String peerNumber;
    private String noCountryNumber;
    private String beginTime;
    private int duration;
    private int type;
    private int read;
    private String peerName;
    private String peerHeaderImage;
    private int infoFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPeerNumber() {
        return peerNumber;
    }

    public void setPeerNumber(String peerNumber) {
        this.peerNumber = peerNumber;
    }

    public String getNoCountryNumber() {
        return noCountryNumber;
    }

    public void setNoCountryNumber(String noCountryNumber) {
        this.noCountryNumber = noCountryNumber;
    }

    public String getPeerName() {
        return peerName;
    }

    public void setPeerName(String peerName) {
        this.peerName = peerName;
    }

    public String getPeerHeaderImage() {
        return peerHeaderImage;
    }

    public void setPeerHeaderImage(String peerHeaderImage) {
        this.peerHeaderImage = peerHeaderImage;
    }

    public int getInfoFlag() {
        return infoFlag;
    }

    public void setInfoFlag(int infoFlag) {
        this.infoFlag = infoFlag;
    }

    public String getBeginTime() {
        Date date = DateTimeUtil.parseDateString(beginTime, new SimpleDateFormat("yyyyMMddHHmmss"));
        if (DateTimeUtil.isYesterday(date)) {
            return "昨天";
        } else if (DateTimeUtil.isToday(date)) {
            return DateTimeUtil.getHHMMByDate(date);
        } else if (DateTimeUtil.isInNineDay(date)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            switch (dayOfWeek) {
                case 1:
                    return "星期日";
                case 2:
                    return "星期一";
                case 3:
                    return "星期二";
                case 4:
                    return "星期三";
                case 5:
                    return "星期四";
                case 6:
                    return "星期五";
                default:
                    return "星期六";
            }
        } else {
            return DateTimeUtil.getYYYYMMDDString(date);
        }
    }

    public String getBeginDay() {
        Date date = DateTimeUtil.parseDateString(beginTime, new SimpleDateFormat("yyyyMMddHHmmss"));
        return DateTimeUtil.getHHMMByDate(date);
    }

    public String getBeginHour() {
        Date date = DateTimeUtil.parseDateString(beginTime, new SimpleDateFormat("yyyyMMddHHmmss"));
        if (DateTimeUtil.isYesterday(date)) {
            return "昨天";
        } else if (DateTimeUtil.isToday(date)) {
            return "今天";
        } else if (DateTimeUtil.isInNineDay(date)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            switch (dayOfWeek) {
                case 1:
                    return "星期日";
                case 2:
                    return "星期一";
                case 3:
                    return "星期二";
                case 4:
                    return "星期三";
                case 5:
                    return "星期四";
                case 6:
                    return "星期五";
                default:
                    return "星期六";
            }
        } else {
            return DateTimeUtil.getYYYYMMDDString(date);
        }
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public boolean isReaded() {
        return read == BussinessConstants.DictInfo.YES;
    }
}
