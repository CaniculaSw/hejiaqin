package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.model.login.req.RegisterFirstStepInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.MyDatePicker;

/**
 * Created by Xiadong on 2016/4/27
 */
public class RegisterDetailActivity extends BasicActivity implements View.OnClickListener {

    private Button nextActionBtn;
    private ILoginLogic loginLogic;
    private RegisterFirstStepInfo registerFirstStepInfo;
    private RadioGroup genderRadioGroup;
    private EditText heightEditTx;
    private EditText weightEditTx;
    private MyDatePicker datePicker;
    private TextView errorInfoTv;
    private int errorFromViewId;
    private boolean requestFailed;


    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic)this.getLogicByInterfaceClass(ILoginLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_detail;
    }

    @Override
    protected void initView() {
        TextView headerTitleTx = (TextView)findViewById(R.id.headertitle);
        headerTitleTx.setText(R.string.register_detail_title);
        genderRadioGroup=(RadioGroup)findViewById(R.id.gender_radio_group);
        heightEditTx = (EditText)findViewById(R.id.height_edit_tx);
        weightEditTx = (EditText)findViewById(R.id.weight_edit_tx);
        datePicker = (MyDatePicker)findViewById(R.id.birthday_date);
        nextActionBtn = (Button)findViewById(R.id.next_action_button);
        errorInfoTv = (TextView) findViewById(R.id.error_info_tv);
    }

    @Override
    protected void initDate() {
        registerFirstStepInfo = getIntent().getParcelableExtra(BussinessConstants.Login.INTENT_REGISTER_FIRST_INFO);
        //默认男
        registerFirstStepInfo.setSex(BussinessConstants.DictInfo.SEX_MALE);


    }

    @Override
    protected void initListener() {
        nextActionBtn.setOnClickListener(this);
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.female_radio_button) {
                    registerFirstStepInfo.setSex(BussinessConstants.DictInfo.SEX_FEMALE);
                } else {
                    registerFirstStepInfo.setSex(BussinessConstants.DictInfo.SEX_MALE);
                }
            }
        });
        heightEditTx.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hideErrorInfo(heightEditTx);
            }
        });
        weightEditTx.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hideErrorInfo(weightEditTx);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_action_button:
                next();
                break;
        }
    }

    private void next()
    {
        if (TextUtils.isEmpty(heightEditTx.getText().toString())) {
            displayErrorInfo(R.string.height_null, heightEditTx);
            heightEditTx.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(weightEditTx.getText().toString())) {
            displayErrorInfo(R.string.weight_null, weightEditTx);
            weightEditTx.requestFocus();
            return;
        }
        registerFirstStepInfo.setBirthday(datePicker.getYear() + "-" + (datePicker.getMonth()+1) + "-" + datePicker.getDayOfMonth());
        hideErrorInfo(null);
        super.showWaitDailog();
        loginLogic.registerFirstStep(registerFirstStepInfo);
    }

    private void displayErrorInfo(int stringId, View view) {
        errorInfoTv.setText(getResources().getString(R.string.error_mark) + getResources().getString(stringId));
        errorInfoTv.setVisibility(View.VISIBLE);
        errorFromViewId = view.getId();
    }


    private void hideErrorInfo(View view) {
        if (view!=null && errorFromViewId == view.getId()) {
            errorFromViewId = 0;
            errorInfoTv.setVisibility(View.INVISIBLE);
            errorInfoTv.setText("");
        }
        if(requestFailed)
        {
            requestFailed = false;
            errorInfoTv.setVisibility(View.INVISIBLE);
            errorInfoTv.setText("");
        }
    }

    private void displayRequestErrorInfo(int stringId) {
        errorInfoTv.setText(getResources().getString(R.string.error_mark) + getResources().getString(stringId));
        errorInfoTv.setVisibility(View.VISIBLE);
        requestFailed = true;
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        String code="";
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.REGISTER_FIRST_STEP_SUCCESS_MSG_ID:
                super.dismissWaitDailog();
                Intent intent = new Intent(RegisterDetailActivity.this, RegisterInterestActivity.class);
                intent.putExtra(BussinessConstants.Login.INTENT_LOGIN_ID, registerFirstStepInfo.getLoginid());
                this.startActivity(intent);
                this.finishAllActivity(RegisterInterestActivity.class.getName());
                break;
            case BussinessConstants.LoginMsgID.REGISTER_FIRST_STEP_FAIL_MSG_ID:
                super.dismissWaitDailog();
                if (msg.obj != null) {
                    code = (String) msg.obj;
                }
                if (BussinessConstants.LoginHttpErrorCode.HAS_REGISTER.equals(code)) {
                    displayRequestErrorInfo(R.string.has_registered);
                } else if (BussinessConstants.LoginHttpErrorCode.HAS_REGISTER_FORBIDDEN.equals(code)) {
                    displayRequestErrorInfo(R.string.has_registered_forbidden);
                } else {
                    displayRequestErrorInfo(R.string.register_fail);
                }
                break;
            default:
                break;
        }
    }
}
