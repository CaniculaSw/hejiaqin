package com.chinamobile.hejiaqin.business.ui.more;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.utils.SystemUtil;

/**
 * Created by eshaohu on 16/5/25.
 */
public class AboutActivity extends BasicActivity implements View.OnClickListener {
    private HeaderView headerView;

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
    }

    @Override
    protected void initDate() {
        TextView packageVersion = (TextView) findViewById(R.id.about_package_version);
        packageVersion.setText(getString(R.string.more_about_version, SystemUtil.getPackageVersionName(getApplicationContext())));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initLogics() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_feedback:
                //TODO 调用logic检查更新接口
                break;
            case R.id.about_check_update:
                break;
            case R.id.about_contract:
                break;
            case R.id.back_btn:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);

        //TODO 检查更新结果处理
    }
}
