package com.chinamobile.hejiaqin.business.ui.setting.fragment;

import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.logic.setting.SettingLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.setting.dialog.RecordingDialog;
import com.chinamobile.hejiaqin.tv.R;
import com.huawei.rcs.call.CallApi;

/**
 * Created by eshaohu on 17/3/6.
 */
public class CheckMicStatusFragment extends BasicFragment implements View.OnClickListener {
    HeaderView headerView;
    RelativeLayout normalLayout;
    RelativeLayout resultLayout;
    LinearLayout startRecording;
    ImageView icon;
    TextView resultTextLine1;
    TextView resultTextLine2;
    LinearLayout testAgainBtn;
    LinearLayout doneBtn;
    RecordingDialog dialog;

    ISettingLogic settingLogic;

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {
        switch (msg.what) {
            case BussinessConstants.SettingMsgID.CHECK_MIC_FINISHED:
                int inputVol = (int) msg.obj;
                showResult(CallApi.getCameraCount() > 0 && inputVol > 0);
                break;
            case BussinessConstants.DialMsgID.CALL_ON_TV_INCOMING_MSG_ID:
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
        }
    }

    @Override
    protected void initLogics() {
        super.initLogics();
        settingLogic = (SettingLogic) getLogicBuilder().getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_check_mic_status;
    }

    @Override
    protected void initView(View view) {
        headerView = (HeaderView) view.findViewById(R.id.title);
        headerView.title.setText(R.string.check_mic_status);
        normalLayout = (RelativeLayout) view.findViewById(R.id.normal_rl);
        startRecording = (LinearLayout) view.findViewById(R.id.start_ll);
        startRecording.setOnClickListener(this);
        resultLayout = (RelativeLayout) view.findViewById(R.id.result_rl);
        icon = (ImageView) view.findViewById(R.id.result_icon);
        resultTextLine1 = (TextView) view.findViewById(R.id.result_tips_line1);
        resultTextLine2 = (TextView) view.findViewById(R.id.result_tips_line2);
        testAgainBtn = (LinearLayout) view.findViewById(R.id.btn_test_again);
        testAgainBtn.setClickable(true);
        testAgainBtn.setOnClickListener(this);
        doneBtn = (LinearLayout) view.findViewById(R.id.btn_done);
        doneBtn.setClickable(true);
        doneBtn.setOnClickListener(this);

        startRecording.requestFocus();
        normalLayout.setVisibility(View.VISIBLE);
        resultLayout.setVisibility(View.GONE);
    }


    @Override
    protected void initData() {

    }

    public View getFirstFouseView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_ll:
            case R.id.btn_test_again:
                showRecordingDialog();
                break;
            case R.id.btn_done:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    private void showResult(boolean isNormal) {
        normalLayout.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);
        testAgainBtn.requestFocus();
        if (isNormal) {
            icon.setImageResource(R.drawable.icon_nomal);
            resultTextLine1.setText(R.string.mic_normal_line1);
            resultTextLine2.setText(R.string.mic_normal_line2);
        } else {
            icon.setImageResource(R.drawable.icon_abnormal);
            resultTextLine1.setText(R.string.mic_abnormal_line1);
            resultTextLine2.setText(R.string.mic_abnormal_line2);
        }
    }

    private void showRecordingDialog() {
        dialog = new RecordingDialog(this.getActivity(), R.style.CalendarDialog, settingLogic);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        dialog.show();
    }
}
