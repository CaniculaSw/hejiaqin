package com.chinamobile.hejiaqin.business.ui.contact;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragmentActivity;
import com.chinamobile.hejiaqin.business.ui.contact.fragment.ContactInfoFragment;
import com.chinamobile.hejiaqin.business.ui.contact.fragment.DialInfoFragment;

import java.util.ArrayList;
import java.util.List;


public class ContactInfoActivity extends BasicFragmentActivity implements View.OnClickListener {

    private View mContactInfoLay;

    private ImageView mContactInfoIcon;

    private ImageView mContactInfoSelected;

    private ImageView mContactInfoUnSelected;


    private View mDialInfoLay;

    private ImageView mDialInfoIcon;

    private ImageView mDialInfoSelected;

    private ImageView mDialInfoUnSelected;

    /**
     * 作为页面容器的ViewPager.
     */
    ViewPager mViewPager;

    private List<Fragment> fragmentList;

    private final int CONTACT_INFO_INDEX = 0;
    private final int DIAL_INFO_INDEX = 1;
    //当前选中的项
    int currentIndex = -1;

    private BasicFragment.BackListener listener = new BasicFragment.BackListener() {
        public void onAction(int actionId, Object obj) {

        }
    };

    @Override
    protected void initLogics() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_info;
    }

    @Override
    protected void initView() {

        mContactInfoLay = findViewById(R.id.contact_info_layout);
        mContactInfoLay.setOnClickListener(this);
        mContactInfoIcon = (ImageView) findViewById(R.id.contact_info_icon);
        mContactInfoSelected = (ImageView) findViewById(R.id.contact_info_selected);
        mContactInfoUnSelected = (ImageView) findViewById(R.id.contact_info_unselected);

        mDialInfoLay = findViewById(R.id.dial_info_layout);
        mDialInfoLay.setOnClickListener(this);
        mDialInfoIcon = (ImageView) findViewById(R.id.dial_info_icon);
        mDialInfoSelected = (ImageView) findViewById(R.id.dial_info_selected);
        mDialInfoUnSelected = (ImageView) findViewById(R.id.dial_info_unselected);

        mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);

        fragmentList = new ArrayList<Fragment>();
        BasicFragment contactInfoFragment = new ContactInfoFragment();
        contactInfoFragment.setActivityListener(listener);
        fragmentList.add(contactInfoFragment);

        BasicFragment dialInfoFragment = new DialInfoFragment();
        dialInfoFragment.setActivityListener(listener);
        fragmentList.add(dialInfoFragment);

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
    }

    @Override
    public void onClick(View v) {

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
            if (currentItem == currentIndex) {
                return;
            }

            if (currentItem == CONTACT_INFO_INDEX) {
                mContactInfoIcon.setImageResource(R.mipmap.icon_personal_data_pre);
                mContactInfoSelected.setVisibility(View.VISIBLE);
                mContactInfoUnSelected.setVisibility(View.GONE);
                mDialInfoIcon.setImageResource(R.mipmap.icon_call_record_nor);
                mDialInfoSelected.setVisibility(View.GONE);
                mDialInfoUnSelected.setVisibility(View.VISIBLE);
            } else if (currentItem == DIAL_INFO_INDEX) {
                mContactInfoIcon.setImageResource(R.mipmap.icon_personal_data_nor);
                mContactInfoSelected.setVisibility(View.GONE);
                mContactInfoUnSelected.setVisibility(View.VISIBLE);
                mDialInfoIcon.setImageResource(R.mipmap.icon_call_record_pre);
                mDialInfoSelected.setVisibility(View.VISIBLE);
                mDialInfoUnSelected.setVisibility(View.GONE);
            }
            currentIndex = mViewPager.getCurrentItem();
        }

    }
}
