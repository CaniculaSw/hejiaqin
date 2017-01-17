package com.chinamobile.hejiaqin.business.manager;

import android.content.Context;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.LoginHistory;
import com.chinamobile.hejiaqin.business.model.login.LoginHistoryList;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.more.TvSettingInfo;
import com.chinamobile.hejiaqin.business.model.more.UserList;
import com.chinamobile.hejiaqin.business.model.more.VersionInfo;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.StringUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/5/13.
 */
public class UserInfoCacheManager {


    public static void updateHistory(Context context, LoginHistory history) {
        Object obj = StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY);
        LoginHistoryList historyList;
        if (obj == null) {
            historyList = new LoginHistoryList();
        } else {
            historyList = (LoginHistoryList) (obj);
        }
        int replaceSeq = -1;
        for (int i = 0; i < historyList.getHistories().size(); i++) {
            if (historyList.getHistories().get(i).getLoginid().equals(history.getLoginid())) {
                replaceSeq = i;
            }
        }
        if (replaceSeq != -1) {
            historyList.getHistories().remove(replaceSeq);
        }
        historyList.getHistories().add(history);
        //超过10个，移除最先加入的
        if (historyList.getHistories().size() > BussinessConstants.Login.LOGIN_HISTORY_LIST_MAX) {
            historyList.getHistories().remove(0);
        }
        saveHistoryToLoacl(context, historyList);
        saveHistoryToMem(context, historyList);
    }

    public static void saveUserToLoacl(Context context, UserInfo info, long tokenDate) {
        HashMap map = new HashMap();
        Gson gson = new Gson();
        map.put(BussinessConstants.Login.USER_INFO_KEY, gson.toJson(info));
        map.put(BussinessConstants.Login.TOKEN_DATE, tokenDate);
        StorageMgr.getInstance().getSharedPStorage(context).save(map);
    }

    private static void saveUserSettingToLocal(Context context, TvSettingInfo setting) {
        HashMap map = new HashMap();
        Gson gson = new Gson();
        map.put(getUserInfo(context).getTvAccount() + BussinessConstants.Setting.USER_SETTING_KEY, gson.toJson(setting));
        StorageMgr.getInstance().getSharedPStorage(context).save(map);
    }

