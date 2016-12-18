package com.chinamobile.hejiaqin.business.ui.more;

import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.LogicBuilder;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by eshaohu on 16/6/4.
 */
public class InputAcountActivity extends BasicActivity implements View.OnClickListener {
    private HeaderView mHeaderView;
    private EditText mName;
    private EditText mNumber;
    private ISettingLogic settingLogic;
    private RelativeLayout progressLayout;
    private TextView progressTip;

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
        progressLayout = (RelativeLayout) findViewById(R.id.progress_tips);
        progressLayout.setVisibility(View.INVISIBLE);
        progressTip = (TextView) findViewById(R.id.progress_text);

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
        settingLogic = (ISettingLogic) LogicBuilder.getInstance(getApplicationContext()).getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.SettingMsgID.STATUS_DELIVERY_OK:
            case BussinessConstants.SettingMsgID.STATUS_DISPLAY_OK:
                progressTip.setText(R.string.waiting_for_respond);
                break;
            case BussinessConstants.SettingMsgID.STATUS_SEND_FAILED:
            case BussinessConstants.SettingMsgID.STATUS_UNDELIVERED:
                progressLayout.setVisibility(View.INVISIBLE);
                showToast(getString(R.string.sending_bind_request_failed), Toast.LENGTH_LONG, null);
                break;
            case BussinessConstants.SettingMsgID.BIND_SUCCESS:
                showToast("绑定成功", Toast.LENGTH_SHORT, null);
                settingLogic.bindSuccNotify();
                doBack();
                break;
            case BussinessConstants.SettingMsgID.BIND_DENIED:
                progressLayout.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                doBack();
                break;
            case R.id.right_btn:
                if (mName.getText().length() <= 0 || mNumber.getText().length() <= 0) {
                    break;
                } else {
//                    showToast("正在等待同意您的请求", Toast.LENGTH_LONG,null);
                    settingLogic.sendBindReq(mNumber.getText().toString(), UserInfoCacheManager.getUserInfo(getApplicationContext()).getPhone());
                    progressLayout.setVisibility(View.VISIBLE);
                    progressTip.setText(R.string.sending_bind_request);

                }
                break;
            default:
                break;
        }
    }

}
