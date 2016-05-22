package com.chinamobile.hejiaqin.business.ui.setting;

import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * 需求反馈页面
 * Created by wubg on 2016/5/5.
 */
public class FeedbackActivity extends BasicActivity implements View.OnClickListener {

    private ISettingLogic settingLogic;

    private HeaderView headerView;
    private EditText feedbackContentEt;
    private Button submitButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView)findViewById(R.id.header_view);
        headerView.title.setText(R.string.feedback_header_title);
        headerView.backImageView.setImageResource(R.mipmap.back);
        feedbackContentEt = (EditText) findViewById(R.id.feedback_content_edit_tx);
        submitButton = (Button) findViewById(R.id.submit_action_button);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initLogics() {
        settingLogic = (ISettingLogic) this.getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    protected void initListener() {
        headerView.backLayout.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:   //回退
                finish();
                break;
            case R.id.submit_action_button:
                submitFeedback();
                break;
        }
    }

    private void submitFeedback() {
        String feedbackContent = feedbackContentEt.getText().toString();
        if (TextUtils.isEmpty(feedbackContent.trim())) {
            return;
        }
        settingLogic.submitUserFeedback(feedbackContent);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.SettingMsgID.SUBMIT_USER_FEEDBACK_SUCCESS_MSG_ID:
                super.showToast(R.string.submit_feedback_success, Toast.LENGTH_SHORT, null);
                finish();
                break;
            case BussinessConstants.SettingMsgID.SUBMIT_USER_FEEDBACK_FAIL_MSG_ID:
                break;
        }
    }
}
