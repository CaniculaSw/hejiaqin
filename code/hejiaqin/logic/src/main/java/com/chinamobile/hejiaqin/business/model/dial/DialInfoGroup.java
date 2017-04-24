package com.chinamobile.hejiaqin.business.model.dial;

import java.util.List;

/**
 * Created by Administrator on 2016/7/3 0003.
 */
public class DialInfoGroup {
    private String groupName;

    List<DialInfo> dialInfoList;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<DialInfo> getDialInfoList() {
        return dialInfoList;
    }

    public void setDialInfoList(List<DialInfo> dialInfoList) {
        this.dialInfoList = dialInfoList;
    }
}
