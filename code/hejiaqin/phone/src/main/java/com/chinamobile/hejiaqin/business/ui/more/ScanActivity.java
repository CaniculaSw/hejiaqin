package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.PhotoManage;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.component.log.Logger;
import com.customer.framework.component.qrCode.QRCodeDecoder;
import com.customer.framework.component.qrCode.ZXingView;
import com.customer.framework.component.qrCode.core.QRCodeView;
import com.customer.framework.utils.FileUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
                Intent getAlbum = new Intent(); // "android.intent.action.GET_CONTENT"
                if (Build.VERSION.SDK_INT < 19) {
                    getAlbum.setAction(Intent.ACTION_GET_CONTENT);
                } else {
                    getAlbum.setAction(Intent.ACTION_OPEN_DOCUMENT);
                }
                getAlbum.setType("image/*");
                Intent wrapperIntent = Intent.createChooser(getAlbum, "选择二维码图片");
                startActivityForResult(wrapperIntent, IMAGE_CODE);
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
                String photoPath = null;
                String[] proj = {MediaStore.Images.Media.DATA};
                // 获取选中图片的路径
                Cursor cursor = getContentResolver().query(data.getData(),
                        proj, null, null, null);

                if (cursor.moveToFirst()) {

                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    photoPath = cursor.getString(column_index);
                    if (photoPath == null) {
                        photoPath = PhotoManage.getPath(getApplicationContext(),
                                data.getData());
                        Logger.i(TAG, photoPath);
                    }
                    Logger.i(TAG, photoPath);

                }

                cursor.close();

                decodeQECodeFromAlbum(photoPath);
                break;
            default:
                break;
        }
    }

    private void decodeQECodeFromAlbum(String photoPath) {
        long photoSize = FileUtil.getFileSize(photoPath);
        if (photoSize <= 0) {
            showToast("图片异常", Toast.LENGTH_LONG, null);
            return;
        }
        Bitmap bm = null;

        if (photoSize > 1024 * 1024 * 10) {
            showToast("图片过大", Toast.LENGTH_LONG, null);
            return;
        }
        if (photoSize > 1024 * 1024 * 2) {
            InputStream is = null;
            try {
                is = new FileInputStream(photoPath);
            } catch (FileNotFoundException e) {
                Logger.e(TAG, "File not found", e);
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 10;   //width，hight设为原来的十分一
            bm = BitmapFactory.decodeStream(is, null, options);
        } else {
            bm = BitmapFactory.decodeFile(photoPath);
        }
        QRCodeDecoder.decodeQRCode(bm, this);
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
