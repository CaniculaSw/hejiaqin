package com.chinamobile.hejiaqin.business.logic.setting;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.more.VersionInfo;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.setting.SettingHttpmanager;
import com.chinamobile.hejiaqin.business.utils.SysInfoUtil;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.logic.LogicImp;
import com.customer.framework.utils.LogUtil;

/**
 * Created by eshaohu on 16/5/24.
 */
public class SettingLogic extends LogicImp implements ISettingLogic {
    private static final String TAG = "SettingLogic";

    public void checkVersion() {
        new SettingHttpmanager(getContext()).checkVersion(null, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                SettingLogic.this.sendEmptyMessage(BussinessConstants.LoginMsgID.UPDATE_PWD_SUCCESS_MSG_ID);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                SettingLogic.this.sendMessage(BussinessConstants.LoginMsgID.UPDATE_PWD_FAIL_MSG_ID, desc);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    private boolean isNewVersion(VersionInfo versionInfo){
        int currentVersioncode,versionCode;
        currentVersioncode = SysInfoUtil.getVersionCode(getContext());
        try {
            versionCode = Integer.parseInt(versionInfo.getVersionCode());
        } catch (Exception e) {
            LogUtil.e(TAG, e);
            return false;
        }
        if (currentVersioncode < versionCode){
            return true;
        }
        return false;
    }
    private boolean isForceUpgrade(VersionInfo versionInfo) {
        if (versionInfo.getByForce() == 1) {
            return true;
        }
        int currentVersioncode, forceVersionCode;
        currentVersioncode = SysInfoUtil.getVersionCode(getContext());
        try {
            forceVersionCode = Integer.parseInt(versionInfo.getForceVersionCode());
        } catch (Exception e) {
            LogUtil.e(TAG, e);
            return false;
        }
        if (currentVersioncode < forceVersionCode){
            return true;
        }
        return false;
    }
}
