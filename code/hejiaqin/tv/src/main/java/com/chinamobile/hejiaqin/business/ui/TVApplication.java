package com.chinamobile.hejiaqin.business.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.Const;
import com.chinamobile.hejiaqin.business.HeApplication;
import com.chinamobile.hejiaqin.business.HeService;
import com.chinamobile.hejiaqin.business.logic.LogicBuilder;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.login.LoginLogic;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.logic.setting.SettingLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.logic.voip.VoipLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.FailResponse;
import com.chinamobile.hejiaqin.business.model.login.RespondInfo;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.TvLoginInfo;
import com.chinamobile.hejiaqin.business.utils.DirUtil;
import com.chinamobile.hejiaqin.tv.BuildConfig;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.logic.BuilderImp;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.StringUtil;

/**
 * Created by  on 2016/6/5.
 */
public class TVApplication extends HeApplication implements Thread.UncaughtExceptionHandler {
    private IVoipLogic voipLogic;
    private ILoginLogic loginLogic;
    private ISettingLogic settingLogic;
    private static final String TAG = TVApplication.class.getSimpleName();
    /**
     * 该activity持有的handler类
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BussinessConstants.SettingMsgID.TEST_ADAPT_FAIL:
                    Const.setLoginStatus(Const.LOGINSTATUS.LOGIN_FAILED);
                    RespondInfo info = (RespondInfo) msg.obj;
                    if (!StringUtil.isNullOrEmpty(info.getMsg())) {
                        LogUtil.e(TAG, info.getMsg());
                    } else {
                        LogUtil.e(TAG, "BussinessConstants.SettingMsgID.TEST_ADAPT_FAIL");
                    }
                    break;
                case BussinessConstants.SettingMsgID.TEST_ADAPT_PASS:
                    //检查是否开户
                    Const.setLoginStatus(Const.LOGINSTATUS.LOGINING);
                    TvLoginInfo tvLoginInfo = new TvLoginInfo();
                    tvLoginInfo.setTvId(UserInfoCacheManager.getTvUserID(getApplicationContext()));
                    tvLoginInfo.setTvToken(UserInfoCacheManager.getTvToken(getApplicationContext()));
                    loginLogic.checkTvAccount(tvLoginInfo);
                    break;
                case BussinessConstants.SettingMsgID.TEST_ADAPT_ERROR:
                    Const.setLoginStatus(Const.LOGINSTATUS.LOGIN_FAILED);
                    LogUtil.e(TAG, getString(R.string.exception_tips));
                    break;
                case BussinessConstants.LoginMsgID.TV_ACCOUNT_UNREGISTERED:
                    Const.setLoginStatus(Const.LOGINSTATUS.LOGIN_FAILED);
                    break;
                case BussinessConstants.LoginMsgID.TV_ACCOUNT_REGISTERED:
                    Const.setLoginStatus(Const.LOGINSTATUS.LOGINING);
                    if (UserInfoCacheManager.getTvIsLogout(getApplicationContext())
                            && !"unknown".equals(UserInfoCacheManager.getTvAccount(getApplicationContext()))) {
                    } else {
                        autoLogin();
                    }
                    break;
                case BussinessConstants.LoginMsgID.LOGIN_SUCCESS_MSG_ID:
                    UserInfo userInfo = UserInfoCacheManager.getUserInfo(getApplicationContext());
                    com.huawei.rcs.login.UserInfo sdkuserInfo = new com.huawei.rcs.login.UserInfo();
                    sdkuserInfo.countryCode = "";
                    sdkuserInfo.username = userInfo.getSdkAccount();
                    sdkuserInfo.password = userInfo.getSdkPassword();
                    LogUtil.i(TAG, "SDK username: " + sdkuserInfo.username);
                    voipLogic.login(sdkuserInfo, null, null);
                    break;
                case BussinessConstants.DialMsgID.VOIP_REGISTER_CONNECTED_MSG_ID:
                    Const.setLoginStatus(Const.LOGINSTATUS.LOGINED);
                    UserInfoCacheManager.clearTvIsLogout(getApplicationContext());
                    voipLogic.setNotNeedVoipLogin();
                    break;
                case BussinessConstants.LoginMsgID.LOGIN_FAIL_MSG_ID:
                    Const.setLoginStatus(Const.LOGINSTATUS.LOGIN_FAILED);
                    if (msg.obj != null) {
                        FailResponse response = (FailResponse) msg.obj;
                        if (!StringUtil.isNullOrEmpty(response.getMsg())) {
                            LogUtil.e(TAG, response.getMsg());
                        } else {
                            LogUtil.e(TAG, getString(R.string.voip_register_fail));
                        }
                    } else {
                        LogUtil.e(TAG, getString(R.string.voip_register_fail));
                    }

                    break;
                case BussinessConstants.CommonMsgId.LOGIN_NETWORK_ERROR_MSG_ID:
                    Const.setLoginStatus(Const.LOGINSTATUS.LOGIN_FAILED);
                    LogUtil.e(TAG, getString(R.string.network_error_tip));
                    break;
                case BussinessConstants.DialMsgID.VOIP_REGISTER_NET_UNAVAILABLE_MSG_ID:
                    Const.setLoginStatus(Const.LOGINSTATUS.LOGIN_FAILED);
//                    if (logining) {
//                        showToast(R.string.network_error_tip, Toast.LENGTH_SHORT, null);
//                        logining = false;
//                    }
                    break;
                case BussinessConstants.DialMsgID.VOIP_REGISTER_DISCONNECTED_MSG_ID:
                    Const.setLoginStatus(Const.LOGINSTATUS.LOGIN_FAILED);
//                    if (logining) {
//                        showToast(R.string.voip_register_fail, Toast.LENGTH_SHORT, null);
//                        ThreadPoolUtil.execute(new ThreadTask() {
//
//                            @Override
//                            public void run() {
//                                LogApi.copyLastLog();
//                            }
//                        });
//                        logining = false;
//                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void autoLogin() {
        TvLoginInfo loginInfo = new TvLoginInfo();
        loginInfo.setTvId(UserInfoCacheManager.getTvUserID(this));
        loginInfo.setTvToken(loginLogic.encryPassword(UserInfoCacheManager.getTvToken(this)));
        Const.setLoginStatus(Const.LOGINSTATUS.LOGINING);
        loginLogic.tvLogin(loginInfo);
    }

    @Override
    public void onCreate() {
        //根据build.gradle设置日志级别
//        Toast.makeText(this, "Application is up", Toast.LENGTH_SHORT).show();
        LogUtil.setContext(getApplicationContext());
        LogUtil.setLogLevel(BuildConfig.LOG_LEVEL);
        LogUtil.setLogCommonDir(DirUtil.getExternalFileDir(this) + "/log/common/");
        super.onCreate();
        startService(new Intent(this, HeService.class));
        //设置Thread Exception Handler
        //        if(BuildConfig.LOG_LEVEL>= LogUtil.WARN) {
        Thread.setDefaultUncaughtExceptionHandler(this);
        voipLogic = (IVoipLogic) VoipLogic.getInstance(getApplicationContext());
        loginLogic = (ILoginLogic) LoginLogic.getInstance(getApplicationContext());
        settingLogic = (ISettingLogic) SettingLogic.getInstance(getApplicationContext());

        BuilderImp.setInstance(LogicBuilder.getInstance(getApplicationContext()));
        LogicBuilder.getInstance(getApplicationContext()).addHandlerToAllLogics(mHandler);
        check();
        Const.setLoginStatus(Const.LOGINSTATUS.LOGOUTED);
    }

    private void check() {
        getSTBConfig();
        settingLogic.testAdapt();
    }


    private boolean getSTBConfig() {
        boolean flag = true;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse(BussinessConstants.Login.BASE_URI), null,
                null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            if (StringUtil.isNullOrEmpty(cursor.getString(cursor.getColumnIndex("UserId")))
                    || StringUtil
                    .isNullOrEmpty(cursor.getString(cursor.getColumnIndex("UserToken")))) {
                LogUtil.e(TAG, getString(R.string.exception_tips));
                if (!cursor.isClosed()) {
                    cursor.close();
                }
//                temp code
//                UserInfoCacheManager.saveSTBConfig(this,
//                        "jiulian9",
//                        "5NVtgJK9u90YUt_eRbAnPV1438916062",
//                        "JLV201646P5501");
                //
                return false;
            }
            UserInfoCacheManager.saveSTBConfig(this,
                    cursor.getString(cursor.getColumnIndex("UserId")),
                    cursor.getString(cursor.getColumnIndex("UserToken")),
                    cursor.getString(cursor.getColumnIndex("SoftwareVersion")));
        } else {
            LogUtil.e(TAG, getString(R.string.exception_tips));
            //temp code
//            UserInfoCacheManager.saveSTBConfig(this,
//                    "jiulian9",
//                    "5NVtgJK9u90YUt_eRbAnPV1438916062",
//                    "JLV201646P5501");
            //
            flag = false;
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return flag;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LogUtil.e("GobalException", ex);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


}
