package com.chinamobile.hejiaqin.business.manager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.customer.framework.component.log.Logger;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactsInfoManager {
    private static final String TAG = "ContactsInfoManager";
    private static ContactsInfoManager instance = new ContactsInfoManager();

    private ContactsInfoManager() {

    }

    public static ContactsInfoManager getInstance() {
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


    public void sortContactsInfoLst(List<ContactsInfo> contactsInfoList) {
        if (null == contactsInfoList) {
            return;
        }

        for (ContactsInfo contactsInfo : contactsInfoList) {
            contactsInfo.setNameInPinyin(StringUtil.hanzi2Pinyin(contactsInfo.getName()));
        }
        Logger.d(TAG, "sortContactsInfoLst, after set pinyin name:  " + contactsInfoList);

        Collections.sort(contactsInfoList, new Comparator<ContactsInfo>() {
            @Override
            public int compare(ContactsInfo lContactsInfo, ContactsInfo rContactsInfo) {
//                char lFirstNameChar = (lContactsInfo == null) ? 255 :
//                        (StringUtil.isNullOrEmpty(lContactsInfo.getNameInPinyin()) ? 255 : lContactsInfo.getNameInPinyin().charAt(0));
//                char rFirstNameChar = (rContactsInfo == null) ? 255 :
//                        (StringUtil.isNullOrEmpty(rContactsInfo.getNameInPinyin()) ? 255 : rContactsInfo.getNameInPinyin().charAt(0));
//
//                return (lFirstNameChar - rFirstNameChar);

                String lNameInPinyin = lContactsInfo == null ? "" : lContactsInfo.getNameInPinyin();
                String rNameInPinyin = rContactsInfo == null ? "" : rContactsInfo.getNameInPinyin();

                if (StringUtil.isNullOrEmpty(lNameInPinyin) && StringUtil.isNullOrEmpty(rNameInPinyin)) {
                    return 0;
                }

                if (StringUtil.isNullOrEmpty(lNameInPinyin)) {
                    return -1;
                }

                if (StringUtil.isNullOrEmpty(rNameInPinyin)) {
                    return 1;
                }
                return lNameInPinyin.compareTo(rNameInPinyin);
            }
        });
        Logger.d(TAG, "sortContactsInfoLst: " + contactsInfoList);
    }
}
