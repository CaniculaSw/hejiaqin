package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.FailResponse;
import com.chinamobile.hejiaqin.business.model.login.req.TvLoginInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.RegistDialog;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.RegistingDialog;
import com.chinamobile.hejiaqin.business.ui.login.dialog.ServiceContractDialog;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.component.qrcode.QRCodeEncoder;
import com.customer.framework.component.qrcode.core.DisplayUtils;
import com.customer.framework.utils.LogUtil;

/**
 * Created by eshaohu on 17/4/14.
 */
public class CreateAccountActivity extends BasicActivity implements View.OnClickListener {
    CheckBox checkBox;
    LinearLayout registerBtn;
    TextView contractContent;
    ILoginLogic loginLogic;
    private boolean logining;
    IVoipLogic mVoipLogic;
    ImageButton qrCode;
    private RegistingDialog registingDialog;
    private String phone;

    //
    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_account;
    }

    @Override
    protected void initView() {
        checkBox = (CheckBox) findViewById(R.id.read);
        contractContent = (TextView) findViewById(R.id.contract_content);
        registerBtn = (LinearLayout) findViewById(R.id.register_ll);
        registingDialog = new RegistingDialog(this, R.style.CalendarDialog);
        qrCode = (ImageButton) findViewById(R.id.qrCode);

    }

    @Override
    protected void initDate() {
        createQRCode(BussinessConstants.Login.DOWNLOAD_URL, 210, qrCode);
    }

    @Override
    protected void initListener() {
        contractContent.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.TV_REGIST_OK:
                registingDialog.dismiss();
                showDialog(getString(R.string.message_send), true);
                break;
            case BussinessConstants.LoginMsgID.TV_REGIST_FAILED:
                registingDialog.dismiss();
                FailResponse info = (FailResponse) msg.obj;
                showDialog(info.getMsg(), false);
                break;
            case BussinessConstants.LoginMsgID.JUMP_TO_LOGIN_ACTIVITY:
                jumpToLoginActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contract_content:
                showContract();
                break;
            case R.id.register_ll:
                if (checkBox.isChecked()) {
                    doRegister();
                } else {
                    showToast("请先阅读并同意《服务条款》", Toast.LENGTH_LONG, null);
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class);
//        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    private void autoLogin() {
        TvLoginInfo loginInfo = new TvLoginInfo();
        loginInfo.setTvId(UserInfoCacheManager.getTvUserID(this));
//        loginInfo.setTvToken(loginLogic.encryPassword(UserInfoCacheManager.getTvToken(this)));
        loginLogic.tvLogin(loginInfo);
    }

    private void showContract() {
        ServiceContractDialog.show(this);
    }

    private void doRegister() {
        registerBtn.setFocusable(false);
        loginLogic.tvRegist(UserInfoCacheManager.getTvUserID(getApplicationContext()));
        registingDialog.show();
    }

    private void showDialog(String text, boolean isOK) {
        RegistDialog.show(this, text, false, isOK);
    }

    private void jumpToLoginActivity(){
        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
        startActivity(intent);
        finishAllActivity(LoginActivity.class.getName());
    }

    private void createQRCode(String url, int size, final ImageView view) {
        QRCodeEncoder.encodeQRCode(url, DisplayUtils.dp2px(this, size),
                Color.parseColor("#000000"), Color.parseColor("#ffffff"),
                new QRCodeEncoder.Delegate() {
                    @Override
                    public void onEncodeQRCodeSuccess(Bitmap qrCode) {
                        view.setImageBitmap(qrCode);
                    }

                    @Override
                    public void onEncodeQRCodeFailure() {
                        LogUtil.e(tag, "生成中文二维码失败");
                    }
                });
    }
}
