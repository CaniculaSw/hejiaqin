package com.chinamobile.hejiaqin.business.logic.setting;

import android.content.Context;
import android.os.Message;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.more.TvSettingInfo;
import com.chinamobile.hejiaqin.business.model.more.VersionInfo;
import com.chinamobile.hejiaqin.business.model.more.req.GetDeviceListReq;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.chinamobile.hejiaqin.business.net.ReqToken;
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

    @Override
    public void handleCommit(Context context, String inputNumber, final String id) {
        TvSettingInfo settingInfo = UserInfoCacheManager.getUserSettingInfo(context);
        if (settingInfo != null) {
            switch (id) {
                case "numberOne":
                    settingInfo.setNumberOne(inputNumber);
                    break;
                case "numberTwo":
                    settingInfo.setNumberTwo(inputNumber);
                    break;
                case "numberThree":
                    settingInfo.setNumberThree(inputNumber);
                    break;
                case "numberFour":
                    settingInfo.setNumberFour(inputNumber);
                    break;
            }
            UserInfoCacheManager.updateUserSetting(context, settingInfo);
            SettingLogic.this.sendEmptyMessage(BussinessConstants.SettingMsgID.AUTO_ANSWER_SETTING_COMMIT);
        }
    }

    @Override
    public void getDeviceList() {
        final GetDeviceListReq reqBody = new GetDeviceListReq();
        new SettingHttpmanager(getContext()).getDeviceList(null, reqBody, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_DEVICE_LIST_SUCCESSFUL,obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {

            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {

            }
        });
    }

    @Override
    public void checkVersion() {
        new SettingHttpmanager(getContext()).checkVersion(null, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                VersionInfo info = (VersionInfo) obj;
//                //TODO Test
//                info = new VersionInfo();
//                info.setVersionCode("1");
//                info.setVersionName("版本1.0");
//                info.setUrl("http://downsz.downcc.com/apk/dianxinxiangjia_downcc.apk");
//                info.setTime("2016-03-01 15:17:42");
//                info.setByForce(0);
//                info.setForceVersionCode("1");
//                //TODO Test End
                if (isNewVersion(info)) {
                    UserInfoCacheManager.saveVersionInfoToLoacl(getContext(), info);
                    if (isForceUpgrade(info)) {
                        LogUtil.i(TAG, "New force version found!");
                        SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.NEW_FORCE_VERSION_AVAILABLE, info);
                    } else {
                        LogUtil.i(TAG, "New version found!");
                        SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.NEW_VERSION_AVAILABLE, info);
                    }
                } else {
                    UserInfoCacheManager.clearVersionInfo(getContext());
                    SettingLogic.this.sendEmptyMessage(BussinessConstants.SettingMsgID.NO_NEW_VERSION_AVAILABLE);
                }
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                UserInfoCacheManager.clearVersionInfo(getContext());
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                UserInfoCacheManager.clearVersionInfo(getContext());
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    private boolean isNewVersion(VersionInfo versionInfo) {
        if (versionInfo == null) {
            return false;
        }
        int currentVersioncode, versionCode;
        currentVersioncode = SysInfoUtil.getVersionCode(getContext());
        try {
            versionCode = Integer.parseInt(versionInfo.getVersionCode());
        } catch (Exception e) {
            LogUtil.e(TAG, e);
            return false;
        }
        if (currentVersioncode < versionCode) {
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
        if (currentVersioncode < forceVersionCode) {
            return true;
        }
        return false;
    }
}
