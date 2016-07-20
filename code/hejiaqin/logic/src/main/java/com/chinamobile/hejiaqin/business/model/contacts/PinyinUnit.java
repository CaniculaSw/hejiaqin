package com.chinamobile.hejiaqin.business.model.contacts;

import com.customer.framework.component.log.Logger;
import com.customer.framework.utils.StringUtil;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
public class PinyinUnit implements Serializable {
    private static final String TAG = "PinyinUnit";
    /**
     * 中文字符，如王金宝
     */
    private String chineseWords;
    /**
     * 中文拼音，如wangjinbao
     */
    private String chinesePinyin;

    /**
     * 首字母，如wjb
     */
    private String firstChars;

    /**
     * 首字母的索引，如047
     */
    private int[] firstCharIndexs;

    public String getChineseWords() {
        return chineseWords;
    }

    public void setChineseWords(String chineseWords) {
        this.chineseWords = chineseWords;
    }

    public String getChinesePinyin() {
        return chinesePinyin;
    }

    public void setChinesePinyin(String chinesePinyin) {
        this.chinesePinyin = chinesePinyin;
    }

    public String getFirstChars() {
        return firstChars;
    }

    public void setFirstChars(String firstChars) {
        this.firstChars = firstChars;
    }

    public int[] getFirstCharIndexs() {
        return firstCharIndexs;
    }

    public void setFirstCharIndexs(int[] firstCharIndexs) {
        this.firstCharIndexs = firstCharIndexs;
    }

    public boolean isValid() {
        if (StringUtil.isNullOrEmpty(chineseWords)) {
            Logger.w(TAG, "isValid, invalid chineseWords, chineseWords is null or empty");
            return false;
        }

        if (StringUtil.isNullOrEmpty(chinesePinyin)) {
            Logger.w(TAG, "isValid, invalid chinesePinyin, chinesePinyin is null or empty");
            return false;
        }

        if (StringUtil.isNullOrEmpty(firstChars) || chineseWords.length() != firstChars.length()) {
            Logger.w(TAG, "isValid, invalid firstChars, firstChars is invalid, firstChars is "
                    + firstChars + ", chineseWords is " + chineseWords);
            return false;
        }

        if (null == firstCharIndexs || firstCharIndexs.length != chineseWords.length()) {
            Logger.w(TAG, "isValid, invalid firstCharIndexs, firstCharIndexs is invalid, firstCharIndexs is "
                    + Arrays.toString(firstCharIndexs) + ", chineseWords is " + chineseWords);
            return false;
        }

        return true;
    }
}
