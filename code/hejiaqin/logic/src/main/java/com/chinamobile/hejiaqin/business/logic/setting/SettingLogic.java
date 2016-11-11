package com.chinamobile.hejiaqin.business.logic.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.more.TvSettingInfo;
import com.chinamobile.hejiaqin.business.model.more.VersionInfo;
import com.chinamobile.hejiaqin.business.model.more.req.GetDeviceListReq;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.setting.SettingHttpmanager;
import com.chinamobile.hejiaqin.business.utils.CaaSUtil;
import com.chinamobile.hejiaqin.business.utils.SysInfoUtil;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.logic.LogicImp;
import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.message.Conversation;
import com.huawei.rcs.message.Message;
import com.huawei.rcs.message.MessageConversation;
import com.huawei.rcs.message.MessagingApi;
import com.huawei.rcs.message.TextMessage;

/**
 * Created by eshaohu on 16/5/24.
 */
public class SettingLogic extends LogicImp implements ISettingLogic {
    private static final String TAG = "SettingLogic";
    private static SettingLogic instance;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Message msg = (Message) bundle.getSerializable(MessagingApi.PARAM_MESSAGE);
//            Message msg = (Message) intent.getSerializableExtra(MessagingApi.PARAM_MESSAGE);
            if (msg == null){
                Toast.makeText(getContext(), "Message received.But message is null", Toast.LENGTH_LONG).show();
                return;
            }
            msg.read();
            LogUtil.i(TAG, "Message received.");
            Toast.makeText(getContext(), "Message received.", Toast.LENGTH_LONG).show();
            int msgType = msg.getType();
            switch (msgType) {
                case Message.MESSAGE_TYPE_TEXT: { // 文本消息
                    TextMessage textMessage = (TextMessage) msg;
                    LogUtil.i(TAG, "receive message: " + msg.getBody() +" Peer name: "+ msg.getPeer().getNumber());
                    break;
                }
                default: { // 其他消息
                }
            }

        }
    };

    private BroadcastReceiver mMessageStatusChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.i(TAG, "Message status changed.");
            Message msg = (Message) intent.getSerializableExtra(MessagingApi.PARAM_MESSAGE);

            if (null != msg) {
                LogUtil.i(TAG, "Status: "+ msg.getStatus() + " body: " +msg.getBody());
                return;
            }
        }
    };

    private SettingLogic(Context context) {
        init(context.getApplicationContext());
    }

    /**
     * 获取单例对象
     *
     * @param context 系统的context对象
     * @return LogicBuilder对象
     */
    public static SettingLogic getInstance(Context context) {
        if (instance == null) {
            synchronized (SettingLogic.class) {
                if (instance == null) {
                    instance = new SettingLogic(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void handleCommit(Context context, String inputNumber, final String id) {
        TvSettingInfo settingInfo = UserInfoCacheManager.getUserSettingInfo(context);
        if (settingInfo != null) {
            switch (id) {
                case "numberOne":
                    settingInfo.setNumberOne(inputNumber);
                    break;
                case "numberTwo":
                    settingInfo.setNumberTwo(inputNumber);
                    break;
                case "numberThree":
                    settingInfo.setNumberThree(inputNumber);
                    break;
                case "numberFour":
                    settingInfo.setNumberFour(inputNumber);
                    break;
            }
            UserInfoCacheManager.updateUserSetting(context, settingInfo);
            SettingLogic.this.sendEmptyMessage(BussinessConstants.SettingMsgID.AUTO_ANSWER_SETTING_COMMIT);
        }
    }

    @Override
    public void getDeviceList() {
        final GetDeviceListReq reqBody = new GetDeviceListReq();
        new SettingHttpmanager(getContext()).getDeviceList(null, reqBody, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.GET_DEVICE_LIST_SUCCESSFUL, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {

            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {

            }
        });
    }

    @Override
    public void checkVersion() {
        new SettingHttpmanager(getContext()).checkVersion(null, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                VersionInfo info = (VersionInfo) obj;
//                //TODO Test
//                info = new VersionInfo();
//                info.setVersionCode("1");
//                info.setVersionName("版本1.0");
//                info.setUrl("http://downsz.downcc.com/apk/dianxinxiangjia_downcc.apk");
//                info.setTime("2016-03-01 15:17:42");
//                info.setByForce(0);
//                info.setForceVersionCode("1");
//                //TODO Test End
                if (isNewVersion(info)) {
                    UserInfoCacheManager.saveVersionInfoToLoacl(getContext(), info);
                    if (isForceUpgrade(info)) {
                        LogUtil.i(TAG, "New force version found!");
                        SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.NEW_FORCE_VERSION_AVAILABLE, info);
                    } else {
                        LogUtil.i(TAG, "New version found!");
                        SettingLogic.this.sendMessage(BussinessConstants.SettingMsgID.NEW_VERSION_AVAILABLE, info);
                    }
                } else {
                    UserInfoCacheManager.clearVersionInfo(getContext());
                    SettingLogic.this.sendEmptyMessage(BussinessConstants.SettingMsgID.NO_NEW_VERSION_AVAILABLE);
                }
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                UserInfoCacheManager.clearVersionInfo(getContext());
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                UserInfoCacheManager.clearVersionInfo(getContext());
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void sendBindReq(String TVNumber) {
        MessageConversation mMessageConversation = MessageConversation.getConversationByNumber(TVNumber);
        LogUtil.i(TAG, "Peer name: " + mMessageConversation.getPeerName());
        Intent intent = new Intent();
        intent.putExtra(MessageConversation.PARAM_SEND_TEXT_PAGE_MODE, true);
        intent.putExtra(Conversation.PARAM_SERVICE_KIND, Conversation.SERVICE_KIND_THROUGH_SEND_MSG);
        mMessageConversation.sendText(CaaSUtil.buildMessage("" + CaaSUtil.CmdType.BIND, "" + 1, "" + CaaSUtil.OpCode.BIND, null), intent);
    }

    private boolean isNewVersion(VersionInfo versionInfo) {
        if (versionInfo == null) {
            return false;
        }
        int currentVersioncode, versionCode;
        currentVersioncode = SysInfoUtil.getVersionCode(getContext());
        try {
            versionCode = Integer.parseInt(versionInfo.getVersionCode());
        } catch (Exception e) {
            LogUtil.e(TAG, e);
            return false;
        }
        if (currentVersioncode < versionCode) {
            return true;
        }
        return false;
    }

    private boolean isForceUpgrade(VersionInfo versionInfo) {
        if (versionInfo.getByForce() == 1) {
            return true;
        }
        int currentVersioncode, forceVersionCode;
        currentVersioncode = SysInfoUtil.getVersionCode(getContext());
        try {
            forceVersionCode = Integer.parseInt(versionInfo.getForceVersionCode());
        } catch (Exception e) {
            LogUtil.e(TAG, e);
            return false;
        }
        if (currentVersioncode < forceVersionCode) {
            return true;
        }
        return false;
    }

    public void registerMessageReceiver() {
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mMessageReceiver, new IntentFilter(MessagingApi.EVENT_MESSAGE_INCOMING));
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mMessageReceiver,new IntentFilter("SMS_DELIVER_ACTION"));
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mMessageStatusChangedReceiver, new IntentFilter(MessagingApi.EVENT_MESSAGE_STATUS_CHANGED));
    }

    public void unRegisterMessageReceiver() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageStatusChangedReceiver);
    }
}
