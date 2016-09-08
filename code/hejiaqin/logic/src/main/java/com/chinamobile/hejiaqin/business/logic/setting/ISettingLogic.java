package com.chinamobile.hejiaqin.business.logic.setting;

import android.content.Context;

/**
 * Created by eshaohu on 16/5/24.
 */
public interface ISettingLogic {
    public void checkVersion();
    public void handleCommit(Context context, String inputNumber,String id);
}
