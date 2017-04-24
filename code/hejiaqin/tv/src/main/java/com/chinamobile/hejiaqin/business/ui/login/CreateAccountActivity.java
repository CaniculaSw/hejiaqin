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
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.TvLoginInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.RegistingDialog;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.UpdateDialog;
import com.chinamobile.hejiaqin.business.ui.login.dialog.ServiceContractDialog;
import com.chinamobile.hejiaqin.business.ui.main.MainFragmentActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.component.qrcode.QRCodeEncoder;
import com.customer.framework.component.qrcode.core.DisplayUtils;
import com.customer.framework.component.threadpool.ThreadPoolUtil;
import com.customer.framework.component.threadpool.ThreadTask;
import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.log.LogApi;

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
            case BussinessConstants.LoginMsgID.LOGIN_SUCCESS_MSG_ID:

                UserInfo userInfo = UserInfoCacheManager.getUserInfo(getApplicationContext());

                com.huawei.rcs.login.UserInfo sdkuserInfo = new com.huawei.rcs.login.UserInfo();
                sdkuserInfo.countryCode = "";
                sdkuserInfo.username = userInfo.getSdkAccount();
                sdkuserInfo.password = userInfo.getSdkPassword();
                LogUtil.i(tag, "SDK username: " + sdkuserInfo.username);
                mVoipLogic.login(sdkuserInfo, null, null);
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_CONNECTED_MSG_ID:
                logining = true;
                Intent intent = new Intent(CreateAccountActivity.this, MainFragmentActivity.class);
                mVoipLogic.setNotNeedVoipLogin();
                registingDialog.dismiss();
                this.startActivity(intent);
                this.finishAllActivity(MainFragmentActivity.class.getName());
                break;
            case BussinessConstants.LoginMsgID.LOGIN_FAIL_MSG_ID:
                registerBtn.setFocusable(true);
                registerBtn.requestFocus();
                registingDialog.dismiss();
                //                showToast(R.string.voip_register_fail, Toast.LENGTH_LONG, null);
                FailResponse response = (FailResponse) msg.obj;
                showUpdateDialog(response.getMsg());
                logining = false;
                break;
            case BussinessConstants.CommonMsgId.LOGIN_NETWORK_ERROR_MSG_ID:
                showToast(R.string.network_error_tip, Toast.LENGTH_SHORT, null);
                logining = false;
                registerBtn.setFocusable(true);
                registingDialog.dismiss();
                registerBtn.requestFocus();
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_NET_UNAVAILABLE_MSG_ID:
                if (logining) {
                    logining = false;
                }
                registerBtn.setFocusable(true);
                registingDialog.dismiss();
                registerBtn.requestFocus();
                showToast(R.string.network_error_tip, Toast.LENGTH_SHORT, null);
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_DISCONNECTED_MSG_ID:
                if (logining) {
                    ThreadPoolUtil.execute(new ThreadTask() {

                        @Override
                        public void run() {
                            LogApi.copyLastLog();
                        }
                    });
                    logining = false;
                }
                registerBtn.setFocusable(true);
                registingDialog.dismiss();
                registerBtn.requestFocus();
                showToast(R.string.voip_register_fail, Toast.LENGTH_SHORT, null);
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
                    showToast("请同意服务条款", Toast.LENGTH_SHORT, null);
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class);
        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    private void autoLogin() {
        TvLoginInfo loginInfo = new TvLoginInfo();
        loginInfo.setTvId(UserInfoCacheManager.getTvUserID(this));
        loginInfo.setTvToken(loginLogic.encryPassword(UserInfoCacheManager.getTvToken(this)));
        loginLogic.tvLogin(loginInfo);
    }

    private void showContract() {
        ServiceContractDialog.show(this);
    }

    private void doRegister() {
        registerBtn.setFocusable(false);
        registingDialog.show();
        autoLogin();
    }

    private void showUpdateDialog(String text) {
        UpdateDialog.show(this, text, true);
        //        finish();
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
