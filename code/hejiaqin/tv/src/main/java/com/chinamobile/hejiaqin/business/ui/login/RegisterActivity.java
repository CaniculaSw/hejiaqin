package com.chinamobile.hejiaqin.business.ui.login;

import android.view.View;
import android.widget.LinearLayout;

import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.RegistingDialog;
import com.chinamobile.hejiaqin.tv.R;

/**
 * Created by eshaohu on 17/1/4.
 */
public class RegisterActivity extends BasicActivity implements View.OnClickListener{
    private LinearLayout registerLayout;
    @Override
    protected int getLayoutId() {
        return R.layout.activiity_register;
    }

    @Override
    protected void initView() {
        registerLayout = (LinearLayout) findViewById(R.id.register_ll);
        registerLayout.setClickable(true);
        registerLayout.setOnClickListener(this);


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
        switch (v.getId()){
            case R.id.register_ll:
                registerLayout.setFocusable(false);
                RegistingDialog.show(this);
                break;
        }
    }
}
