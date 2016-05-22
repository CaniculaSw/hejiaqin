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

import com.squareup.otto.Subscribe;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.criteria.CourseCriteria;
import com.chinamobile.hejiaqin.business.logic.courses.ICoursesLogic;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.person.IPersonLogic;
import com.chinamobile.hejiaqin.business.model.courses.ResultMessageAction;
import com.chinamobile.hejiaqin.business.model.event.CoursesLoadDataEvent;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragmentActivity;
import com.chinamobile.hejiaqin.business.ui.login.LoginActivity;
import com.chinamobile.hejiaqin.business.utils.BusProvider;
import com.customer.framework.utils.StringUtil;

/**
 * desc: main
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/22.
 */
public class MainFragmentActivity extends BasicFragmentActivity {

    private ILoginLogic loginLogic;

    private IPersonLogic personLogic;

    private ICoursesLogic coursesLogic;

    FragmentManager mFm;

    View mHomePageLay;

    View mCoursesLay;

    View mHealthBankLay;

    View mSettingLay;

    BasicFragment[] mFragments = new BasicFragment[4];

    TextView[] mTextView = new TextView[4];

    ImageView[] mImageViewView = new ImageView[4];

    int[] mImageSelectedBgResId = new int[4];

    int[] mImageUnSelectedBgResId = new int[4];

    int mCurrentIndex;

    final int mHomeIndex = 0;

    final int mCoursesIndex = 1;

    final int mHealthBankIndex = 2;

    final int mSettingIndex = 3;
//
//    int testValue = 1;

    private BasicFragment.BackListener listener = new BasicFragment.BackListener() {
        public void onAction(int actionId, Object obj) {
            switch (actionId) {
                case BussinessConstants.FragmentActionId.SETTING_FRAGMENT_LOGOUT_ACTION_ID:
                    loginLogic.logout();
                    Intent intent = new Intent(MainFragmentActivity.this, LoginActivity.class);
                    MainFragmentActivity.this.startActivity(intent);
                    MainFragmentActivity.this.finishAllActivity(LoginActivity.class.getName());
                    break;
                case BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_PRACTICE_ID:
                case BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_CARE_ID:
                case BussinessConstants.FragmentActionId.COURSES_SWITCH_FORUM_ID:
                    Message msg = new Message();
                    msg.what = actionId;
                    switchFragment(mCoursesIndex);
                    mFragments[mCoursesIndex].recieveMsg(msg);
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
        mHomePageLay = findViewById(R.id.home_page_layout);
        mCoursesLay = findViewById(R.id.courses_layout);
        mHealthBankLay = findViewById(R.id.health_blank_layout);
        mSettingLay = findViewById(R.id.setting_layout);

        mTextView[mHomeIndex] = (TextView) findViewById(R.id.home_page_text);
        mTextView[mCoursesIndex] = (TextView) findViewById(R.id.courses_text);
        mTextView[mHealthBankIndex] = (TextView) findViewById(R.id.health_blank_text);
        mTextView[mSettingIndex] = (TextView) findViewById(R.id.setting_text);

        mImageViewView[mHomeIndex] = (ImageView) findViewById(R.id.home_page_image);
        mImageViewView[mCoursesIndex] = (ImageView) findViewById(R.id.courses_image);
        mImageViewView[mHealthBankIndex] = (ImageView) findViewById(R.id.health_blank_image);
        mImageViewView[mSettingIndex] = (ImageView) findViewById(R.id.setting_image);

        mImageSelectedBgResId[mHomeIndex] = R.mipmap.main_navigation_selected_home;
        mImageSelectedBgResId[mCoursesIndex] = R.mipmap.main_navigation_selected_courses;
        mImageSelectedBgResId[mHealthBankIndex] = R.mipmap.main_navigation_selected_bank;
        mImageSelectedBgResId[mSettingIndex] = R.mipmap.main_navigation_selected_me;

        mImageUnSelectedBgResId[mHomeIndex] = R.mipmap.main_navigation_unselected_home;
        mImageUnSelectedBgResId[mCoursesIndex] = R.mipmap.main_navigation_unselected_courses;
        mImageUnSelectedBgResId[mHealthBankIndex] = R.mipmap.main_navigation_unselected_bank;
        mImageUnSelectedBgResId[mSettingIndex] = R.mipmap.main_navigation_unselected_me;

        mFm = getSupportFragmentManager();
        mFragments[mHomeIndex] = new HomePageFragment();
        mFragments[mHomeIndex].setActivityListener(listener);
        FragmentTransaction ft = mFm.beginTransaction();
        ft.add(R.id.content, mFragments[mHomeIndex]);
        ft.commit();
        mTextView[mHomeIndex].setTextColor(getResources().getColor(R.color.navigation_selected));
        mImageViewView[mHomeIndex].setBackgroundResource(mImageSelectedBgResId[mHomeIndex]);
        mCurrentIndex = mHomeIndex;

    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        mHomePageLay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragment(mHomeIndex);
            }
        });

