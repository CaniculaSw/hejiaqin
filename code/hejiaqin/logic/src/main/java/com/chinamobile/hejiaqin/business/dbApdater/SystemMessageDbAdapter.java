package com.chinamobile.hejiaqin.business.dbApdater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.chinamobile.hejiaqin.business.model.more.SystemMessage;
import com.customer.framework.component.db.DatabaseHelper;
import com.customer.framework.component.db.DatabaseInfo;
import com.customer.framework.component.db.operation.BaseDbAdapter;
import com.customer.framework.component.db.operation.DbOperation;
import com.customer.framework.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/7/14.
 */
public class SystemMessageDbAdapter extends BaseDbAdapter {
    private static SystemMessageDbAdapter mSystemMessageDbAdapter;
    private Context mContext;
    private DatabaseHelper mDbHelper;
    private String mUserId;

    private SystemMessageDbAdapter(Context context, String userId) {
        this.mContext = context.getApplicationContext();
        this.mUserId = userId;
        this.mDbHelper = DatabaseHelper.getInstance(this.mContext, mUserId);
    }

    public static synchronized SystemMessageDbAdapter getInstance(Context context, String userId) {
        if ((mSystemMessageDbAdapter == null) || (!userId.equals(mSystemMessageDbAdapter.mUserId))) {
            mSystemMessageDbAdapter = new SystemMessageDbAdapter(context, userId);
        }
        return mSystemMessageDbAdapter;
    }

    public void add(SystemMessage systemMessage) {
        if (null == systemMessage) {
            return;
        }
        List<DbOperation> operationList = new ArrayList<DbOperation>();
        ContentValues contentValues = getCVBySystemMessage(systemMessage);
        if (null == contentValues) {
            return;
        }
        operationList.add(DbOperation.newInsert(DatabaseInfo.SystemMessage.TABLE_NAME)
                        .withValues(contentValues).build());
        super.applyBatch(operationList);
    }

    public List<SystemMessage> queryAll() {
        List<SystemMessage> systemMessagesList = new ArrayList<SystemMessage>();
        String[] columns =  {"_ID","title","time"};
        Cursor cursor = query(DatabaseInfo.SystemMessage.TABLE_NAME, columns,null, null,null,null,DatabaseInfo.SystemMessage.TIME,null);
        if (null == cursor) {
            LogUtil.d("SystemMessageDbAdapter","cursor is null");
            return systemMessagesList;
        }
        LogUtil.d("SystemMessageDbAdapter","size of cursor is "+ cursor.getCount());
        while (cursor.moveToNext()) {
            SystemMessage systemMessage = new SystemMessage();
            systemMessage.setId(cursor.getString(cursor.getColumnIndex(DatabaseInfo.SystemMessage.TABLE_ID)));
            systemMessage.setTime(cursor.getString(cursor.getColumnIndex(DatabaseInfo.SystemMessage.TIME)));
            systemMessage.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseInfo.SystemMessage.TITLE)));
            systemMessagesList.add(systemMessage);
        }
        return systemMessagesList;
    }
    public SystemMessage querySystemMessageByID(String id) {
       SystemMessage systemMessage = new SystemMessage();
        String[] columns =  {"title","time","content"};
        String[] selectionArgs = {id};
        Cursor cursor = query(DatabaseInfo.SystemMessage.TABLE_NAME, columns,"_ID=?", selectionArgs ,null,null,null,null);
        if (null == cursor) {
            return systemMessage;
        }
        while (cursor.moveToNext()) {
            systemMessage.setContent(cursor.getString(cursor.getColumnIndex(DatabaseInfo.SystemMessage.CONTENT)));
            systemMessage.setTime(cursor.getString(cursor.getColumnIndex(DatabaseInfo.SystemMessage.TIME)));
            systemMessage.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseInfo.SystemMessage.TITLE)));
        }
        return systemMessage;
    }

    public void deleteSystemMessageByIDs(String[] ids) {
            if (ids == null || ids.length == 0) {
                return;
            }
            List<DbOperation> operationList = new ArrayList<DbOperation>();
            for (String id : ids) {
                operationList.add(DbOperation.newDelete(DatabaseInfo.SystemMessage.TABLE_NAME)
                        .withSelection(DatabaseInfo.SystemMessage.TABLE_ID + "=? ", new String[]{id})
                        .build());
            }
            super.applyBatch(operationList);
    }

    public void deleteSystemMessageByID(String id) {
        if (id == null) {
            return;
        }
        List<DbOperation> operationList = new ArrayList<DbOperation>();
            operationList.add(DbOperation.newDelete(DatabaseInfo.SystemMessage.TABLE_NAME)
                    .withSelection(DatabaseInfo.SystemMessage.TABLE_ID + "=? ", new String[]{id})
                    .build());
        super.applyBatch(operationList);
    }
    @NonNull
    private ContentValues getCVBySystemMessage(SystemMessage systemMessage) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseInfo.SystemMessage.TITLE, systemMessage.getTitle());
        contentValues.put(DatabaseInfo.SystemMessage.TIME, systemMessage.getTime());
        contentValues.put(DatabaseInfo.SystemMessage.CONTENT, systemMessage.getContent());
        return contentValues;
    }

    @Override
    protected SQLiteOpenHelper getDbHelper() {
        return this.mDbHelper;
    }
}
