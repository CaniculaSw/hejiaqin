package com.chinamobile.hejiaqin.business.ui.setting.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.basic.MyActivityManager;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.component.qrCode.QRCodeEncoder;
import com.customer.framework.component.qrCode.core.DisplayUtils;
import com.customer.framework.utils.LogUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by eshaohu on 16/8/30.
 */
public class BoxAccountFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "BoxAccountFragment";
    private ILoginLogic loginLogic;
    private ISettingLogic settingLogic;
    private IVoipLogic mVoipLogic;
    TextView boxAccount;
    TextView password;
    TextView bindedAccount;
    ImageView qrCode;
    Button logout;

    @Override
    protected void initLogics() {
        super.initLogics();
        loginLogic = (ILoginLogic) getLogicByInterfaceClass(ILoginLogic.class);
        mVoipLogic = (IVoipLogic) getLogicByInterfaceClass(IVoipLogic.class);
        settingLogic = (ISettingLogic) getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {
        switch (msg.what) {
            case BussinessConstants.SettingMsgID.GET_BIND_LIST_SUCCESSFUL:
                if (msg.obj != null) {
                    List<UserInfo> userInfoList = new ArrayList<UserInfo>();
                    userInfoList.addAll((List<UserInfo>) msg.obj);
                    StringBuilder sb = new StringBuilder();
                    Iterator<UserInfo> iterator = userInfoList.iterator();
                    while (iterator.hasNext()) {
                        UserInfo bindedUser = iterator.next();
                        sb.append(bindedUser.getPhone());
                        sb.append(" ");
                    }

                    bindedAccount.setText(sb.toString());
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_box_account;
    }

    @Override
    protected void initView(View view) {
        HeaderView headerView = (HeaderView) view.findViewById(R.id.title);
        headerView.title.setText(getString(R.string.box_account));
        boxAccount = (TextView) view.findViewById(R.id.box_account_tv);
        password = (TextView) view.findViewById(R.id.password_tv);
        bindedAccount = (TextView) view.findViewById(R.id.binded_account_tv);
        qrCode = (ImageView) view.findViewById(R.id.account_qr_code_iv);
        logout = (Button) view.findViewById(R.id.logout_btn);
        logout.setOnClickListener(this);

    }

    private void createQRCode(String url, int size, final ImageView view) {
        QRCodeEncoder.encodeQRCode(url, DisplayUtils.dp2px(getActivity(), size), Color.parseColor("#000000"), Color.parseColor("#878ea3"), new QRCodeEncoder.Delegate() {
            @Override
            public void onEncodeQRCodeSuccess(Bitmap qrCode) {
                view.setImageBitmap(qrCode);
            }

            @Override
            public void onEncodeQRCodeFailure() {
                LogUtil.e(TAG, "生成二维码失败");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        settingLogic.getBindList();
    }

    @Override
    protected void initData() {
        UserInfo userInfo = (UserInfo) UserInfoCacheManager.getUserInfo(getContext());
        if (userInfo != null) {
            boxAccount.setText(userInfo.getTvAccount());
            String tvAccount = userInfo.getSdkAccount();
            //TODO:使用临时代码
//            if(Integer.parseInt(tvAccount.substring(tvAccount.length() - 1)) % 2 == 0) {
//                tvAccount = "2886544004";
//            }else{
//                tvAccount = "2886544005";
//            }
            LogUtil.d(TAG, "sdk account: " + tvAccount);
            createQRCode(tvAccount, 1400, qrCode);
        }
//        createQRCode("13776570335", 1400, qrCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout_btn:
                loginLogic.logout();
                mVoipLogic.logout();
                mVoipLogic.clearLogined();
                FragmentMgr.resetFragmentMgr();
//                Intent intent = new Intent(getContext(), LoginActivity.class);
//                startActivity(intent);
                MyActivityManager.getInstance().finishAllActivity(null);
                break;
            default:
                break;
        }

    }

    public View getFirstFouseView() {
        return logout;
    }
}
