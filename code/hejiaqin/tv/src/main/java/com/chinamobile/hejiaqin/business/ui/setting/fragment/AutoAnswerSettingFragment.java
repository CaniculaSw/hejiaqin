package com.chinamobile.hejiaqin.business.ui.setting.fragment;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;

import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.main.SettingFragment;
import com.chinamobile.hejiaqin.tv.R;

/**
 * Created by eshaohu on 16/8/24.
 */
public class AutoAnswerSettingFragment extends BasicFragment implements View.OnClickListener {
    HeaderView headerView;
    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_auto_answer_setting;
    }

    @Override
    protected void initView(View view) {
        headerView = (HeaderView) view.findViewById(R.id.title);
        headerView.title.setText(getString(R.string.auto_answer));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                FragmentMgr.getInstance().showSettingFragment(new SettingFragment());
                break;
            default:
                break;
        }
    }
}
