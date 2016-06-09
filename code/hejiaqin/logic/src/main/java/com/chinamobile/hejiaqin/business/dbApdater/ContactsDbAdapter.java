package com.chinamobile.hejiaqin.business.dbApdater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.chinamobile.hejiaqin.business.model.contacts.ContactList;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.customer.framework.component.db.DatabaseHelper;
import com.customer.framework.component.db.DatabaseInfo;
import com.customer.framework.component.db.operation.BaseDbAdapter;
import com.customer.framework.component.db.operation.DbOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanggj on 2016/5/30.
 */
public class ContactsDbAdapter extends BaseDbAdapter {

    private static ContactsDbAdapter mContactsDbAdapter;
    private Context mContext;
    private DatabaseHelper mDbHelper;
    private String mUserId;

    private ContactsDbAdapter(Context context, String userId) {
        this.mContext = context.getApplicationContext();
        this.mUserId = userId;
        this.mDbHelper = DatabaseHelper.getInstance(this.mContext, mUserId);
    }

    public static synchronized ContactsDbAdapter getInstance(Context context, String userId) {
        if ((mContactsDbAdapter == null) || (!userId.equals(mContactsDbAdapter.mUserId))) {
            mContactsDbAdapter = new ContactsDbAdapter(context, userId);
        }
        return mContactsDbAdapter;
    }

    public void add(List<ContactsInfo> contactsInfoList) {
        if (null == contactsInfoList || contactsInfoList.isEmpty()) {
            return;
        }
        List<DbOperation> operationList = new ArrayList<DbOperation>();
        for (ContactsInfo contactsInfo : contactsInfoList) {
            List<ContentValues> contentValuesList = getCVByContactsInfo(contactsInfo);
            for (ContentValues contentValues : contentValuesList) {
                operationList.add(DbOperation.newInsert(DatabaseInfo.ContactsInfo.TABLE_NAME).withValues(contentValues).build());
            }
        }
        super.applyBatch(operationList);
    }

    public List<ContactsInfo> queryAll() {
        ContactList contactList = new ContactList();

        Cursor cursor = rawQuery(DatabaseInfo.ContactsInfo.SQL_SElECT_ALL, null);
        if (null == cursor) {
            return contactList.get();
        }

        while (cursor.moveToNext()) {
            // 得到联系人姓名
            String contactName = cursor.getString(cursor
                    .getColumnIndex(DatabaseInfo.ContactsInfo.NAME));

            NumberInfo numberInfo = new NumberInfo();
            // 得到手机号码
            numberInfo.setNumber(cursor.getString(cursor
                    .getColumnIndex(DatabaseInfo.ContactsInfo.NUMBER)));
            // 得到号码类型
            numberInfo.setType(cursor.getInt(cursor
                    .getColumnIndex(DatabaseInfo.ContactsInfo.NUMBER_TYPE)));
            contactList.addLocalContact(contactName, numberInfo);
        }
        return contactList.get();
    }


    /**
     * 根据ids删除
     *
     * @param ids
     */
    public void delById(String[] ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        List<DbOperation> operationList = new ArrayList<DbOperation>();
        for (int i = 0; i < ids.length; i++) {
            operationList.add(DbOperation.newDelete(DatabaseInfo.ContactsInfo.TABLE_NAME).withSelection(DatabaseInfo.CallRecord.TABLE_ID + " = ? ", new String[]{ids[i]}).build());
        }
        super.applyBatch(operationList);
    }

    /**
     * 全部删除
     */
    public void delAll() {
        super.delete(DatabaseInfo.ContactsInfo.TABLE_NAME, null, null);
    }

    @NonNull
    private List<ContentValues> getCVByContactsInfo(ContactsInfo contactsInfo) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        List<NumberInfo> numberInfoList = contactsInfo.getNumberLst();
        if (null == numberInfoList) {
            return contentValuesList;
        }

        for (NumberInfo numberInfo : numberInfoList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseInfo.ContactsInfo.CONTACT_MODE, contactsInfo.getContactMode().ordinal());
            contentValues.put(DatabaseInfo.ContactsInfo.NAME, contactsInfo.getName());
            contentValues.put(DatabaseInfo.ContactsInfo.NAME_IN_PINYIN, contactsInfo.getNameInPinyin());
            contentValues.put(DatabaseInfo.ContactsInfo.NUMBER, numberInfo.getNumber());
            contentValues.put(DatabaseInfo.ContactsInfo.NUMBER_DESC, numberInfo.getDesc());
            contentValues.put(DatabaseInfo.ContactsInfo.NUMBER_TYPE, numberInfo.getType());
            contentValues.put(DatabaseInfo.ContactsInfo.CONTACT_ID, contactsInfo.getName() + numberInfo.getNumber());
            contentValuesList.add(contentValues);
        }
        return contentValuesList;
    }

    @Override
    protected SQLiteOpenHelper getDbHelper() {
        return this.mDbHelper;
    }

}
