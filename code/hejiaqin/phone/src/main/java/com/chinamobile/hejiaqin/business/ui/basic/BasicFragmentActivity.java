package com.chinamobile.hejiaqin.business.ui.basic;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.chinamobile.hejiaqin.BuildConfig;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.LogicBuilder;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.more.UserList;
import com.chinamobile.hejiaqin.business.model.more.VersionInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.MyToast;
import com.chinamobile.hejiaqin.business.ui.login.LoginActivity;
import com.chinamobile.hejiaqin.business.ui.more.manger.UpdateManger;
import com.chinamobile.hejiaqin.business.utils.DirUtil;
import com.customer.framework.logic.BuilderImp;
import com.customer.framework.ui.BaseFragmentActivity;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.PermissionsChecker;

import java.util.List;

/**
 * desc:
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/8.
 */
public abstract class BasicFragmentActivity extends BaseFragmentActivity {

    private MyToast myToast;

    private Dialog waitDialog;

    private boolean mIsNecessaryPermission;

    protected boolean networkConnected = true;

    BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo activeInfo = connectMgr.getActiveNetworkInfo();
            if (activeInfo != null && activeInfo.isConnected()) {
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
    protected void handleStateMessage(Message msg) {
        //只在当前activity处理
        if (MyActivityManager.getInstance().isCurrentActity(this.getClass().getName())) {
            switch (msg.what) {
                case BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID:
                    showToast(R.string.network_error_tip, Toast.LENGTH_SHORT, null);
                    break;
                case BussinessConstants.DialMsgID.VOIP_REGISTER_KICK_OUT_MSG_ID:
                    showToast(R.string.kick_out, Toast.LENGTH_SHORT, null);
                    ((ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class)).logout();
                    Intent intent = new Intent(this, LoginActivity.class);
                    this.startActivity(intent);
                    this.finishAllActivity(LoginActivity.class.getName());
                    break;
                case BussinessConstants.CommonMsgId.SERVER_SIDE_ERROR:
                    dismissWaitDailog();
                    showToast(R.string.server_side_error, Toast.LENGTH_SHORT, null);
                    break;
                case BussinessConstants.SettingMsgID.NEW_FORCE_VERSION_AVAILABLE:
                    new UpdateManger(BasicFragmentActivity.this).showForcedUpdateDialog((VersionInfo) msg.obj);
                    break;
                case BussinessConstants.SettingMsgID.NEW_VERSION_AVAILABLE:
                    new UpdateManger(BasicFragmentActivity.this).showNoticeDialog((VersionInfo) msg.obj);
                    break;
                case BussinessConstants.SettingMsgID.NO_NEW_VERSION_AVAILABLE:
                    UserInfoCacheManager.clearVersionInfo(getApplicationContext());
                    break;
                case BussinessConstants.SettingMsgID.BIND_DENIED:
                    LogUtil.i(TAG, "对方不同意你的绑定请求");
                    showToast("对方不同意你的绑定请求", Toast.LENGTH_LONG, null);
                    break;
                case BussinessConstants.SettingMsgID.SEND_CONTACT_RESPOND_DENIED:
                    showToast("发送联系人被拒绝", Toast.LENGTH_LONG, null);
                    break;
                case BussinessConstants.SettingMsgID.SEND_CONTACT_RESPOND_SUCCESS:
                    showToast("发送联系人成功", Toast.LENGTH_LONG, null);
                    break;
                case BussinessConstants.SettingMsgID.GET_DEVICE_LIST_SUCCESSFUL:
                    if (msg.obj != null) {
                        UserList userList = new UserList();
                        userList.setUsers((List<UserInfo>) msg.obj);
                        UserInfoCacheManager.saveBindDeviceToLoacl(getApplicationContext(), userList);
                        UserInfoCacheManager.saveBindDeviceToMem(getApplicationContext(), userList);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void initSystem(Context context) {
        //根据build.gradle设置日志级别
        LogUtil.setContext(getApplicationContext());
        LogUtil.setLogLevel(BuildConfig.LOG_LEVEL);
        LogUtil.setLogCommonDir(DirUtil.getExternalFileDir(context) + "/log/common/");
        ((ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class)).loadUserFromLocal();
        ((ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class)).loadHistoryFromLocal();
    }

    @TargetApi(23)
    protected boolean checkPermissions(String[] needPermissions, boolean isNecessary) {
        mIsNecessaryPermission = isNecessary;
        if (Build.VERSION.SDK_INT >= 23 && needPermissions != null && needPermissions.length != 0) {
            if (PermissionsChecker.lacksPermissions(getApplicationContext(), needPermissions)) {
                startPermissionsActivity(needPermissions);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private void startPermissionsActivity(String[] needPermissions) {
        Intent intent = new Intent(BasicFragmentActivity.this, PermissionsActivity.class);
        intent.putExtra(BussinessConstants.CommonInfo.INTENT_EXTRA_PERMISSIONS, needPermissions);
        startActivityForResult(intent, BussinessConstants.ActivityRequestCode.PERMISSIONS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        switch (requestCode) {
            case BussinessConstants.ActivityRequestCode.PERMISSIONS_REQUEST_CODE:
                if (resultCode == BussinessConstants.CommonInfo.PERMISSIONS_DENIED && mIsNecessaryPermission) {
                    finish();
                }
                break;
        }
    }

    @Override
    protected BuilderImp createLogicBuilder(Context context) {
        return LogicBuilder.getInstance(context);
    }

    protected void showToast(int resId) {
        myToast.showToast(resId, Toast.LENGTH_SHORT, null);
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

    protected void showWaitDailog() {
        if (waitDialog != null) {
            waitDialog.cancel();
        }
        waitDialog = new hejiaqinProgressDialog(this, null);
        waitDialog.show();
    }

    protected void dismissWaitDailog() {
        if (waitDialog != null) {
            waitDialog.cancel();
        }
    }

    public void doNetWorkConnect() {
        this.networkConnected = true;

    }

    public void doNetworkDisConnect() {
        this.networkConnected = false;
    }

}
