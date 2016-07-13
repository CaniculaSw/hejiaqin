package com.chinamobile.hejiaqin.business.ui.more;

import android.view.View;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by eshaohu on 16/5/23.
 */
public class MoreFunActivity extends BasicActivity implements View.OnClickListener {
    private HeaderView headerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_fun;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView) findViewById(R.id.more_fun_header);
        headerView.title.setText(R.string.more_more_fun);
        headerView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);
        headerView.backImageView.setClickable(true);
        headerView.backImageView.setOnClickListener(this);
    }

    @Override
    protected void initDate() {

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
            case R.id.back_iv:
                finish();
                break;
            default:
                break;
        }
    }
}
