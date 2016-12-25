package com.chinamobile.hejiaqin.business.logic.voip;

import com.huawei.rcs.call.CallSession;
import com.huawei.rcs.login.UserInfo;

/**
 * Created by  on 2016/6/5.
 */
public interface IVoipLogic {

    public void registerVoipReceiver();

    public void unRegisterVoipReceiver();

    public void autoLogin();

    public void login(UserInfo userInfo, String ip, String port);

    public void logout();

    public void clearLogined();

    public boolean hasLogined();

    public CallSession call(String calleeNumber, boolean isVideoCall);

    public void hangup(CallSession callSession, boolean isInComing, boolean isTalking, int callTime);

    public void answerVideo(CallSession callSession);

    public void dealOnClosed(CallSession callSession, boolean isInComing, boolean isTalking, int callTime);

    public void delAllCallRecord();

    public void delCallRecord(final String[] ids);

    public void getCallRecord();

    void search(String input);

    public boolean isTv();

    public void setIsTv(boolean isTv);
}
