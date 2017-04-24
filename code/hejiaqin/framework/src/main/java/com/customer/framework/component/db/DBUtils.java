package com.customer.framework.component.db;

import android.content.Context;
import android.database.Cursor;

import com.customer.framework.utils.LogUtil;

import java.io.InputStream;

/**
 * 读取数据库表结构文件
 * hejiaqin Version 001
 * author:
 * Created: 2016/4/8.
 */
public class DBUtils {

    private static final String TAG = "DBUtils";

    /**
     * 根据异常控制开关打印异常<BR>
     *
     * @param e 异常
     */
    public static void printException(Exception e) {
        LogUtil.e(TAG, "DatabaseException: ", e);
    }

    /**
     * 关闭游标<BR>
     *
     * @param cursor 要关闭的游标对象
     */
    public static void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     * 文件的编码格式
     */
    public static final String ENCODING = "UTF-8";

    /**
     * [ 从assets 文件夹中获取文件并读取数据]<BR>
     * [功能详细描述]
     *
     * @param fileName 文件名称
     * @param context  当前操 作的上下文卦象
     * @return xml文件描述
     */
    public static String getFromAssets(String fileName, Context context) {
        String result = "";
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int lenght = inputStream.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            inputStream.read(buffer);
            result = new String(buffer, 0, buffer.length, ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception ex) {
                printException(ex);
            }
        }
        return result;
    }
}
