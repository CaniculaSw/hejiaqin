package com.chinamobile.hejiaqin.business.ui.main;


import android.os.Message;
import android.view.View;

import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.tv.R;


/**
 * desc: 设置
 * project:hejiaqin
 * version 001
 * author: zhanggj
 * Created: 2016/4/22.
 */
public class TestFragment extends BasicFragment implements View.OnClickListener {

    private static final String TAG = "TestFragment";

    @Override
    protected void handleFragmentMsg(Message msg) {
    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_test;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void initLogics() {
    }

    @Override
    public void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
    }
}
