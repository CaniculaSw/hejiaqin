package com.chinamobile.hejiaqin.business.ui.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.model.setting.UserSettingInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.login.UpdatePasswordActivity;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/3.
 */
public class SettingActivity extends BasicActivity implements View.OnClickListener {

    private ISettingLogic settingLogic;

    private HeaderView headerView;
    private SwitchButton mWifiSb;
    private SwitchButton mMsgSb;
    private TextView coachTextView;
    private RelativeLayout demonstrateCoachLayout;
    private RelativeLayout updatePwdLayout;
    private TextView availableMemorySizeTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        headerView =(HeaderView)findViewById(R.id.headView);
        headerView.title.setText(R.string.my_setting);
        headerView.backImageView.setImageResource(R.mipmap.back);

        mWifiSb = (SwitchButton) findViewById(R.id.sb_wifi_load);
        mMsgSb = (SwitchButton) findViewById(R.id.sb_msg);
        coachTextView = (TextView) findViewById(R.id.coach_gender_tv);
        demonstrateCoachLayout = (RelativeLayout) findViewById(R.id.demonstration_coach_layout);
        updatePwdLayout = (RelativeLayout) findViewById(R.id.update_pwd_layout);
        availableMemorySizeTv = (TextView) findViewById(R.id.available_memory_size);

        //内容存储的剩余空间
        availableMemorySizeTv.setText(Formatter.formatFileSize(this, CommonUtils.getAvailableInternalMemorySize()));
    }

    @Override
    protected void initDate() {
        loadingUserSetting();
    }

    @Override
    protected void initListener() {
        mWifiSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mWifiSb.setBackColorRes(R.color.backgroud_color);
                }else{
                    mWifiSb.setBackColorRes(R.color.switch_button_off);
                }
                UserSettingInfo settingInfo = new UserSettingInfo();
                settingInfo.setAutoDownloadIfWIFI(isChecked?1:0);
                amendUserSetting(settingInfo);
            }
        });

        mMsgSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mMsgSb.setBackColorRes(R.color.backgroud_color);
                }else{
                    mMsgSb.setBackColorRes(R.color.switch_button_off);
                }
                UserSettingInfo settingInfo = new UserSettingInfo();
                settingInfo.setOrderMessage(isChecked?1:0);
                amendUserSetting(settingInfo);
            }
        });

        headerView.backLayout.setOnClickListener(this);
        demonstrateCoachLayout.setOnClickListener(this);
        updatePwdLayout.setOnClickListener(this);
    }

    @Override
    protected void initLogics() {
        settingLogic = (ISettingLogic) this.getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                //回退
                finish();
                break;
            case R.id.demonstration_coach_layout:
                showGenderAlertDialog();
                break;
            case R.id.update_pwd_layout:
                Intent intent = new Intent(SettingActivity.this, UpdatePasswordActivity.class);
                this.startActivity(intent);
                break;
        }
    }

    private void loadingUserSetting() {
        settingLogic.loadingUserSetting();
    }

    private void showGenderAlertDialog() {
        String[] gender = {"男", "女"};
        AlertDialog alertDialog = new AlertDialog.Builder(this).
            setTitle(R.string.demonstration_dialog_title).
            setItems(gender, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which != -1) {
                        coachTextView.setText(which == 0?R.string.gender_male:R.string.gender_female);
                        UserSettingInfo settingInfo = new UserSettingInfo();
                        settingInfo.setCoach(which == 0? 1:0);
                        amendUserSetting(settingInfo);
                    }
                }
        }).create();
        alertDialog.show();
    }

    private void amendUserSetting(UserSettingInfo settingInfo) {
        settingLogic.amendUserSetting(settingInfo);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.SettingMsgID.LOAD_USER_SETTING_SUCCESS_MSG_ID:
                UserSettingInfo settingInfo = (UserSettingInfo) msg.obj;
                mWifiSb.setChecked(settingInfo.getAutoDownloadIfWIFI() == 1?true:false);
                mMsgSb.setChecked(settingInfo.getOrderMessage() == 1?true:false);
                coachTextView.setText(settingInfo.getCoach() == 1?R.string.gender_male: R.string.gender_female);
                break;
            case BussinessConstants.SettingMsgID.LOAD_USER_SETTING_FAIL_MSG_ID:
                break;
            case BussinessConstants.SettingMsgID.AMEND_USER_SETTING_SUCCESS_MSG_ID:
                break;
            case BussinessConstants.SettingMsgID.AMEND_USER_SETTING_FAIL_MSG_ID:
                break;
        }
    }

}
