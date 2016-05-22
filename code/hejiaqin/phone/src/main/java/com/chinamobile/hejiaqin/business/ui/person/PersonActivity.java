package com.chinamobile.hejiaqin.business.ui.person;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.person.IPersonLogic;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.person.PersonInfo;
import com.chinamobile.hejiaqin.business.model.person.PersonalDocument;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.dialog.PhotoManage;
import com.chinamobile.hejiaqin.business.ui.basic.view.CustomDialog;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.basic.view.MyDatePicker;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.customer.framework.utils.StringUtil;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/4/28.
 */
public class PersonActivity extends BasicActivity implements View.OnClickListener {

    private static final String TAG = "PersonActivity";

    private IPersonLogic personLogic;

    private HeaderView headerView;
    private ImageView iv_head;                  //头像
    private EditText tv_name;                   //昵称
    private TextView tv_sex;                    //性别
    private TextView tv_birth_date;             //出生日期
    private TextView tv_preference;             //偏好
    private EditText tv_autograph;              //签名
    private Button right_btn;
    private String preferIds = "";                   //偏好ids

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_info;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView) findViewById(R.id.header_view);
        headerView.title.setText(R.string.person_information);
        headerView.backImageView.setImageResource(R.mipmap.back);
        iv_head = (ImageView) findViewById(R.id.img_head);
        tv_name = (EditText) findViewById(R.id.tv_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_birth_date = (TextView) findViewById(R.id.tv_birth_date);
        tv_preference = (TextView) findViewById(R.id.tv_preference);
        tv_autograph = (EditText) findViewById(R.id.tv_autograph);
        right_btn = (Button) findViewById(R.id.right_btn);
        right_btn.setTextColor(Color.WHITE);
        right_btn.setText(R.string.person_information_confirm_to_modify);
    }

    @Override
    protected void initDate() {
        personLogic.loadPersonInfo();
        UserInfo info = CommonUtils.getLocalUserInfo();
        if (info != null) {
            setUpView(info);
        }

    }

    @Override
    protected void initListener() {
        //设置拍照监听
        PhotoManage.getInstance(this).setPhotoListener(new ImagePhotoChangeListener());
        headerView.backLayout.setOnClickListener(this);
        tv_preference.setOnClickListener(this);
        tv_autograph.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        tv_birth_date.setOnClickListener(this);
        right_btn.setOnClickListener(this);
        iv_head.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backLayout:
                finish();   //回退
                break;
            case R.id.tv_preference:
                Intent intent = new Intent(PersonActivity.this, ChoosePreferActivity.class);
                intent.putExtra(BussinessConstants.Person.INTENT_CHECKED_PREFER_IDS, preferIds);
                startActivityForResult(intent, BussinessConstants.ActivityRequestCode.CHOOSE_PREFER_REQUEST_CODE);
                break;
            case R.id.tv_autograph:
                break;
            case R.id.tv_name:
                break;
            case R.id.tv_birth_date:
                showDataPickerDialog();
                break;
            case R.id.right_btn:
                final PersonalDocument personalDocument = new PersonalDocument();
                personalDocument.setName(StringUtil.isNullOrEmpty(tv_name.getText().toString()) ? "" : tv_name.getText().toString());
                // personalDocument.setAvatar();
                personalDocument.setBirthday(StringUtil.isNullOrEmpty(tv_birth_date.getText().toString()) ? "" : tv_birth_date.getText().toString());
                personalDocument.setMotto(StringUtil.isNullOrEmpty(tv_autograph.getText().toString()) ? "" : tv_autograph.getText().toString());
                personalDocument.setPreferIds(preferIds);
                personalDocument.setPrefers(StringUtil.isNullOrEmpty(tv_preference.getText().toString()) ? "" : tv_preference.getText().toString());
                personLogic.changePersonalDoc(personalDocument);
                break;
            case R.id.img_head:
                //点击头像
                PhotoManage.getInstance(PersonActivity.this).showDialog();
                break;
        }
    }

    private void showDataPickerDialog() {
        String dateStr = tv_birth_date.getText().toString().trim();
        Configuration cfg = this.getResources().getConfiguration();
        Locale locale = cfg.locale;
        Calendar mCurrentDate =Calendar.getInstance(locale);
        mCurrentDate.setTimeInMillis(System.currentTimeMillis());
        int year = mCurrentDate.get(Calendar.YEAR);
        int month = mCurrentDate.get(Calendar.MONTH);
        int day =  mCurrentDate.get(Calendar.DAY_OF_MONTH);
        if (!StringUtil.isNullOrEmpty(dateStr)) {
            String[] ds = dateStr.split("-");
            if (ds.length >= 3) {
                try {
                    year = Integer.parseInt(ds[0]);
                    month = Integer.parseInt(ds[1])-1;
                    day = Integer.parseInt(ds[2]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_date_layout, null);
        final MyDatePicker datePicker = (MyDatePicker) view.findViewById(R.id.dp_date);
        datePicker.init(year, month, day, new MyDatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(MyDatePicker view, int year, int monthOfYear, int dayOfMonth) {
            }
        });
        CustomDialog.Builder dialog = new CustomDialog.Builder(this);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setTitle(R.string.person_information_date_picker)
                .setNegativeButton(R.string.person_information_dialog_cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton(R.string.person_information_dialog_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                tv_birth_date.setText(datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth());
                dialog.dismiss();
            }
        });
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initLogics() {
        personLogic = (IPersonLogic) this.getLogicByInterfaceClass(IPersonLogic.class);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.PersonMsgID.GET_PERSON_INFO_SUCCESS_MSG_ID:
                if (msg.obj != null) {
                    PersonInfo personInfo = (PersonInfo) msg.obj;
                    setUpView(personInfo);
                }
                break;
            case BussinessConstants.PersonMsgID.GET_PERSON_INFO_FAIL_MSG_ID:
                showToast(R.string.not_person_info, Toast.LENGTH_SHORT, null);
                break;
            case BussinessConstants.PersonMsgID.CHANGE_PERSONAL_DOC_SUCCESS_MSG_ID:
                showToast(R.string.person_information_modify_success, Toast.LENGTH_SHORT, null);
                break;
            case BussinessConstants.PersonMsgID.CHANGE_PERSONAL_DOC_FAIL_MSG_ID:
                showToast(R.string.person_information_modify_fail, Toast.LENGTH_SHORT, null);
                break;
            case BussinessConstants.PersonMsgID.UPLOAD_USER_AVATAR_IMAGE_SUCCESS_MSG_ID:
                if (msg.obj != null) {
                    String avatarPath = (String) msg.obj;
                    Picasso.with(this).invalidate(avatarPath);
                    Picasso.with(this).load(avatarPath).into(iv_head);
                }
                break;
            case BussinessConstants.PersonMsgID.UPLOAD_USER_AVATAR_IMAGE_FAIL_MSG_ID:
                    showToast(R.string.person_update_header_fail, Toast.LENGTH_SHORT, null);
            default:
                break;
        }
    }

    private void setUpView(PersonInfo personInfo) {
        if (personInfo != null) {
            if (!StringUtil.isNullOrEmpty(personInfo.getAvatar())) {
                Picasso.with(this).load(personInfo.getAvatar()).placeholder(R.mipmap.default_avatar).into(iv_head);
            } else {
                iv_head.setImageResource(R.mipmap.default_avatar);
            }
            tv_name.setText(StringUtil.isNullOrEmpty(personInfo.getName()) ? "" : personInfo.getName());
            String sex = personInfo.getSex();
            if (BussinessConstants.DictInfo.SEX_MALE.equals(sex)) {
                tv_sex.setText(R.string.person_information_sex_male);
            } else {
                tv_sex.setText(R.string.person_information_sex_female);
            }
            tv_birth_date.setText((StringUtil.isNullOrEmpty(personInfo.getBirthday()) ? "" : personInfo.getBirthday()));
            preferIds = personInfo.getPreferIds();
            tv_preference.setText((StringUtil.isNullOrEmpty(personInfo.getPrefer()) ? "" : personInfo.getPrefer()));
            tv_autograph.setText((StringUtil.isNullOrEmpty(personInfo.getMotto()) ? "" : personInfo.getMotto()));
        }
    }

    private void setUpView(UserInfo userInfo) {
        if (StringUtil.isNullOrEmpty(userInfo.getAvatar())) {
            iv_head.setImageResource(R.mipmap.default_avatar);
        } else {
            Picasso.with(this).load(userInfo.getAvatar()).placeholder(R.mipmap.default_avatar).into(iv_head);
        }
        tv_name.setText(StringUtil.isNullOrEmpty(userInfo.getName()) ? "" : userInfo.getName());
    }

    /**
     * 监听拍摄后得到照片信息
     */
    class ImagePhotoChangeListener implements PhotoManage.PhotoChangeListener {
        public void end(String url, Bitmap bitmap) {
            if (bitmap != null) {
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                iv_head.setImageBitmap(bitmap);
            }
            //上传头像
            updateUserHeader(url);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case PhotoManage.IMAGE_CODE:
                    PhotoManage.getInstance(PersonActivity.this).startPhotoZoom(data.getData());
                    break;
                case PhotoManage.CAMERA_CODE:
                    if (CommonUtils.hasSdcard()) {
                        File tempFile = new File(Environment.getExternalStorageDirectory() + BussinessConstants.Setting.APP_SAVE_PATH + BussinessConstants.Setting.APP_IMG_DEFAULT_NAME);
                        PhotoManage.getInstance(PersonActivity.this).startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        showToast(R.string.no_sdcard_update_header, 1, null);
                    }
                    break;
                case PhotoManage.CROP_CODE:
                    if (data != null) {
                        PhotoManage.getInstance(PersonActivity.this).sendPhotoEnd(data);
                    }
                    break;
            }
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case BussinessConstants.ActivityRequestCode.CHOOSE_PREFER_REQUEST_CODE:
                    preferIds = data.getStringExtra(BussinessConstants.Person.INTENT_CHECKED_PREFER_IDS);
                    String checkedPrefers = data.getStringExtra(BussinessConstants.Person.INTENT_CHECKED_PREFERS);
                    tv_preference.setText((StringUtil.isNullOrEmpty(checkedPrefers) ? "" : checkedPrefers));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUserHeader(String path) {
        if (StringUtil.isNullOrEmpty(path)) {
            return;
        }
        personLogic.updateUserHeader(path);
    }

}
