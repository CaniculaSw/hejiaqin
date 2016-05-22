package com.chinamobile.hejiaqin.business.ui.basic;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.LogicBuilder;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.ui.basic.view.MyToast;
import com.chinamobile.hejiaqin.business.utils.DirUtil;
import com.customer.framework.component.log.Logger;
import com.customer.framework.logic.BuilderImp;
import com.customer.framework.ui.BaseActivity;

/**
 * desc:Basic Actity,处理系统初始化以及将LogicBuilder传入底层框架
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/8.
 */
public abstract class BasicActivity extends BaseActivity {

    private MyToast myToast;

    private Dialog waitDialog;

    BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo activeInfo = connectMgr.getActiveNetworkInfo();
            if (activeInfo!=null && activeInfo.isConnected()) {
                doNetWorkConnect();
            } else {
                doNetworkDisConnect();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myToast = new MyToast(this.getApplicationContext());
        setContentView(getLayoutId());
        initView();
        initListener();
        initDate();
        //注册监听
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionReceiver, intentFilter);
        MyActivityManager.getInstance().AddActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyActivityManager.getInstance().setCurrentActivityName(this.getClass().getName());
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initDate();

    protected abstract void initListener();

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

    @Override
    protected void handleStateMessage(Message msg) {
        //只在当前activity处理
        if (MyActivityManager.getInstance().isCurrentActity(this.getClass().getName())) {
            switch (msg.what) {
                case BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID:
                    dismissWaitDailog();
                    showToast(R.string.network_error_tip, Toast.LENGTH_SHORT, null);
                    break;
                default:
                    break;
            }
        }
    }

    protected void showToast(int resId, int duration, MyToast.Position pos) {
        myToast.showToast(resId, duration, pos);
    }

    protected void showToast(String text, int duration, MyToast.Position pos) {
        myToast.showToast(text, duration, pos);
    }

    public void showToast(View view, int duration, MyToast.Position pos) {
        myToast.showToast(view, duration, pos);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(connectionReceiver);
        MyActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    protected void finishAllActivity(String exceptActivityName) {
        MyActivityManager.getInstance().finishAllActivity(exceptActivityName);
    }

    protected void finishActivitys(String[] needFinishActivityNames) {
        MyActivityManager.getInstance().finishActivitys(needFinishActivityNames);
    }

    protected void showWaitDailog() {
        if (waitDialog != null) {
            waitDialog.cancel();
        }
        waitDialog = new KangxiProgressDialog(this,null);
        waitDialog.show();
    }

    protected void dismissWaitDailog() {
        if (waitDialog != null) {
            waitDialog.cancel();
        }
    }

    protected void doNetWorkConnect() {

    }

    protected void doNetworkDisConnect() {

    }

}
