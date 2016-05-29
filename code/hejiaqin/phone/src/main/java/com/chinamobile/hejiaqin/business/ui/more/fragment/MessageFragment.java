package com.chinamobile.hejiaqin.business.ui.more.fragment;

import android.os.Message;
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
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/5/25.
 */
public class MessageFragment extends BasicFragment implements View.OnClickListener {
    /**
     * 作为页面容器的ViewPager.
     */
    ViewPager mViewPager;

    /**
     * 漏接来电布局
     */
    View mMissCallLay;

    /**
     * 系统消息布局
     */
    View mSysMessageLay;

    private List<BasicFragment> fragmentList;

    private TextView[] mTextView = new TextView[2];
    private ImageView[] mImageView = new ImageView[2];
    private TextView editTv;

    private final int mMissCallLayIndex = 0;
    private final int mSysMessageLayIndex = 1;
    private BasicFragment missCallListFragment;
    private BasicFragment sysMessageListFragment;
    private ImageView mBackButton;

    //当前选中的项
    int currentIndex = -1;

    private BackListener listener = new BackListener() {
        public void onAction(int actionId, Object obj) {

        }
    };

    @Override
    protected void initLogics() {
    }

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sys_message;
    }

    @Override
    protected void initView(View view) {
        mMissCallLay = view.findViewById(R.id.more_sys_msg_miss_call_layout);
        mMissCallLay.setOnClickListener(this);
        mSysMessageLay = view.findViewById(R.id.more_sys_msg_sys_message_layout);
        mSysMessageLay.setOnClickListener(this);
        mTextView[0] = (TextView) view.findViewById(R.id.more_sys_msg_miss_call_tv);
        mTextView[1] = (TextView) view.findViewById(R.id.more_sys_msg_sys_message_tv);
        mImageView[0] = (ImageView) view.findViewById(R.id.more_sys_msg_miss_call_iv);
        mImageView[1] = (ImageView) view.findViewById(R.id.more_sys_msg_sys_message_iv);
        editTv = (TextView) view.findViewById(R.id.more_sys_msg_edit);
        editTv.setOnClickListener(this);
        mViewPager = (ViewPager) view.findViewById(R.id.more_msg_viewpager);

        fragmentList = new ArrayList<BasicFragment>();
        missCallListFragment = new MissCallListFragment();
        missCallListFragment.setActivityListener(listener);
        fragmentList.add(missCallListFragment);

        sysMessageListFragment = new SystemMessageListFragment();
        sysMessageListFragment.setActivityListener(listener);
        fragmentList.add(sysMessageListFragment);

        mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));

        mBackButton = (ImageView) view.findViewById(R.id.more_sys_msg_back_btn);
        mBackButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }
    //手动设置ViewPager要显示的视图
    private void switchFragment(int toIndex) {
        mViewPager.setCurrentItem(toIndex, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_sys_msg_miss_call_layout:
                if (currentIndex != mMissCallLayIndex) {
                    Message msg = new Message();
                    msg.what = BussinessConstants.SettingMsgID.CLEAN_MESSAGES_SELECTED_STATE;
                    missCallListFragment.recieveMsg(msg);
                    switchFragment(mMissCallLayIndex);
                }
                break;
            case R.id.more_sys_msg_sys_message_layout:
                if (currentIndex != mSysMessageLayIndex) {
                    switchFragment(mSysMessageLayIndex);
                }
                break;
            case R.id.more_sys_msg_edit:
                editTv.setText(editTv.getText().equals(getResources().getString(R.string.more_edit))? getResources().getString(R.string.more_cancel): getResources().getString(R.string.more_edit));
                Message msg = new Message();
                msg.what = BussinessConstants.SettingMsgID.EDIT_BUTTON_PRESSED;
                (fragmentList.get(currentIndex)).recieveMsg(msg);
                break;
            case R.id.more_sys_msg_back_btn:
                getActivity().finish();
            default:
                break;
        }
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
            editTv.setText(getString(R.string.more_edit));
            mTextView[currentItem].setTextColor(getResources().getColor(R.color.more_sys_msg_navigator_text_selected));
            mImageView[currentItem].setVisibility(View.VISIBLE);

            if (currentIndex != -1) {
                mTextView[currentIndex].setTextColor(getResources().getColor(R.color.more_sys_msg_navigator_text_unselected));
                mImageView[currentIndex].setVisibility(View.GONE);
                Message msg = new Message();
                msg.what = BussinessConstants.SettingMsgID.MESSAGE_FRAGMENT_SWITCH_OFF;
                (fragmentList.get(currentIndex)).recieveMsg(msg);
            }
            currentIndex = mViewPager.getCurrentItem();
        }

    }
}
