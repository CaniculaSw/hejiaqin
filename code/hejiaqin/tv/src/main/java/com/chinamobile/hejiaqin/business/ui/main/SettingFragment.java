package com.chinamobile.hejiaqin.business.ui.main;


import android.os.Message;
import android.view.FocusFinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.FocusManager;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.LogUtil;


/**
 * desc: 设置
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/22.
 */
public class SettingFragment extends BasicFragment implements View.OnClickListener {
    HeaderView settingHeader;
    LinearLayout functionSettingLL;
    LinearLayout boxAccountLL;
    LinearLayout checkStatusLL;
    LinearLayout aboutLL;
    LinearLayout downloadAppLL;

    private ILoginLogic loginLogic;
    private IVoipLogic mVoipLogic;

    private static final String TAG = "SettingFragment";

    @Override
    protected void handleFragmentMsg(Message msg) {
    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_more;
    }

    @Override
    protected void initView(View view) {
        settingHeader = (HeaderView) view.findViewById(R.id.title);
        settingHeader.title.setText(getResources().getText(R.string.setting_title));

        functionSettingLL = (LinearLayout) view.findViewById(R.id.function_setting_ll);
        boxAccountLL = (LinearLayout) view.findViewById(R.id.box_account_ll);
        checkStatusLL = (LinearLayout) view.findViewById(R.id.check_status_ll);
        aboutLL = (LinearLayout) view.findViewById(R.id.about_ll);
        downloadAppLL = (LinearLayout) view.findViewById(R.id.download_app_ll);

        functionSettingLL.setOnClickListener(this);
        boxAccountLL.setOnClickListener(this);
        checkStatusLL.setOnClickListener(this);
        aboutLL.setOnClickListener(this);
        downloadAppLL.setOnClickListener(this);

        FocusManager.getInstance().addFocusViewInLeftFrag("3", functionSettingLL);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        LogUtil.i(TAG, String.valueOf(v.getId()));
    }

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class);
        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    @Override
    public void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {

        }
    }
}
