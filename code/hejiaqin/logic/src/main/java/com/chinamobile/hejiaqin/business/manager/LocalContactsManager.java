package com.chinamobile.hejiaqin.business.manager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;

import java.util.ArrayList;
import java.util.List;

public class LocalContactsManager {

    private static LocalContactsManager instance = new LocalContactsManager();

    private LocalContactsManager() {

    }

    public static LocalContactsManager getInstance() {
        return instance;
    }

    public List<ContactsInfo> getLocalContactLst(Context context) {
        List<ContactsInfo> contactsInfoList = new ArrayList<ContactsInfo>();
        ContentResolver cr = context.getContentResolver();
        String str[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID};
        Cursor cur = null;
        try {
            cur = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, str, null,
                    null, null);
            if (cur != null) {
                while (cur.moveToNext()) {
                    ContactsInfo contactsInfo = new ContactsInfo();
                    // 得到手机号码
                    contactsInfo.setNumber(cur.getString(cur
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    contactsInfo.setName(cur.getString(cur
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                    contactsInfoList.add(contactsInfo);
                }
            }
        } catch (Exception e) {

        } finally {
            if (null != cur) {
                cur.close();
            }
        }
        return contactsInfoList;
    }
}
