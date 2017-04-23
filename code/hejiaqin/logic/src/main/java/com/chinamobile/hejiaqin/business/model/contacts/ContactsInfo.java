package com.chinamobile.hejiaqin.business.model.contacts;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.customer.framework.utils.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 联系人详情
 */
public class ContactsInfo implements Serializable {
    public enum ContactMode {
        // 系统通讯录联系人
        system,
        // 应用通讯录联系人
        app
    }

    private String contactId;

    private String name;

    private List<NumberInfo> numberLst = new ArrayList<NumberInfo>();

    private ContactMode contactMode;

    private String photoLg;

    private String photoSm;

    private SearchUnit searchUnit;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactMode getContactMode() {
        return contactMode;
    }

    public void setContactMode(ContactMode contactMode) {
        this.contactMode = contactMode;
    }

    public List<NumberInfo> getNumberLst() {
        return numberLst;
    }

    public String getPhone() {
        if (null == numberLst || numberLst.isEmpty()) {
            return null;
        }
        return numberLst.get(0).getNumber();
    }

    public String getPhotoLg() {
        return photoLg;
    }

    public void setPhotoLg(String photoLg) {
        if (StringUtil.isNullOrEmpty(photoLg)) {
            this.photoLg = photoLg;
            return;
        }

        if (photoLg.startsWith(BussinessConstants.ServerInfo.HTTP_ADDRESS)) {
            this.photoLg = photoLg;
            return;
        }

        this.photoLg = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/" + photoLg;
    }

    public String getPhotoSm() {
        return photoSm;
    }

    public void setPhotoSm(String photoSm) {

        if (StringUtil.isNullOrEmpty(photoSm)) {
            this.photoSm = photoSm;
            return;
        }

        if (photoSm.startsWith(BussinessConstants.ServerInfo.HTTP_ADDRESS)) {
            this.photoSm = photoSm;
            return;
        }

        this.photoSm = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/" + photoSm;
    }
    /***/
    public void addNumber(NumberInfo numberInfo) {
        if (null == numberInfo) {
            return;
        }
        this.numberLst.add(numberInfo);
    }

    public String getNameInPinyin() {
        if (null == searchUnit) {
            return "";
        }
        return searchUnit.getChinesePinyin();
    }

    public SearchUnit getSearchUnit() {
        return searchUnit;
    }
    /***/
    public void genSearchUnit(PinyinUnit pinyinUnit) {
        if (null == searchUnit) {
            searchUnit = new SearchUnit();
        }
        searchUnit.setPinyinUnit(pinyinUnit);
        searchUnit.setNumberLst(this.numberLst);
    }

    /**
     * 获取分组名称，目前大写字母A-Z分组
     *
     * @return
     */
    public String getGroupName() {
        String chinesePinyin = getNameInPinyin();
        if (StringUtil.isNullOrEmpty(chinesePinyin)) {
            return "#";
        }

        char firstChar = chinesePinyin.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            return String.valueOf(firstChar);
        }
        return "#";
    }

    public boolean isMatch(String input) {
        if (StringUtil.isNullOrEmpty(input)) {
            return false;
        }

        if (null == searchUnit) {
            return false;
        }

        return searchUnit.search(input);
    }

    @Override
    public String toString() {
        return "ContactsInfo{" +
                "photoSm='" + photoSm + '\'' +
                ", photoLg='" + photoLg + '\'' +
                ", contactMode=" + contactMode +
                ", numberLst=" + numberLst +
                ", name='" + name + '\'' +
                ", contactId='" + contactId + '\'' +
                '}';
    }

}
