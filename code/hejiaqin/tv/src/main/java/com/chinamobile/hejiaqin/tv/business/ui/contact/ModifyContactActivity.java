package com.chinamobile.hejiaqin.tv.business.ui.contact;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.tv.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.tv.business.ui.basic.dialog.PhotoManage;
import com.chinamobile.hejiaqin.tv.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;

import java.io.File;

public class ModifyContactActivity extends BasicActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_INPUT_NAME = 10001;

    private static final int REQUEST_CODE_INPUT_NUMBER = 10002;

    private static final int REQUEST_CODE_INPUT_PHOTO = 10003;

    private HeaderView titleLayout;

    private View headView;
    private ImageView headImg;

    private View nameView;

    private View numberView;

    /**
     * 当前支持两种模式:新增联系人和修改联系人;
     * 默认为新增联系人
     */
    private boolean addContactMode = true;

    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    @Override
    protected void initLogics() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_modify;
    }

    @Override
    protected void initView() {
        // title
        titleLayout = (HeaderView) findViewById(R.id.title);
        titleLayout.title.setText(R.string.contact_modify_title_add_text);
        titleLayout.rightBtn.setImageResource(R.mipmap.title_icon_check_nor);
        titleLayout.backImageView.setImageResource(R.mipmap.title_icon_back_nor);

        // 头像
        headView = findViewById(R.id.contact_head_layout);
        headImg = (ImageView) findViewById(R.id.contact_head_img);

        // 姓名
        nameView = findViewById(R.id.contact_name_layout);

        // 号码
        numberView = findViewById(R.id.contact_number_layout);
    }

    @Override
    protected void initDate() {
        PhotoManage.getInstance(this).setPhotoListener(mPhotoChangeListener);
    }

    @Override
    protected void initListener() {
        titleLayout.rightBtn.setOnClickListener(this);
        titleLayout.backImageView.setOnClickListener(this);

        headView.setOnClickListener(this);
        nameView.setOnClickListener(this);
        numberView.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_btn:
                doClickSubmit();
                break;
            case R.id.back_iv:
                doBack();
                break;
            case R.id.contact_head_layout:
                doClickHeadLayout();
                break;
            case R.id.contact_name_layout:
                doClickNameLayout();
                break;
            case R.id.contact_number_layout:
                doClickNumberLayout();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    private void doClickSubmit() {

    }

    private void doClickHeadLayout() {
        PhotoManage.getInstance(this).showDialog();
    }

    private void doClickNameLayout() {
        Intent intent = new Intent(this, InputInfoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_INPUT_NAME);
    }

    private void doClickNumberLayout() {
        Intent intent = new Intent(this, InputInfoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_INPUT_NUMBER);
    }


    /**
     * 监听拍摄后得到照片信息
     */
    private PhotoManage.PhotoChangeListener mPhotoChangeListener = new PhotoManage.PhotoChangeListener() {

        @Override
        public void end(String url, Bitmap bitmap) {
            if (bitmap != null) {
                headImg.setImageBitmap(bitmap);
            }
            // TODO 上传头像
        }
    };
}
