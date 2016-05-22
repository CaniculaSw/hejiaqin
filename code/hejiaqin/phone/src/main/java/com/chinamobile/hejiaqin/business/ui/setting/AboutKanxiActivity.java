package com.chinamobile.hejiaqin.business.ui.setting;

import android.content.Intent;
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
import com.chinamobile.hejiaqin.business.utils.SysInfoUtil;
import com.customer.framework.utils.StringUtil;

/**
 * desc:关于康兮页面
 * Created by wubg on 2016/5/5.
 */
public class AboutKanxiActivity extends BasicActivity {
    private ISettingLogic settingLogic;

    private HeaderView headerView;
    private TextView tv_version;
    private TextView tv_about;
    private TextView tv_tel;
    private TextView tv_email;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_kanxi;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView) findViewById(R.id.header_view);
        headerView.title.setText(R.string.about_kanxi);
        headerView.backImageView.setImageResource(R.mipmap.back);
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_tel = (TextView) findViewById(R.id.tv_tel);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_version.setText(getResources().getString(R.string.about_kanxi_new_version) + SysInfoUtil.getVersionName(this.getApplication()));
    }

    @Override
    protected void initDate() {
        settingLogic.getAbout();
    }

    @Override
    protected void initListener() {
        headerView.backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   //回退
            }
        });
        tv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url != null) {
                    Intent intent = new Intent(AboutKanxiActivity.this, KanxiIntroduceActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            }
        });
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
            case BussinessConstants.SettingMsgID.GET_ABOUT_SUCCESS_MSG_ID:
                AppAboutInfo aboutInfo = (AppAboutInfo) msg.obj;
                if (aboutInfo != null) {
                    url = aboutInfo.getContentUrl();
                    tv_about.setText(Html.fromHtml(aboutInfo.getContent()));
                    tv_email.setText(StringUtil.isNullOrEmpty(aboutInfo.getEmail()) ? getResources().getString(R.string.about_email_cooperation) : getResources().getString(R.string.about_email_cooperation) + aboutInfo.getEmail());
                    tv_tel.setText(StringUtil.isNullOrEmpty(aboutInfo.getTel()) ? getResources().getString(R.string.about_phone_connection) : getResources().getString(R.string.about_phone_connection) + aboutInfo.getTel());
                }
                break;
            case BussinessConstants.SettingMsgID.GET_ABOUT_FAIL_MSG_ID:
                //TODO:停止等待的动画
                showToast(R.string.about_data_fail, Toast.LENGTH_SHORT, null);
                break;
            default:
                break;
        }
        dismissWaitDailog();
    }

}
