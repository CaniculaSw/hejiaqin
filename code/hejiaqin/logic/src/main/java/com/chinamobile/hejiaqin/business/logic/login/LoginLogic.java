package com.chinamobile.hejiaqin.business.logic.login;

import android.content.Context;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.FailResponse;
import com.chinamobile.hejiaqin.business.model.login.LoginHistory;
import com.chinamobile.hejiaqin.business.model.login.LoginHistoryList;
import com.chinamobile.hejiaqin.business.model.login.RespondInfo;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.FeedBackReq;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.business.model.login.req.PasswordInfo;
import com.chinamobile.hejiaqin.business.model.login.req.RegisterSecondStepInfo;
import com.chinamobile.hejiaqin.business.model.login.req.TvLoginInfo;
import com.chinamobile.hejiaqin.business.model.login.req.UpdatePhotoReq;
import com.chinamobile.hejiaqin.business.model.login.req.VerifyInfo;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.NVPWithTokenReqBody;
import com.chinamobile.hejiaqin.business.net.login.CMIMHelperManager;
import com.chinamobile.hejiaqin.business.net.login.LoginHttpManager;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.logic.LogicImp;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.StringUtil;
import com.customer.framework.utils.cryptor.DigestUtil;
import com.google.gson.Gson;
import com.littlec.sdk.manager.CMIMHelper;

import java.util.Date;

/**
 * 登录逻辑接口
 * hejiaqin Version 001
 * author:
 * Created: 2016/4/8.
 */
public class LoginLogic extends LogicImp implements ILoginLogic {
    private static final String TAG = "LoginLogic";
    private static LoginLogic instance;

    /**
     * 获取单例对象
     *
     * @param context 系统的context对象
     * @return LogicBuilder对象
     */
    public synchronized static LoginLogic getInstance(Context context) {
        if (instance == null) {
            instance = new LoginLogic(context);
        }
        return instance;
    }

    private LoginLogic(Context context) {
        init(context.getApplicationContext());
    }

