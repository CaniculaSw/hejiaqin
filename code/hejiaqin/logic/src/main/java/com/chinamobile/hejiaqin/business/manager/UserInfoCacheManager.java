package com.chinamobile.hejiaqin.business.manager;

import android.content.Context;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.LoginHistory;
import com.chinamobile.hejiaqin.business.model.login.LoginHistoryList;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.customer.framework.component.storage.StorageMgr;

import java.util.HashMap;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
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

    public static void saveUserToMem(Context context, UserInfo info, long tokenDate) {
        if (info != null) {
            StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Login.USER_INFO_KEY, info);
        }
        StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Login.TOKEN_DATE, tokenDate);
    }

    public static void saveHistoryToLoacl(Context context, LoginHistoryList historyList) {
        HashMap map = new HashMap();
        Gson gson = new Gson();
        map.put(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY, gson.toJson(historyList));
        StorageMgr.getInstance().getSharedPStorage(context).save(map);
    }

    public static UserInfo getUserInfo(Context context) {
        return (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
    }

    public static String getToken(Context context) {
        UserInfo userInfo = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
        if (null == userInfo) {
            return null;
        }
        
        return userInfo.getToken();
    }

    public static void saveHistoryToMem(Context context, LoginHistoryList historyList) {
        if (historyList != null) {
            StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY, historyList);
        }
    }

    public static void clearUserInfo(Context context) {
        String[] keys = new String[]{BussinessConstants.Login.USER_INFO_KEY, BussinessConstants.Login.TOKEN_DATE};
        StorageMgr.getInstance().getMemStorage().remove(keys);
        StorageMgr.getInstance().getSharedPStorage(context).remove(keys);
    }


}
