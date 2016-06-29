package com.chinamobile.hejiaqin.business.logic.login;

import com.chinamobile.hejiaqin.business.model.login.LoginHistory;
import com.chinamobile.hejiaqin.business.model.login.req.FeedBackReq;
import com.chinamobile.hejiaqin.business.model.login.req.LoginInfo;
import com.chinamobile.hejiaqin.business.model.login.req.PasswordInfo;
import com.chinamobile.hejiaqin.business.model.login.req.RegisterSecondStepInfo;
import com.chinamobile.hejiaqin.business.model.login.req.UpdatePhotoReq;
import com.chinamobile.hejiaqin.business.model.login.req.VerifyInfo;

/**
 * 登录逻辑接口
 * Kangxi Version 001
 * author: zhanggj
 * Created: 2016/4/8.
 */
public interface ILoginLogic {
    /**
     * 获取验证码
     *
     * @param userLoginId 用户id 手机号码
     */
    void getVerifyCode(String userLoginId);

    /**
     * 获取验证码---重置密码
     *
     * @param phone 手机号码
     */
    public void getResetPasswordCode(String phone);

    /**
     * 检查验证码
     *
     * @param verifyInfo 验证信息
     */
    void checkVerifyCode(VerifyInfo verifyInfo);

    /**
     * 检查验证码---重置密码
     *
     * @param verifyInfo 验证信息
     */
    void checkResetPasswordCode(VerifyInfo verifyInfo);

    /**
     * 注册第二步
     *
     * @param info 注册信息
     */
    void registerSecondStep(RegisterSecondStepInfo info);

    /**
     * 登录
     *
     * @param info 登录信息
     */
    void login(LoginInfo info);

    /**
     * 用户注销
     */
    void logout();

    /**
     * 已经登录过
     *
     * @return 是否已经登录
     */
    boolean hasLogined();

    /**
     * 修改密码
     */
    void updatePassword(PasswordInfo pwdInfo);

    void loadUserFromLocal();

    void loadHistoryFromLocal();

    void initCMIMSdk();

    void updatePhoto(UpdatePhotoReq updatePhoto);

    void feedBack(FeedBackReq feedBackReq);

    String encryPassword(String password);

    LoginHistory getLoginHistory(String loginid);

}
