package com.customer.framework.utils.string;

import android.content.Context;

import com.customer.framework.R;
import com.customer.framework.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * 汉字转拼音工具。注意汉字集没有使用ICU的字符集，是通过自定义文件读取的方式完成
 * 所以使用该功能必须要在定制工程assets目录下提供字符集文件pinyin.txt
 */
public class HanziToPinyin {
    /**
     * 未知字符（非英语字母及中文的字符标识）
     */
    public static final char UNRESOLVED_CHAR_FLAG = '#';

    /**
     * 拼音字符集文件名称
     */
    private static String tablePath = "pinyin.txt";

    /**
     * 缓存大小
     */
    private static final int BUFFER_SIZE = 13;

    /**
     * 打印标示
     */
    private static final String TAG = "HanziToPinyin";

    /**
     * 单例对象
     */
    private static HanziToPinyin sInstance;

    /**
     * 读取字符集文件流
     */
    private InputStream inputStream;

    /**
     * 上下文环境
     */
    private Context mContext;

    /**
     * 构造单例
     *
     * @param context 上下文环境
     */
    private HanziToPinyin(Context context) {
        mContext = context;
    }

    /**
     * 获得HanziToPinyin对象
     *
     * @param context 上下文
     * @return HanziToPinyin对象
     */
    public static synchronized HanziToPinyin getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new HanziToPinyin(context);
        }
        return sInstance;
    }

    /**
     * 汉字转换成拼音，将会忽略空格
     *
     * @param hanzi 汉字字符
     * @return 返回拼音
     */
    public String hanziToPinyin(String hanzi) {
        StringBuffer sb = new StringBuffer();
        String tempStr = null;
        char[] nameChar = hanzi.toCharArray();

        int index = -1;
        int spaceIndex = 0;
        int commIndex = 0;

        for (int i = 0; i < nameChar.length; i++) {
            if (!isChinese(nameChar[i]) && nameChar[i] != ' ') {
                sb.append(nameChar[i]);
                continue;
            }
            index = getUnicodeIndex(nameChar[i]);
            // get the line string from the inputStream
            if (index != -1) {
                tempStr = getLineStr(index);
            } else {
                tempStr = "";
            }
            spaceIndex = tempStr.indexOf(" ");
            commIndex = tempStr.indexOf(",");
            if (spaceIndex != -1 && commIndex != -1) {
                tempStr = tempStr.substring(spaceIndex + 1, commIndex);
            }
            sb.append(tempStr);
        }
        return sb.toString();
    }

    /**
     * 汉字首字符转换成对应的拼音首字母大写形式
     * 将会忽略空格
     * 如果是字母开头，返回首字母大写
     * 如果非字母且非数字，返回'#'
     *
     * @param hanzi 汉字字符串
     * @return 返回拼音首字母大写
     */
    public char hanziHeadToPinyin(String hanzi) {
        char headChar = UNRESOLVED_CHAR_FLAG;
        String tempStr = null;
        char[] nameChar = hanzi.toCharArray();

        int index = -1;
        int spaceIndex = 0;
        int commIndex = 0;

        for (int i = 0; i < nameChar.length; i++) {
            if (!isChinese(nameChar[i]) && nameChar[i] != ' ') {
                if (nameChar[i] <= 128) {
                    headChar = nameChar[i];
                }
                break;
            }

            index = getUnicodeIndex(nameChar[i]);
            // get the line string from the inputStream
            if (index != -1) {
                tempStr = getLineStr(index);
            } else {
                tempStr = "";
            }
            spaceIndex = tempStr.indexOf(" ");
            commIndex = tempStr.indexOf(",");
            if (spaceIndex != -1 && commIndex != -1) {
                tempStr = tempStr.substring(spaceIndex + 1, commIndex);
                headChar = tempStr.charAt(0);
                break;
            }
        }

        return Character.toUpperCase(headChar);
    }

    /**
     * 单个汉字字符转换成拼音
     *
     * @param hanzi 汉字字符
     * @return 返回拼音
     */
    public String hanziToPinyin(char hanzi) {
        return hanziToPinyin(String.valueOf(hanzi));
    }

    /**
     * 判断是否为中文.
     *
     * @param c 需要判断的字符.
     * @return true: 是中文 ; false: 不是中文
     */
    private static boolean isChinese(char c) {
        if (c == '\u3007' || (c >= '\u4E00' && c <= '\u9FA5')
                || (c >= '\uF900' && c <= '\uFA2D')) {
            return true;
        }
        return false;
    }

    /**
     * 获取Unicode字符的int值大小
     *
     * @param nameChar Unicode字符
     * @return int值大小
     */
    private int getUnicodeIndex(char nameChar) {
        int hNum = 0;
        int lNum = 0;
        if (nameChar > 256) {
            hNum = (nameChar & 0xff00) >> 8;
            lNum = nameChar & 0xff;

            if (hNum == 0x30) {
                return 0;
            } else {
                return 1 + (hNum - 78) * 256 + lNum;
            }
        }
        return -1;
    }

    /**
     * 从文件流中读取相应行的字符串
     *
     * @param index 行数索引
     * @return
     */
    private synchronized String getLineStr(int index) {
        try {
            if (null == inputStream) {
                inputStream = mContext.getResources().openRawResource(R.raw.pinyin);
            }
            inputStream.reset();
            byte[] buffer = new byte[BUFFER_SIZE];
            long slong = inputStream.skip((long) index * BUFFER_SIZE);
            int rlen = inputStream.read(buffer, 0, BUFFER_SIZE);
            String line = new String(buffer);
            return line;
        } catch (Exception e) {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    LogUtil.e(TAG, e1.getMessage());
                }
            }
            inputStream = null;
            // 如果发生异常则返回""
            return "";
        }
    }
}
