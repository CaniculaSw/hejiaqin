package com.customer.framework.component.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.customer.framework.component.db.operation.BaseDbAdapter;
import com.customer.framework.component.db.operation.DbOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 2016/5/30.
 */
public class DbAdapterTest extends BaseDbAdapter {

    private static DbAdapterTest mDbAdapterTest;
    private Context mContext;
    private DatabaseHelper mDbHelper;
    private String mUserId;

    private DbAdapterTest(Context context, String userId) {
        this.mContext = context.getApplicationContext();
        this.mUserId = userId;
        this.mDbHelper = DatabaseHelper.getInstance(this.mContext, mUserId);
    }

    public static synchronized DbAdapterTest getInstance(Context context, String userId) {
        if ((mDbAdapterTest == null) || (!userId.equals(mDbAdapterTest.mUserId))) {
            mDbAdapterTest = new DbAdapterTest(context, userId);
        }
        return mDbAdapterTest;
    }

    /***/
    public void add(List<ContactsInfo> contactsInfoList) {
        if (null == contactsInfoList || contactsInfoList.isEmpty()) {
            return;
        }
        List<DbOperation> operationList = new ArrayList<DbOperation>();
        for (ContactsInfo contactsInfo : contactsInfoList) {
            List<ContentValues> contentValuesList = getCVByContactsInfo(contactsInfo);
            if (null == contentValuesList || contentValuesList.isEmpty()) {
                continue;
            }

            for (ContentValues contentValues : contentValuesList) {
                operationList.add(DbOperation.newInsert(DatabaseInfo.ContactsInfo.TABLE_NAME)
                        .withValues(contentValues).build());
            }
        }
        super.applyBatch(operationList);
    }

    /***/
    public void update(ContactsInfo contactsInfo) {
        if (null == contactsInfo) {
            return;
        }

        List<ContentValues> contentValuesList = getCVByContactsInfo(contactsInfo);
        if (null == contentValuesList || contentValuesList.isEmpty()) {
            return;
        }

        String selection = DatabaseInfo.ContactsInfo.CONTACT_ID + " = ? ";
        String[] selectionArgs = { contactsInfo.getContactId() };

        List<DbOperation> operationList = new ArrayList<DbOperation>();
        for (ContentValues contentValues : contentValuesList) {
            operationList.add(DbOperation.newUpdate(DatabaseInfo.ContactsInfo.TABLE_NAME)
                    .withSelection(selection, selectionArgs).withValues(contentValues).build());
        }
        super.applyBatch(operationList);
    }

    /***/
    public List<ContactsInfo> queryAll() {
        ContactList contactList = new ContactList();

        Cursor cursor = rawQuery(DatabaseInfo.ContactsInfo.SQL_SElECT_ALL, null);
        if (null == cursor) {
            return contactList.get();
        }

        while (cursor.moveToNext()) {
            ContactBean contactBean = new ContactBean();
            contactBean.setContactId(cursor.getString(cursor
                    .getColumnIndex(DatabaseInfo.ContactsInfo.CONTACT_ID)));
            // 得到联系人姓名
            contactBean.setName(cursor.getString(cursor
                    .getColumnIndex(DatabaseInfo.ContactsInfo.NAME)));
            // 得到手机号码
            contactBean.setPhone(cursor.getString(cursor
                    .getColumnIndex(DatabaseInfo.ContactsInfo.NUMBER)));
            contactBean.setPhotoLg(cursor.getString(cursor
                    .getColumnIndex(DatabaseInfo.ContactsInfo.PHOTO_LG)));
            contactBean.setPhotoSm(cursor.getString(cursor
                    .getColumnIndex(DatabaseInfo.ContactsInfo.PHOTO_SM)));
            contactList.addAppContact(contactBean);
        }
        return contactList.get();
    }

    /**
     * 根据conntactId删除
     *
     * @param conntactId
     */
    public void delByContactId(String conntactId) {
        if (conntactId == null) {
            return;
        }
        List<DbOperation> operationList = new ArrayList<DbOperation>();
        operationList.add(DbOperation
                .newDelete(DatabaseInfo.ContactsInfo.TABLE_NAME)
                .withSelection(DatabaseInfo.ContactsInfo.CONTACT_ID + " = ? ",
                        new String[] { conntactId }).build());
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
            contentValues.put(DatabaseInfo.ContactsInfo.CONTACT_MODE, contactsInfo.getContactMode()
                    .ordinal());
            contentValues.put(DatabaseInfo.ContactsInfo.NAME, contactsInfo.getName());
            contentValues.put(DatabaseInfo.ContactsInfo.NUMBER, numberInfo.getNumber());
            contentValues.put(DatabaseInfo.ContactsInfo.NUMBER_DESC, numberInfo.getDesc());
            contentValues.put(DatabaseInfo.ContactsInfo.NUMBER_TYPE, numberInfo.getType());
            contentValues.put(DatabaseInfo.ContactsInfo.CONTACT_ID, contactsInfo.getContactId());
            contentValues.put(DatabaseInfo.ContactsInfo.PHOTO_LG, contactsInfo.getPhotoLg());
            contentValues.put(DatabaseInfo.ContactsInfo.PHOTO_SM, contactsInfo.getPhotoSm());
            contentValuesList.add(contentValues);
        }
        return contentValuesList;
    }

    @Override
    protected SQLiteOpenHelper getDbHelper() {
        return this.mDbHelper;
    }

}
