package com.chinamobile.hejiaqin.business.net.login;

import android.content.Context;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.FeedBackReq;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.business.model.login.req.PasswordInfo;
import com.chinamobile.hejiaqin.business.model.login.req.RegisterSecondStepInfo;
import com.chinamobile.hejiaqin.business.model.login.req.TvLoginInfo;
import com.chinamobile.hejiaqin.business.model.login.req.UpdatePhotoReq;
import com.chinamobile.hejiaqin.business.model.login.req.VerifyInfo;
import com.chinamobile.hejiaqin.business.net.AbsHttpManager;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.NVPWithTokenReqBody;
import com.customer.framework.component.net.NetRequest;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.utils.LogUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/14.
 */
public class LoginHttpManager extends AbsHttpManager {

    /**
     * 打印标志
     */
    private static final String TAG = "LoginHttpManager";

    private static final int ACTION_BASE = 0;
    /**
     * 获取验证码网络请求
     */
    private static final int GET_VERIFY_CODE_ACTION = ACTION_BASE + 1;

    /**
     * 获取验证码网络请求
     */
    private static final int CHECK_VERIFY_CODE_ACTION = ACTION_BASE + 2;

    /**
     * 注册网络请求第二步
     */
    private static final int REGISTER_SECOND_STEP_ACTION = ACTION_BASE + 4;

    /**
     * 登录网络请求
     */
    private static final int LOGIN_ACTION = ACTION_BASE + 5;

    /**
     * 登出网络请求
     */
    private static final int LOGOUT_ACTION = ACTION_BASE + 6;

    /**
     * 修改密码请求
     */
    private static final int UPDATE_PWD_ACTION = ACTION_BASE + 7;

    /**
     * 修改用户头像
     */
    private static final int UPDATE_PHOTO = ACTION_BASE + 11;

    /**
     * 用户反馈
     */
    private static final int FEED_BACK = ACTION_BASE + 12;

    /**
     * 忘记密码-获取短信验证码
     */
    private static final int FORGET_PASSWORD_CODE_ACTION = ACTION_BASE + 9;

    /**
     * 忘记密码-验证短信验证码
     */
    private static final int CHECK_FORGET_PASSWORD_CODE_ACTION = ACTION_BASE + 10;

    private static final int GET_USER_INFO_ACTION = ACTION_BASE + 13;

    private static final int TV_LOGIN_ACTION = ACTION_BASE + 14;

    private static final int CHECK_TV_ACCOUNT = ACTION_BASE + 15;

    /**
     * 请求action
     */
    private int mAction;

    private Context mContext;

    public LoginHttpManager(Context context) {
        this.mContext = context;
    }

    @Override
    protected Context getContext() {
        return this.mContext;
    }

    @Override
    protected String getUrl() {
        String url = null;
        switch (this.mAction) {
            case GET_VERIFY_CODE_ACTION:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/registCode";
                break;
            case CHECK_VERIFY_CODE_ACTION:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/checkRegistCode";
                break;
            case REGISTER_SECOND_STEP_ACTION:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/regist";
                break;
            case LOGIN_ACTION:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/login";
                break;
            case LOGOUT_ACTION:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/logout";
                break;
            case FORGET_PASSWORD_CODE_ACTION:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/forgetPasswordCode";
                break;
            case CHECK_FORGET_PASSWORD_CODE_ACTION:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/checkForgetPasswordCode";
                break;
            case UPDATE_PWD_ACTION:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/resetPassword";
                break;
            case UPDATE_PHOTO:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/updatePhoto";
                break;
            case FEED_BACK:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/feedback";
                break;
            case GET_USER_INFO_ACTION:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/info";
                break;
            case TV_LOGIN_ACTION:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/tvLogin";
                break;
            case CHECK_TV_ACCOUNT:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/checkTvAccount";
                break;
            default:
                break;
        }
        return url;
    }

