package com.chinamobile.hejiaqin.business.ui.basic;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.ui.basic.view.MyToast;
import com.chinamobile.hejiaqin.tv.BuildConfig;
import com.chinamobile.hejiaqin.business.logic.LogicBuilder;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.utils.DirUtil;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.logic.BuilderImp;
import com.customer.framework.ui.BaseFragment;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/23.
 */
public abstract class BasicFragment extends BaseFragment {

    protected BackListener mListener;

    protected boolean isCreateView = false;

    protected boolean networkConnected = true;

    private MyToast myToast;

    @Override
    protected void initSystem(Context context) {
        //根据build.gradle设置日志级别
        LogUtil.setContext(getActivity().getApplicationContext());
        LogUtil.setLogLevel(BuildConfig.LOG_LEVEL);
        LogUtil.setLogCommonDir(DirUtil.getExternalFileDir(context) + "/log/common/");
        LogUtil.d(TAG, "device:" + Build.DEVICE);
        LogUtil.d(TAG, "model:" + Build.MODEL);
        ((ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class)).loadUserFromLocal();
        ((ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class)).loadHistoryFromLocal();
        ((IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class)).setIsTv(true);
    }

    @Override
    protected BuilderImp createLogicBuilder(Context context) {
        return LogicBuilder.getInstance(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int resId = getLayoutResId();
        if (resId <= 0) {
            return null;
        }
        myToast = new MyToast(this.getContext().getApplicationContext());
        View view = inflater.inflate(resId, container, false);
        initView(view);
        initData();
        isCreateView = true;
        return view;
    }


    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    @Override
    protected void initLogics() {

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
    public void recieveMsg(Message msg) {
        if (!isCreateView) {
            return;
        }
        handleFragmentMsg(msg);
    }

    @Override
    public void handleStateMessage(Message msg) {
        if (!isCreateView) {
            return;
        }
        handleLogicMsg(msg);
    }

    protected abstract void handleFragmentMsg(Message msg);

    protected abstract void handleLogicMsg(Message msg);

    protected abstract int getLayoutResId();

    protected abstract void initView(View view);

    protected abstract void initData();


    public void setActivityListener(BackListener listener) {
        this.mListener = listener;
    }

    public void doNetWorkConnect() {
        this.networkConnected = true;

    }

    public void doNetworkDisConnect() {
        this.networkConnected = false;
    }

    protected void showToast(int resId) {
        MyToast.Position position = myToast.new Position();
        showToast(resId, Toast.LENGTH_SHORT, position);
    }

    protected void showToast(int resId, int duration, MyToast.Position pos) {
        myToast.showToast(resId, duration, pos);
    }

    public abstract View getFirstFouseView();
}
