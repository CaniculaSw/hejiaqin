package com.chinamobile.hejiaqin.business.ui.setting.dialog;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.business.logic.LogicBuilder;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.StringUtil;

/**
 * Created by eshaohu on 16/8/26.
 */
public class AutoAnswerNumberSettingDialog extends BasicActivity implements View.OnClickListener {
    EditText inputNumber;
    ImageButton deleteAllBtn;
    LinearLayout commitBtn;
    LinearLayout cancleBtn;
    ISettingLogic settingLogic;
    RelativeLayout inputLayout;
    LinearLayout numberLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_auto_answer_number_setting;
    }

    @Override
    protected void initView() {
        inputNumber = (EditText) findViewById(R.id.number_et);
        deleteAllBtn = (ImageButton) findViewById(R.id.delete_all_btn);
        commitBtn = (LinearLayout) findViewById(R.id.btn_commit);
        cancleBtn = (LinearLayout) findViewById(R.id.btn_cancle);
        inputLayout = (RelativeLayout) findViewById(R.id.input_number_layout);
        numberLayout = (LinearLayout) findViewById(R.id.number_ll);
        numberLayout.setBackgroundResource(R.drawable.btn_bg_selected);
        inputLayout.setBackgroundResource(R.color.transparent);
    }

    @Override
    protected void initDate() {
        Intent intent = getIntent();
        inputNumber.setText(intent.getStringExtra("number"));
        inputNumber.setSelection(intent.getStringExtra("number") == null ? 0 : intent
                .getStringExtra("number").length());
    }

    @Override
    protected void initListener() {
        deleteAllBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
        cancleBtn.setOnClickListener(this);
        inputNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocused) {
                if (hasFocused) {
                    inputNumber.setSelection(inputNumber.getText().length());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                    numberLayout.setBackgroundResource(R.drawable.btn_bg_selected);
                    inputLayout.setBackgroundResource(R.color.transparent);
                } else {
                    numberLayout.setBackgroundResource(R.color.transparent);
                    inputLayout.setBackgroundResource(R.drawable.edittext_bg);
                }
            }
        });
        deleteAllBtn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    numberLayout.setBackgroundResource(R.drawable.btn_bg_selected);
                    inputLayout.setBackgroundResource(R.color.transparent);
                } else {
                    numberLayout.setBackgroundResource(R.color.transparent);
                    inputLayout.setBackgroundResource(R.drawable.edittext_bg);
                }
            }
        });

    }

    @Override
    protected void initLogics() {
        settingLogic = (ISettingLogic) LogicBuilder.getInstance(getApplicationContext())
                .getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_all_btn:
                inputNumber.setText("");
                break;
            case R.id.btn_cancle:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.btn_commit:
                doCommit();
                break;
            default:
                break;
        }
    }

    private void doCommit() {
        String input = inputNumber.getText().toString();
        String id = getIntent().getStringExtra("id");
        if (input.length() > 0 && StringUtil.isNumeric(input, false)) {
            settingLogic.handleCommit(getApplicationContext(), input, id);
            finish();
        } else {
            settingLogic.handleCommit(getApplicationContext(), "", id);
            finish();
        }
    }

}
