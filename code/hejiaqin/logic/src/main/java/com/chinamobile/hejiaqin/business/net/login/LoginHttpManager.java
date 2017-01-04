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

    private final int action_base = 0;
    /**
     * 获取验证码网络请求
     */
    private final int get_verify_code_action = action_base + 1;

    /**
     * 获取验证码网络请求
     */
    private final int check_verify_code_action = action_base + 2;


    /**
     * 注册网络请求第二步
     */
    private final int register_secondStep_action = action_base + 4;

    /**
     * 登录网络请求
     */
    private final int login_action = action_base + 5;

    /**
     * 登出网络请求
     */
    private final int logout_action = action_base + 6;

    /**
     * 修改密码请求
     */
    private final int update_pwd_action = action_base + 7;


    /**
     * 修改用户头像
     */
    private final int update_photo = action_base + 11;

    /**
     * 用户反馈
     */
    private final int feed_back = action_base + 12;

    /**
     * 忘记密码-获取短信验证码
     */
    private final int forget_password_code_action = action_base + 9;

    /**
     * 忘记密码-验证短信验证码
     */
    private final int check_forget_password_code_action = action_base + 10;

    private final int get_user_info_action = action_base + 13;

    private final int tv_login_action = action_base + 14;

    private final int check_tv_account = action_base + 15;

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
            case get_verify_code_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/registCode";
                break;
            case check_verify_code_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/checkRegistCode";
                break;
            case register_secondStep_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/regist";
                break;
            case login_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/login";
                break;
            case logout_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/logout";
                break;
            case forget_password_code_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/forgetPasswordCode";
                break;
            case check_forget_password_code_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/checkForgetPasswordCode";
                break;
            case update_pwd_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/resetPassword";
                break;
            case update_photo:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/updatePhoto";
                break;
            case feed_back:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/feedback";
                break;
            case get_user_info_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/info";
                break;
            case tv_login_action:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/tvLogin";
                break;
            case check_tv_account:
                url = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/user/checkTvAccount";
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
            case get_verify_code_action:
            case check_verify_code_action:
            case register_secondStep_action:
            case logout_action:
            case login_action:
            case forget_password_code_action:
            case check_forget_password_code_action:
            case update_pwd_action:
            case update_photo:
            case feed_back:
            case get_user_info_action:
            case tv_login_action:
            case check_tv_account:
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
            case get_verify_code_action:
            case check_verify_code_action:
            case register_secondStep_action:
            case login_action:
            case forget_password_code_action:
            case check_forget_password_code_action:
            case update_pwd_action:
            case tv_login_action:
            case check_tv_account:
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
            case update_photo:
                return NetRequest.ContentType.FORM_DATA;
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
                    case get_verify_code_action:
                        break;
                    case check_verify_code_action:
                        break;
                    case forget_password_code_action:
                        break;
                    case check_forget_password_code_action:
                        obj = gson.fromJson(data, PasswordInfo.class);
                        break;
                    case register_secondStep_action:
                        break;
                    case tv_login_action:
                    case login_action:
                        obj = gson.fromJson(data, UserInfo.class);
                        break;
                    case check_tv_account:
                        obj = data;
                        break;
                    case logout_action:
                        break;
                    case update_pwd_action:
                        break;
                    case feed_back:
                        break;
                    case update_photo:
                        obj = gson.fromJson(data, UserInfo.class);
                        break;
                    case get_user_info_action:
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
    public void getVerifyCode(final Object invoker, final NVPReqBody phoneReqBody, final IHttpCallBack callBack) {
        this.mAction = get_verify_code_action;
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
    public void getResetPasswordCode(final Object invoker, final NVPReqBody phoneReqBody, final IHttpCallBack callBack) {
        this.mAction = forget_password_code_action;
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
    public void checkVerifyCode(final Object invoker, final VerifyInfo verifyInfo, final IHttpCallBack callBack) {
        this.mAction = check_verify_code_action;
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
    public void checkResetPasswordCode(final Object invoker, final VerifyInfo verifyInfo, final IHttpCallBack callBack) {
        this.mAction = check_forget_password_code_action;
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
    public void registerSecondStep(final Object invoker, final RegisterSecondStepInfo registerInfo, final IHttpCallBack callBack) {
        this.mAction = register_secondStep_action;
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
        this.mAction = login_action;
        this.mData = loginInfo;
        send(invoker, callBack);
    }

    public void tvLogin(final Object invoker, final TvLoginInfo loginInfo, final IHttpCallBack callBack) {
        this.mAction = tv_login_action;
        this.mData = loginInfo;
        send(invoker, callBack);
    }

    public void checkTvAccount(final Object invoker, final TvLoginInfo loginInfo, final IHttpCallBack callBack) {
        this.mAction = check_tv_account;
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
        this.mAction = logout_action;
        this.mData = new NVPWithTokenReqBody();
        send(invoker, callBack);
    }

    /**
     * 修改密码
     *
     * @param invoker  调用者(用来区分不同的调用场景，差异化实现回调逻辑)
     * @param callBack 回调监听
     */
    public void updatePassword(final Object invoker, final PasswordInfo info, final IHttpCallBack callBack) {
        this.mAction = update_pwd_action;
        this.mData = info;
        send(invoker, callBack);
    }

    public void updatePhoto(final Object invoker, final UpdatePhotoReq updatePhoto, final IHttpCallBack callBack) {
        this.mAction = update_photo;
        this.mData = updatePhoto;
        send(invoker, callBack);
    }

    public void feedBack(final Object invoker, final FeedBackReq feedBackReq, final IHttpCallBack callBack) {
        this.mAction = feed_back;
        this.mData = feedBackReq;
        send(invoker, callBack);
    }

    public void getUserInfo(final Object invoker, final NVPWithTokenReqBody reqBody, final IHttpCallBack callBack) {
        this.mAction = get_user_info_action;
        this.mData = reqBody;
        send(invoker, callBack);
    }
}
