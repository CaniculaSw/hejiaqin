package com.chinamobile.hejiaqin.business.ui.serviceAgreement;

import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.model.setting.AppAboutInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by wubg on 2016/5/5.
 */
public class ServiceAgreementActivity extends BasicActivity implements View.OnClickListener {
    private ISettingLogic settingLogic;

    private HeaderView headerView;
    private TextView tv_content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_servive_agreement;
    }

    @Override
    protected void initView() {
        showWaitDailog();
        headerView = (HeaderView) findViewById(R.id.header_view);
        headerView.title.setText(R.string.kanxi_service_agreement);
        headerView.backImageView.setImageResource(R.mipmap.back);

        tv_content = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    protected void initDate() {
        settingLogic.downloadService();
    }

    @Override
    protected void initListener() {
        headerView.backLayout.setOnClickListener(this);
    }

    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    @Override
    protected void initLogics() {
        settingLogic = (ISettingLogic) this.getLogicByInterfaceClass(ISettingLogic.class);
    }


    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.SettingMsgID.GET_SERVICE_CONTENT_SUCCESS_MSG_ID:
                AppAboutInfo aboutInfo = (AppAboutInfo) msg.obj;
                upDateUI(aboutInfo);
                break;
            case BussinessConstants.SettingMsgID.GET_ABOUT_FAIL_MSG_ID:
                //TODO:停止等待的动画
                showToast("测试，获取数据失败", Toast.LENGTH_SHORT, null);
                break;
            default:
                break;
        }
        dismissWaitDailog();
    }

    private void upDateUI(AppAboutInfo aboutInfo) {
        if (aboutInfo != null) {
            tv_content.setText(Html.fromHtml(aboutInfo.getContent()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backLayout:
                finish();   //回退
                break;
        }
    }

}