//    private static void saveUserSettingToMem(Context context, TvSettingInfo setting){
//        if (setting != null){
//            StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Setting.USER_SETTING_KEY, setting);
//        }
//    }

    public static void updateUserSetting(Context context, TvSettingInfo newSettingInfo) {
        if (newSettingInfo != null) {
            saveUserSettingToLocal(context, newSettingInfo);
        }
    }

    public static void saveUserToMem(Context context, UserInfo info, long tokenDate) {
        if (info != null) {
            StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Login.USER_INFO_KEY, info);
        }
        StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Login.TOKEN_DATE, tokenDate);
    }

    public static void saveVersionInfoToLoacl(Context context, VersionInfo info) {
        if (info != null) {
            HashMap map = new HashMap();
            Gson gson = new Gson();
            map.put(BussinessConstants.Setting.VERSION_INFO_KEY, gson.toJson(info));
            StorageMgr.getInstance().getSharedPStorage(context).save(map);
            LogUtil.d("UserInfoCacheManager", "Version info is saved.");
        }
    }

    public static void saveHistoryToLoacl(Context context, LoginHistoryList historyList) {
        HashMap map = new HashMap();
        Gson gson = new Gson();
        map.put(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY, gson.toJson(historyList));
        StorageMgr.getInstance().getSharedPStorage(context).save(map);
    }

    public static void saveBindDeviceToLoacl(Context context, UserList userList) {
        HashMap map = new HashMap();
        Gson gson = new Gson();
        map.put(BussinessConstants.Setting.BINDED_DEVICE_KEY, gson.toJson(userList));
        StorageMgr.getInstance().getSharedPStorage(context).save(map);
    }

    public static void saveBindDeviceToMem(Context context, UserList userList) {
        if (userList != null) {
            StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Setting.BINDED_DEVICE_KEY, userList);
        }
    }

    public static void saveBindAppToLoacl(Context context, UserList userList) {
        HashMap map = new HashMap();
        Gson gson = new Gson();
        map.put(BussinessConstants.Setting.BINDED_APP_KEY, gson.toJson(userList));
        StorageMgr.getInstance().getSharedPStorage(context).save(map);
    }

    public static void saveBindAppToMem(Context context, UserList userList) {
        if (userList != null) {
            StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Setting.BINDED_APP_KEY, userList);
        }
    }

    public static boolean isBinded(Context context) {
        UserList userList = getUserList(context, BussinessConstants.Setting.BINDED_DEVICE_KEY);
        if (userList == null || userList.getUsers().size() <= 0) {
            return false;
        }
        return true;
    }

    public static boolean isBindedApp(Context context, String num) {
        //TODO:因为服务器不OK，暂时返回true
        return true;
//        UserList userList = getUserList(context, BussinessConstants.Setting.BINDED_APP_KEY);
//        if (userList == null) {
//            return false;
//        }
//        List<UserInfo> userInfoList = userList.getUsers();
//        boolean flag = false;
//        Iterator<UserInfo> iterator = userInfoList.iterator();
//        while (iterator.hasNext()) {
//            UserInfo userInfo = iterator.next();
//            if (CommonUtils.getPhoneNumber(num).equals(CommonUtils.getPhoneNumber(userInfo.getPhone()))) {
//                flag = true;
//                break;
//            }
//        }
//        return flag;
    }

    public static boolean isBindedDevice(Context context, String num) {
        UserList userList = getUserList(context, BussinessConstants.Setting.BINDED_DEVICE_KEY);
        List<UserInfo> userInfoList = userList.getUsers();
        boolean flag = false;
        Iterator<UserInfo> iterator = userInfoList.iterator();
        while (iterator.hasNext()) {
            UserInfo userInfo = iterator.next();
            if (CommonUtils.getPhoneNumber(num).equals(CommonUtils.getPhoneNumber(userInfo.getTvAccount()))) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static UserList getUserList(Context context, String key) {
        String userListStr = StorageMgr.getInstance().getSharedPStorage(context).getString(key);
        if (userListStr != null) {
            Gson gson = new Gson();
            return gson.fromJson(userListStr, UserList.class);
        }
        return null;
    }

    public static UserInfo getUserInfo(Context context) {
        return (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
    }

    public static VersionInfo getVersionInfo(Context context) {
        String infoStr = StorageMgr.getInstance().getSharedPStorage(context).getString(BussinessConstants.Setting.VERSION_INFO_KEY);
        if (infoStr != null) {
            Gson gson = new Gson();
            return gson.fromJson(infoStr, VersionInfo.class);
        }
        return null;
    }

    public static TvSettingInfo getUserSettingInfo(Context context) {
        String settingStr = StorageMgr.getInstance().getSharedPStorage(context).getString(getUserInfo(context).getTvAccount() + BussinessConstants.Setting.USER_SETTING_KEY);
        if (settingStr != null) {
            Gson gson = new Gson();
            return gson.fromJson(settingStr, TvSettingInfo.class);
        }
        return null;
    }

    public static String getToken(Context context) {
        UserInfo userInfo = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
        if (null == userInfo) {
            return null;
        }

        return userInfo.getToken();
    }

    public static String getUserId(Context context) {
        UserInfo userInfo = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
        if (null == userInfo) {
            return "unknown";
        }

        String userId = userInfo.getUserId();
        return StringUtil.isNullOrEmpty(userId) ? "unknown" : userId;
    }

    public static void saveHistoryToMem(Context context, LoginHistoryList historyList) {
        if (historyList != null) {
            StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY, historyList);
        }
    }

    public static void clearUserInfo(Context context) {
        String[] keys = new String[]{BussinessConstants.Login.USER_INFO_KEY, BussinessConstants.Login.TOKEN_DATE, BussinessConstants.Setting.BINDED_DEVICE_KEY, BussinessConstants.Setting.BINDED_DEVICE_KEY};
        StorageMgr.getInstance().getMemStorage().remove(keys);
        StorageMgr.getInstance().getSharedPStorage(context).remove(keys);
    }

    public static void clearVersionInfo(Context context) {
        StorageMgr.getInstance().getSharedPStorage(context).remove(new String[]{BussinessConstants.Setting.VERSION_INFO_KEY});
        LogUtil.d("UserInfoCacheManager", "Version info is remove.");
    }

    public static void saveVoipLogined(Context context) {
        StorageMgr.getInstance().getSharedPStorage(context).save(BussinessConstants.Login.VOIP_LOGINED_KEY, true);
    }

    public static void saveSTBConfig(Context context, String userid, String token, String softVersion) {
        StorageMgr.getInstance().getSharedPStorage(context).save(BussinessConstants.Login.TV_USERID_KEY, userid);
        StorageMgr.getInstance().getSharedPStorage(context).save(BussinessConstants.Login.TV_TOKEN_KEY, token);
        StorageMgr.getInstance().getSharedPStorage(context).save(BussinessConstants.Login.TV_SOFT_WARE_KEY, softVersion);
    }

//    public static void clearSTBConfig(Context context) {
//        StorageMgr.getInstance().getSharedPStorage(context).remove(new String[]{BussinessConstants.Login.TV_USERID_KEY, BussinessConstants.Login.TV_TOKEN_KEY});
//    }

    public static void clearVoipLogined(Context context) {
        StorageMgr.getInstance().getSharedPStorage(context).remove(new String[]{BussinessConstants.Login.VOIP_LOGINED_KEY});
    }

    public static boolean getVoipLogined(Context context) {
        return StorageMgr.getInstance().getSharedPStorage(context).getBoolean(BussinessConstants.Login.VOIP_LOGINED_KEY);
    }

    public static String getTvUserID(Context context) {
        return StorageMgr.getInstance().getSharedPStorage(context).getString(BussinessConstants.Login.TV_USERID_KEY);
    }

    public static String getTvToken(Context context) {
        return StorageMgr.getInstance().getSharedPStorage(context).getString(BussinessConstants.Login.TV_TOKEN_KEY);
    }

    public static String getSoftware(Context context) {
        return StorageMgr.getInstance().getSharedPStorage(context).getString(BussinessConstants.Login.TV_SOFT_WARE_KEY);
    }
}
