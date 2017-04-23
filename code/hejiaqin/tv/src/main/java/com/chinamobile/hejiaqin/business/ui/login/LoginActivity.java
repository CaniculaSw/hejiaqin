package com.chinamobile.hejiaqin.business.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Message;
import android.view.View;
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
import com.chinamobile.hejiaqin.business.ui.main.MainFragmentActivity;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.component.threadpool.ThreadPoolUtil;
import com.customer.framework.component.threadpool.ThreadTask;
import com.customer.framework.component.qrcode.QRCodeEncoder;
import com.customer.framework.component.qrcode.core.DisplayUtils;
import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.log.LogApi;
/**
 * Created by Administrator on 2015/12/21.
 */

public class LoginActivity extends BasicActivity implements View.OnClickListener {
    ImageButton qrCode;
    TextView tvAccount;
    LinearLayout loginBtn;
    ILoginLogic loginLogic;
    private boolean logining;
    IVoipLogic mVoipLogic;
    RegistingDialog registingDialog;

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class);
        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        qrCode = (ImageButton) findViewById(R.id.qrCode);
        tvAccount = (TextView) findViewById(R.id.account);
        tvAccount.setText(UserInfoCacheManager.getTvAccount(getApplicationContext()));
        loginBtn = (LinearLayout) findViewById(R.id.login_ll);
        registingDialog = new RegistingDialog(this, R.style.CalendarDialog);
    }

    @Override
    protected void initDate() {
        createQRCode(BussinessConstants.Login.DOWNLOAD_URL, 210, qrCode);
    }

    @Override
    protected void initListener() {
        loginBtn.setOnClickListener(this);

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
                Intent intent = new Intent(LoginActivity.this, MainFragmentActivity.class);
                mVoipLogic.setNotNeedVoipLogin();
                registingDialog.dismiss();
                UserInfoCacheManager.clearTvIsLogout(getApplicationContext());
                this.startActivity(intent);
                this.finishAllActivity(MainFragmentActivity.class.getName());
                break;
            case BussinessConstants.LoginMsgID.LOGIN_FAIL_MSG_ID:
                loginBtn.setFocusable(true);
                loginBtn.requestFocus();
                registingDialog.dismiss();
                FailResponse response = (FailResponse) msg.obj;
                showUpdateDialog(response.getMsg());
                logining = false;
                break;
            case BussinessConstants.CommonMsgId.LOGIN_NETWORK_ERROR_MSG_ID:
                showToast(R.string.network_error_tip, Toast.LENGTH_SHORT, null);
                logining = false;
                loginBtn.setFocusable(true);
                registingDialog.dismiss();
                loginBtn.requestFocus();
                break;
            case BussinessConstants.DialMsgID.VOIP_REGISTER_NET_UNAVAILABLE_MSG_ID:
                if (logining) {
                    logining = false;
                }
                loginBtn.setFocusable(true);
                registingDialog.dismiss();
                loginBtn.requestFocus();
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
                loginBtn.setFocusable(true);
                registingDialog.dismiss();
                loginBtn.requestFocus();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_ll:
                login();
                break;
            default:
                break;
        }

    }

    private void login() {
        registingDialog.show("正在登录，请稍后...");
        TvLoginInfo loginInfo = new TvLoginInfo();
        loginInfo.setTvId(UserInfoCacheManager.getTvUserID(this));
        loginInfo.setTvToken(loginLogic.encryPassword(UserInfoCacheManager.getTvToken(this)));
        loginLogic.tvLogin(loginInfo);
    }


    private void createQRCode(String url, int size, final ImageView view) {
        QRCodeEncoder.encodeQRCode(url, DisplayUtils.dp2px(this, size), Color.parseColor("#000000"), Color.parseColor("#ffffff"), new QRCodeEncoder.Delegate() {
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

    private void showUpdateDialog(String text) {
        UpdateDialog.show(this, text, true);
//        finish();
    }
}

