package com.chinamobile.hejiaqin.business.manager;

import android.content.Context;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.LoginHistory;
import com.chinamobile.hejiaqin.business.model.login.LoginHistoryList;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.person.PersonInfo;
import com.chinamobile.hejiaqin.business.model.person.PersonalDocument;
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


    public static void updateHistory(Context context,LoginHistory history)
    {
        Object obj = StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY);
        LoginHistoryList historyList;
        if (obj == null) {
            historyList= new LoginHistoryList();
        }else
        {
            historyList = (LoginHistoryList)(obj);
        }
        int replaceSeq =-1;
        for(int i=0;i<historyList.getHistories().size();i++)
        {
            if(historyList.getHistories().get(i).getLoginid().equals(history.getLoginid()))
            {
                replaceSeq =i;
            }
        }
        if(replaceSeq!=-1)
        {
            historyList.getHistories().remove(replaceSeq);
        }
        historyList.getHistories().add(history);
        //超过10个，移除最先加入的
        if(historyList.getHistories().size()>BussinessConstants.Login.LOGIN_HISTORY_LIST_MAX)
        {
            historyList.getHistories().remove(0);
        }
        saveHistoryToLoacl(context,historyList);
        saveHistoryToMem(context,historyList);
    }

    public static void saveUserToLoacl(Context context,UserInfo info, long tokenDate) {
        HashMap map = new HashMap();
        Gson gson = new Gson();
        map.put(BussinessConstants.Login.USER_INFO_KEY, gson.toJson(info));
        map.put(BussinessConstants.Login.TOKEN_DATE, tokenDate);
        StorageMgr.getInstance().getSharedPStorage(context).save(map);
    }

    public static void saveUserToMem(Context context,UserInfo info, long tokenDate) {
        if (info != null) {
            StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Login.USER_INFO_KEY, info);
        }
        StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Login.TOKEN_DATE, tokenDate);
    }

    public static void saveHistoryToLoacl(Context context,LoginHistoryList historyList) {
        HashMap map = new HashMap();
        Gson gson = new Gson();
        map.put(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY, gson.toJson(historyList));
        StorageMgr.getInstance().getSharedPStorage(context).save(map);
    }

    public static void saveHistoryToMem(Context context,LoginHistoryList historyList) {
        if (historyList != null) {
            StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY, historyList);
        }
    }

    public static void clearUserInfo(Context context) {
        String[] keys = new String[]{BussinessConstants.Login.USER_INFO_KEY, BussinessConstants.Login.TOKEN_DATE};
        StorageMgr.getInstance().getMemStorage().remove(keys);
        StorageMgr.getInstance().getSharedPStorage(context).remove(keys);
    }

    public static void updateUserInfo(Context context,PersonInfo personInfo)
    {
        UserInfo userInfo = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
        String userInfoAvatar = userInfo.getAvatar() == null ? "" : userInfo.getAvatar();
        String userInfoName = userInfo.getName() == null ? "" : userInfo.getName();
        String userInfoPhone = userInfo.getPhone() == null ? "" : userInfo.getPhone();
        if (userInfoAvatar.equals(personInfo.getAvatar()) && userInfoName.equals(personInfo.getName()) && userInfoPhone.equals(personInfo.getPhone())) {
            return;
        }
        //同步到内存userInfo中
        userInfo.setAvatar(personInfo.getAvatar());
        userInfo.setName(personInfo.getName());
        userInfo.setPhone(personInfo.getPhone());
        //同步到本地缓存
        HashMap map = new HashMap();
        Gson gson = new Gson();
        map.put(BussinessConstants.Login.USER_INFO_KEY, gson.toJson(userInfo));
        StorageMgr.getInstance().getSharedPStorage(context).save(map);

        LoginHistory history = new LoginHistory();
        history.setLoginid(personInfo.getLoginid());
        history.setAvatar(personInfo.getAvatar());
        updateHistory(context,history);
    }

    public static void updateUserInfo(Context context,PersonalDocument personalDocument)
    {
        UserInfo userInfo = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
        String userInfoName = userInfo.getName() == null ? "" : userInfo.getName();
        if (userInfoName.equals(personalDocument.getName())) {
            return;
        }
        //同步到内存userInfo中
        userInfo.setName(personalDocument.getName());
        //同步到本地缓存
        HashMap map = new HashMap();
        Gson gson = new Gson();
        map.put(BussinessConstants.Login.USER_INFO_KEY, gson.toJson(userInfo));
        StorageMgr.getInstance().getSharedPStorage(context).save(map);
    }
}
