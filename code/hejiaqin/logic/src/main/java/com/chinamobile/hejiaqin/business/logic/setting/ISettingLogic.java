package com.chinamobile.hejiaqin.business.logic.setting;

import android.content.Context;

import com.huawei.rcs.message.TextMessage;

/**
 * Created by eshaohu on 16/5/24.
 */
public interface ISettingLogic {
    public void checkVersion();
    public void getDeviceList();
    public void handleCommit(Context context, String inputNumber,String id);
    public void sendBindReq(String tvNumber,String phoneNum);
    public void sendBindResult(String toNumber,String opCode);
    public void saveBindRequest(TextMessage message);
}
