package com.chinamobile.hejiaqin.business.model.contacts;

import com.customer.framework.utils.StringUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
public class SearchUnit {
    public enum SearchType {
        searchByNumber, searchByName
    }

    private List<NumberInfo> numberLst;

    private PinyinUnit pinyinUnit;

    /**
     * 搜索中要展示的原文
     */
    private String originalText;

    /**
     * 搜索中匹配的关键字
     */
    private String matchedText;

    private SearchType searchType;

    public List<NumberInfo> getNumberLst() {
        return numberLst;
    }

    public void setNumberLst(List<NumberInfo> numberLst) {
        this.numberLst = numberLst;
    }

    public PinyinUnit getPinyinUnit() {
        return pinyinUnit;
    }

    public void setPinyinUnit(PinyinUnit pinyinUnit) {
        this.pinyinUnit = pinyinUnit;
    }

    public String getNumberText() {
        if (searchType == SearchType.searchByNumber) {
            return hightMatchedText();
        } else {
            if (null != this.numberLst && !this.numberLst.isEmpty()) {
                return numberLst.get(0).getNumber();
            }
            return "";
        }
    }

    public String getNameText() {
        if (searchType == SearchType.searchByNumber) {
            return pinyinUnit.getChineseWords();
        } else {
            return hightMatchedText();
        }
    }

    private String hightMatchedText() {
        String hightMatchedText = new String(this.originalText);

        StringBuffer matchedTextBuffer = new StringBuffer();
        matchedTextBuffer.append("<font color='#37c972'>").append(matchedText).append("</font>");
        return hightMatchedText.replace(matchedText, matchedTextBuffer.toString());

    }

    public String getChinesePinyin() {
        if (null == pinyinUnit) {
            return "";
        }
        return pinyinUnit.getChinesePinyin();
    }

    public boolean search(String input) {
        if (StringUtil.isNullOrEmpty(input)) {
            return false;
        }

        input = input.toUpperCase();
        if (searchName(input)) {
            return true;
        }

        if (searchNumber(input)) {
            return true;
        }
        return false;
    }

    private boolean searchNumber(String input) {
        if (null == numberLst || numberLst.isEmpty()) {
            return false;
        }

        for (NumberInfo numberInfo : numberLst) {
            if (numberInfo.getNumber().contains(input)) {
                searchType = SearchType.searchByNumber;
                originalText = numberInfo.getNumber();
                matchedText = input;
                return true;
            }

        }
        return false;
    }

    private boolean searchName(String input) {
        if (null == pinyinUnit || !pinyinUnit.isValid()) {
            return false;
        }

        if (searchChineseWords(input)) {
            return true;
        }

        if (searchPinyinWords(input)) {
            return true;
        }

        if (searchPinyinFirstChars(input)) {
            return true;
        }

        return false;
    }

    private boolean searchChineseWords(String input) {
        String chineseWords = pinyinUnit.getChineseWords();
        if (chineseWords.contains(input)) {
            searchType = SearchType.searchByName;
            originalText = chineseWords;
            matchedText = input;
            return true;
        }
        return false;
    }

    private boolean searchPinyinWords(String input) {
        String chinesePinyin = pinyinUnit.getChinesePinyin();
        int index = chinesePinyin.indexOf(input);
        // 未搜索到关键字
        if (index == -1) {
            return false;
        }

        boolean isStartWithFirstChar = false;
        int[] firstCharIndexs = pinyinUnit.getFirstCharIndexs();
        for (int firstCharIndex : firstCharIndexs) {
            if (index == firstCharIndex) {
                isStartWithFirstChar = true;
            }
        }
        // 关键字并非从各首字母开始
        if (!isStartWithFirstChar) {
            return false;
        }

        // 根据拼音关键字找出对应的汉字关键字
        searchType = SearchType.searchByName;
        originalText = pinyinUnit.getChineseWords();
        matchedText = "";

        int endIndex = index + input.length();
        for (int i = 0; i < firstCharIndexs.length; i++) {
            if (index <= firstCharIndexs[i] && firstCharIndexs[i] < endIndex) {
                matchedText += originalText.charAt(i);
            }
        }
        return true;
    }

    private boolean searchPinyinFirstChars(String input) {
        String firstChars = pinyinUnit.getFirstChars();
        int index = firstChars.indexOf(input);
        if (index >= 0) {
            searchType = SearchType.searchByName;
            originalText = pinyinUnit.getChineseWords();
            matchedText = originalText.substring(index, input.length());
            return true;
        }
        return false;
    }
}
