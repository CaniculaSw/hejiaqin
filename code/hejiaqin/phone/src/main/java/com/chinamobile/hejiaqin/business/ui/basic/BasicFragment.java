package com.chinamobile.hejiaqin.business.ui.basic;

import android.content.Context;
import android.os.Message;

import com.chinamobile.hejiaqin.business.logic.LogicBuilder;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.utils.DirUtil;
import com.customer.framework.component.log.Logger;
import com.customer.framework.logic.BuilderImp;
import com.customer.framework.ui.BaseFragment;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/23.
 */
public abstract class BasicFragment extends BaseFragment {

    protected BackListener mListener;

    @Override
    protected void initSystem(Context context) {
        Logger.setLogCommonDir(DirUtil.getExternalFileDir(context) + "/log/common/");
        ((ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class)).loadUserFromLocal();
        ((ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class)).loadHistoryFromLocal();
    }

    @Override
    protected BuilderImp createLogicBuilder(Context context) {
        return LogicBuilder.getInstance(context);
    }

    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    @Override
    protected void initLogics()
    {

    }
    /**
     * 回调至父类(FragmentActivity或者BaseFragment)
     */
    public interface BackListener {
        void onAction(int actionId, Object obj);
    }

    /**
     * 接收父类(FragmentActivity或者BaseFragment)的消息
     */
    public abstract void recieveMsg(Message msg);

    public void setActivityListener(BackListener listener) {
        this.mListener = listener;
    }

    public void doNetWorkConnect() {

    }

    public void doNetworkDisConnect() {

    }
}
