package com.chinamobile.hejiaqin.tv.business.ui;

import android.content.Intent;
import android.os.Bundle;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.tv.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.tv.business.ui.main.MainFragmentActivity;

public class MainActivity extends BasicActivity {

    private ILoginLogic loginLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
            //TODO:自动登录后跳转至主Activity
            intent = new Intent(MainActivity.this, MainFragmentActivity.class);
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
        loginLogic =(ILoginLogic)super.getLogicByInterfaceClass(ILoginLogic.class);
    }
}
