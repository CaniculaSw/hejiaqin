package com.chinamobile.hejiaqin.business.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.login.LoginActivity;
import com.chinamobile.hejiaqin.business.ui.main.MainFragmentActivity;
import com.customer.framework.utils.LogUtil;

public class MainActivity extends BasicActivity {

    private ILoginLogic loginLogic;
    private ISettingLogic settingLogic;
    private static final String TAG = "MainActivity";

//    RelativeLayout mNavigatorLay;
//    LinearLayout mContactsLay, mDialLay, mSettingLay;

    View mNavigatorLay, mContactsLay, mDialLay, mSettingLay;
    TextView[] mTextViews = new TextView[3];

    ImageView[] mImageViews = new ImageView[3];
    final int mContactsIndex = 0;

    final int mDialIndex = 1;

    final int mSettingIndex = 2;

    int[] mImageSelectedBgResId = new int[3];

    int[] mImageUnSelectedBgResId = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jumpToMainFragmentActivity();
//        if (loginLogic.hasLogined()) {
//            jumpToMainFragmentActivity();
//        } else {
//            jumpToLoginActivity();
//        }

//        mNavigatorLay = (RelativeLayout) findViewById(R.id.main_bottom);
//        mContactsLay = (LinearLayout) findViewById(R.id.contact_layout);
//        mDialLay = (LinearLayout) findViewById(R.id.dial_layout);
//        mSettingLay = (LinearLayout) findViewById(R.id.more_layout);
//
//        mTextViews[mContactsIndex] = (TextView) findViewById(R.id.contact_text);
//        mTextViews[mDialIndex] = (TextView) findViewById(R.id.dial_text);
//        mTextViews[mSettingIndex] = (TextView) findViewById(R.id.more_text);
//
//        mImageViews[mContactsIndex] = (ImageView) findViewById(R.id.contact_image);
//        mImageViews[mDialIndex] = (ImageView) findViewById(R.id.dial_image);
//        mImageViews[mSettingIndex] = (ImageView) findViewById(R.id.more_image);
//
//        mImageSelectedBgResId[mContactsIndex] = R.mipmap.main_navigation_selected_contact;
//        mImageSelectedBgResId[mDialIndex] = R.mipmap.main_navigation_selected_dial_show;
//        mImageSelectedBgResId[mSettingIndex] = R.mipmap.main_navigation_selected_more;
//
//        mImageUnSelectedBgResId[mContactsIndex] = R.mipmap.main_navigation_unselected_contact;
//        mImageUnSelectedBgResId[mDialIndex] = R.mipmap.main_navigation_unselected_dial;
//        mImageUnSelectedBgResId[mSettingIndex] = R.mipmap.main_navigation_unselected_more;
//
//        mTextViews[mContactsIndex].setTextColor(getResources().getColor(R.color.navigation_selected));
//        mImageViews[mContactsIndex].setBackgroundResource(mImageSelectedBgResId[mContactsIndex]);
//
//        mContactsLay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    mTextViews[mContactsIndex].setTextColor(getResources().getColor(R.color.navigation_selected));
//                    mImageViews[mContactsIndex].setBackgroundResource(mImageSelectedBgResId[mContactsIndex]);
//                } else {
//                    mTextViews[mContactsIndex].setTextColor(getResources().getColor(R.color.navigation_unselected));
//                    mImageViews[mContactsIndex].setBackgroundResource(mImageUnSelectedBgResId[mContactsIndex]);
//                }
//            }
//        });
//
//        mDialLay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    mTextViews[mDialIndex].setTextColor(getResources().getColor(R.color.navigation_selected));
//                    mImageViews[mDialIndex].setBackgroundResource(mImageSelectedBgResId[mContactsIndex]);
//                } else {
//                    mTextViews[mDialIndex].setTextColor(getResources().getColor(R.color.navigation_unselected));
//                    mImageViews[mDialIndex].setBackgroundResource(mImageUnSelectedBgResId[mDialIndex]);
//                }
//            }
//        });
//
//        mSettingLay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    mTextViews[mSettingIndex].setTextColor(getResources().getColor(R.color.navigation_selected));
//                    mImageViews[mSettingIndex].setBackgroundResource(mImageSelectedBgResId[mContactsIndex]);
//                } else {
//                    mTextViews[mSettingIndex].setTextColor(getResources().getColor(R.color.navigation_unselected));
//                    mImageViews[mSettingIndex].setBackgroundResource(mImageUnSelectedBgResId[mSettingIndex]);
//                }
//            }
//        });
//
//        mContactsLay.requestFocus();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class);
        settingLogic = (ISettingLogic) super.getLogicByInterfaceClass(ISettingLogic.class);
    }

    private void jumpToMainFragmentActivity() {
        Intent intent = new Intent(MainActivity.this, MainFragmentActivity.class);
        LogUtil.d(TAG, "Init the CMIM SDK");
        loginLogic.initCMIMSdk();
        settingLogic.checkVersion();
        startActivity(intent);
        finish();
    }

    private void jumpToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
