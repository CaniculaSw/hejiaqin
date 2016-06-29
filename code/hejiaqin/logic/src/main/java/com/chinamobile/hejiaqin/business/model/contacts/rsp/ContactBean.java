package com.chinamobile.hejiaqin.business.model.contacts.rsp;

/**
 * Created by Administrator on 2016/6/24 0024.
 */
public class ContactBean {
    private String contactId;

    private String name;

    private String phone;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
