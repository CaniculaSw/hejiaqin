package com.chinamobile.hejiaqin.business.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragmentActivity;
import com.chinamobile.hejiaqin.business.ui.login.LoginActivity;
import com.chinamobile.hejiaqin.business.utils.BusProvider;

/**
 * desc: main
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/22.
 */
public class MainFragmentActivity extends BasicFragmentActivity {

    FragmentManager mFm;

    View mContactsLay;

    View mDialLay;

    View mSettingLay;

    BasicFragment[] mFragments = new BasicFragment[3];

    TextView[] mTextView = new TextView[3];

    ImageView[] mImageViewView = new ImageView[3];

    int[] mImageSelectedBgResId = new int[3];

    int[] mImageUnSelectedBgResId = new int[3];

    int mCurrentIndex;

    final int mContactsIndex = 0;

    final int mDialIndex = 1;

    final int mSettingIndex = 2;
//
//    int testValue = 1;

    private BasicFragment.BackListener listener = new BasicFragment.BackListener() {
        public void onAction(int actionId, Object obj) {
            switch (actionId) {
                case BussinessConstants.FragmentActionId.SETTING_FRAGMENT_LOGOUT_ACTION_ID:
                    Intent intent = new Intent(MainFragmentActivity.this, LoginActivity.class);
                    MainFragmentActivity.this.startActivity(intent);
                    MainFragmentActivity.this.finishAllActivity(LoginActivity.class.getName());
                    break;
                case BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_PRACTICE_ID:
                case BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_CARE_ID:
                case BussinessConstants.FragmentActionId.COURSES_SWITCH_FORUM_ID:
                    Message msg = new Message();
                    msg.what = actionId;
                    switchFragment(mDialIndex);
                    mFragments[mDialIndex].recieveMsg(msg);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_activity_main;
    }

    @Override
    protected void initView() {
        mContactsLay = findViewById(R.id.home_page_layout);
        mDialLay = findViewById(R.id.courses_layout);
        mSettingLay = findViewById(R.id.setting_layout);

        mTextView[mContactsIndex] = (TextView) findViewById(R.id.home_page_text);
        mTextView[mDialIndex] = (TextView) findViewById(R.id.courses_text);
        mTextView[mSettingIndex] = (TextView) findViewById(R.id.setting_text);

        mImageViewView[mContactsIndex] = (ImageView) findViewById(R.id.home_page_image);
        mImageViewView[mDialIndex] = (ImageView) findViewById(R.id.courses_image);
        mImageViewView[mSettingIndex] = (ImageView) findViewById(R.id.setting_image);

        mImageSelectedBgResId[mContactsIndex] = R.mipmap.main_navigation_selected_home;
        mImageSelectedBgResId[mDialIndex] = R.mipmap.main_navigation_selected_courses;
        mImageSelectedBgResId[mSettingIndex] = R.mipmap.main_navigation_selected_more;

        mImageUnSelectedBgResId[mContactsIndex] = R.mipmap.main_navigation_unselected_home;
        mImageUnSelectedBgResId[mDialIndex] = R.mipmap.main_navigation_unselected_courses;
        mImageUnSelectedBgResId[mSettingIndex] = R.mipmap.main_navigation_unselected_more;

        mFm = getSupportFragmentManager();
        mFragments[mContactsIndex] = new ContactsFragment();
        mFragments[mContactsIndex].setActivityListener(listener);
        FragmentTransaction ft = mFm.beginTransaction();
        ft.add(R.id.content, mFragments[mContactsIndex]);
        ft.commit();
        mTextView[mContactsIndex].setTextColor(getResources().getColor(R.color.navigation_selected));
        mImageViewView[mContactsIndex].setBackgroundResource(mImageSelectedBgResId[mContactsIndex]);
        mCurrentIndex = mContactsIndex;

    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        mContactsLay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragment(mContactsIndex);
            }
        });

        mDialLay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragment(mDialIndex);
            }
        });

        mSettingLay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragment(mSettingIndex);
            }
        });

    }

    private void switchFragment(int toIndex) {
        if (mCurrentIndex == toIndex) {
            return;
        }
        FragmentTransaction ft = mFm.beginTransaction();
        ft.hide(mFragments[mCurrentIndex]);
        if (mFragments[toIndex] != null) {
            if (mFragments[toIndex].isAdded()) {
                ft.show(mFragments[toIndex]);
            } else {
                ft.add(R.id.content, mFragments[toIndex]);
            }
        } else {
            switch (toIndex) {
                case mContactsIndex:
                    mFragments[toIndex] = new ContactsFragment();
                    mFragments[toIndex].setActivityListener(listener);
                    break;
                case mDialIndex:
                    mFragments[toIndex] = new DialFragment();
                    mFragments[toIndex].setActivityListener(listener);
                    break;
                case mSettingIndex:
                    mFragments[toIndex] = new SettingFragment();
                    mFragments[toIndex].setActivityListener(listener);
                    break;
            }
            ft.add(R.id.content, mFragments[toIndex]);
        }
        Log.d("MainFragmentActivity", "commit:" + mFragments[toIndex].getClass());
        ft.commit();
        mTextView[mCurrentIndex].setTextColor(getResources().getColor(R.color.navigation_unselected));
        mTextView[toIndex].setTextColor(getResources().getColor(R.color.navigation_selected));
        mImageViewView[mCurrentIndex].setBackgroundResource(mImageUnSelectedBgResId[mCurrentIndex]);
        mImageViewView[toIndex].setBackgroundResource(mImageSelectedBgResId[toIndex]);
        mCurrentIndex = toIndex;
    }

    @Override
    protected void initLogics() {

    }


    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        dismissWaitDailog();
        switch (msg.what) {
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getUIBusInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getUIBusInstance().unregister(this);
    }


    @Override
    protected void doNetWorkConnect() {
        //通知Fragment网络已经连接
       if(mFragments[mContactsIndex]!=null)
       {
           mFragments[mContactsIndex].doNetWorkConnect();
       }
    }
}
