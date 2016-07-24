package com.chinamobile.hejiaqin.business.manager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.chinamobile.hejiaqin.business.model.contacts.ContactList;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.model.contacts.PinyinUnit;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.StringUtil;
import com.customer.framework.utils.string.HanziToPinyin;
import com.google.zxing.common.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactsInfoManager {
    private static final String TAG = "ContactsInfoManager";
    private static ContactsInfoManager instance = new ContactsInfoManager();
    List<ContactsInfo> mLocalContactsInfoList = new ArrayList<>();
    List<ContactsInfo> mAppContactsInfoList = new ArrayList<>();

    private ContactsInfoManager() {

    }

    public static ContactsInfoManager getInstance() {
        return instance;
    }

    public List<ContactsInfo> getLocalContactLst(Context context) {
        ContactList contactList = new ContactList();
        ContentResolver cr = context.getContentResolver();
        String str[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE};
        Cursor cur = null;
        try {
            cur = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, str, null,
                    null, null);
            if (cur != null) {
                while (cur.moveToNext()) {
                    // 得到联系人姓名
                    String contactName = cur.getString(cur
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    NumberInfo numberInfo = new NumberInfo();
                    // 得到手机号码
                    numberInfo.setNumber(cur.getString(cur
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    // 得到号码类型
                    numberInfo.setType(cur.getInt(cur
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)));
                    contactList.addLocalContact(contactName, numberInfo);
                }
            }
        } catch (Exception e) {

        } finally {
            if (null != cur) {
                cur.close();
            }
        }
        return contactList.get();
    }


    public void sortContactsInfoLst(Context context, List<ContactsInfo> contactsInfoList) {
        if (null == contactsInfoList) {
            return;
        }

        for (ContactsInfo contactsInfo : contactsInfoList) {
            PinyinUnit pinyinUnit = genPinyinUnit(context, contactsInfo.getName());

            if (null == pinyinUnit || !pinyinUnit.isValid()) {
                continue;
            }

            contactsInfo.genSearchUnit(pinyinUnit);
        }

        Collections.sort(contactsInfoList, new Comparator<ContactsInfo>() {
            @Override
            public int compare(ContactsInfo lContactsInfo, ContactsInfo rContactsInfo) {
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
        LogUtil.d(TAG, "sortContactsInfoLst: " + contactsInfoList);
    }

    public void cacheLocalContactInfo(List<ContactsInfo> localContactsInfoList) {
        mLocalContactsInfoList.clear();
        if (null != localContactsInfoList) {
            mLocalContactsInfoList.addAll(localContactsInfoList);
        }
    }

    public void cacheAppContactInfo(List<ContactsInfo> appContactsInfoList) {
        mAppContactsInfoList.clear();
        if (null != appContactsInfoList) {
            mAppContactsInfoList.addAll(appContactsInfoList);
        }
    }

    public List<ContactsInfo> getCachedLocalContactInfo() {
        List<ContactsInfo> localContactInfos = new ArrayList<>();
        localContactInfos.addAll(mLocalContactsInfoList);
        return localContactInfos;
    }

    public List<ContactsInfo> getCachedAppContactInfo() {
        List<ContactsInfo> appContactInfos = new ArrayList<>();
        appContactInfos.addAll(mAppContactsInfoList);
        return appContactInfos;
    }

    public List<ContactsInfo> searchContactsInfoLst(List<ContactsInfo> contactsInfoList, String input) {
        List<ContactsInfo> matchedContactsInfoList = new ArrayList<>();

        if (null == contactsInfoList) {
            return matchedContactsInfoList;
        }

        if (StringUtil.isNullOrEmpty(input)) {
            matchedContactsInfoList.addAll(contactsInfoList);
            return matchedContactsInfoList;
        }

        for (ContactsInfo contactsInfo : contactsInfoList) {
            if (contactsInfo.isMatch(input)) {
                matchedContactsInfoList.add(contactsInfo);
            }
        }
        return matchedContactsInfoList;
    }

    private PinyinUnit genPinyinUnit(Context context, String chineseWords) {
        if (StringUtil.isNullOrEmpty(chineseWords)) {
            return null;
        }


        char[] chineseWordArray = chineseWords.toCharArray();
        int len = chineseWordArray.length;

        StringBuffer chinesePinyinBuf = new StringBuffer();
        StringBuffer firstCharsBuf = new StringBuffer();
        int[] firstCharIndexs = new int[len];
        for (int i = 0; i < len; i++) {
            String chineseWord = String.valueOf(chineseWordArray[i]);
            // 获取字符对应的拼音
            String pinyin = null;
            if (" ".equals(chineseWord)) {
                pinyin = " ";
            } else {
                pinyin = HanziToPinyin.getInstance(context).hanziToPinyin(chineseWord);
            }

            if (pinyin.length() > 0) {
                firstCharIndexs[i] = chinesePinyinBuf.length();
                firstCharsBuf.append(pinyin.charAt(0));
                chinesePinyinBuf.append(pinyin);
            }
        }

        PinyinUnit pinyinUnit = new PinyinUnit();
        pinyinUnit.setChineseWords(chineseWords);
        pinyinUnit.setChinesePinyin(chinesePinyinBuf.toString().toUpperCase());
        pinyinUnit.setFirstChars(firstCharsBuf.toString().toUpperCase());
        pinyinUnit.setFirstCharIndexs(firstCharIndexs);
        return pinyinUnit;
    }
}
