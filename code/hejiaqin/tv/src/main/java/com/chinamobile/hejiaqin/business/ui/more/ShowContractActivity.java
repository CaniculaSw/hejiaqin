package com.chinamobile.hejiaqin.business.ui.more;

import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.utils.FileUtil;

/**
 * Created by eshaohu on 16/6/30.
 */
public class ShowContractActivity extends BasicActivity implements View.OnClickListener {
    private HeaderView mHeadView;
    private TextView mContractContentTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contract;
    }

    @Override
    protected void initView() {
        mHeadView = (HeaderView) findViewById(R.id.header);
        mHeadView.title.setText(getString(R.string.more_about_contract));
        mHeadView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);

        mContractContentTv = (TextView) findViewById(R.id.contract_content);
    }

    @Override
    protected void initDate() {
        String content = FileUtil.getFileFromAssets("service_contract.txt", ShowContractActivity.this);
        mContractContentTv.setText(content);
    }

    @Override
    protected void initListener() {
        mHeadView.backImageView.setClickable(true);
        mHeadView.backImageView.setOnClickListener(this);
    }

    @Override
    protected void initLogics() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                doBack();
            default:
                break;
        }
    }
}
