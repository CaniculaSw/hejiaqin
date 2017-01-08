package com.chinamobile.hejiaqin.business.ui.main;


import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.FocusManager;
import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.setting.fragment.AboutFragment;
import com.chinamobile.hejiaqin.business.ui.setting.fragment.AutoAnswerSettingFragment;
import com.chinamobile.hejiaqin.business.ui.setting.fragment.BoxAccountFragment;
import com.chinamobile.hejiaqin.business.ui.setting.fragment.CheckStatusFragment;
import com.chinamobile.hejiaqin.business.ui.setting.fragment.DownloadAppFragment;
import com.chinamobile.hejiaqin.tv.R;


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
        return R.layout.fragment_tab_setting;
    }

    @Override
    protected void initView(View view) {
        settingHeader = (HeaderView) view.findViewById(R.id.title);
        settingHeader.title.setText(getResources().getText(R.string.setting));

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

//        FocusManager.getInstance().addFocusViewInLeftFrag("3", functionSettingLL);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.function_setting_ll:
                FragmentMgr.getInstance().showSettingFragment(new AutoAnswerSettingFragment());
                break;
            case R.id.download_app_ll:
                FragmentMgr.getInstance().showSettingFragment(new DownloadAppFragment());
                break;
            case R.id.about_ll:
                FragmentMgr.getInstance().showSettingFragment(new AboutFragment());
                break;
            case R.id.check_status_ll:
                FragmentMgr.getInstance().showSettingFragment(new CheckStatusFragment());
                break;
            case R.id.box_account_ll:
                FragmentMgr.getInstance().showSettingFragment(new BoxAccountFragment());
                break;
            default:
                break;
        }

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

    public View getFirstFouseView()
    {
        return functionSettingLL;
    }
}
