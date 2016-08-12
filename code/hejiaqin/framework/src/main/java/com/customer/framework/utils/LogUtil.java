package com.customer.framework.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.customer.framework.component.log.Logger;


/**
 * desc:
 * version 001
 * author: zhanggj
 */
public class LogUtil {

    /**
     * 本地日志打印优先级; 调用系统 Log.v.
     */
    public static final int VERBOSE = 2;
    /**
     * 本地日志打印优先级; 调用系统  Log.d.
     */
    public static final int DEBUG = 3;
    /**
     * 本地日志打印优先级; 调用系统  Log.i.
     */
    public static final int INFO = 4;
    /**
     * 本地日志打印优先级; 调用系统 Log.w.
     */
    public static final int WARN = 5;
    /**
     * 本地日志打印优先级; 调用系统 Log.e.
     */
    public static final int ERROR = 6;
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static Context mContext;

    /**
     * 当前打印优先级别
     */
    private static int currentLevel = VERBOSE;

    /**
     * 设置Android的上下文
     *
     * @param context Android的上下文
     */
    public static void setContext(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 设置普通打印的路径
     *
     * @param commonDir 全路径名称
     */
    public static void setLogCommonDir(String commonDir) {
        Logger.setLogCommonDir(commonDir);
    }

    /**
     * 设置打印级别
     *
     * @param level 打印级别
     *              VERBOSE = 2;
     *              DEBUG = 3;
     *              INFO = 4;
     *              WARN = 5;
     *              ERROR = 6;
     */
    public static void setLogLevel(int level) {
        if (level > ERROR) {
            currentLevel = ERROR;
        } else if (level < VERBOSE) {
            currentLevel = VERBOSE;
        } else {
            currentLevel = level;
        }
        Logger.setLogLevel(level);
    }

    /**
     * VERBOSE优先级别打印日志，默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param msg 打印信息
     * @return 返回
     */
    public static void v(String tag, String msg) {
        if(isLoggable(VERBOSE)) {
            if (hasPermissions()) {
                Logger.v(tag, msg);
            } else {
                Log.v(tag, msg);
            }
        }
    }

    /**
     * VERBOSE优先级别打印日志, 默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param msg 打印信息
     * @param tr  异常堆栈
     * @return 返回
     */
    public static void v(String tag, String msg, Throwable tr) {
        if(isLoggable(VERBOSE)) {
            if (hasPermissions()) {
                Logger.v(tag, msg, tr);
            } else {
                Log.v(tag, msg, tr);
            }
        }
    }


    /**
     * VERBOSE优先级别打印日志, 默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param tr  异常堆栈
     * @return 返回
     */
    public static void v(String tag, Throwable tr) {
        if(isLoggable(VERBOSE)) {
            if (hasPermissions()) {
                Logger.v(tag, tr);
            } else {
                Log.v(tag, getStackTraceString(tr));
            }
        }
    }

    /**
     * DEBUG优先级别打印日志，默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param msg 打印信息
     * @return 返回
     */
    public static void d(String tag, String msg) {
        if(isLoggable(DEBUG)) {
            if (hasPermissions()) {
                Logger.d(tag, msg);
            } else {
                Log.d(tag, msg);
            }
        }
    }

    /**
     * DEBUG优先级别打印日志, 默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param msg 打印信息
     * @param tr  异常堆栈
     * @return 返回
     */
    public static void d(String tag, String msg, Throwable tr) {
        if(isLoggable(DEBUG)) {
            if (hasPermissions()) {
                Logger.d(tag, msg, tr);
            } else {
                Log.d(tag, msg, tr);
            }
        }
    }

    /**
     * DEBUG优先级别打印日志, 默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param tr  异常堆栈
     * @return 返回
     */
    public static void d(String tag, Throwable tr) {
        if(isLoggable(DEBUG)) {
            if (hasPermissions()) {
                Logger.d(tag, tr);
            } else {
                Log.d(tag, getStackTraceString(tr));
            }
        }
    }

    /**
     * INFO优先级别打印日志，默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param msg 打印信息
     * @return 返回
     */
    public static void i(String tag, String msg) {
        if(isLoggable(INFO)) {
            if (hasPermissions()) {
                Logger.i(tag, msg);
            } else {
                Log.i(tag, msg);
            }
        }
    }

    /**
     * INFO优先级别打印日志, 默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param msg 打印信息
     * @param tr  异常堆栈
     * @return 返回
     */
    public static void i(String tag, String msg, Throwable tr) {
        if(isLoggable(INFO)) {
            if (hasPermissions()) {
                Logger.i(tag, msg, tr);
            } else {
                Log.i(tag, msg, tr);
            }
        }
    }

    /**
     * INFO优先级别打印日志, 默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param tr  异常堆栈
     * @return 返回
     */
    public static void i(String tag, Throwable tr) {
        if(isLoggable(INFO)) {
            if (hasPermissions()) {
                Logger.i(tag, tr);
            } else {
                Log.i(tag, getStackTraceString(tr));
            }
        }
    }

    /**
     * WARN优先级别打印日志，默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param msg 打印信息
     * @return 返回
     */
    public static void w(String tag, String msg) {
        if(isLoggable(WARN)) {
            if (hasPermissions()) {
                Logger.w(tag, msg);
            } else {
                Log.w(tag, msg);
            }
        }
    }

    /**
     * WARN优先级别打印日志, 默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param msg 打印信息
     * @param tr  异常堆栈
     * @return 返回
     */
    public static void w(String tag, String msg, Throwable tr) {
        if(isLoggable(WARN)) {
            if (hasPermissions()) {
                Logger.w(tag, msg, tr);
            } else {
                Log.w(tag, msg, tr);
            }
        }
    }

    /**
     * WARN优先级别打印日志, 默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param tr  异常堆栈
     * @return 返回
     */
    public static void w(String tag, Throwable tr) {
        if(isLoggable(WARN)) {
            if (hasPermissions()) {
                Logger.w(tag, tr);
            } else {
                Log.w(tag, getStackTraceString(tr));
            }
        }
    }

    /**
     * ERROR优先级别打印日志，默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param msg 打印信息
     * @return 返回
     */
    public static void e(String tag, String msg) {
        if(isLoggable(ERROR)) {
            if (hasPermissions()) {
                Logger.e(tag, msg);
            } else {
                Log.e(tag, msg);
            }
        }
    }

    /**
     * ERROR优先级别打印日志, 默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param msg 打印信息
     * @param tr  异常堆栈
     * @return 返回
     */
    public static void e(String tag, String msg, Throwable tr) {
        if(isLoggable(ERROR)) {
            if (hasPermissions()) {
                Logger.e(tag, msg, tr);
            } else {
                Log.e(tag, msg, tr);
            }
        }
    }

    /**
     * ERROR优先级别打印日志, 默认是正常打印类型的日志
     *
     * @param tag 打印TAG
     * @param tr  异常堆栈
     * @return 返回
     */
    public static void e(String tag, Throwable tr) {
        if(isLoggable(ERROR)) {
            if (hasPermissions()) {
                Logger.e(tag, tr);
            } else {
                Log.e(tag, getStackTraceString(tr));
            }
        }
    }

    /**
     * 对当前打印优先级设置，判断是否需要对信息进行打印
     *
     * @param level 打印优先级
     * @return
     */
    private static boolean isLoggable(int level) {
        return (level >= currentLevel);
    }

    @TargetApi(23)
    private static boolean hasPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            return mContext!=null && !PermissionsChecker.lacksPermissions(mContext, PERMISSIONS);
        } else {
            return true;
        }
    }

    /**
     * 获取异常的打印信息
     *
     * @param tr 异常
     * @return
     */
    private static String getStackTraceString(Throwable tr) {
        return android.util.Log.getStackTraceString(tr);
    }
}