    @Override
    public void getVerifyCode(String phone) {
        final NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("phone", phone);
        new LoginHttpManager(getContext()).getVerifyCode(invoker, reqBody, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                RespondInfo info = (RespondInfo) obj;
                if ("0".equals(info.getCode())) {
                    LoginLogic.this
                            .sendEmptyMessage(BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_SUCCESS_MSG_ID);
                }else {
                    FailResponse response = new FailResponse();
                    response.setCode(info.getCode());
                    response.setMsg(info.getMsg());
                    LoginLogic.this.sendMessage(
                            BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_FAIL_MSG_ID, response);
                }
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                if (isCommonFailRes(code, desc)) {
                    return;
                }
                FailResponse response = new FailResponse();
                response.setCode(code);
                response.setMsg(desc);
                LoginLogic.this.sendMessage(
                        BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_FAIL_MSG_ID, response);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID,
                        errorCode);
                LoginLogic.this.sendMessage(
                        BussinessConstants.LoginMsgID.GET_VERIFY_CDOE_NET_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void getResetPasswordCode(String phone) {
        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("phone", phone);
        new LoginHttpManager(getContext()).getResetPasswordCode(invoker, reqBody, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                LoginLogic.this
                        .sendEmptyMessage(BussinessConstants.LoginMsgID.RESET_GET_VERIFY_CDOE_SUCCESS_MSG_ID);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                if (isCommonFailRes(code, desc)) {
                    return;
                }
                FailResponse response = new FailResponse();
                response.setCode(code);
                response.setMsg(desc);
                LoginLogic.this.sendMessage(
                        BussinessConstants.LoginMsgID.RESET_GET_VERIFY_CDOE_FAIL_MSG_ID, response);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID,
                        errorCode);
                LoginLogic.this.sendMessage(
                        BussinessConstants.LoginMsgID.RESET_GET_VERIFY_CDOE_NET_ERROR_MSG_ID,
                        errorCode);
            }
        });
    }

    @Override
    public void checkVerifyCode(VerifyInfo verifyInfo) {
        new LoginHttpManager(getContext()).checkVerifyCode(invoker, verifyInfo, new IHttpCallBack() {

            @Override
            public void onSuccessful(Object invoker, Object obj) {
                LoginLogic.this
                        .sendEmptyMessage(BussinessConstants.LoginMsgID.CHECK_VERIFY_CDOE_SUCCESS_MSG_ID);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                if (isCommonFailRes(code, desc)) {
                    return;
                }
                FailResponse response = new FailResponse();
                response.setCode(code);
                response.setMsg(desc);
                LoginLogic.this.sendMessage(
                        BussinessConstants.LoginMsgID.CHECK_VERIFY_CDOE_FAIL_MSG_ID, response);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID,
                        errorCode);
            }
        });
    }

    @Override
    public void checkResetPasswordCode(VerifyInfo verifyInfo) {
        new LoginHttpManager(getContext()).checkResetPasswordCode(invoker, verifyInfo,
                new IHttpCallBack() {

                    @Override
                    public void onSuccessful(Object invoker, Object obj) {
                        LoginLogic.this
                                .sendMessage(
                                        BussinessConstants.LoginMsgID.RESET_CHECK_VERIFY_CDOE_SUCCESS_MSG_ID,
                                        obj);
                    }

                    @Override
                    public void onFailure(Object invoker, String code, String desc) {
                        if (isCommonFailRes(code, desc)) {
                            return;
                        }
                        FailResponse response = new FailResponse();
                        response.setCode(code);
                        response.setMsg(desc);
                        LoginLogic.this.sendMessage(
                                BussinessConstants.LoginMsgID.RESET_CHECK_VERIFY_CDOE_FAIL_MSG_ID,
                                response);
                    }

                    @Override
                    public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                        LoginLogic.this.sendMessage(
                                BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
                    }
                });
    }

    @Override
    public void registerSecondStep(RegisterSecondStepInfo info) {
        new LoginHttpManager(getContext()).registerSecondStep(invoker, info, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                LoginLogic.this
                        .sendEmptyMessage(BussinessConstants.LoginMsgID.REGISTER_SECOND_STEP_SUCCESS_MSG_ID);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                if (isCommonFailRes(code, desc)) {
                    return;
                }
                FailResponse response = new FailResponse();
                response.setCode(code);
                response.setMsg(desc);
                LoginLogic.this.sendMessage(
                        BussinessConstants.LoginMsgID.REGISTER_SECOND_STEP_FAIL_MSG_ID, response);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID,
                        errorCode);
            }
        });
    }

    @Override
    public void login(LoginInfo info) {
        new LoginHttpManager(getContext()).login(invoker, info, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                UserInfo userInfo = (UserInfo) obj;
                LogUtil.d(TAG, "UserInfo" + userInfo.toString());
                Date now = new Date();
                UserInfoCacheManager.saveUserToMem(getContext(), userInfo, now.getTime());
                initCMIMSdk();
                LoginLogic.this
                        .sendEmptyMessage(BussinessConstants.LoginMsgID.LOGIN_SUCCESS_MSG_ID);
                UserInfoCacheManager.saveUserToLoacl(getContext(), userInfo, now.getTime());
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                if (isCommonFailRes(code, desc)) {
                    return;
                }
                FailResponse response = new FailResponse();
                response.setCode(code);
                response.setMsg(desc);
                LoginLogic.this.sendMessage(BussinessConstants.LoginMsgID.LOGIN_FAIL_MSG_ID,
                        response);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(
                        BussinessConstants.CommonMsgId.LOGIN_NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void tvLogin(TvLoginInfo info) {
        new LoginHttpManager(getContext()).tvLogin(invoker, info, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                UserInfo userInfo = (UserInfo) obj;
                Date now = new Date();
                UserInfoCacheManager.saveUserToMem(getContext(), userInfo, now.getTime());
                UserInfoCacheManager.saveTvAccountToLoacl(getContext(), userInfo.getTvAccount());
                //                initCMIMSdk();
                LoginLogic.this
                        .sendEmptyMessage(BussinessConstants.LoginMsgID.LOGIN_SUCCESS_MSG_ID);
                UserInfoCacheManager.saveUserToLoacl(getContext(), userInfo, now.getTime());
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                if (isCommonFailRes(code, desc)) {
                    return;
                }
                FailResponse response = new FailResponse();
                response.setCode(code);
                response.setMsg(desc);
                LoginLogic.this.sendMessage(BussinessConstants.LoginMsgID.LOGIN_FAIL_MSG_ID,
                        response);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(
                        BussinessConstants.CommonMsgId.LOGIN_NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void checkTvAccount(TvLoginInfo info) {
        new LoginHttpManager(getContext()).checkTvAccount(invoker, info, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                String data = (String) obj;
                LogUtil.d(TAG, "data is: " + data);
                switch (data) {
                    case "0":
                        LoginLogic.this
                                .sendEmptyMessage(BussinessConstants.LoginMsgID.TV_ACCOUNT_UNREGISTERED);
                        break;
                    case "1":
                        LoginLogic.this
                                .sendEmptyMessage(BussinessConstants.LoginMsgID.TV_ACCOUNT_REGISTERED);
                        break;
                    default:
                        LoginLogic.this
                                .sendEmptyMessage(BussinessConstants.CommonMsgId.SERVER_SIDE_ERROR);
                        break;
                }
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                if (isCommonFailRes(code, desc)) {
                    return;
                }
                FailResponse response = new FailResponse();
                response.setCode(code);
                response.setMsg(desc);
                LoginLogic.this.sendEmptyMessage(BussinessConstants.CommonMsgId.SERVER_SIDE_ERROR);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(
                        BussinessConstants.CommonMsgId.LOGIN_NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void logout() {
        new LoginHttpManager(getContext()).logout(invoker, new IHttpCallBack() {
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
        clean();
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
        String infoCache = StorageMgr.getInstance().getSharedPStorage(getContext())
                .getString(BussinessConstants.Login.USER_INFO_KEY);
        UserInfo info = null;
        if (infoCache != null) {
            Gson gson = new Gson();
            info = gson.fromJson(infoCache, UserInfo.class);
        }
        long tokenDate = StorageMgr.getInstance().getSharedPStorage(getContext())
                .getLong(BussinessConstants.Login.TOKEN_DATE);
        UserInfoCacheManager.saveUserToMem(getContext(), info, tokenDate);
    }

    @Override
    public void loadHistoryFromLocal() {
        String historyCache = StorageMgr.getInstance().getSharedPStorage(getContext())
                .getString(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY);
        LoginHistoryList historyList = null;
        if (historyCache != null) {
            Gson gson = new Gson();
            historyList = gson.fromJson(historyCache, LoginHistoryList.class);
        }
        if (historyList != null) {
            StorageMgr.getInstance().getMemStorage()
                    .save(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY, historyList);
        }
    }

    @Override
    public String encryPassword(String password) {
        if (StringUtil.isNullOrEmpty(password)) {
            LogUtil.e(TAG, "Empty password is not allow, return null");
            return null;
        }
        String now = "" + new Date().getTime();
        String md5password = DigestUtil.encryptMD5(password);
        String md5withDate = DigestUtil.encryptMD5(now + md5password);

        return now + "." + md5withDate;

    }

    @Override
    public LoginHistory getLoginHistory(String loginid) {
        Object obj = StorageMgr.getInstance().getMemStorage()
                .getObject(BussinessConstants.Login.LOGIN_HISTORY_LIST_KEY);
        LoginHistoryList historyList;
        if (obj == null) {
            historyList = new LoginHistoryList();
        } else {
            historyList = (LoginHistoryList) (obj);
        }
        LoginHistory loginHistory = null;
        for (int i = 0; i < historyList.getHistories().size(); i++) {
            if (loginid.equals(historyList.getHistories().get(i).getLoginid())) {
                loginHistory = historyList.getHistories().get(i);
            }
        }
        return loginHistory;
    }

    private boolean isExpired() {
        Object obj = StorageMgr.getInstance().getMemStorage()
                .getObject(BussinessConstants.Login.USER_INFO_KEY);
        if (obj == null) {
            return true;
        }
        return false;
        //        UserInfo info = (UserInfo) obj;
        //        String tokenExpire = info.getTokenExpire();
        //        long tokenDate = StorageMgr.getInstance().getMemStorage().getLong(BussinessConstants.Login.TOKEN_DATE);
        //
        //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //
        //        long expire = 0;
        //        try {
        //            expire = sdf.parse(tokenExpire).getTime() - tokenDate;
        //        } catch (ParseException e) {
        //            LogUtil.e(tag, e);
        //        }
        //        //永不过期
        //        if (expire == -1) {
        //            return false;
        //        }
        //        if (tokenDate != Long.MIN_VALUE) {
        //            Date now = new Date();
        //            //在有效期内
        //            if (now.getTime() - tokenDate < expire) {
        //                return false;
        //            }
        //        }
        //        return true;
    }

    @Override
    public void getUserInfo(NVPWithTokenReqBody reqBody) {
        new LoginHttpManager(getContext()).getUserInfo(invoker, reqBody, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                UserInfo newUserInfo = (UserInfo) obj;
                UserInfo oldUserInfo = UserInfoCacheManager.getUserInfo(getContext());
                newUserInfo.setToken(oldUserInfo.getToken());
                UserInfoCacheManager
                        .saveUserToLoacl(getContext(), newUserInfo, StorageMgr.getInstance()
                                .getMemStorage().getLong(BussinessConstants.Login.TOKEN_DATE));
                UserInfoCacheManager
                        .saveUserToMem(getContext(), newUserInfo, StorageMgr.getInstance()
                                .getMemStorage().getLong(BussinessConstants.Login.TOKEN_DATE));
                LoginLogic.this
                        .sendEmptyMessage(BussinessConstants.LoginMsgID.GET_USER_INFO_SUCCESS_MSG_ID);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                if (isCommonFailRes(code, desc)) {
                    return;
                }
                FailResponse response = new FailResponse();
                response.setCode(code);
                response.setMsg(desc);
                LoginLogic.this.sendMessage(
                        BussinessConstants.LoginMsgID.GET_USER_INFO_FAIL_MSG_ID, response);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID,
                        errorCode);
            }
        });
    }

    @Override
    public void updatePassword(PasswordInfo pwdInfo) {
        new LoginHttpManager(getContext()).updatePassword(invoker, pwdInfo, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                LoginLogic.this
                        .sendEmptyMessage(BussinessConstants.LoginMsgID.UPDATE_PWD_SUCCESS_MSG_ID);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                if (isCommonFailRes(code, desc)) {
                    return;
                }
                FailResponse response = new FailResponse();
                response.setCode(code);
                response.setMsg(desc);
                LoginLogic.this.sendMessage(BussinessConstants.LoginMsgID.UPDATE_PWD_FAIL_MSG_ID,
                        response);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID,
                        errorCode);
            }
        });
    }

    /***/
    public void initCMIMSdk() {
        Object obj = StorageMgr.getInstance().getMemStorage()
                .getObject(BussinessConstants.Login.USER_INFO_KEY);
        if (obj == null) {
            return;
        }
        UserInfo userInfo = (UserInfo) obj;
        new CMIMHelperManager(getContext()).doLogin(userInfo);
    }

    private void clean() {
        UserInfoCacheManager.clearUserInfo(getContext());
        try {
            CMIMHelper.getCmAccountManager().doLogOut();
        } catch (Exception e) {
            LogUtil.d(TAG, "小溪推送退出异常。");
        }
    }

    @Override
    public void updatePhoto(UpdatePhotoReq updatePhoto) {
        new LoginHttpManager(getContext()).updatePhoto(invoker, updatePhoto, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                UserInfo userInfo = (UserInfo) obj;
                UserInfo oldUserInfo = (UserInfo) StorageMgr.getInstance().getMemStorage()
                        .getObject(BussinessConstants.Login.USER_INFO_KEY);
                oldUserInfo.setPhotoLg(userInfo.getPhotoLg());
                oldUserInfo.setPhotoSm(userInfo.getPhotoSm());
                UserInfoCacheManager.saveUserToLoacl(
                        getContext(),
                        oldUserInfo,
                        StorageMgr.getInstance().getSharedPStorage(getContext())
                                .getLong(BussinessConstants.Login.TOKEN_DATE));
                loadUserFromLocal();
                LoginLogic.this
                        .sendEmptyMessage(BussinessConstants.LoginMsgID.UPDATE_PHOTO_SUCCESS_MSG_ID);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                if (isCommonFailRes(code, desc)) {
                    return;
                }
                FailResponse response = new FailResponse();
                response.setCode(code);
                response.setMsg(desc);
                LoginLogic.this.sendMessage(BussinessConstants.LoginMsgID.UPDATE_PHOTO_FAIL_MSG_ID,
                        response);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID,
                        errorCode);
            }
        });
    }

    @Override
    public void feedBack(FeedBackReq feedBackReq) {
        new LoginHttpManager(getContext()).feedBack(invoker, feedBackReq, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                LoginLogic.this
                        .sendEmptyMessage(BussinessConstants.SettingMsgID.SEND_FEED_BACK_SUCCESS);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                LoginLogic.this
                        .sendEmptyMessage(BussinessConstants.SettingMsgID.SEND_FEED_BACK_SUCCESS);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                LoginLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID,
                        errorCode);
            }
        });
    }

    private boolean isCommonFailRes(String code, String desc) {
        if ("-4".equals(code) || "-5".equals(code)) {
            LoginLogic.this.sendEmptyMessage(BussinessConstants.CommonMsgId.SERVER_SIDE_ERROR);
            return true;
        }
        return false;
    }

    private Object invoker;
    public void setInvoker(Object invoker){
        this.invoker = invoker;
    }
}
