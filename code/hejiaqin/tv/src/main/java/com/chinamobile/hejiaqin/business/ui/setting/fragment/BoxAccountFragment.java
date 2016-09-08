package com.chinamobile.hejiaqin.business.ui.setting.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.component.qrCode.QRCodeEncoder;
import com.customer.framework.component.qrCode.core.DisplayUtils;
import com.customer.framework.utils.LogUtil;

/**
 * Created by eshaohu on 16/8/30.
 */
public class BoxAccountFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "BoxAccountFragment";
    TextView boxAccount;
    TextView password;
    TextView bindedAccount;
    ImageView qrCode;
    Button logout;

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {

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
    protected void initData() {
        createQRCode("13776570335", 1400, qrCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout_btn:
                LogUtil.i(TAG, "will logout!");
                break;
            default:
                break;
        }

    }
}
