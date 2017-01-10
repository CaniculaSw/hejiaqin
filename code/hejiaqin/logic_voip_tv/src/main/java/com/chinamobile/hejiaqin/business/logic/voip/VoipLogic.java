package com.chinamobile.hejiaqin.business.logic.voip;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.dbApdater.CallRecordDbAdapter;
import com.chinamobile.hejiaqin.business.manager.ContactsInfoManager;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.SearchResultContacts;
import com.chinamobile.hejiaqin.business.model.dial.CallRecord;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.customer.framework.component.ThreadPool.ThreadPoolUtil;
import com.customer.framework.component.ThreadPool.ThreadTask;
import com.customer.framework.component.db.DatabaseInfo;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.component.time.DateTimeUtil;
import com.customer.framework.logic.LogicImp;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.StringUtil;
import com.google.gson.Gson;
import com.huawei.rcs.call.CallApi;
import com.huawei.rcs.call.CallSession;
import com.huawei.rcs.log.LogApi;
import com.huawei.rcs.login.LoginApi;
import com.huawei.rcs.login.LoginCfg;
import com.huawei.rcs.login.UserInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by  on 2016/6/5.
 */
public class VoipLogic extends LogicImp implements IVoipLogic {

    public static final String TAG = VoipLogic.class.getSimpleName();

    private static final String DEFAULT_IP = "223.87.12.235";

    private static final String DEFAULT_PORT = "443";

    private static VoipLogic instance;

    private Map<String,String> recordMap = new HashMap<String,String>();

    private CallRecordDbAdapter mCallRecordDbAdapter;

    private boolean isTv;

