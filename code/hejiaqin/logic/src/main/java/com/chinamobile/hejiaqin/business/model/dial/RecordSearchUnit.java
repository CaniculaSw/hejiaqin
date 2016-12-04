package com.chinamobile.hejiaqin.business.model.dial;

import com.customer.framework.utils.StringUtil;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
public class RecordSearchUnit implements Serializable {

    private String mPeerNumber;

    /**
     * 搜索中要展示的原文
     */
    private String originalText;

    /**
     * 搜索中匹配的关键字
     */
    private String matchedText;

    public void setPeerNumber(String peerNumber) {
        this.mPeerNumber = peerNumber;
    }

    public String getNumberText() {
        return hightMatchedText();
    }

    private String hightMatchedText() {
        String hightMatchedText = (null == this.originalText ? "" : new String(this.originalText));

        StringBuffer matchedTextBuffer = new StringBuffer();
        matchedTextBuffer.append("<font color='#37c972'>").append(matchedText).append("</font>");
        return hightMatchedText.replace(matchedText, matchedTextBuffer.toString());

    }


    public boolean search(String input) {
        if (StringUtil.isNullOrEmpty(input)) {
            return false;
        }

        input = input.toUpperCase();

        if (searchNumber(input)) {
            return true;
        }
        return false;
    }

    private boolean searchNumber(String input) {
        if (StringUtil.isNullOrEmpty(mPeerNumber)) {
            return false;
        }
        if (mPeerNumber.contains(input)) {
            originalText = mPeerNumber;
            matchedText = input;
            return true;
        }
        return false;
    }


}
