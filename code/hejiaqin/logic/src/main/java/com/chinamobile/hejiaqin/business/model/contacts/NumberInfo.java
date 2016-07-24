package com.chinamobile.hejiaqin.business.model.contacts;

import android.provider.ContactsContract;

import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.customer.framework.utils.StringUtil;

import java.io.Serializable;

/**
 * 号码详情
 * Created by Administrator on 2016/5/26 0026.
 */
public class NumberInfo implements Serializable {
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

    public String getNumberNoCountryCode() {
        return CommonUtils.getPhoneNumber(number);
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
        return "移动电话";
    }

    public boolean isMatch(String input) {
        if (StringUtil.isNullOrEmpty(input)) {
            return true;
        }

        if (number.contains(input)) {
            return true;
        }
        return false;
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