    private BroadcastReceiver mLoginStatusChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int old_status = intent.getIntExtra(LoginApi.PARAM_OLD_STATUS, -1);
            int new_status = intent.getIntExtra(LoginApi.PARAM_NEW_STATUS, -1);
            int reason = intent.getIntExtra(LoginApi.PARAM_REASON, -1);
            switch (new_status) {
                case LoginApi.STATUS_CONNECTED:
                    LogUtil.d(VoipLogic.TAG, "the status is STATUS_CONNECTED");
                    UserInfoCacheManager.saveVoipLogined(getContext());
                    VoipLogic.this.sendEmptyMessage(BussinessConstants.DialMsgID.VOIP_REGISTER_CONNECTED_MSG_ID);
                    break;
                case LoginApi.STATUS_CONNECTING:
                    LogUtil.d(VoipLogic.TAG, "the status is STATUS_CONNECTING");
                    VoipLogic.this.sendEmptyMessage(BussinessConstants.DialMsgID.VOIP_REGISTER_CONNECTING_MSG_ID);
                    break;
                case LoginApi.STATUS_DISCONNECTED:
                    LogUtil.d(VoipLogic.TAG, "the status is STATUS_DISCONNECTED");
                    if (reason == LoginApi.REASON_SRV_FORCE_LOGOUT) {
                        //服务器强制注销 如：同一账号在多终端上登录
                        VoipLogic.this.sendEmptyMessage(BussinessConstants.DialMsgID.VOIP_REGISTER_KICK_OUT_MSG_ID);
                    } else if (reason == LoginApi.REASON_NET_UNAVAILABLE) {
                        //网络不可用
                        VoipLogic.this.sendEmptyMessage(BussinessConstants.DialMsgID.VOIP_REGISTER_NET_UNAVAILABLE_MSG_ID);
                    }else{
                        VoipLogic.this.sendEmptyMessage(BussinessConstants.DialMsgID.VOIP_REGISTER_DISCONNECTED_MSG_ID);
                    }

                    break;
                case LoginApi.STATUS_DISCONNECTING:
                    LogUtil.d(VoipLogic.TAG, "the status is STATUS_DISCONNECTING");
                    break;
                case LoginApi.STATUS_IDLE:
                    LogUtil.d(VoipLogic.TAG, "the status is STATUS_IDLE");
                    VoipLogic.this.sendEmptyMessage(BussinessConstants.DialMsgID.VOIP_REGISTER_DISCONNECTED_MSG_ID);
                    break;
            }
        }
    };

    private BroadcastReceiver mCallInvitationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CallSession callSession = (CallSession) intent.getSerializableExtra(CallApi.PARAM_CALL_SESSION);
            LogUtil.d(TAG, "INCOMING");
            if (callSession.getType() == CallSession.TYPE_VIDEO_SHARE) {
                LogUtil.w(TAG,"VIDEO_SHARE");
                callSession.terminate();
                return;
            }
            if (callSession.getType() != CallSession.TYPE_VIDEO) {
                LogUtil.w(TAG,"AUDIO_INCOMING");
                callSession.terminate();
                return;
            }
            if (callSession.getType() == CallSession.TYPE_VIDEO) {
                // 保存通话记录
                if(!callSession.isNurse()) {
                    CallRecord callRecord = new CallRecord();
                    String recordId = UUID.randomUUID().toString();
                    callRecord.setRecordId(recordId);
                    callRecord.setPeerNumber(CommonUtils.getPhoneNumber(callSession.getPeer().getNumber()));
                    callRecord.setNoCountryNumber(CommonUtils.getPhoneNumber(callSession.getPeer().getNumber()));
                    callRecord.setBeginTime(DateTimeUtil.getDateString(new Date()));
                    callRecord.setDuration(callSession.getDuration());
                    callRecord.setType(CallRecord.TYPE_VIDEO_INCOMING);
                    callRecord.setRead(BussinessConstants.DictInfo.YES);
                    CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).insert(callRecord);
                    recordMap.put(String.valueOf(callSession.getSessionId()), recordId);
                }
                if (isTv()) {
                    if(callSession.isNurse())
                    {
                        LogUtil.d(TAG, "NURSE_ON_TV_INCOMING_MSG_ID");
                        //远程看护是绑定APP启动UI
                        if(UserInfoCacheManager.isBindedApp(getContext(),callSession.getPeer().getNumber()) ||
                                UserInfoCacheManager.isBindedApp(getContext(),CommonUtils.getPhoneNumber(callSession.getPeer().getNumber())))
                        {
                            //远程看护不是绑定APP直接挂断
                            LogUtil.i(TAG,"NURSE_ON_TV_INCOMING_MSG_ID BIND APP ");
                            VoipLogic.this.sendMessage(BussinessConstants.DialMsgID.NURSE_ON_TV_INCOMING_MSG_ID, callSession.getSessionId());
                        }else{
                            //远程看护不是绑定APP直接挂断
                            LogUtil.w(TAG,"NURSE_ON_TV_INCOMING_MSG_ID NO BIND APP ");
                            callSession.terminate();
                        }
                    }else {
                        LogUtil.d(TAG, "CALL_ON_TV_INCOMING_MSG_ID");
                        VoipLogic.this.sendMessage(BussinessConstants.DialMsgID.CALL_ON_TV_INCOMING_MSG_ID, callSession.getSessionId());
                    }
                } else {
                    LogUtil.d(TAG,"INTENT_INCOMING_SESSION_ID");
                    Intent inComingIntent = new Intent();
                    inComingIntent.setAction(BussinessConstants.Dial.CALL_ACTION);
                    inComingIntent.putExtra(BussinessConstants.Dial.INTENT_CALL_INCOMING, true);
                    inComingIntent.putExtra(BussinessConstants.Dial.INTENT_INCOMING_SESSION_ID, callSession.getSessionId());
                    inComingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(inComingIntent);
                }
            }
        }
    };

    private BroadcastReceiver mCallStatusChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CallSession callSession = (CallSession) intent.getSerializableExtra(CallApi.PARAM_CALL_SESSION);
            int newStatus = intent.getIntExtra(CallApi.PARAM_NEW_STATUS, CallSession.STATUS_IDLE);
            switch (newStatus) {
                case CallSession.STATUS_CONNECTED:
                    LogUtil.d(TAG, "STATUS_CONNECTED");
                    if(callSession.isNurse())
                    {
                        LogUtil.d(TAG, "nurse");
                        VoipLogic.this.sendMessage(BussinessConstants.DialMsgID.NURSE_CALL_ON_TALKING_MSG_ID, callSession);
                    }else {
                        VoipLogic.this.sendMessage(BussinessConstants.DialMsgID.CALL_ON_TALKING_MSG_ID, callSession);
                    }
                    break;
                case CallSession.STATUS_IDLE:
                    LogUtil.d(TAG, "SIP_CAUSE:" + callSession.getSipCause());
                    ThreadPoolUtil.execute(new ThreadTask() {

                        @Override
                        public void run() {
                            LogApi.copyLastLog();
                        }
                    });
                    if (callSession.isNurse()) {
                        VoipLogic.this.sendMessage(BussinessConstants.DialMsgID.NURSE_CALL_CLOSED_MSG_ID, callSession);
                    } else{
                        VoipLogic.this.sendMessage(BussinessConstants.DialMsgID.CALL_CLOSED_MSG_ID, callSession);
                    }
                    break;
                default:
                    break;
            }
            //TODO 修改通话记录
        }
    };


    private VoipLogic(Context context) {
        init(context.getApplicationContext());
    }

    /**
     * 获取单例对象
     *
     * @param context 系统的context对象
     * @return LogicBuilder对象
     */
    public static VoipLogic getInstance(Context context) {
        if (instance == null) {
            synchronized (VoipLogic.class) {
                if (instance == null) {
                    instance = new VoipLogic(context);
                }
            }
        }
        return instance;
    }

    public void registerVoipReceiver() {
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mLoginStatusChangedReceiver, new IntentFilter(LoginApi.EVENT_LOGIN_STATUS_CHANGED));
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mCallInvitationReceiver, new IntentFilter(CallApi.EVENT_CALL_INVITATION));
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mCallStatusChangedReceiver, new IntentFilter(CallApi.EVENT_CALL_STATUS_CHANGED));
    }

    public void unRegisterVoipReceiver() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mLoginStatusChangedReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mCallInvitationReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mCallStatusChangedReceiver);
    }

    @Override
    public void autoLogin() {
        LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_IP, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, DEFAULT_IP);
        LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_PORT, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, DEFAULT_PORT);
        com.chinamobile.hejiaqin.business.model.login.UserInfo userInfo = CommonUtils.getLocalUserInfo();
        com.huawei.rcs.login.UserInfo sdkuserInfo = new com.huawei.rcs.login.UserInfo();
        sdkuserInfo.countryCode="+86";
        sdkuserInfo.username = userInfo.getSdkAccount();
        sdkuserInfo.password = userInfo.getSdkPassword();
        LoginCfg loginCfg = new LoginCfg();
        //断网SDK自动重连设置
        loginCfg.isAutoLogin = true;
        loginCfg.isRememberPassword = true;
        loginCfg.isVerified = false;
        LoginApi.login(sdkuserInfo, loginCfg);
    }

    @Override
    public void login(UserInfo userInfo, String ip, String port) {
        if (!StringUtil.isNullOrEmpty(ip) && !StringUtil.isNullOrEmpty(port)) {
            LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_IP, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, ip);
            LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_PORT, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, port);
        } else {
            LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_IP, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, DEFAULT_IP);
            LoginApi.setConfig(LoginApi.CONFIG_MAJOR_TYPE_DM_PORT, LoginApi.CONFIG_MINOR_TYPE_DEFAULT, DEFAULT_PORT);
        }
        LoginCfg loginCfg = new LoginCfg();
        //断网SDK自动重连设置
        loginCfg.isAutoLogin = true;
        loginCfg.isRememberPassword = true;
        loginCfg.isVerified = false;
        LoginApi.login(userInfo, loginCfg);
        //TODO TEST:服务器没有保存SDK账号和密码
        com.chinamobile.hejiaqin.business.model.login.UserInfo clientUserInfo = UserInfoCacheManager.getUserInfo(getContext());
        clientUserInfo.setSdkAccount(userInfo.username);
        clientUserInfo.setSdkPassword(userInfo.password);
        HashMap map = new HashMap();
        Gson gson = new Gson();
        map.put(BussinessConstants.Login.USER_INFO_KEY, gson.toJson(clientUserInfo));
        StorageMgr.getInstance().getSharedPStorage(getContext()).save(map);
        StorageMgr.getInstance().getMemStorage().save(BussinessConstants.Login.USER_INFO_KEY, clientUserInfo);
        //TODO TEST:服务器没有保存SDK账号和密码
    }

    @Override
    public void logout() {
        LoginApi.logout();
    }

    @Override
    public void clearLogined()
    {
        UserInfoCacheManager.clearVoipLogined(getContext());
    }

    /**
     * 已经登录过
     *
     * @return 是否已经登录
     */
    @Override
    public boolean hasLogined() {
        return UserInfoCacheManager.getVoipLogined(getContext());
    }

    @Override
    public CallSession call(String calleeNumber, boolean isVideoCall,boolean isPhoneAPP) {
        CallSession callSession = null;
        String tmpNumber = calleeNumber;
        if(isPhoneAPP)
        {
            if(StringUtil.isMobileNO(CommonUtils.getPhoneNumber(tmpNumber))) {
                tmpNumber = "995" + CommonUtils.getPhoneNumber(tmpNumber);
            }
        }
        if (isVideoCall) {
            callSession = CallApi.initiateVideoCall(CommonUtils.getCountryPhoneNumber(tmpNumber));
        } else {
            callSession = CallApi.initiateAudioCall(CommonUtils.getCountryPhoneNumber(tmpNumber));
        }

        // 保存通话记录
        CallRecord callRecord = new CallRecord();
        String recordId = UUID.randomUUID().toString();
        callRecord.setRecordId(recordId);
        callRecord.setPeerNumber(calleeNumber);
        callRecord.setNoCountryNumber(CommonUtils.getPhoneNumber(calleeNumber));
        callRecord.setBeginTime(DateTimeUtil.getDateString(new Date()));
        callRecord.setDuration(callSession.getDuration());
        callRecord.setType(CallRecord.TYPE_VIDEO_OUTGOING);
        callRecord.setRead(BussinessConstants.DictInfo.YES);
        CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).insert(callRecord);

        if (callSession.getErrCode() == CallSession.ERRCODE_OK) {
            recordMap.put(String.valueOf(callSession.getSessionId()), recordId);
        }else{
            ThreadPoolUtil.execute(new ThreadTask() {

                @Override
                public void run() {
                    LogApi.copyLastLog();
                }
            });
            LogUtil.d(TAG, "call errorcode:" + callSession.getErrCode());
            LogUtil.d(TAG, "call sip causes:" + callSession.getSipCause());
            this.sendEmptyMessage(BussinessConstants.DialMsgID.CALL_RECORD_REFRESH_MSG_ID);
        }
        return callSession;
    }

    @Override
    public void hangup(CallSession callSession, boolean isInComing, boolean isTalking,int callTime) {
        String recordId = "";
        if(recordMap.containsKey(String.valueOf(callSession.getSessionId())))
        {
            recordId = recordMap.get(String.valueOf(callSession.getSessionId()));
            recordMap.remove(String.valueOf(callSession.getSessionId()));
        }
        callSession.terminate();
        if(StringUtil.isNullOrEmpty(recordId))
        {
            return;
        }
        if(isTalking)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseInfo.CallRecord.DURATION, callTime);
            CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).updateByRecordId(recordId, contentValues);
        }else if(isInComing){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseInfo.CallRecord.TYPE, CallRecord.TYPE_VIDEO_REJECT);
            contentValues.put(DatabaseInfo.CallRecord.READ,BussinessConstants.DictInfo.NO);
            CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).updateByRecordId(recordId, contentValues);
        }
        this.sendEmptyMessage(BussinessConstants.DialMsgID.CALL_RECORD_REFRESH_MSG_ID);
    }

    @Override
    public void answerVideo(CallSession callSession) {
        LogUtil.d(TAG,"answerVideo start");
        callSession.accept(CallSession.TYPE_VIDEO);
        LogUtil.d(TAG, "answerVideo end");
    }

    @Override
    public void dealOnClosed(CallSession callSession, boolean isInComing, boolean isTalking,int callTime) {
        String recordId = "";
        if(recordMap.containsKey(String.valueOf(callSession.getSessionId())))
        {
            recordId = recordMap.get(String.valueOf(callSession.getSessionId()));
            recordMap.remove(String.valueOf(callSession.getSessionId()));
        }
        if(StringUtil.isNullOrEmpty(recordId))
        {
            return;
        }

        if(isTalking)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseInfo.CallRecord.DURATION, callTime);
            CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).updateByRecordId(recordId, contentValues);
        }
        else if(isInComing)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseInfo.CallRecord.TYPE, CallRecord.TYPE_VIDEO_MISSING);
            contentValues.put(DatabaseInfo.CallRecord.READ,BussinessConstants.DictInfo.NO);
            CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).updateByRecordId(recordId, contentValues);
        }
        this.sendEmptyMessage(BussinessConstants.DialMsgID.CALL_RECORD_REFRESH_MSG_ID);
    }

    public void delAllCallRecord()
    {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {
                CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).delAll();
                VoipLogic.this.sendEmptyMessage(BussinessConstants.DialMsgID.CALL_RECORD_DEL_ALL_MSG_ID);
            }
        });
    }

    public void delCallRecord(final String[] ids)
    {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {
                CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).delById(ids);
                VoipLogic.this.sendMessage(BussinessConstants.DialMsgID.CALL_RECORD_DEL_MSG_ID, ids);
            }
        });
    }

    public void getCallRecord()
    {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {
                List<CallRecord> callRecords = CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).queryWithName();
                VoipLogic.this.sendMessage(BussinessConstants.DialMsgID.CALL_RECORD_GET_ALL_MSG_ID, callRecords);
            }
        });
    }

    @Override
    public void search(final String input) {
        List<ContactsInfo> contactsInfoList = ContactsInfoManager.getInstance().getCachedLocalContactInfo();
        contactsInfoList.addAll(ContactsInfoManager.getInstance().getCachedAppContactInfo());
        List<ContactsInfo> result = ContactsInfoManager.getInstance().searchContactsInfoLst(contactsInfoList, input);
        if(result!=null && result.size()>0) {
            sendMessage(BussinessConstants.DialMsgID.SEARCH_CONTACTS_SUCCESS_MSG_ID, new SearchResultContacts("", result));
        }else {
            ThreadPoolUtil.execute(new ThreadTask() {
                @Override
                public void run() {
                    List<CallRecord> callRecords = CallRecordDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).searchNumbers(input);
                    VoipLogic.this.sendMessage(BussinessConstants.DialMsgID.CALL_RECORD_SEARCH_MSG_ID, callRecords);
                }
            });
        }
    }

    public boolean isTv() {
        return isTv;
    }

    public void setIsTv(boolean isTv) {
        this.isTv = isTv;
    }
}
