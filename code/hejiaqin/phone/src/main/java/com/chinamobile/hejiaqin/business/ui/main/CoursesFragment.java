package com.chinamobile.hejiaqin.business.ui.main;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.courses.ForumFragment;
import com.chinamobile.hejiaqin.business.ui.courses.HealthcareFragment;
import com.chinamobile.hejiaqin.business.ui.courses.PracticeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * desc: 康兮课程
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/22.
 */
public class CoursesFragment extends BasicFragment {

    private boolean isCreateView;

    /**
     * .
     * 作为页面容器的ViewPager.
     */
    ViewPager mViewPager;

    /**
     * 练习课程
     */
    View mPracticeLay;

    /**
     * 保健康复
     */
    View mHealthcareLay;

    /**
     * 康兮讲堂
     */
    View mForumLay;

    List<Fragment> fragmentList;

    TextView[] mTextView = new TextView[3];
    ImageView[] mImageView = new ImageView[3];

    int mCurrentIndex;

    final int mPracticeIndex = 0;

    final int mHealthcareIndex = 1;

    final int mForumIndex = 2;

    //当前选中的项
    int currentIndex = -1;

    private Message switchFromHomeMsg;


    private BackListener listener = new BackListener() {
        public void onAction(int actionId, Object obj) {

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        HeaderView headerView=(HeaderView)view.findViewById(R.id.header_view);
        headerView.title.setText(R.string.navigation_courses);
        mPracticeLay = view.findViewById(R.id.practice_layout);
        mHealthcareLay = view.findViewById(R.id.healthcare_layout);
        mForumLay = view.findViewById(R.id.forum_layout);
        mTextView[0] = (TextView) view.findViewById(R.id.practice_text);
        mTextView[1] = (TextView) view.findViewById(R.id.healthcare_text);
        mTextView[2] = (TextView) view.findViewById(R.id.forum_text);
        mImageView[0] = (ImageView) view.findViewById(R.id.iv_practice);
        mImageView[1] = (ImageView) view.findViewById(R.id.iv_healthcare);
        mImageView[2] = (ImageView) view.findViewById(R.id.iv_forum);
        mViewPager = (ViewPager) view.findViewById(R.id.courses_viewpager);
        initListener();
        fragmentList = new ArrayList<Fragment>();

        BasicFragment practiceFragment = new PracticeFragment();
        practiceFragment.setActivityListener(listener);
        fragmentList.add(practiceFragment);

        BasicFragment healthcareFragment = new HealthcareFragment();
        healthcareFragment.setActivityListener(listener);
        fragmentList.add(healthcareFragment);

        BasicFragment forumFragment = new ForumFragment();
        forumFragment.setActivityListener(listener);
        fragmentList.add(forumFragment);

        mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        if(switchFromHomeMsg!=null)
        {
            switchFromHomePage(switchFromHomeMsg);
        }
        isCreateView = true;
        return view;
    }

    protected void initListener() {
        mPracticeLay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragment(mPracticeIndex);
            }
        });

        mHealthcareLay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragment(mHealthcareIndex);
            }
        });

        mForumLay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragment(mForumIndex);
            }
        });

    }

    @Override
    public void recieveMsg(Message msg) {
        if (!isCreateView) {
            //没有创建前先缓存消息
            //首页查看
            switch (msg.what)
            {
                case BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_PRACTICE_ID:
                case BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_CARE_ID:
                case BussinessConstants.FragmentActionId.COURSES_SWITCH_FORUM_ID:
                    switchFromHomeMsg =msg;
                    break;
            }
            return;
        }
        switchFromHomePage(msg);
    }

    //首页查看切换通知
    private void switchFromHomePage(Message msg) {
        switch (msg.what)
        {
            case BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_PRACTICE_ID:
                switchFragment(mPracticeIndex);
                ((BasicFragment)fragmentList.get(mPracticeIndex)).recieveMsg(msg);
                break;
            case BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_CARE_ID:
                switchFragment(mHealthcareIndex);
                ((BasicFragment)fragmentList.get(mHealthcareIndex)).recieveMsg(msg);
                break;
            case BussinessConstants.FragmentActionId.COURSES_SWITCH_FORUM_ID:
                switchFragment(mForumIndex);
                break;
        }
    }


    public void ChildFragment(Message msg) {
        if (!isCreateView) {
            return;
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
            mTextView[currentItem].setTextColor(getResources().getColor(R.color.navigation_selected));
            mImageView[currentItem].setImageResource(R.mipmap.bottom_line);
            if(currentIndex!=-1) {
                mTextView[currentIndex].setTextColor(getResources().getColor(R.color.navigation_unselected));
                mImageView[currentIndex].setImageDrawable(null);
            }
            currentIndex = mViewPager.getCurrentItem();
        }

    }

}
