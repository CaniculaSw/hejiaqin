package com.chinamobile.hejiaqin.business.ui.setting;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.component.qrCode.QRCodeEncoder;
import com.customer.framework.component.qrCode.core.DisplayUtils;

/**
 * Created by eshaohu on 16/5/24.
 */
public class ShareAppActivity extends BasicActivity implements View.OnClickListener {
    ImageView qrCodeIv;
    HeaderView headerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_app;
    }

    @Override
    protected void initView() {
        qrCodeIv = (ImageView) findViewById(R.id.more_qr_iv);
        headerView = (HeaderView) findViewById(R.id.more_share_app_header);
        headerView.title.setText("分享app");
        headerView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);
        headerView.backImageView.setClickable(true);
        headerView.backImageView.setOnClickListener(this);
    }

    @Override
    protected void initDate() {
        createQRCode(BussinessConstants.Setting.MORE_SHARE_APP_URL, 150);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initLogics() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            default:
                break;
        }
    }

    private void createQRCode(String url, int size) {
        QRCodeEncoder.encodeQRCode(url, DisplayUtils.dp2px(ShareAppActivity.this, size), Color.parseColor("#000000"), new QRCodeEncoder.Delegate() {
            @Override
            public void onEncodeQRCodeSuccess(Bitmap qrCode) {
                qrCodeIv.setImageBitmap(qrCode);
            }

            @Override
            public void onEncodeQRCodeFailure() {
                showToast("生成中文二维码失败", Toast.LENGTH_SHORT, null);
            }
        });
    }
}
