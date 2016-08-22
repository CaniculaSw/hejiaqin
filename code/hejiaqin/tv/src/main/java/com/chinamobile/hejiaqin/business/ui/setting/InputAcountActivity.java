package com.chinamobile.hejiaqin.business.ui.setting;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by eshaohu on 16/6/4.
 */
public class InputAcountActivity extends BasicActivity implements View.OnClickListener {
    private HeaderView mHeaderView;
    private EditText mName;
    private EditText mNumber;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_input_contact;
    }

    @Override
    protected void initView() {
        mHeaderView = (HeaderView) findViewById(R.id.more_input_contact_title);
        mName = (EditText) findViewById(R.id.more_input_contact_name_et);
        mNumber = (EditText) findViewById(R.id.more_input_contact_number_et);
        mHeaderView.rightBtn.setImageResource(R.mipmap.title_icon_check_nor);
        mHeaderView.title.setText(R.string.more_input_account);
        mHeaderView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        mHeaderView.rightBtn.setOnClickListener(this);
        mHeaderView.backImageView.setOnClickListener(this);
        mNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
            case R.id.right_btn:
                if (mName.getText().length() <= 0 || mNumber.getText().length() <= 0) {
                    break;
                } else {
                    showToast("OK", Toast.LENGTH_LONG, null);
                }
                break;
            default:
                break;
        }
    }

}
