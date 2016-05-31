package com.chinamobile.hejiaqin.business.dbApdater;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.customer.framework.component.db.DatabaseHelper;
import com.customer.framework.component.db.DatabaseInfo;
import com.customer.framework.component.db.operation.BaseDbAdapter;
import com.customer.framework.component.db.operation.DbOperation;

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

    /**
     * 全部删除
     */
    public void delAll() {
        super.delete(DatabaseInfo.CallRecord.TABLE_NAME, null, null);
    }

    @Override
    protected SQLiteOpenHelper getDbHelper() {
        return this.mDbHelper;
    }

}