    /**
     * 请求method类型<BR>
     *
     * @return 默认为GET请求
     */
    protected NetRequest.RequestMethod getRequestMethod() {
        NetRequest.RequestMethod method = NetRequest.RequestMethod.GET;
        switch (this.mAction) {
            case GET_VERIFY_CODE_ACTION:
            case CHECK_VERIFY_CODE_ACTION:
            case REGISTER_SECOND_STEP_ACTION:
            case LOGOUT_ACTION:
            case LOGIN_ACTION:
            case FORGET_PASSWORD_CODE_ACTION:
            case CHECK_FORGET_PASSWORD_CODE_ACTION:
            case UPDATE_PWD_ACTION:
            case UPDATE_PHOTO:
            case FEED_BACK:
            case GET_USER_INFO_ACTION:
            case TV_LOGIN_ACTION:
            case CHECK_TV_ACCOUNT:
                method = NetRequest.RequestMethod.POST;
                break;
            default:
                break;
        }
        return method;
    }

    @Override
    protected boolean isNeedToken() {
        boolean flag = true;
        switch (this.mAction) {
            case GET_VERIFY_CODE_ACTION:
            case CHECK_VERIFY_CODE_ACTION:
            case REGISTER_SECOND_STEP_ACTION:
            case LOGIN_ACTION:
            case FORGET_PASSWORD_CODE_ACTION:
            case CHECK_FORGET_PASSWORD_CODE_ACTION:
            case UPDATE_PWD_ACTION:
            case TV_LOGIN_ACTION:
            case CHECK_TV_ACCOUNT:
                flag = false;
                break;
            default:
                break;
        }
        return flag;
    }

    @Override
    protected NetRequest.ContentType getContentType() {
        switch (this.mAction) {
            case UPDATE_PHOTO:
                return NetRequest.ContentType.FORM_DATA;
            default:
                break;
        }
        return NetRequest.ContentType.FORM_URLENCODED;
    }

    @Override
    protected Object handleResponse(NetResponse response) {
        Object obj = null;
        if (BussinessConstants.HttpCommonCode.COMMON_SUCCESS_CODE.equals(response.getResultCode())) {
            try {
                String data = "";
                JSONObject rootJsonObj = new JSONObject(response.getData());
                if (rootJsonObj.has("data")) {
                    data = rootJsonObj.get("data").toString();
                }
                Gson gson = new Gson();
                switch (this.mAction) {
                    case GET_VERIFY_CODE_ACTION:
                        break;
                    case CHECK_VERIFY_CODE_ACTION:
                        break;
                    case FORGET_PASSWORD_CODE_ACTION:
                        break;
                    case CHECK_FORGET_PASSWORD_CODE_ACTION:
                        obj = gson.fromJson(data, PasswordInfo.class);
                        break;
                    case REGISTER_SECOND_STEP_ACTION:
                        break;
                    case TV_LOGIN_ACTION:
                    case LOGIN_ACTION:
                        obj = gson.fromJson(data, UserInfo.class);
                        break;
                    case CHECK_TV_ACCOUNT:
                        obj = data;
                        break;
                    case LOGOUT_ACTION:
                        break;
                    case UPDATE_PWD_ACTION:
                        break;
                    case FEED_BACK:
                        break;
                    case UPDATE_PHOTO:
                        obj = gson.fromJson(data, UserInfo.class);
                        break;
                    case GET_USER_INFO_ACTION:
                        obj = gson.fromJson(data, UserInfo.class);
                        break;
                    default:
                        break;
                }

            } catch (JSONException e) {
                LogUtil.e(TAG, e.toString());
            }
        }
        return obj;
    }

    /**
     * 获取验证码
     *
     * @param invoker      调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param phoneReqBody 用户id 手机号码
     * @param callBack     回调监听
     */
    public void getVerifyCode(final Object invoker, final NVPReqBody phoneReqBody,
                              final IHttpCallBack callBack) {
        this.mAction = GET_VERIFY_CODE_ACTION;
        this.mData = phoneReqBody;
        send(invoker, callBack);
    }

