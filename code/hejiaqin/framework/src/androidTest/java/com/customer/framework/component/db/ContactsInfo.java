package com.customer.framework.component.db;

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

        this.photoLg = photoLg;
    }

    public String getPhotoSm() {
        return photoSm;
    }

    public void setPhotoSm(String photoSm) {

        this.photoSm = photoSm;
    }

    /***/
    public void addNumber(NumberInfo numberInfo) {
        if (null == numberInfo) {
            return;
        }
        this.numberLst.add(numberInfo);
    }

    @Override
    public String toString() {
        return "ContactsInfo{" + "photoSm='" + photoSm + '\'' + ", photoLg='" + photoLg + '\''
                + ", contactMode=" + contactMode + ", numberLst=" + numberLst + ", name='" + name
                + '\'' + ", contactId='" + contactId + '\'' + '}';
    }

}
