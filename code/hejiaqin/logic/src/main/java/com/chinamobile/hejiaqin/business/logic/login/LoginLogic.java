package com.chinamobile.hejiaqin.business.logic.login;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.req.PasswordInfo;
import com.chinamobile.hejiaqin.business.model.login.LoginHistory;
import com.chinamobile.hejiaqin.business.model.login.LoginHistoryList;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.business.model.login.req.RegisterFirstStepInfo;
import com.chinamobile.hejiaqin.business.model.login.req.RegisterSecondStepInfo;
import com.chinamobile.hejiaqin.business.model.login.req.VerifyInfo;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.MapStrReqBody;
import com.chinamobile.hejiaqin.business.net.login.LoginHttpManager;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.logic.LogicImp;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;

import java.util.Date;

/**
 * 登录逻辑接口
 * Kangxi Version 001
 * author: zhanggj
 * Created: 2016/4/8.
 */
public class LoginLogic extends LogicImp implements ILoginLogic {

    @Override
    public void getVerifyCode(String phone) {
        MapStrReqBody reqBody = new MapStrReqBody();
        reqBody.add("phone", phone);
        new LoginHttpManager(getContext()).getVerifyCode(null, reqBody, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                LoginLogic.this.sendEmptyMessage(BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_SUCCESS_MSG_ID);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                LoginLogic.this.sendMessage(BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
                LoginLogic.this.sendMessage(BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_NET_ERROR_MSG_ID, errorCode);
            }
        });
    }


    @Override
    public void checkVerifyCode(VerifyInfo verifyInfo) {
        new LoginHttpManager(getContext()).checkVerifyCode(null, verifyInfo, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                LoginLogic.this.sendEmptyMessage(BussinessConstants.LoginMsgID.CHECK_VERIFY_CDOE_SUCCESS_MSG_ID);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                LoginLogic.this.sendMessage(BussinessConstants.LoginMsgID.CHECK_VERIFY_CDOE_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void registerFirstStep(RegisterFirstStepInfo info) {
        new LoginHttpManager(getContext()).registerFirstStep(null, info, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                UserInfo userInfo = (UserInfo) obj;
                Date now = new Date();
                UserInfoCacheManager.saveUserToMem(getContext(),userInfo, now.getTime());
                LoginLogic.this.sendEmptyMessage(BussinessConstants.LoginMsgID.REGISTER_FIRST_STEP_SUCCESS_MSG_ID);
                UserInfoCacheManager.saveUserToLoacl(getContext(),userInfo, now.getTime());
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                LoginLogic.this.sendMessage(BussinessConstants.LoginMsgID.REGISTER_FIRST_STEP_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void registerSecondStep(RegisterSecondStepInfo info) {
        new LoginHttpManager(getContext()).registerSecondStep(null, info, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                UserInfo userInfo = (UserInfo) obj;
                Date now = new Date();
                UserInfoCacheManager.saveUserToMem(getContext(),userInfo, now.getTime());
                LoginLogic.this.sendEmptyMessage(BussinessConstants.LoginMsgID.REGISTER_SECOND_STEP_SUCCESS_MSG_ID);
                UserInfoCacheManager.saveUserToLoacl(getContext(),userInfo, now.getTime());
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                LoginLogic.this.sendMessage(BussinessConstants.LoginMsgID.REGISTER_SECOND_STEP_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void login(LoginInfo info) {
        new LoginHttpManager(getContext()).login(null, info, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                UserInfo userInfo = (UserInfo) obj;
                Date now = new Date();
                UserInfoCacheManager.saveUserToMem(getContext(),userInfo, now.getTime());
                LoginLogic.this.sendEmptyMessage(BussinessConstants.LoginMsgID.LOGIN_SUCCESS_MSG_ID);
                UserInfoCacheManager.saveUserToLoacl(getContext(),userInfo, now.getTime());
                LoginHistory history = new LoginHistory();
                history.setLoginid(userInfo.getLoginid());
                history.setAvatar(userInfo.getAvatar());
                UserInfoCacheManager.updateHistory(getContext(),history);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                LoginLogic.this.sendMessage(BussinessConstants.LoginMsgID.LOGIN_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void logout() {
        UserInfoCacheManager.clearUserInfo(getContext());
        new LoginHttpManager(getContext()).logout(null, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
            }
        });
    }

    /**
     * 已经登录过
     *
     * @return 是否已经登录
     */
    @Override
    public boolean hasLogined() {
        return !isExpired();
    }

    @Override
    public void loadUserFromLocal() {
        String infoCache = StorageMgr.getInstance().getSharedPStorage(getContext()).getString(BussinessConstants.Login.USER_INFO_KEY);
        UserInfo info = null;
        if (infoCache != null) {
            Gson gson = new Gson();
            info = gson.fromJson(infoCache, UserInfo.class);
        }
        long tokenDate = StorageMgr.getInstance().getSharedPStorage(getContext()).getLong(BussinessConstants.Login.TOKEN_DATE);
        UserInfoCacheManager.saveUserToMem(getContext(),info, tokenDate);
    }

    @Override
    public void loadHistoryFromLocal() {
        String historyCache = StorageMgr.getInstance().getSharedPStorage(getContext()).getString(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY);
        LoginHistoryList historyList = null;
        if (historyCache != null) {
            Gson gson = new Gson();
            historyList = gson.fromJson(historyCache, LoginHistoryList.class);
        }
        if (historyList != null) {
            StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY, historyList);
        }
    }

    @Override
    public LoginHistory getLoginHistory(String loginid) {
        Object obj = StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY);
        LoginHistoryList historyList;
        if (obj == null) {
            historyList= new LoginHistoryList();
        }else
        {
            historyList = (LoginHistoryList)(obj);
        }
        LoginHistory loginHistory =null;
        for(int i=0;i<historyList.getHistories().size();i++)
        {
            if(loginid.equals(historyList.getHistories().get(i).getLoginid()))
            {
                loginHistory = historyList.getHistories().get(i);
            }
        }
        return loginHistory;
    }

    private boolean isExpired() {
        Object obj =  StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
        if (obj == null) {
            return true;
        }
        UserInfo info = (UserInfo)obj;
        long expire = info.getExpire();
        //永不过期
        if (expire == -1) {
            return false;
        }
        long tokenDate = StorageMgr.getInstance().getMemStorage().getLong(BussinessConstants.Login.TOKEN_DATE);
        if (tokenDate != Long.MIN_VALUE) {
            Date now = new Date();
            //在有效期内
            if (now.getTime() - tokenDate < expire) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updatePassword(PasswordInfo pwdInfo) {
        new LoginHttpManager(getContext()).updatePassword(null, pwdInfo, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                LoginLogic.this.sendEmptyMessage(BussinessConstants.LoginMsgID.UPDATE_PWD_SUCCESS_MSG_ID);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                LoginLogic.this.sendMessage(BussinessConstants.LoginMsgID.UPDATE_PWD_FAIL_MSG_ID, desc);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }




}
