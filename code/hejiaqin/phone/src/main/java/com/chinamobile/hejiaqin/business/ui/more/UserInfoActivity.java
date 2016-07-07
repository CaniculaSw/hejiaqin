package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.UpdatePhotoReq;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.PhotoManage;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.utils.LogUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.ref.WeakReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by eshaohu on 16/5/29.
 */
public class UserInfoActivity extends BasicActivity implements View.OnClickListener {
    private CircleImageView mUserAvatarIv;
    private TextView mUserAccountTv;
    private UserInfo userInfo;
    private ILoginLogic loginLogic;
    HeaderView header;

    private static final String TAG = "UserInfoActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_profile;
    }

    @Override
    protected void initView() {
        mUserAvatarIv = (CircleImageView) findViewById(R.id.more_user_avatar_ci);
        mUserAccountTv = (TextView) findViewById(R.id.more_user_account_tv);
        header = (HeaderView) findViewById(R.id.more_user_info_header);

        mUserAccountTv.setClickable(true);
        mUserAvatarIv.setOnClickListener(this);

        header.title.setText(getString(R.string.more_user_info));
        header.backImageView.setImageResource(R.mipmap.title_icon_back_nor);
        header.backImageView.setClickable(true);
        header.backImageView.setOnClickListener(this);
    }

    @Override
    protected void initDate() {
        userInfo = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
        if (null != userInfo){
            mUserAccountTv.setText(userInfo.getUserName());
            Picasso.with(UserInfoActivity.this.getApplicationContext())
                    .load(BussinessConstants.ServerInfo.HTTP_ADDRESS + "/" + userInfo.getPhotoSm())
                    .placeholder(R.drawable.contact_photo_default)
                    .error(R.drawable.contact_photo_default).into(mUserAvatarIv);
            //userAvatarIv.setImageResource(R.drawable.contact_photo_default);
        }
    }

    @Override
    protected void initListener() {
        PhotoManage.getInstance(this).setPhotoListener(mPhotoChangeListener);
    }

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null){
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PhotoManage.IMAGE_CODE:
                PhotoManage.getInstance(this).startPhotoZoom(data.getData());
                break;
            case PhotoManage.CAMERA_CODE:
                if (CommonUtils.hasSdcard()) {
                    File tempFile = new File(Environment.getExternalStorageDirectory() + BussinessConstants.Setting.APP_SAVE_PATH + BussinessConstants.Setting.APP_IMG_DEFAULT_NAME);
                    PhotoManage.getInstance(this).startPhotoZoom(Uri.fromFile(tempFile));
                } else {
                    showToast(R.string.no_sdcard_update_header, 1, null);
                }
                break;
            case PhotoManage.CROP_CODE:
                if (data != null) {
                    PhotoManage.getInstance(this).sendPhotoEnd(data);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PhotoManage.getInstance(this).setPhotoListener(null);
        PhotoManage.getInstance(this).removeContext();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.more_user_avatar_ci:
                changeAvatar();
            default:
                break;
        }
    }

    private void changeAvatar(){
        PhotoManage.getInstance(this).showDialog();
    }


    private Bitmap convertToBitmap(String url, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;

        BitmapFactory.decodeFile(url, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.0f;
        float scalHeight = 0.0f;

        if (width > w || height > h) {
            scaleWidth = ((float) width) / w;
            scalHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scalHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(url, opts));

        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    /**
     * 监听拍摄后得到照片信息
     */
    private PhotoManage.PhotoChangeListener mPhotoChangeListener = new PhotoManage.PhotoChangeListener() {

        @Override
        public void end(String url, Bitmap bitmap) {
            if (bitmap != null) {
                mUserAvatarIv.setImageBitmap(bitmap);
            }
            LogUtil.i(TAG,"Avatar file is: "+ url);
            UpdatePhotoReq req = new UpdatePhotoReq();
            req.setToken(userInfo.getToken());
            req.setFile(url);
            loginLogic.updatePhoto(req);
        }
    };
}
