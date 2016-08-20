package com.customer.framework.component.storage.impl;

import android.content.SharedPreferences;

import com.customer.framework.utils.LogUtil;
import com.customer.framework.component.storage.ISharedPStorage;
import com.customer.framework.utils.FileUtil;
import com.customer.framework.utils.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/14.
 */
public abstract class AbsSharedPStorage implements ISharedPStorage {
    private static final String TAG = "AbsSharedPStorage";
    protected SharedPreferences sharedPreferencesCache;

    @Override
    public void save(String key, String value) {
        if (null == key) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferencesCache.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public void save(String key, int value) {
        if (null == key) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferencesCache.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    @Override
    public void save(String key, boolean value) {
        if (null == key) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferencesCache.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public void save(String key, float value) {
        if (null == key) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferencesCache.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    @Override
    public void save(String key, long value) {
        if (null == key) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferencesCache.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    @Override
    public void save(String key, Serializable value) {
        if (null == key) {
            return;
        }

        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
        } catch (IOException e) {
            LogUtil.w(TAG, "save serializable object failed. IOException: ", e);
        } catch (Exception e) {
            LogUtil.w(TAG, "save serializable object failed. Exception: ", e);
        } finally {
            FileUtil.closeStream(os);
            FileUtil.closeStream(bos);
        }


        String valueStr = StringUtil.bytes2Hex(bos.toByteArray());
        save(key, valueStr);
    }

    @Override
    public void save(Map<String, Object> map) {
        if (null == map) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferencesCache.edit();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                editor.putString(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof Integer) {
                editor.putInt(entry.getKey(), (Integer) entry.getValue());
            } else if (entry.getValue() instanceof Boolean) {
                editor.putBoolean(entry.getKey(), (Boolean) entry.getValue());
            }
            else if (entry.getValue() instanceof Float) {
                editor.putFloat(entry.getKey(), (Float) entry.getValue());
            }
            else if (entry.getValue() instanceof Long) {
                editor.putLong(entry.getKey(), (Long) entry.getValue());
            }
        }
        editor.commit();
    }

    @Override
    public boolean isContain(String key) {
        if (null == key) {
            return false;
        }

        return sharedPreferencesCache.contains(key);
    }

    @Override
    public String getString(String key) {
        if (null == key) {
            return null;
        }
        return sharedPreferencesCache.getString(key, null);
    }

    @Override
    public int getInt(String key) {
        if (null == key) {
            return Integer.MIN_VALUE;
        }
        return sharedPreferencesCache.getInt(key, Integer.MIN_VALUE);
    }

    @Override
    public boolean getBoolean(String key) {
        if (null == key) {
            return Boolean.FALSE;
        }
        return sharedPreferencesCache.getBoolean(key, Boolean.FALSE);
    }

    @Override
    public float getFloat(String key) {
        if (null == key) {
            return Float.MIN_VALUE;
        }
        return sharedPreferencesCache.getFloat(key, Float.MIN_VALUE);
    }

    @Override
    public long getLong(String key) {
        if (null == key) {
            return Long.MIN_VALUE;
        }
        return sharedPreferencesCache.getLong(key, Long.MIN_VALUE);
    }

    @Override
    public Serializable getObject(String key) {
        if (null == key) {
            return null;
        }

        String valueStr = getString(key);
        if (null == valueStr) {
            return null;
        }

        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        Serializable readObject = null;
        try {
            bis = new ByteArrayInputStream(StringUtil.hex2Bytes(valueStr));
            is = new ObjectInputStream(bis);
            readObject = (Serializable)is.readObject();
        } catch (ClassNotFoundException e) {
            LogUtil.w(TAG, "get object failed. ClassNotFoundException: ", e);
        } catch (IOException e) {
            LogUtil.w(TAG, "get object failed. IOException: ", e);
        } catch (Exception e) {
            LogUtil.w(TAG, "get object failed. Exception: ", e);
        } finally {
            FileUtil.closeStream(is);
            FileUtil.closeStream(bis);
        }
        return readObject;
    }

    @Override
    public void remove(String[] key) {
        SharedPreferences.Editor editor = sharedPreferencesCache.edit();
        for(int i =0;i<key.length;i++) {
            editor.remove(key[i]);
        }
        editor.commit();
    }

    @Override
    public void clearAll() {
        SharedPreferences.Editor editor = sharedPreferencesCache.edit();
        editor.clear();
        editor.commit();
    }

}
