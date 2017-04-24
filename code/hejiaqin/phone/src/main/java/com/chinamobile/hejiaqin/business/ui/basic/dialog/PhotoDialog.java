package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;

import java.io.File;

/***/

public class PhotoDialog extends Dialog {

    private Context mContext;

    public static final String IMAGE_TYPE = "image/*";
    public static final int IMAGE_CODE = 1;//相册
    public static final int CAMERA_CODE = 2;//相机
    public static final int CROP_CODE = 3;//裁剪

    //    private static PhotoDialog mPhotoDialog;

    private FragmentActivity mContext2;
    //    private TextView tvPhoto;
    //    private TextView tvCamera;
    //    private TextView tvDismiss;
    private RelativeLayout rlPhoto;
    private RelativeLayout rlCamera;
    private RelativeLayout rlDismiss;
    private ClickListener mClickListener;

    public PhotoDialog(Context context) {
        super(context);
        this.mContext = context;

    }

    public PhotoDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    protected PhotoDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo_layout);
        mClickListener = new ClickListener();
        rlPhoto = (RelativeLayout) findViewById(R.id.rl_photo);
        rlCamera = (RelativeLayout) findViewById(R.id.rl_camera);
        rlDismiss = (RelativeLayout) findViewById(R.id.rl_dismiss);
        rlPhoto.setOnClickListener(mClickListener);
        rlCamera.setOnClickListener(mClickListener);
        rlDismiss.setOnClickListener(mClickListener);

    }

    public void setFragmentActivity(FragmentActivity fragment) {
        this.mContext2 = fragment;
    }

    private void startActivity(Intent intent, int flag) {
        try {
            ((Activity) mContext).startActivityForResult(intent, flag);
        } catch (Exception e) {
            mContext2.startActivityForResult(intent, flag);
        }
    }

    class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_photo:
                    //打开相册
                    dismiss();
                    Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    getAlbum.setType(IMAGE_TYPE);
                    startActivity(getAlbum, IMAGE_CODE);
                    break;
                case R.id.rl_camera:
                    //相机功能
                    dismiss();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 判断存储卡是否可以用，可用进行存储
                    if (CommonUtils.hasSdcard()) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory()
                                + BussinessConstants.Setting.APP_SAVE_PATH,
                                BussinessConstants.Setting.APP_IMG_DEFAULT_NAME)));
                    }

                    startActivity(intent, CAMERA_CODE);
                    break;
                case R.id.rl_dismiss:
                    //取消键
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }
}
