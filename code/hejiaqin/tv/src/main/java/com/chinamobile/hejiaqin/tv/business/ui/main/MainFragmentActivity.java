package com.chinamobile.hejiaqin.tv.business.ui.main;

import android.content.Intent;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.tv.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.tv.business.ui.basic.BasicFragmentActivity;
import com.chinamobile.hejiaqin.tv.business.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * desc: main
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/22.
 */
public class MainFragmentActivity extends BasicFragmentActivity {

    FragmentManager mFm;

    View mNavigatorLay;

    View mContactsLay;

    View mDialLay;

    View mSettingLay;

    BasicFragment[] mFragments = new BasicFragment[3];

    TextView[] mTextViews = new TextView[3];

    ImageView[] mImageViews = new ImageView[3];

    int[] mImageSelectedBgResId = new int[3];

    int[] mImageUnSelectedBgResId = new int[3];

    int mCurrentIndex;

    final int mContactsIndex = 0;

    final int mDialIndex = 1;

    final int mSettingIndex = 2;

    private List<Fragment> fragmentList;

    /**
     * 作为页面容器的ViewPager.
     */
    ViewPager mViewPager;


    private BasicFragment.BackListener listener = new BasicFragment.BackListener() {
        public void onAction(int actionId, Object obj) {
            switch (actionId) {
                case BussinessConstants.FragmentActionId.SETTING_FRAGMENT_LOGOUT_ACTION_ID:
                    Intent intent = new Intent(MainFragmentActivity.this, LoginActivity.class);
                    MainFragmentActivity.this.startActivity(intent);
                    MainFragmentActivity.this.finishAllActivity(LoginActivity.class.getName());
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
        mImageSelectedBgResId[mDialIndex] = R.mipmap.main_navigation_selected_dial_show;
        mImageSelectedBgResId[mSettingIndex] = R.mipmap.main_navigation_selected_more;

        mImageUnSelectedBgResId[mContactsIndex] = R.mipmap.main_navigation_unselected_contact;
        mImageUnSelectedBgResId[mDialIndex] = R.mipmap.main_navigation_unselected_dial;
        mImageUnSelectedBgResId[mSettingIndex] = R.mipmap.main_navigation_unselected_more;

        mViewPager = (ViewPager) findViewById(R.id.main_content_viewpager);

        fragmentList = new ArrayList<Fragment>();
        BasicFragment contactsFragment = new ContactsFragment();
        contactsFragment.setActivityListener(listener);
        fragmentList.add(contactsFragment);

        BasicFragment dialFragment = new DialFragment();
        dialFragment.setActivityListener(listener);
        fragmentList.add(dialFragment);

        BasicFragment settingFragment = new SettingFragment();
        settingFragment.setActivityListener(listener);
        fragmentList.add(settingFragment);

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

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
    //手动设置ViewPager要显示的视图
    private void switchFragment(int toIndex) {
        mViewPager.setCurrentItem(toIndex, true);
    }

    /**
     * 定义自己的ViewPager适配器。
     */
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        /**
         * 每次更新完成ViewPager的内容后，调用该接口，此处复写主要是为了让导航按钮上层的覆盖层能够动态的移动
         */
        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);//这句话要放在最前面，否则会报错
            //获取当前的视图是位于ViewGroup的第几个位置，用来更新对应的覆盖层所在的位置
            int currentItem = mViewPager.getCurrentItem();
            showFragment(currentItem);
        }

    }

    private void showFragment(int toIndex) {
        if (mCurrentIndex == toIndex) {
            return;
        }
        mTextViews[mCurrentIndex].setTextColor(getResources().getColor(R.color.navigation_unselected));
        mImageViews[mCurrentIndex].setBackgroundResource(mImageUnSelectedBgResId[mCurrentIndex]);
        mTextViews[toIndex].setTextColor(getResources().getColor(R.color.navigation_selected));
        mImageViews[toIndex].setBackgroundResource(mImageSelectedBgResId[toIndex]);
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
    protected void doNetWorkConnect() {
        //通知Fragment网络已经连接
        if (mFragments[mContactsIndex] != null) {
            mFragments[mContactsIndex].doNetWorkConnect();
        }
    }
}