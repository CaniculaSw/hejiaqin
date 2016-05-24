package com.chinamobile.hejiaqin.business.ui.more;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.more.IMoreLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by eshaohu on 16/5/24.
 */
public class ShareAppActivity extends BasicActivity implements View.OnClickListener{
    ImageView qrCodeIv;
    HeaderView headerView;
    IMoreLogic moreLogic;
    Bitmap qrCode;
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
        qrCode = moreLogic.createQRImage(BussinessConstants.Setting.MORE_SHARE_APP_URL,200,200);
        if(qrCode != null){
            qrCodeIv.setImageBitmap(qrCode);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initLogics() {
        moreLogic = (IMoreLogic) this.getLogicByInterfaceClass(IMoreLogic.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            default:
                break;
        }
    }
}
