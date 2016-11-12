package com.chinamobile.hejiaqin.business.ui.setting.dialog;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.huawei.rcs.message.TextMessage;

/**
 * Created by eshaohu on 16/11/12.
 */
public class BindRequestDialog extends BasicActivity implements View.OnClickListener {
    public TextMessage message;
    private String fromNumber;
    private LinearLayout agreeButton;
    private LinearLayout deniedButton;
    private TextView tips;

    @Override
    protected void initLogics() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.popwindow_bind_request_incoming;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        message = (TextMessage) intent.getSerializableExtra("message");
        fromNumber = message.getPeer().getNumber();
        agreeButton = (LinearLayout) findViewById(R.id.btn_agree);
        deniedButton = (LinearLayout) findViewById(R.id.btn_denied);
        tips = (TextView) findViewById(R.id.hint);
        tips.setText(getString(R.string.prompt_bind_hint, fromNumber));
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        agreeButton.setOnClickListener(this);
        deniedButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_agree:
                break;
            case R.id.btn_denied:
                finish();
                break;
        }
    }
}