        mCoursesLay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragment(mCoursesIndex);
            }
        });

        mHealthBankLay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switchFragment(mHealthBankIndex);
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
                case mHomeIndex:
                    mFragments[toIndex] = new HomePageFragment();
                    mFragments[toIndex].setActivityListener(listener);
                    break;
                case mCoursesIndex:
                    mFragments[toIndex] = new CoursesFragment();
                    mFragments[toIndex].setActivityListener(listener);
                    break;
                case mHealthBankIndex:
                    mFragments[toIndex] = new HealthBankFragment();
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
        loginLogic = (ILoginLogic) this.getLogicByInterfaceClass(ILoginLogic.class);
        personLogic = (IPersonLogic) this.getLogicByInterfaceClass(IPersonLogic.class);
        coursesLogic = (ICoursesLogic) this.getLogicByInterfaceClass(ICoursesLogic.class);
    }


    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        dismissWaitDailog();
        switch (msg.what) {
            case BussinessConstants.CoursesMsgID.FIND_ALL_TRAINING_COURSE_SUCCESS_MSG_ID:
            case BussinessConstants.CoursesMsgID.FIND_ALL_REHABILITATION_COURSE_SUCCESS_MSG_ID:
            case BussinessConstants.CoursesMsgID.FIND_RECOMMEND_REHABILITATION_COURSE_SUCCESS_MSG_ID:
            case BussinessConstants.CoursesMsgID.FIND_ALL_LECTURE_COURSE_SUCCESS_MSG_ID:
                if (msg.obj != null) {
                    ResultMessageAction resultMsgAction = (ResultMessageAction) msg.obj;
                    BusProvider.getUIBusInstance().post(resultMsgAction);
                }
                break;
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

    /**
     * 监听课程加载数据
     */
    @Subscribe
    public void loadCoursesData(CoursesLoadDataEvent event) {
        if (event != null) {
            switch (event.getActionId()) {
                case BussinessConstants.CoursesHttpErrorCode.COURSE_PRACTICE_ACTION_ID:
                    if (event.getObj() != null) {
                        CourseCriteria criteria = (CourseCriteria) event.getObj();
                        if (StringUtil.equals("-1", criteria.getMovementType())) {
                            //推荐练习，不显示加载框
                            criteria.setMovementType(null);
                            coursesLogic.findRecommendTrainingCourses(event.getActionId(), criteria);
                        } else {
                            //全部练习，不显示加载框
                            coursesLogic.findAllTrainingCourses(event.getActionId(), criteria);
                        }
                    }
                    break;
                case BussinessConstants.CoursesHttpErrorCode.COURSE_PRACTICE_SHOW_LOADING_ACTION_ID:
                    if (event.getObj() != null) {
                        showWaitDailog();
                        CourseCriteria criteria = (CourseCriteria) event.getObj();
                        if (StringUtil.equals("-1", criteria.getMovementType())) {
                            //推荐练习，显示加载框
                            criteria.setMovementType(null);
                            coursesLogic.findRecommendTrainingCourses(event.getActionId(), criteria);
                        } else {
                            //全部练习，显示加载框
                            coursesLogic.findAllTrainingCourses(event.getActionId(), criteria);
                        }
                    }
                    break;
                case BussinessConstants.CoursesHttpErrorCode.COURSE_HEALTHCARE_ALL_ACTION_ID:
                    if (event.getObj() != null) {
                        CourseCriteria criteria = (CourseCriteria) event.getObj();
                        //全部保健，不显示加载框
                        coursesLogic.findAllRehabilitationCourses(event.getActionId(), criteria);
                    }
                    break;
                case BussinessConstants.CoursesHttpErrorCode.COURSE_HEALTHCARE_ALL_SHOW_LOADING_ACTION_ID:
                    if (event.getObj() != null) {
                        showWaitDailog();
                        CourseCriteria criteria = (CourseCriteria) event.getObj();
                        //全部保健,显示加载框
                        coursesLogic.findAllRehabilitationCourses(event.getActionId(), criteria);
                    }
                    break;
                case BussinessConstants.CoursesHttpErrorCode.COURSE_HEALTHCARE_RECOMMEND_ACTION_ID:
                    if (event.getObj() != null) {
                        CourseCriteria criteria = (CourseCriteria) event.getObj();
                        //推荐保健，不显示加载框
                        coursesLogic.findRecommendRehabilitationCourses(event.getActionId(), criteria);
                    }
                    break;
                case BussinessConstants.CoursesHttpErrorCode.COURSE_HEALTHCARE_RECOMMEND_SHOW_LOADING_ACTION_ID:
                    if (event.getObj() != null) {
                        showWaitDailog();
                        CourseCriteria criteria = (CourseCriteria) event.getObj();
                        //推荐保健，显示加载框
                        coursesLogic.findRecommendRehabilitationCourses(event.getActionId(), criteria);
                    }
                    break;
                case BussinessConstants.CoursesHttpErrorCode.COURSE_FORUM_ACTION_ID:
                    if (event.getObj() != null) {
                        CourseCriteria criteria = (CourseCriteria) event.getObj();
                        //推荐保健，不显示加载框
                        coursesLogic.findAllLectureCourses(event.getActionId(), criteria);
                    }
                    break;
                case BussinessConstants.CoursesHttpErrorCode.COURSE_FORUM_SHOW_LOADING_ACTION_ID:
                    if (event.getObj() != null) {
                        showWaitDailog();
                        CourseCriteria criteria = (CourseCriteria) event.getObj();
                        //推荐保健，显示加载框
                        coursesLogic.findAllLectureCourses(event.getActionId(), criteria);
                    }
                    break;
                case BussinessConstants.CoursesHttpErrorCode.COURSE_LAST_PAGE_ACTION_ID:
                    //最后一页
                    showToast(R.string.last_page, 1, null);
                    break;
            }
        }
    }

    @Override
    protected void doNetWorkConnect() {
        //通知Fragment网络已经连接
       if(mFragments[mHomeIndex]!=null)
       {
           mFragments[mHomeIndex].doNetWorkConnect();
       }
    }
}
