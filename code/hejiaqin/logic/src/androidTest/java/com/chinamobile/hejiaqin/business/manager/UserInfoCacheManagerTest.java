package com.chinamobile.hejiaqin.business.manager;

import android.test.AndroidTestCase;

import com.chinamobile.hejiaqin.business.model.login.LoginHistory;
import com.chinamobile.hejiaqin.business.model.login.LoginHistoryList;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.more.TvSettingInfo;
import com.chinamobile.hejiaqin.business.model.more.UserList;
import com.chinamobile.hejiaqin.business.model.more.VersionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class UserInfoCacheManagerTest extends AndroidTestCase {

    public void testUpdateHistory() throws Exception {

        try {
            UserInfoCacheManager.updateHistory(getContext(), null);

            LoginHistory loginHistory = new LoginHistory();
            loginHistory.setLoginid("123");
            loginHistory.setAvatar("123");
            UserInfoCacheManager.updateHistory(getContext(), loginHistory);
        } catch (Exception e) {

        }
    }

    public void testSaveUserToLoacl() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("123");
        userInfo.setTvAccount("123");
        userInfo.setSdkPassword("123");
        userInfo.setPhotoSm("123");
        userInfo.setPhotoLg("123");
        userInfo.setPhone("123");
        userInfo.setImAccount("123");
        userInfo.setImPassword("123");
        userInfo.setSdkAccount("123");
        userInfo.setToken("123");
        UserInfoCacheManager.saveUserToLoacl(getContext(), userInfo, 100);
        UserInfoCacheManager.clearUserInfo(getContext());
    }

    public void testUpdateUserSetting() throws Exception {
        TvSettingInfo tvSettingInfo = new TvSettingInfo();
        tvSettingInfo.setNumberOne("1");
        tvSettingInfo.setNumberTwo("2");
        tvSettingInfo.setNumberThree("3");
        tvSettingInfo.setNumberFour("4");
        tvSettingInfo.setAutoAnswer(true);

        try {
            UserInfoCacheManager.updateUserSetting(getContext(), tvSettingInfo);
        } catch (Exception e) {

        }
    }

    public void testSaveUserToMem() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("123");
        userInfo.setTvAccount("123");
        userInfo.setSdkPassword("123");
        userInfo.setPhotoSm("123");
        userInfo.setPhotoLg("123");
        userInfo.setPhone("123");
        userInfo.setImAccount("123");
        userInfo.setImPassword("123");
        userInfo.setSdkAccount("123");
        userInfo.setToken("123");
        UserInfoCacheManager.saveUserToMem(getContext(), userInfo, 100);
        UserInfoCacheManager.clearUserInfo(getContext());
    }

    public void testSaveVersionInfoToLoacl() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setByForce(1);
        versionInfo.setForceVersionCode("123");
        versionInfo.setNew(true);
        versionInfo.setTime("123");
        versionInfo.setUrl("1234");
        versionInfo.setVersionCode("123");
        versionInfo.setVersionName("aaa");
        UserInfoCacheManager.saveVersionInfoToLoacl(getContext(), versionInfo);
        UserInfoCacheManager.clearVersionInfo(getContext());
    }

    public void testSaveHistoryToLoacl() throws Exception {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setLoginid("123");
        loginHistory.setAvatar("123");

        LoginHistoryList loginHistoryList = new LoginHistoryList();
        List<LoginHistory> loginHistories = new ArrayList<>();
        loginHistories.add(loginHistory);
        loginHistoryList.setHistories(loginHistories);
        UserInfoCacheManager.saveHistoryToLoacl(getContext(), loginHistoryList);
    }

    public void testSaveBindDeviceToLoacl() throws Exception {
        UserList userList = new UserList();
        List<UserInfo> userInfos = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setName("123");
        userInfo.setTvAccount("123");
        userInfo.setSdkPassword("123");
        userInfo.setPhotoSm("123");
        userInfo.setPhotoLg("123");
        userInfo.setPhone("123");
        userInfo.setImAccount("123");
        userInfo.setImPassword("123");
        userInfo.setSdkAccount("123");
        userInfo.setToken("123");
        userInfos.add(userInfo);
        userList.setUsers(userInfos);
        UserInfoCacheManager.saveBindDeviceToLoacl(getContext(), userList);
    }

    public void testSaveTvAccountToLoacl() throws Exception {
        UserInfoCacheManager.saveTvAccountToLoacl(getContext(), "123");
    }

    public void testSaveBindDeviceToMem() throws Exception {
        UserList userList = new UserList();
        List<UserInfo> userInfos = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setName("123");
        userInfo.setTvAccount("123");
        userInfo.setSdkPassword("123");
        userInfo.setPhotoSm("123");
        userInfo.setPhotoLg("123");
        userInfo.setPhone("123");
        userInfo.setImAccount("123");
        userInfo.setImPassword("123");
        userInfo.setSdkAccount("123");
        userInfo.setToken("123");
        userInfos.add(userInfo);
        userList.setUsers(userInfos);
        UserInfoCacheManager.saveBindDeviceToMem(getContext(), userList);
    }

    public void testSaveBindAppToLoacl() throws Exception {
        UserList userList = new UserList();
        List<UserInfo> userInfos = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setName("123");
        userInfo.setTvAccount("123");
        userInfo.setSdkPassword("123");
        userInfo.setPhotoSm("123");
        userInfo.setPhotoLg("123");
        userInfo.setPhone("123");
        userInfo.setImAccount("123");
        userInfo.setImPassword("123");
        userInfo.setSdkAccount("123");
        userInfo.setToken("123");
        userInfos.add(userInfo);
        userList.setUsers(userInfos);

        UserInfoCacheManager.saveBindAppToLoacl(getContext(), userList);
    }

    public void testSaveBindAppToMem() throws Exception {
        UserList userList = new UserList();
        List<UserInfo> userInfos = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setName("123");
        userInfo.setTvAccount("123");
        userInfo.setSdkPassword("123");
        userInfo.setPhotoSm("123");
        userInfo.setPhotoLg("123");
        userInfo.setPhone("123");
        userInfo.setImAccount("123");
        userInfo.setImPassword("123");
        userInfo.setSdkAccount("123");
        userInfo.setToken("123");
        userInfos.add(userInfo);
        userList.setUsers(userInfos);

        UserInfoCacheManager.saveBindAppToMem(getContext(), userList);
    }

    public void testIsBinded() throws Exception {

        UserInfoCacheManager.isBinded(getContext());
    }

    public void testIsBindedApp() throws Exception {
        UserInfoCacheManager.isBindedApp(getContext(), "123");
    }

    public void testIsBindedDevice() throws Exception {
        try {
            UserInfoCacheManager.isBindedDevice(getContext(), "123");
        } catch (Exception e) {

        }
    }

    public void testGetUserList() throws Exception {
        UserInfoCacheManager.getUserList(getContext(), "123");
    }

    public void testGetUserInfo() throws Exception {
        UserInfoCacheManager.getUserInfo(getContext());
    }

    public void testGetVersionInfo() throws Exception {
        UserInfoCacheManager.getVersionInfo(getContext());
    }

    public void testGetUserSettingInfo() throws Exception {
        UserInfoCacheManager.getUserInfo(getContext());
    }

    public void testGetToken() throws Exception {
        UserInfoCacheManager.getToken(getContext());
    }

    public void testGetTvAccount() throws Exception {
        UserInfoCacheManager.getTvAccount(getContext());
    }

    public void testGetUserId() throws Exception {
        UserInfoCacheManager.getUserId(getContext());
    }

    public void testSaveHistoryToMem() throws Exception {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setLoginid("123");
        loginHistory.setAvatar("123");

        LoginHistoryList loginHistoryList = new LoginHistoryList();
        List<LoginHistory> loginHistories = new ArrayList<>();
        loginHistories.add(loginHistory);
        loginHistoryList.setHistories(loginHistories);

        UserInfoCacheManager.saveHistoryToMem(getContext(), loginHistoryList);
    }

    public void testClearUserInfo() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("123");
        userInfo.setTvAccount("123");
        userInfo.setSdkPassword("123");
        userInfo.setPhotoSm("123");
        userInfo.setPhotoLg("123");
        userInfo.setPhone("123");
        userInfo.setImAccount("123");
        userInfo.setImPassword("123");
        userInfo.setSdkAccount("123");
        userInfo.setToken("123");
        UserInfoCacheManager.saveUserToLoacl(getContext(), userInfo, 100);
        UserInfoCacheManager.clearUserInfo(getContext());
    }

    public void testClearVersionInfo() throws Exception {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setByForce(1);
        versionInfo.setForceVersionCode("123");
        versionInfo.setNew(true);
        versionInfo.setTime("123");
        versionInfo.setUrl("1234");
        versionInfo.setVersionCode("123");
        versionInfo.setVersionName("aaa");
        UserInfoCacheManager.saveVersionInfoToLoacl(getContext(), versionInfo);
        UserInfoCacheManager.clearVersionInfo(getContext());
    }

    public void testSaveVoipLogined() throws Exception {
        UserInfoCacheManager.saveVoipLogined(getContext());
    }

    public void testSaveTvLogout() throws Exception {
        UserInfoCacheManager.saveTvLogout(getContext());
    }

    public void testSaveSTBConfig() throws Exception {
        UserInfoCacheManager.saveSTBConfig(getContext(), "123", "123", "123");
    }

    public void testClearVoipLogined() throws Exception {
        UserInfoCacheManager.clearVoipLogined(getContext());
    }

    public void testGetVoipLogined() throws Exception {
        UserInfoCacheManager.getVoipLogined(getContext());
    }

    public void testClearTvIsLogout() throws Exception {
        UserInfoCacheManager.clearTvIsLogout(getContext());
    }

    public void testGetTvIsLogout() throws Exception {
        UserInfoCacheManager.getTvIsLogout(getContext());
    }

    public void testGetTvUserID() throws Exception {
        UserInfoCacheManager.getTvUserID(getContext());
    }

    public void testGetTvToken() throws Exception {
        UserInfoCacheManager.getTvToken(getContext());
    }

    public void testGetSoftware() throws Exception {
        UserInfoCacheManager.getSoftware(getContext());
    }
}