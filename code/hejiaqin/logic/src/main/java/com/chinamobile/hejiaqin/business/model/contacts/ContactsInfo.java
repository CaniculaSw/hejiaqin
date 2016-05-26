package com.chinamobile.hejiaqin.business.model.contacts;

import com.customer.framework.utils.StringUtil;

import java.util.Comparator;

/**
 * Created by yupeng on 5/24/16.
 */
public class ContactsInfo {
    private String name;

    private String number;

    private String nameInPinyin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNameInPinyin() {
        return nameInPinyin;
    }

    public void setNameInPinyin(String nameInPinyin) {
        this.nameInPinyin = nameInPinyin;
    }

    /**
     * 获取分组名称，目前大写字母A-Z分组
     *
     * @return
     */
    public String getGroupName() {
        if (StringUtil.isNullOrEmpty(this.nameInPinyin)) {
            return "#";
        }

        char firstChar = this.nameInPinyin.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            return String.valueOf(firstChar);
        }
        return "#";
    }

    @Override
    public String toString() {
        return "ContactsInfo{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", nameInPinyin='" + nameInPinyin + '\'' +
                '}';
    }
}
