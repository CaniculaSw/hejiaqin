package com.chinamobile.hejiaqin.business.model.more;

import com.customer.framework.utils.StringUtil;

/**
 * Created by eshaohu on 16/9/2.
 */
public class TvSettingInfo {
    private boolean isAutoAnswer;
    private String numberOne;
    private String numberTwo;
    private String numberThree;
    private String numberFour;

    public String getNumberOne() {
        return numberOne;
    }

    public void setNumberOne(String numberOne) {
        this.numberOne = numberOne;
    }

    public String getNumberTwo() {
        return numberTwo;
    }

    public void setNumberTwo(String numberTwo) {
        this.numberTwo = numberTwo;
    }

    public String getNumberThree() {
        return numberThree;
    }

    public void setNumberThree(String numberThree) {
        this.numberThree = numberThree;
    }

    public String getNumberFour() {
        return numberFour;
    }

    public void setNumberFour(String numberFour) {
        this.numberFour = numberFour;
    }


    public boolean isAutoAnswer() {
        return isAutoAnswer;
    }

    public void setAutoAnswer(boolean autoAnswer) {
        isAutoAnswer = autoAnswer;
    }

    public boolean isInAutoAnswerList(String inNumber) {
        if (!isAutoAnswer || (StringUtil.isNullOrEmpty(numberOne) && StringUtil.isNullOrEmpty(numberTwo) && StringUtil.isNullOrEmpty(numberThree) && StringUtil.isNullOrEmpty(numberFour))) {
            return false;
        }else if (StringUtil.isNullOrEmpty(numberOne) && StringUtil.isNullOrEmpty(numberTwo) && StringUtil.isNullOrEmpty(numberThree) && StringUtil.isNullOrEmpty(numberFour)){
            return true;
        }else {
            return inNumber.equals(numberOne) || inNumber.equals(numberTwo)|| inNumber.equals(numberThree)||inNumber.equals(numberFour);
        }
    }
}
