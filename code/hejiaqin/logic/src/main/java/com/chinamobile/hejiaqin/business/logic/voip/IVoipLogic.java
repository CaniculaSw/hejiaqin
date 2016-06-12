package com.chinamobile.hejiaqin.business.logic.voip;

import com.huawei.rcs.call.CallSession;
import com.huawei.rcs.login.UserInfo;

/**
 * Created by zhanggj on 2016/6/5.
 */
public interface IVoipLogic {

    public void registerVoipReceiver();

    public void unRegisterVoipReceiver();

    public void login(UserInfo userInfo,String ip,String port);

    public void logout();

    public CallSession call(String calleeNumber,boolean isVideoCall);
}
