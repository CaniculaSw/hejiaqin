package com.chinamobile.hejiaqin.business.dbApdater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import com.chinamobile.hejiaqin.business.model.contacts.ContactList;
import com.chinamobile.hejiaqin.business.model.contacts.rsp.ContactBean;
import com.chinamobile.hejiaqin.business.model.dial.CallRecord;
import com.customer.framework.component.db.DatabaseHelper;
import com.customer.framework.component.db.DatabaseInfo;
import com.customer.framework.component.db.operation.BaseDbAdapter;
import com.customer.framework.component.db.operation.DbOperation;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanggj on 2016/5/30.
 */
public class CallRecordDbAdapter extends BaseDbAdapter {

    private static CallRecordDbAdapter mCallRecordDbAdapter;
    private Context mContext;
    private DatabaseHelper mDbHelper;
    private String mUserId;

    private CallRecordDbAdapter(Context context, String userId) {
        this.mContext = context.getApplicationContext();
        this.mUserId = userId;
        this.mDbHelper = DatabaseHelper.getInstance(this.mContext, mUserId);
    }

    public static synchronized CallRecordDbAdapter getInstance(Context context, String userId) {
        if ((mCallRecordDbAdapter == null) || (!userId.equals(mCallRecordDbAdapter.mUserId))) {
            mCallRecordDbAdapter = new CallRecordDbAdapter(context, userId);
        }
        return mCallRecordDbAdapter;
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
            operationList.add(DbOperation.newDelete(DatabaseInfo.CallRecord.TABLE_NAME).withSelection(DatabaseInfo.CallRecord.TABLE_ID + " = ? ", new String[]{ids[i]}).build());
        }
        super.applyBatch(operationList);
    }

    public List<CallRecord> queryWithNumbers(String[] numbers) {
        if(numbers ==null || numbers.length ==0)
        {
            return null;
        }
        List<CallRecord> list = new ArrayList<CallRecord>();
        String selection = "";
        if(numbers!=null && numbers.length!=0) {
            if (numbers.length == 1) {
                selection = " where " + DatabaseInfo.CallRecord.NO_COUNTRY_NUMBER + " = ? ";
            }else {
                for (int i = 0; i < numbers.length; i++) {
                    if (i == 0) {
                        selection = " where " + DatabaseInfo.CallRecord.NO_COUNTRY_NUMBER + " in ( ? ";
                    } else if (i == numbers.length - 1) {
                        selection = selection + " , ? ) ";
                    } else {
                        selection = selection + " , ? ";
                    }
                }
            }
        }
        String sql = "select * from " + DatabaseInfo.CallRecord.TABLE_NAME + selection +" order by "+ DatabaseInfo.CallRecord.BEGIN_TIME + " desc ";
        Cursor cursor = super.rawQuery(sql, numbers);
        while (cursor.moveToNext()) {
            list.add(parseValuesToBean(cursor));
        }
        return list;
    }

    public void deleteByNumbers(String[] numbers) {
        String selection = "";
        if(numbers!=null && numbers.length!=0) {
            if (numbers.length == 1) {
                selection =  DatabaseInfo.CallRecord.NO_COUNTRY_NUMBER + " = ? ";
            }else {
                for (int i = 0; i < numbers.length; i++) {
                    if (i == 0) {
                        selection = DatabaseInfo.CallRecord.NO_COUNTRY_NUMBER + " in ( ? ";
                    } else if (i == numbers.length - 1) {
                        selection = selection + " , ? ) ";
                    } else {
                        selection = selection + " , ? ";
                    }
                }
            }
        }
        super.delete(DatabaseInfo.CallRecord.TABLE_NAME, selection, numbers);
    }

    public List<CallRecord> queryWithName() {
        List<CallRecord> list = new ArrayList<CallRecord>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(DatabaseInfo.CallRecord.TABLE_NAME);
        sql.append(".");
        sql.append(DatabaseInfo.CallRecord.TABLE_ID);
        sql.append(",");
        sql.append(DatabaseInfo.CallRecord.RECORD_ID);
        sql.append(",");
        sql.append(DatabaseInfo.CallRecord.PEER_NUMBER);
        sql.append(",");
        sql.append(DatabaseInfo.CallRecord.NO_COUNTRY_NUMBER);
        sql.append(",");
        sql.append(DatabaseInfo.CallRecord.BEGIN_TIME);
        sql.append(",");
        sql.append(DatabaseInfo.CallRecord.DURATION);
        sql.append(",");
        sql.append(DatabaseInfo.CallRecord.TYPE);
        sql.append(",");
        sql.append(DatabaseInfo.CallRecord.READ);
        sql.append(",");
        sql.append(DatabaseInfo.ContactsInfo.CONTACT_ID);
        sql.append(",");
        sql.append(DatabaseInfo.ContactsInfo.NAME);
        sql.append(",");
        sql.append(DatabaseInfo.ContactsInfo.CONTACT_MODE);
        sql.append(",");
        sql.append(DatabaseInfo.ContactsInfo.NAME_IN_PINYIN);
        sql.append(",");
        sql.append(DatabaseInfo.ContactsInfo.NUMBER);
        sql.append(",");
        sql.append(DatabaseInfo.ContactsInfo.NUMBER_TYPE);
        sql.append(",");
        sql.append(DatabaseInfo.ContactsInfo.NUMBER_DESC);
        sql.append(",");
        sql.append(DatabaseInfo.ContactsInfo.PHOTO_LG);
        sql.append(",");
        sql.append(DatabaseInfo.ContactsInfo.PHOTO_SM);
        sql.append(" from ");
        sql.append( DatabaseInfo.CallRecord.TABLE_NAME);
        sql.append(" left join " );
        sql.append(DatabaseInfo.ContactsInfo.TABLE_NAME);
        sql.append(" on ");
        sql.append(DatabaseInfo.CallRecord.TABLE_NAME);
        sql.append(".");
        sql.append(DatabaseInfo.CallRecord.NO_COUNTRY_NUMBER);
        sql.append(" = ");
        sql.append(DatabaseInfo.ContactsInfo.TABLE_NAME);
        sql.append(".");
        sql.append(DatabaseInfo.ContactsInfo.NUMBER);
        Cursor cursor = super.rawQuery(sql.toString(), null);
        CallRecord callRecord;
        ContactBean contactBean;
        String contactId;
        String callRecordId ="-1";
        while (cursor.moveToNext()) {
            callRecord = parseValuesToBean(cursor);
            if(!callRecord.getRecordId().equals(callRecordId)) {
                contactId = cursor.getString(cursor.getColumnIndex(DatabaseInfo.ContactsInfo.CONTACT_ID));
                if (!StringUtil.isNullOrEmpty(contactId)) {
                    contactBean = new ContactBean();
                    contactBean.setContactId(contactId);
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
                    callRecord.setContactsInfo(ContactList.convert(contactBean));
                    callRecord.setPeerName(contactBean.getName());
                }
                list.add(callRecord);
                callRecordId = callRecord.getRecordId();
            }
        }
        return list;
    }


    /**
     * 根据ids删除
     *
     * @param callRecord
     */
    public void insert(CallRecord callRecord) {
        super.insert(DatabaseInfo.CallRecord.TABLE_NAME, parseBeanToValues(callRecord));
    }

    /**
     * @param recordId
     * @param contentValues
     */
    public void updateByRecordId(String recordId, ContentValues contentValues) {
        super.update(DatabaseInfo.CallRecord.TABLE_NAME, contentValues, DatabaseInfo.CallRecord.RECORD_ID + " = ?", new String[]{recordId});
    }

    /**
     * @param id
     * @param contentValues
     */
    public void updateById(String id, ContentValues contentValues) {
        super.update(DatabaseInfo.CallRecord.TABLE_NAME, contentValues, DatabaseInfo.CallRecord.TABLE_ID + " = ?", new String[]{id});
    }

    /**
     * 全部删除
     */
    public void delAll() {
        super.delete(DatabaseInfo.CallRecord.TABLE_NAME, null, null);
    }

    private ContentValues parseBeanToValues(CallRecord callRecord) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseInfo.CallRecord.RECORD_ID, callRecord.getRecordId());
        contentValues.put(DatabaseInfo.CallRecord.PEER_NUMBER, callRecord.getPeerNumber());
        contentValues.put(DatabaseInfo.CallRecord.NO_COUNTRY_NUMBER, callRecord.getNoCountryNumber());
        contentValues.put(DatabaseInfo.CallRecord.BEGIN_TIME, callRecord.getBeginTime());
        contentValues.put(DatabaseInfo.CallRecord.DURATION, callRecord.getDuration());
        contentValues.put(DatabaseInfo.CallRecord.TYPE, callRecord.getType());
        contentValues.put(DatabaseInfo.CallRecord.READ, callRecord.getRead());
        return contentValues;
    }

    private CallRecord parseValuesToBean(Cursor cursor) {
        CallRecord callRecord = new CallRecord();
        callRecord.setId(cursor.getString(cursor.getColumnIndex(DatabaseInfo.CallRecord.TABLE_ID)));
        callRecord.setRecordId(cursor.getString(cursor.getColumnIndex(DatabaseInfo.CallRecord.RECORD_ID)));
        callRecord.setPeerNumber(cursor.getString(cursor.getColumnIndex(DatabaseInfo.CallRecord.PEER_NUMBER)));
        callRecord.setNoCountryNumber(cursor.getString(cursor.getColumnIndex(DatabaseInfo.CallRecord.NO_COUNTRY_NUMBER)));
        callRecord.setBeginTime(cursor.getString(cursor.getColumnIndex(DatabaseInfo.CallRecord.BEGIN_TIME)));
        callRecord.setDuration(cursor.getInt(cursor.getColumnIndex(DatabaseInfo.CallRecord.DURATION)));
        callRecord.setType(cursor.getInt(cursor.getColumnIndex(DatabaseInfo.CallRecord.TYPE)));
        callRecord.setRead(cursor.getInt(cursor.getColumnIndex(DatabaseInfo.CallRecord.READ)));
        return callRecord;
    }

    @Override
    protected SQLiteOpenHelper getDbHelper() {
        return this.mDbHelper;
    }

}
