package com.chinamobile.hejiaqin.business.ui.more;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

import java.io.IOException;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by eshaohu on 16/6/1.
 */
public class ScanActivity extends BasicActivity implements View.OnClickListener, QRCodeView.Delegate, QRCodeDecoder.Delegate {
    private HeaderView mHeaderView;
    private QRCodeView mQRCodeView;
    private final static String TAG = "ScanActivity";
    public static final String IMAGE_TYPE = "image/*";
    public static final int IMAGE_CODE = 1;//相册

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initView() {
        mQRCodeView = (ZXingView) findViewById(R.id.more_zxingview);
        mHeaderView = (HeaderView) findViewById(R.id.more_scan_header);
        mHeaderView.title.setText(getString(R.string.more_bind_tv_dialog_by_scan));
        mHeaderView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);
        mHeaderView.backImageView.setClickable(true);
        //TODO Need update the icon
        mHeaderView.rightBtn.setImageResource(R.mipmap.more_icon_set_top_box);
        mHeaderView.rightBtn.setClickable(true);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        mQRCodeView.setResultHandler(this);
        mHeaderView.backImageView.setOnClickListener(this);
        mHeaderView.rightBtn.setOnClickListener(this);
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
            case R.id.right_btn:
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getAlbum.setType(IMAGE_TYPE);
                startActivityForResult(getAlbum, IMAGE_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_CODE:
                Bitmap bm = null;
                ContentResolver resolver = getContentResolver();
                try {

                    Uri originalUri = data.getData();  //获得图片的uri
                    bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                }     //显得到bitmap图片
                catch (IOException e) {
                    Log.e(TAG, e.toString());
                }
                decodeQECodeFromAlbum(bm);
                break;
            default:
                break;
        }
    }


    private void decodeQECodeFromAlbum(Bitmap bm) {
        QRCodeDecoder.decodeQRCode(bm,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mQRCodeView.startSpotAndShowRect();
    }

    @Override
    protected void onPause() {
        mQRCodeView.stopSpot();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQRCodeView.startSpotAndShowRect();
        //TODO finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


    @Override
    public void onDecodeQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQRCodeView.startSpotAndShowRect();
        //TODO finish();
    }

    @Override
    public void onDecodeQRCodeFailure() {
        Toast.makeText(this, "不能识别的图片二维码", Toast.LENGTH_SHORT).show();
    }
}
