package com.chinamobile.hejiaqin.business.ui.contact;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

public class InputInfoActivity extends BasicActivity implements View.OnClickListener {
    private HeaderView titleLayout;

    private EditText input;

    private View cancel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_info;
    }

    @Override
    protected void initView() {
        // title
        titleLayout = (HeaderView) findViewById(R.id.title);
        titleLayout.title.setText(R.string.contact_modify_title_add_text);
        titleLayout.rightBtn.setImageResource(R.mipmap.title_icon_check_nor);
        titleLayout.backImageView.setImageResource(R.mipmap.title_icon_back_nor);

        input = (EditText) findViewById(R.id.input);

        cancel = findViewById(R.id.delete);
    }

    @Override
    protected void initDate() {
    }

    @Override
    protected void initListener() {
        titleLayout.rightBtn.setOnClickListener(this);
        titleLayout.backImageView.setOnClickListener(this);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cancel.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });

        cancel.setOnClickListener(this);
    }

    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    @Override
    protected void initLogics() {

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
            case R.id.delete:
                doClickDelete();
                break;
        }
    }

    private void doClickSubmit() {

    }

    private void doClickDelete() {
        input.setText("");
    }
}
