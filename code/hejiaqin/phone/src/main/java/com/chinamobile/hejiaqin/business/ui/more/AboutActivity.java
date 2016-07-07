package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.utils.SystemUtil;

/**
 * Created by eshaohu on 16/5/25.
 */
public class AboutActivity extends BasicActivity implements View.OnClickListener {
    private HeaderView headerView;
    private LinearLayout feedBackLL;
    private LinearLayout checkUpdateLL;
    private LinearLayout showContract;
    private ISettingLogic settingLogic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView) findViewById(R.id.about_header);
        headerView.title.setText(R.string.more_about);
        headerView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);
        headerView.backImageView.setClickable(true);
        headerView.backImageView.setOnClickListener(this);

        feedBackLL = (LinearLayout) findViewById(R.id.about_feedback);
        checkUpdateLL = (LinearLayout) findViewById(R.id.about_check_update);
        showContract = (LinearLayout) findViewById(R.id.about_contract);
    }

    @Override
    protected void initDate() {
        TextView packageVersion = (TextView) findViewById(R.id.about_package_version);
        packageVersion.setText(getString(R.string.more_about_version, SystemUtil.getPackageVersionName(getApplicationContext())));
        feedBackLL.setClickable(true);
        feedBackLL.setOnClickListener(this);
        checkUpdateLL.setClickable(true);
        checkUpdateLL.setOnClickListener(this);
        showContract.setClickable(true);
        showContract.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initLogics() {
        settingLogic = (ISettingLogic) getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_feedback:
                jumpToFeedBack();
                break;
            case R.id.about_check_update:
                settingLogic.checkVersion();
                break;
            case R.id.about_contract:
                jumpToShowContract();
                break;
            case R.id.back_iv:
                doBack();
                break;
            default:
                break;
        }
    }

    private void jumpToFeedBack() {
        Intent intent = new Intent(AboutActivity.this, FeedBackActivity.class);
        startActivity(intent);
    }

    private void jumpToShowContract() {
        Intent intent = new Intent(AboutActivity.this, ShowContractActivity.class);
        startActivity(intent);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);

        //TODO 检查更新结果处理
    }
}