    /**
     * 获取验证码--reset
     *
     * @param invoker      调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param phoneReqBody 用户id 手机号码
     * @param callBack     回调监听
     */
    public void getResetPasswordCode(final Object invoker, final NVPReqBody phoneReqBody,
                                     final IHttpCallBack callBack) {
        this.mAction = FORGET_PASSWORD_CODE_ACTION;
        this.mData = phoneReqBody;
        send(invoker, callBack);
    }

    /**
     * 检查验证码
     *
     * @param invoker    调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param verifyInfo 用户id 手机号码
     * @param callBack   回调监听
     */
    public void checkVerifyCode(final Object invoker, final VerifyInfo verifyInfo,
                                final IHttpCallBack callBack) {
        this.mAction = CHECK_VERIFY_CODE_ACTION;
        this.mData = verifyInfo;
        send(invoker, callBack);
    }

    /**
     * 检查验证码--reset
     *
     * @param invoker    调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param verifyInfo 用户id 手机号码
     * @param callBack   回调监听
     */
    public void checkResetPasswordCode(final Object invoker, final VerifyInfo verifyInfo,
                                       final IHttpCallBack callBack) {
        this.mAction = CHECK_FORGET_PASSWORD_CODE_ACTION;
        this.mData = verifyInfo;
        send(invoker, callBack);
    }

    /**
     * 注册方法
     *
     * @param invoker      调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param registerInfo 注册信息
     * @param callBack     回调监听
     */
    public void registerSecondStep(final Object invoker, final RegisterSecondStepInfo registerInfo,
                                   final IHttpCallBack callBack) {
        this.mAction = REGISTER_SECOND_STEP_ACTION;
        this.mData = registerInfo;
        send(invoker, callBack);
    }

    /**
     * 登录方法
     *
     * @param invoker   调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param loginInfo 登录信息
     * @param callBack  回调监听
     */
    public void login(final Object invoker, final LoginInfo loginInfo, final IHttpCallBack callBack) {
        this.mAction = LOGIN_ACTION;
        this.mData = loginInfo;
        send(invoker, callBack);
    }

    /***/
    public void tvLogin(final Object invoker, final TvLoginInfo loginInfo,
                        final IHttpCallBack callBack) {
        this.mAction = TV_LOGIN_ACTION;
        this.mData = loginInfo;
        send(invoker, callBack);
    }

    /***/
    public void checkTvAccount(final Object invoker, final TvLoginInfo loginInfo,
                               final IHttpCallBack callBack) {
        this.mAction = CHECK_TV_ACCOUNT;
        this.mData = loginInfo;
        send(invoker, callBack);
    }

    /**
     * 登录方法
     *
     * @param invoker  调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param callBack 回调监听
     */
    public void logout(final Object invoker, final IHttpCallBack callBack) {
        this.mAction = LOGOUT_ACTION;
        this.mData = new NVPWithTokenReqBody();
        send(invoker, callBack);
    }

    /**
     * 修改密码
     *
     * @param invoker  调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param callBack 回调监听
     */
    public void updatePassword(final Object invoker, final PasswordInfo info,
                               final IHttpCallBack callBack) {
        this.mAction = UPDATE_PWD_ACTION;
        this.mData = info;
        send(invoker, callBack);
    }

    /***/
    public void updatePhoto(final Object invoker, final UpdatePhotoReq updatePhoto,
                            final IHttpCallBack callBack) {
        this.mAction = this.UPDATE_PHOTO;
        this.mData = updatePhoto;
        send(invoker, callBack);
    }

    /***/
    public void feedBack(final Object invoker, final FeedBackReq feedBackReq,
                         final IHttpCallBack callBack) {
        this.mAction = FEED_BACK;
        this.mData = feedBackReq;
        send(invoker, callBack);
    }

    public void getUserInfo(final Object invoker, final NVPWithTokenReqBody reqBody,
                            final IHttpCallBack callBack) {
        this.mAction = GET_USER_INFO_ACTION;
        this.mData = reqBody;
        send(invoker, callBack);
    }
}
