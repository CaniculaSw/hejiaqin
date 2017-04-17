package com.chinamobile.hejiaqin.business.ui.setting.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.component.qrCode.QRCodeEncoder;
import com.customer.framework.component.qrCode.core.DisplayUtils;
import com.customer.framework.utils.LogUtil;

/**
 * Created by eshaohu on 16/8/29.
 */
public class DownloadAppFragment extends BasicFragment {
    ImageView qrCodeAndroid;
    ImageView qrCodeIos;
    private static final String TAG = "DownloadAppFragment";
    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_download_app;
    }

    @Override
    protected void initView(View view) {
        HeaderView headerView = (HeaderView) view.findViewById(R.id.title);
        headerView.title.setText(getText(R.string.downlaod_app_title));
        qrCodeAndroid = (ImageView) view.findViewById(R.id.qr_code_android);
        qrCodeIos = (ImageView) view.findViewById(R.id.qr_code_ios);
//        qrCodeAndroid.setAlpha(150);
//        qrCodeIos.setAlpha(150);
    }

    @Override
    protected void initData() {
        createQRCode(BussinessConstants.Login.download_url_android,190,qrCodeAndroid);
        createQRCode(BussinessConstants.Login.download_url_ios,190,qrCodeIos);
    }

    private void createQRCode(String url, int size, final ImageView view) {
        QRCodeEncoder.encodeQRCode(url, DisplayUtils.dp2px(getActivity(), size), Color.parseColor("#000000"), Color.parseColor("#878ea3"), new QRCodeEncoder.Delegate() {
            @Override
            public void onEncodeQRCodeSuccess(Bitmap qrCode) {
                view.setImageBitmap(qrCode);
            }

            @Override
            public void onEncodeQRCodeFailure() {
                LogUtil.e(TAG, "生成中文二维码失败");
            }
        });
    }

    public View getFirstFouseView()
    {
        return null;
    }
}
