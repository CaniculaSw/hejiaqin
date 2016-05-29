package com.chinamobile.hejiaqin.business.ui.contact;

import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragmentActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.contact.fragment.ContactInfoFragment;
import com.chinamobile.hejiaqin.business.ui.contact.fragment.DialInfoFragment;

import java.util.ArrayList;
import java.util.List;


public class ContactInfoActivity extends BasicFragmentActivity implements View.OnClickListener {
    private HeaderView titleLayout;
    private TextView mContactNameText;
    private ImageView mContactHeadImg;

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

    private ContactsInfo mContactsInfo;

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
        // title
        titleLayout = (HeaderView) findViewById(R.id.title);
        titleLayout.title.setText(R.string.contact_modify_title_add_text);
        titleLayout.backImageView.setImageResource(R.mipmap.title_icon_back_nor);

        // 联系人姓名
        mContactNameText = (TextView) findViewById(R.id.contact_name_text);
        // 联系人头像
        mContactHeadImg = (ImageView) findViewById(R.id.contact_head_img);

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

        ImageView dialImg = (ImageView) findViewById(R.id.dial_img);
        dialImg.setOnClickListener(this);
    }

    @Override
    protected void initDate() {
        mContactsInfo = (ContactsInfo) getIntent().getSerializableExtra(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY);

        mContactNameText.setText(mContactsInfo.getName());

        fragmentList = new ArrayList<Fragment>();
        BasicFragment contactInfoFragment = new ContactInfoFragment();
        contactInfoFragment.setActivityListener(listener);
        ((ContactInfoFragment) contactInfoFragment).setContactsInfo(mContactsInfo);
        fragmentList.add(contactInfoFragment);

        BasicFragment dialInfoFragment = new DialInfoFragment();
        dialInfoFragment.setActivityListener(listener);
        ((DialInfoFragment) dialInfoFragment).setContactsInfo(mContactsInfo);
        fragmentList.add(dialInfoFragment);

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        showViewByCurIndex(mViewPager.getCurrentItem());
    }

    @Override
    protected void initListener() {
        titleLayout.rightBtn.setOnClickListener(this);
        titleLayout.backImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_btn:
                // TODO
                break;
            case R.id.back_iv:
                this.finish();
                break;
            case R.id.contact_info_layout:
                if (currentIndex != CONTACT_INFO_INDEX) {
                    switchFragment(CONTACT_INFO_INDEX);
                }
                break;
            case R.id.dial_info_layout:
                if (currentIndex != DIAL_INFO_INDEX) {
                    switchFragment(DIAL_INFO_INDEX);
                }
                break;
            case R.id.dial_img:
                // TODO
                break;
        }
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
            if (currentItem == currentIndex) {
                return;
            }

            showViewByCurIndex(currentItem);
            currentIndex = mViewPager.getCurrentItem();
        }

    }

    private void showViewByCurIndex(int currentItem) {
        if (currentItem == CONTACT_INFO_INDEX) {
            mContactInfoIcon.setImageResource(R.mipmap.icon_personal_data_pre);
            mContactInfoSelected.setVisibility(View.VISIBLE);
            mContactInfoUnSelected.setVisibility(View.GONE);
            mDialInfoIcon.setImageResource(R.mipmap.icon_call_record_nor);
            mDialInfoSelected.setVisibility(View.GONE);
            mDialInfoUnSelected.setVisibility(View.VISIBLE);
            titleLayout.rightBtn.setImageResource(mContactsInfo.getContactMode() == ContactsInfo.ContactMode.system
                    ? R.mipmap.title_icon_add_nor : R.mipmap.title_icon_more_nor);
        } else if (currentItem == DIAL_INFO_INDEX) {
            mContactInfoIcon.setImageResource(R.mipmap.icon_personal_data_nor);
            mContactInfoSelected.setVisibility(View.GONE);
            mContactInfoUnSelected.setVisibility(View.VISIBLE);
            mDialInfoIcon.setImageResource(R.mipmap.icon_call_record_pre);
            mDialInfoSelected.setVisibility(View.VISIBLE);
            mDialInfoUnSelected.setVisibility(View.GONE);
            titleLayout.rightBtn.setImageResource(R.mipmap.title_icon_delete_dis);
        }
    }
}
