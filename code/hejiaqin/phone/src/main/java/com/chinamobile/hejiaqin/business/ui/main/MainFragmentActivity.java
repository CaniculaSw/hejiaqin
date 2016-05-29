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

    private static final int DIAL_STATUS_NORMAL = 1;

    private static final int DIAL_STATUS_SHOW_KEYBORD = 2;

    private static final int DIAL_STATUS_CALL = 3;

    FragmentManager mFm;

    View mNavigatorLay;

    View mContactsLay;

    View mDialLay;

    View mSettingLay;

    BasicFragment[] mFragments = new BasicFragment[3];

    TextView[] mTextViews = new TextView[3];

    ImageView[] mImageViews = new ImageView[3];

    ImageView mDialCallImage;

    int[] mImageSelectedBgResId = new int[3];

    int[] mImageUnSelectedBgResId = new int[3];

    int mCurrentIndex;

    final int mContactsIndex = 0;

    final int mDialIndex = 1;

    final int mSettingIndex = 2;

    private boolean mDialSeleted = false;

    private int mDialStatus = DIAL_STATUS_NORMAL;

    private BasicFragment.BackListener listener = new BasicFragment.BackListener() {
        public void onAction(int actionId, Object obj) {
            switch (actionId) {
                case BussinessConstants.FragmentActionId.SETTING_FRAGMENT_LOGOUT_ACTION_ID:
                    Intent intent = new Intent(MainFragmentActivity.this, LoginActivity.class);
                    MainFragmentActivity.this.startActivity(intent);
                    MainFragmentActivity.this.finishAllActivity(LoginActivity.class.getName());
                    break;
                case BussinessConstants.FragmentActionId.DAIL_FRAGMENT_SHOW_CALL_ACTION_ID:
                    mImageViews[mDialIndex].setVisibility(View.GONE);
                    mTextViews[mDialIndex].setVisibility(View.GONE);
                    mDialCallImage.setVisibility(View.VISIBLE);
                    mDialStatus = DIAL_STATUS_CALL;
                    break;
                case BussinessConstants.FragmentActionId.DAIL_FRAGMENT_HIDE_CALL_ACTION_ID:
                    mDialCallImage.setVisibility(View.GONE);
                    mImageViews[mDialIndex].setVisibility(View.VISIBLE);
                    mTextViews[mDialIndex].setVisibility(View.VISIBLE);
                    mDialStatus = DIAL_STATUS_SHOW_KEYBORD;
                    break;
                 // 显示导航栏
                case BussinessConstants.FragmentActionId.CONTACT_FRAGMENT_SHOW_NAVIGATOR_ACTION_ID:
                    mNavigatorLay.setVisibility(View.VISIBLE);
                    break;
                // 隐藏导航栏
                 case BussinessConstants.FragmentActionId.CONTACT_FRAGMENT_HIDE_NAVIGATOR_ACTION_ID:
                    mNavigatorLay.setVisibility(View.GONE);
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
        mNavigatorLay = findViewById(R.id.main_bottom);
        mContactsLay = findViewById(R.id.contact_layout);
        mDialLay = findViewById(R.id.dial_layout);
        mSettingLay = findViewById(R.id.more_layout);

        mTextViews[mContactsIndex] = (TextView) findViewById(R.id.contact_text);
        mTextViews[mDialIndex] = (TextView) findViewById(R.id.dial_text);
        mTextViews[mSettingIndex] = (TextView) findViewById(R.id.more_text);

        mImageViews[mContactsIndex] = (ImageView) findViewById(R.id.contact_image);
        mImageViews[mDialIndex] = (ImageView) findViewById(R.id.dial_image);
        mImageViews[mSettingIndex] = (ImageView) findViewById(R.id.more_image);

        mImageSelectedBgResId[mContactsIndex] = R.mipmap.main_navigation_selected_contact;
        mImageSelectedBgResId[mDialIndex] = R.mipmap.main_navigation_selected_dial;
        mImageSelectedBgResId[mSettingIndex] = R.mipmap.main_navigation_selected_more;

        mImageUnSelectedBgResId[mContactsIndex] = R.mipmap.main_navigation_unselected_contact;
        mImageUnSelectedBgResId[mDialIndex] = R.mipmap.main_navigation_unselected_dial;
        mImageUnSelectedBgResId[mSettingIndex] = R.mipmap.main_navigation_unselected_more;

        mDialCallImage = (ImageView) findViewById(R.id.dial_call_image);

        mFm = getSupportFragmentManager();
        mFragments[mContactsIndex] = new ContactsFragment();
        mFragments[mContactsIndex].setActivityListener(listener);
        FragmentTransaction ft = mFm.beginTransaction();
        ft.add(R.id.content, mFragments[mContactsIndex]);
        ft.commit();
        mTextViews[mContactsIndex].setTextColor(getResources().getColor(R.color.navigation_selected));
        mImageViews[mContactsIndex].setBackgroundResource(mImageSelectedBgResId[mContactsIndex]);
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
                if (!mDialSeleted) {
                    switchFragment(mDialIndex);
                } else {
                    Message msg;
                    switch (mDialStatus) {
                        case DIAL_STATUS_NORMAL:
                            mImageViews[mDialIndex].setBackgroundResource(R.mipmap.main_navigation_selected_dial_show);
                            mDialStatus = DIAL_STATUS_SHOW_KEYBORD;
                            //TODO:显示拨号盘
                            msg = new Message();
                            msg.what = BussinessConstants.FragmentActionId.DAIL_FRAGMENT_SHOW_KEYBORD_MSG_ID;
                            mFragments[mDialIndex].recieveMsg(msg);
                            break;
                        case DIAL_STATUS_SHOW_KEYBORD:
                            mImageViews[mDialIndex].setBackgroundResource(mImageSelectedBgResId[mDialIndex]);
                            mDialStatus = DIAL_STATUS_NORMAL;
                            //TODO:收起拨号盘
                            msg = new Message();
                            msg.what = BussinessConstants.FragmentActionId.DAIL_FRAGMENT_HIDE_KEYBORD_MSG_ID;
                            mFragments[mDialIndex].recieveMsg(msg);
                            break;
                        case DIAL_STATUS_CALL:
                            //TODO:呼叫电话
                            msg = new Message();
                            msg.what = BussinessConstants.FragmentActionId.DAIL_FRAGMENT_CALL_MSG_ID;
                            mFragments[mDialIndex].recieveMsg(msg);
                            break;
                    }
                }
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
        mTextViews[mCurrentIndex].setTextColor(getResources().getColor(R.color.navigation_unselected));
        mImageViews[mCurrentIndex].setBackgroundResource(mImageUnSelectedBgResId[mCurrentIndex]);
        if (toIndex != mDialIndex) {
            mTextViews[toIndex].setTextColor(getResources().getColor(R.color.navigation_selected));
            mImageViews[toIndex].setBackgroundResource(mImageSelectedBgResId[toIndex]);
        }
        if (mCurrentIndex == mDialIndex) {
            mDialSeleted = false;
            if (mDialStatus == DIAL_STATUS_SHOW_KEYBORD) {
                mDialStatus = DIAL_STATUS_NORMAL;
            }
        } else if (toIndex == mDialIndex) {
            mDialSeleted = true;
            switch (mDialStatus) {
                case DIAL_STATUS_NORMAL:
                    mDialCallImage.setVisibility(View.GONE);
                    mTextViews[toIndex].setTextColor(getResources().getColor(R.color.navigation_selected));
                    mImageViews[toIndex].setBackgroundResource(mImageSelectedBgResId[toIndex]);
                    mImageViews[toIndex].setVisibility(View.VISIBLE);
                    mTextViews[toIndex].setVisibility(View.VISIBLE);
                    break;
                case DIAL_STATUS_CALL:
                    mImageViews[toIndex].setVisibility(View.GONE);
                    mTextViews[toIndex].setVisibility(View.GONE);
                    mDialCallImage.setVisibility(View.VISIBLE);
                    break;

            }
        }
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
        if (mFragments[mContactsIndex] != null) {
            mFragments[mContactsIndex].doNetWorkConnect();
        }
    }
}
