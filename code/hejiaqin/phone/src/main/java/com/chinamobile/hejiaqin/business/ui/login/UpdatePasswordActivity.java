package com.chinamobile.hejiaqin.business.ui.login;

import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.model.login.req.PasswordInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.utils.StringUtil;

/**
 * Created by Xiadong on 2016/5/9.
 */
public class UpdatePasswordActivity extends BasicActivity implements OnClickListener {

    private ILoginLogic loginLogic;

    private HeaderView headerView;
    private EditText originalPwdEditText;
    private EditText newPwdEditText;
    private EditText confirmPwdEditText;
    private Button submitButton;

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) this.getLogicByInterfaceClass(ILoginLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_password;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView)findViewById(R.id.header_view);
        headerView.title.setText(R.string.update_password_title);
        headerView.backImageView.setImageResource(R.mipmap.back);

        originalPwdEditText = (EditText) findViewById(R.id.original_pwd_edit_tx);
        newPwdEditText = (EditText) findViewById(R.id.new_pwd_edit_tx);
        confirmPwdEditText = (EditText) findViewById(R.id.confirm_pwd_edit_tx);
        submitButton = (Button) findViewById(R.id.submit_action_button);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        headerView.backLayout.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                //回退
                finish();
                break;
            case R.id.submit_action_button:
                updateUserPassword();
                break;
        }
    }

    private void updateUserPassword() {
        String originalPassword = originalPwdEditText.getText().toString();
        if (TextUtils.isEmpty(originalPassword)) {
            showToast(R.string.original_pwd_required, Toast.LENGTH_SHORT, null);
            originalPwdEditText.requestFocus();
            return;
        }

        String newPassword = newPwdEditText.getText().toString();
        if (TextUtils.isEmpty(newPassword)) {
            showToast(R.string.new_pwd_required, Toast.LENGTH_SHORT, null);
            newPwdEditText.requestFocus();
            return;
        }

        String confirmPassword = confirmPwdEditText.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            showToast(R.string.confirm_pwd_required, Toast.LENGTH_SHORT, null);
            confirmPwdEditText.requestFocus();
            return;
        }

        if (!StringUtil.equals(newPassword, confirmPassword)) {
            showToast(R.string.password_not_match, Toast.LENGTH_SHORT, null);
            confirmPwdEditText.requestFocus();
            return;
        }

        PasswordInfo pwdInfo = new PasswordInfo();
        pwdInfo.setOldpassword(originalPassword);
        pwdInfo.setNewpassword(newPassword);
        //TODO：显示等待的动画
        super.showWaitDailog();
        loginLogic.updatePassword(pwdInfo);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.UPDATE_PWD_SUCCESS_MSG_ID:
                //TODO:停止等待的动画
                super.dismissWaitDailog();
                showToast(R.string.update_password_success, Toast.LENGTH_SHORT, null);
                this.finish();
                break;
            case BussinessConstants.LoginMsgID.UPDATE_PWD_FAIL_MSG_ID:
                //TODO:停止等待的动画
                super.dismissWaitDailog();
                showToast(msg.obj.toString(), Toast.LENGTH_SHORT, null);
                break;
            default:
                break;
        }
    }
}
