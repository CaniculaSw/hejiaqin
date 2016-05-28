package com.chinamobile.hejiaqin.business.model.contacts;

import android.provider.ContactsContract;

/**
 * 号码详情
 * Created by Administrator on 2016/5/26 0026.
 */
public class NumberInfo {
    /**
     * 号码
     */
    private String number;

    /**
     * 号码类型
     */
    private int type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        if (type == ContactsContract.CommonDataKinds.Phone.TYPE_HOME) {
            return "家庭电话";
        } else if (type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
            return "移动电话";
        }
        return "";
    }

    @Override
    public String toString() {
        return "NumberInfo{" +
                "number='" + number + '\'' +
                ", type=" + type +
                ", desc='" + getDesc() + '\'' +
                '}';
    }
}
