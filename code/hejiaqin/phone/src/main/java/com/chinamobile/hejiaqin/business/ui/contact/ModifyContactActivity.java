package com.chinamobile.hejiaqin.business.ui.contact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

public class ModifyContactActivity extends BasicActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_INPUT_NAME = 10001;

    private static final int REQUEST_CODE_INPUT_NUMBER = 10002;

    private static final int REQUEST_CODE_INPUT_PHOTO = 10003;

    private HeaderView titleLayout;

    private View headView;

    private View nameView;

    private View numberView;

    /**
     * 当前支持两种模式:新增联系人和修改联系人;
     * 默认为新增联系人
     */
    private boolean addContactMode = true;

    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    @Override
    protected void initLogics() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_modify;
    }

    @Override
    protected void initView() {
        // title
        titleLayout = (HeaderView) findViewById(R.id.title);
        titleLayout.title.setText(R.string.contact_modify_title_add_text);
        titleLayout.rightBtn.setImageResource(R.mipmap.title_icon_check_nor);
        titleLayout.backImageView.setImageResource(R.mipmap.title_icon_back_nor);

        // 头像
        headView = findViewById(R.id.contact_head_layout);


        // 姓名
        nameView = findViewById(R.id.contact_name_layout);

        // 号码
        numberView = findViewById(R.id.contact_number_layout);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        titleLayout.rightBtn.setOnClickListener(this);
        titleLayout.backImageView.setOnClickListener(this);

        headView.setOnClickListener(this);
        nameView.setOnClickListener(this);
        numberView.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_btn:
                doClickSubmit();
                break;
            case R.id.back_btn:
                doBack();
                break;
            case R.id.contact_head_layout:
                doClickHeadLayout();
                break;
            case R.id.contact_name_layout:
                doClickNameLayout();
                break;
            case R.id.contact_number_layout:
                doClickNumberLayout();
                break;
        }
    }

    private void doClickSubmit() {

    }

    private void doClickHeadLayout() {

    }

    private void doClickNameLayout() {
        Intent intent = new Intent(this, InputInfoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_INPUT_NAME);
    }

    private void doClickNumberLayout() {
        Intent intent = new Intent(this, InputInfoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_INPUT_NUMBER);
    }
}
