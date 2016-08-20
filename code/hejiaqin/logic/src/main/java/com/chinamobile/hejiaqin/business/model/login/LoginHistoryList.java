package com.chinamobile.hejiaqin.business.model.login;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/5/9.
 */
public class LoginHistoryList {

    private List<LoginHistory> histories = new ArrayList<LoginHistory>();

    public List<LoginHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<LoginHistory> histories) {
        this.histories = histories;
    }
}
