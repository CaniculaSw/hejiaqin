package com.chinamobile.hejiaqin.business.ui;

import android.content.Intent;
import android.os.Bundle;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.login.LoginActivity;
import com.chinamobile.hejiaqin.business.ui.main.MainFragmentActivity;
import com.customer.framework.utils.LogUtil;

public class MainActivity extends BasicActivity {

    private ILoginLogic loginLogic;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        if (loginLogic.hasLogined()) {
            intent = new Intent(MainActivity.this, MainFragmentActivity.class);
            LogUtil.d(TAG, "Init the CMIM SDK");
            loginLogic.initCMIMSdk();
        } else {
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }
//        sendEmptyMessageDelayed(BussinessConstants.LoginMsgID.SHOW_LAUNCH_PAGE_FINISHED, 3*1000);
        // intent = new Intent(MainActivity.this, MainFragmentActivity.class);
        this.startActivity(intent);
        this.finish();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class);
    }
}
