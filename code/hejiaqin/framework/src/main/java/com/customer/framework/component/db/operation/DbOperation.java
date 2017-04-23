package com.customer.framework.component.db.operation;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/***/
public class DbOperation
{
  private static final String TAG = "DbOperation";
  public static final int TYPE_DELETE = 3;
  public static final int TYPE_EXEC_PRE_SQL = 4;
  public static final int TYPE_EXEC_SQL = 5;
  public static final int TYPE_INSERT = 1;
  public static final int TYPE_UPDATE = 2;
  private Object[] mBindArgs;
  private String mSelection;
  private String[] mSelectionArgs;
  private String mSql;
  private String mTable;
  private final int mType;
  private ContentValues mValues;
  private boolean mYieldAllowed;
  
  private DbOperation(DbOperation.Builder builder)
  {
    this.mType = builder.mType;
    this.mTable = builder.mTable;
    this.mValues = builder.mValues;
    this.mSelection = builder.mSelection;
    this.mSelectionArgs = builder.mSelectionArgs;
    this.mYieldAllowed = builder.mYieldAllowed;
    this.mSql = builder.mSql;
    this.mBindArgs = builder.mBindArgs;
  }
  /***/
  public static DbOperation.Builder newDelete(String table)
  {
    return new DbOperation.Builder(TYPE_DELETE, table);
  }
  /***/
  public static DbOperation.Builder newExecPreSql(String sql, Object[] bindArgs)
  {
    return new DbOperation.Builder(TYPE_EXEC_PRE_SQL, sql, bindArgs);
  }
  /***/
  public static DbOperation.Builder newExecSql(String paramString)
  {
    return new DbOperation.Builder(TYPE_EXEC_SQL, paramString,null);
  }
  /***/
  public static DbOperation.Builder newInsert(String table)
  {
    return new DbOperation.Builder(TYPE_INSERT, table);
  }
  /***/
  public static DbOperation.Builder newUpdate(String table)
  {
    return new DbOperation.Builder(TYPE_UPDATE, table);
  }
  /***/
  public void apply(SQLiteDatabase sqlLiteDb)
  {
    if (this.mType == TYPE_INSERT)
    {
      if (sqlLiteDb.insert(this.mTable, null, this.mValues) <= 0) {
        Log.e("DbOperation", "insert failed");
      }
      return;
    }
    if (this.mType == TYPE_DELETE)
    {
      sqlLiteDb.delete(this.mTable, this.mSelection, this.mSelectionArgs);
      return;
    }
    if (this.mType == TYPE_UPDATE)
    {
      sqlLiteDb.update(this.mTable, this.mValues, this.mSelection, this.mSelectionArgs);
      return;
    }
    if (this.mType == TYPE_EXEC_PRE_SQL)
    {
      sqlLiteDb.execSQL(this.mSql, this.mBindArgs);
      return;
    }
    if (this.mType == TYPE_EXEC_SQL)
    {
      sqlLiteDb.execSQL(this.mSql);
      return;
    }
    Log.i("DbOperation", toString());
    throw new IllegalStateException("bad type, " + this.mType);
  }
  
  public boolean isYieldAllowed()
  {
    return this.mYieldAllowed;
  }
  /***/
  public static class Builder {
    private Object[] mBindArgs;
    private String mSelection;
    private String[] mSelectionArgs;
    private String mSql;
    private String mTable;
    private final int mType;
    private ContentValues mValues;
    private boolean mYieldAllowed;

    private Builder(int type, String table)
    {
      if (table == null) {
        throw new IllegalArgumentException("table must not be null");
      }
      this.mType = type;
      this.mTable = table;
    }

    private Builder(int type, String sql, Object[] bindArgs)
    {
      if (sql == null) {
        throw new IllegalArgumentException("sql must not be null");
      }
      if ((type == TYPE_EXEC_PRE_SQL) && (bindArgs == null)) {
        throw new IllegalArgumentException("bindArgs must not be null");
      }
      this.mType = type;
      this.mSql = sql;
      this.mBindArgs = bindArgs;
    }
    /***/
    public DbOperation build()
    {
      if (((this.mType == TYPE_UPDATE) || (this.mType == TYPE_INSERT)) && ((this.mValues == null) || (this.mValues.size() == 0))) {
        throw new IllegalArgumentException("Empty values");
      }
      return new DbOperation(this);
    }
    /***/
    public Builder withSelection(String selection, String[] selectionArgs)
    {
      if ((this.mType != TYPE_UPDATE) && (this.mType != TYPE_DELETE)) {
        throw new IllegalArgumentException("only updates, deletes can have selections");
      }
      this.mSelection = selection;
      if (selectionArgs == null)
      {
        this.mSelectionArgs = null;
        return this;
      }
      this.mSelectionArgs = new String[selectionArgs.length];
      System.arraycopy(selectionArgs, 0, this.mSelectionArgs, 0, selectionArgs.length);
      return this;
    }
    /***/
    public Builder withValue(String key, Object value)
    {
      if ((this.mType != TYPE_INSERT) && (this.mType != TYPE_UPDATE)) {
        throw new IllegalArgumentException("only inserts and updates can have values");
      }
      if (this.mValues == null) {
        this.mValues = new ContentValues();
      }
      if (value == null)
      {
        this.mValues.putNull(key);
        return this;
      }
      if ((value instanceof String))
      {
        this.mValues.put(key, (String)value);
        return this;
      }
      if ((value instanceof Byte))
      {
        this.mValues.put(key, (Byte)value);
        return this;
      }
      if ((value instanceof Short))
      {
        this.mValues.put(key, (Short)value);
        return this;
      }
      if ((value instanceof Integer))
      {
        this.mValues.put(key, (Integer)value);
        return this;
      }
      if ((value instanceof Long))
      {
        this.mValues.put(key, (Long)value);
        return this;
      }
      if ((value instanceof Float))
      {
        this.mValues.put(key, (Float)value);
        return this;
      }
      if ((value instanceof Double))
      {
        this.mValues.put(key, (Double)value);
        return this;
      }
      if ((value instanceof Boolean))
      {
        this.mValues.put(key, (Boolean)value);
        return this;
      }
      if ((value instanceof byte[]))
      {
        this.mValues.put(key, (byte[])value);
        return this;
      }
      throw new IllegalArgumentException("bad value type: " + value.getClass().getName());
    }
    /***/
    public Builder withValues(ContentValues values)
    {
      if ((this.mType != TYPE_INSERT) && (this.mType != TYPE_UPDATE)) {
        throw new IllegalArgumentException("only inserts, updates can have values");
      }
      if (this.mValues == null) {
        this.mValues = new ContentValues();
      }
      this.mValues.putAll(values);
      return this;
    }
    /***/
    public Builder withYieldAllowed(boolean paramBoolean)
    {
      this.mYieldAllowed = paramBoolean;
      return this;
    }
  }
}

