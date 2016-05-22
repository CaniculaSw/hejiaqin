package com.customer.framework.component.db.operation;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.List;

public abstract class BaseDbAdapter {
    private static final int SLEEP_AFTER_YIELD_DELAY = 2000;
    private static final String TAG = "BaseDbAdapter";

    /**
     * 各线程是否在批处理的标记
     */
    private final ThreadLocal<Boolean> mApplyingBatch = new ThreadLocal<Boolean>();

    /**
     * 判断是否在执行批处理
     *
     * @return 是否在执行批处理
     */
    private boolean applyingBatch() {
        return mApplyingBatch.get() != null && mApplyingBatch.get();
    }

    protected void applyBatch(List<DbOperation> paramList) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // 标记当前线程为正在执行批量处理
            mApplyingBatch.set(true);
            int i = paramList.size();
            for (int j = 0; j < i; j++) {
                DbOperation dbOperation =  paramList.get(j);
                if ((j > 0) && (dbOperation.isYieldAllowed())) {
                    db.yieldIfContendedSafely(SLEEP_AFTER_YIELD_DELAY);
                }
                dbOperation.apply(db);
            }
            db.setTransactionSuccessful();
        } finally {
            // 标记当前线程不在执行批量处理
            mApplyingBatch.set(false);
            db.endTransaction();
        }
    }

    /**
     * 更新方法
     * 更新数据库中的数据
     *
     * @param table         表名
     * @param values        更新的值
     * @param selection     更新的条件
     * @param selectionArgs 更新的条件值
     * @return 更新条目的数量
     */
    public int update(String table, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        boolean applyingBatch = applyingBatch();
        SQLiteDatabase db = getWritableDatabase();
        // 没有批处理执行时开始一个事物
        if (!applyingBatch) {
            db.beginTransaction();
            try {
                count = db.update(table, values, selection, selectionArgs);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
        // 有批处理在执行时不需要另外开启事物
        else {
            count = db.update(table, values, selection, selectionArgs);
        }

        return count;
    }

    /**
     * 删除方法<BR>
     * 删除数据库中的数据
     *
     * @param table         表名
     * @param selection     删除的条件语句
     * @param selectionArgs 删除的条件值
     * @return 删除的条数
     */
    public int delete(String table, String selection, String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase db = getWritableDatabase();
        boolean applyingBatch = applyingBatch();
        // 没有批处理执行时开始一个事物
        if (!applyingBatch) {
            db.beginTransaction();
            try {
                count = db.delete(table, selection, selectionArgs);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
        // 有批处理在执行时不需要另外开启事物
        else {
            count = db.delete(table, selection, selectionArgs);
        }
        return count;
    }

    /**
     * 插入方法
     * 向数据库中插入一条数据
     *
     * @param table 通用资源标志符 代表要操作的数据
     * @param value ContentValues 插入的数据值
     * @see ContentProvider#insert(Uri, ContentValues)
     */
    protected void insert(String table, ContentValues value) {
        SQLiteDatabase db = getWritableDatabase();
        if (!applyingBatch()) {
            db.beginTransaction();
            try {
                db.insert(table, null, value);
                // 操作成功标记有数据改动
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } else {
            db.insert(table, null, value);
        }
    }

    protected Cursor rawQuery(String sql, String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, selectionArgs);
    }


    protected void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    protected abstract SQLiteOpenHelper getDbHelper();

    protected SQLiteDatabase getReadableDatabase() {
        return getDbHelper().getReadableDatabase();
    }

    protected SQLiteDatabase getWritableDatabase() {
        return getDbHelper().getWritableDatabase();
    }

    protected void printException(Exception paramException) {
        Log.e("BaseDbAdapter", "DbAdapter: ", paramException);
    }
}
