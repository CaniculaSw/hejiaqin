package com.chinamobile.hejiaqin.business.model.contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
public class NameInfo {
    private String chineseName;

    private String nameInPinyin;

    private String nameHeadChars;

    private List<Word> wordList = new ArrayList<>();


    public class Word {
        /**
         * 一个中文或者英文字符
         */
        private String chineseWord;

        /**
         * 中文转拼音
         */
        private String wordInPinyin;

        /**
         * 中文首字母
         */
        private String wordFirstChar;

        public String getChineseWord() {
            return chineseWord;
        }

        public void setChineseWord(String chineseWord) {
            this.chineseWord = chineseWord;
        }

        public String getWordInPinyin() {
            return wordInPinyin;
        }

        public void setWordInPinyin(String wordInPinyin) {
            this.wordInPinyin = wordInPinyin;
        }

        public String getWordFirstChar() {
            return wordFirstChar;
        }

        public void setWordFirstChar(String wordFirstChar) {
            this.wordFirstChar = wordFirstChar;
        }
    }
}
