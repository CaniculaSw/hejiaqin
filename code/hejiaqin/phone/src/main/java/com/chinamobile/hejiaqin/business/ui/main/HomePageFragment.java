package com.chinamobile.hejiaqin.business.ui.main;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.homePage.IHomePageLogic;
import com.chinamobile.hejiaqin.business.model.homePage.HomeForumInfo;
import com.chinamobile.hejiaqin.business.model.homePage.MyHealthInfo;
import com.chinamobile.hejiaqin.business.model.homePage.MyPracticeInfo;
import com.chinamobile.hejiaqin.business.model.homePage.req.TopCourseInfo;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.person.PersonInfo;
import com.chinamobile.hejiaqin.business.ui.adapter.HomePageCareAapter;
import com.chinamobile.hejiaqin.business.ui.adapter.HomePageForumAapter;
import com.chinamobile.hejiaqin.business.ui.adapter.HomePagePracticeAapter;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:首页
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/22.
 */
public class HomePageFragment extends BasicFragment implements View.OnClickListener {

    private View view;
    private ImageView headerImageView;
    private TextView healthGrowupRankingTv;
    private TextView healthExecNumDescTv;
    private RecyclerView myPracticeRecyclerView;
    private RecyclerView myCareRecyclerView;
    private RecyclerView homeForumRecyclerView;
    private ImageView homePracticeMoreImage;
    private ImageView homeCareMoreImage;
    private ImageView homeForumMoreImage;
    private LinearLayout homePullLineLayout;
    private ImageView homePullImage;
    private TextView homeForumMoreTv;
    private ScrollView homePageScrollView;
    private TextView homePracticeTitleTv;
    private TextView homeCareTitleTv;

    private boolean isCreateView;
    private IHomePageLogic homePageLogic;
    private HomePagePracticeAapter homePagePracticeAapter;
    private HomePageCareAapter homePageCareAapter;
    private HomePageForumAapter homePageForumAapter;
    private boolean isAll;
    boolean isMyPractice = false;
    boolean isMyCare = false;
    boolean loadNetWorkMyHealth = false;
    boolean loadNetWorkMyPractice = false;
    boolean loadNetWorkMycare = false;
    boolean loadNetWorkIndexLecture = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage, container, false);
        Log.d("HomePageFragment", "onCreateView");
        initFragment();
        initView();
        initData();
        isCreateView = true;
        return view;
    }

    @Override
    protected void initLogics()
    {
        homePageLogic = (IHomePageLogic) this.getLogicByInterfaceClass(IHomePageLogic.class);
    }

    private void initFragment() {

    }

    private void initView() {
        headerImageView = (ImageView) view.findViewById(R.id.avatar_image_view);
        healthGrowupRankingTv = (TextView) view.findViewById(R.id.health_growup_ranking_desc_tv);
        healthExecNumDescTv = (TextView) view.findViewById(R.id.health_exec_num_desc_tv);
        myPracticeRecyclerView = (RecyclerView) view.findViewById(R.id.my_practice_recycler_view);
        myCareRecyclerView = (RecyclerView) view.findViewById(R.id.my_care_recycler_view);
        homeForumRecyclerView = (RecyclerView) view.findViewById(R.id.home_forum_recycler_view);

        homePracticeMoreImage = (ImageView) view.findViewById(R.id.home_practice_more_image);
        homeCareMoreImage = (ImageView) view.findViewById(R.id.home_care_more_image);
        homePullLineLayout = (LinearLayout) view.findViewById(R.id.home_pull_line_layout);
        homePullImage = (ImageView) view.findViewById(R.id.home_pull_image);
        homeForumMoreTv = (TextView) view.findViewById(R.id.home_forum_more_tv);
        homeForumMoreImage = (ImageView) view.findViewById(R.id.home_forum_more_image);

        homePageScrollView = (ScrollView) view.findViewById(R.id.home_page_scrollView);
        homePracticeTitleTv = (TextView) view.findViewById(R.id.home_practice_title_tv);
        homeCareTitleTv = (TextView) view.findViewById(R.id.home_care_title_tv);

        homePracticeMoreImage.setOnClickListener(this);
        homeCareMoreImage.setOnClickListener(this);
        homePullLineLayout.setOnClickListener(this);
        homePullImage.setOnClickListener(this);
        homeForumMoreTv.setOnClickListener(this);
        homeForumMoreImage.setOnClickListener(this);

        homePagePracticeAapter = new HomePagePracticeAapter(getContext(), new HomePagePracticeAapter.PopClickListen() {
            @Override
            public void update(MyPracticeInfo info) {
                TopCourseInfo topCourseInfo = new TopCourseInfo();
                topCourseInfo.setId(info.getId());
                topCourseInfo.setPlacedTop(info.getPlacedTop());
                homePageLogic.doTopCourseAction(topCourseInfo);
            }
        });
        myPracticeRecyclerView.setAdapter(homePagePracticeAapter);
        myPracticeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myPracticeRecyclerView.setHasFixedSize(true);

        homePageCareAapter = new HomePageCareAapter(getContext(), new HomePageCareAapter.PopClickListen() {
            @Override
            public void update(MyPracticeInfo info) {
                TopCourseInfo topCourseInfo = new TopCourseInfo();
                topCourseInfo.setId(info.getId());
                topCourseInfo.setPlacedTop(info.getPlacedTop());
                homePageLogic.doTopCourseAction(topCourseInfo);
            }
        });
        myCareRecyclerView.setAdapter(homePageCareAapter);
        myCareRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myCareRecyclerView.setHasFixedSize(true);

        homePageForumAapter = new HomePageForumAapter(getContext());
        homeForumRecyclerView.setAdapter(homePageForumAapter);
        homeForumRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeForumRecyclerView.setHasFixedSize(true);

    }

    private void initData() {
        UserInfo info = CommonUtils.getLocalUserInfo();
        if (info != null) {
            //先做一次头像的刷新
            Picasso.with(this.getContext()).invalidate(info.getAvatar());
            Picasso.with(this.getContext()).load(info.getAvatar()).placeholder(R.mipmap.default_avatar).into(headerImageView);
        }
        //TODO:TEST数据
        testData();
        //TODO:TEST数据
        homePageLogic.getMyHealth();
        homePageLogic.getMyPractice();
        homePageLogic.getMycare();
        homePageLogic.getIndexLecture();
    }

    private void testData() {
        List<MyPracticeInfo> practiceInfoList = new ArrayList<MyPracticeInfo>();
        MyPracticeInfo myPracticeInfo1 = new MyPracticeInfo();
        myPracticeInfo1.setId("1");
        myPracticeInfo1.setTitle("健身跑");
        myPracticeInfo1.setWeeknum(12);
        myPracticeInfo1.setTotalnum(20);
        myPracticeInfo1.setScore(80);
        myPracticeInfo1.setType("1");
        myPracticeInfo1.setPlacedTop("1");
        myPracticeInfo1.setPracticeDate("2016-05-12 14:22:19.0");
        practiceInfoList.add(myPracticeInfo1);

        MyPracticeInfo myPracticeInfo2 = new MyPracticeInfo();
        myPracticeInfo2.setId("2");
        myPracticeInfo2.setTitle("仰卧起坐");
        myPracticeInfo2.setWeeknum(12);
        myPracticeInfo2.setTotalnum(20);
        myPracticeInfo2.setScore(80);
        myPracticeInfo2.setType("1");
        myPracticeInfo2.setPlacedTop("1");
        myPracticeInfo2.setPracticeDate("2016-05-11 14:22:19.0");
        practiceInfoList.add(myPracticeInfo2);

        MyPracticeInfo myPracticeInfo3 = new MyPracticeInfo();
        myPracticeInfo3.setId("3");
        myPracticeInfo3.setTitle("健身走");
        myPracticeInfo3.setWeeknum(12);
        myPracticeInfo3.setTotalnum(20);
        myPracticeInfo3.setScore(80);
        myPracticeInfo3.setType("1");
        myPracticeInfo3.setPlacedTop("0");
        myPracticeInfo3.setPracticeDate("2016-05-16 14:22:19.0");
        practiceInfoList.add(myPracticeInfo3);

        MyPracticeInfo myPracticeInfo4 = new MyPracticeInfo();
        myPracticeInfo4.setId("4");
        myPracticeInfo4.setTitle("俯卧撑");
        myPracticeInfo4.setWeeknum(12);
        myPracticeInfo4.setTotalnum(20);
        myPracticeInfo4.setScore(80);
        myPracticeInfo4.setType("1");
        myPracticeInfo4.setPlacedTop("0");
        myPracticeInfo4.setPracticeDate("2016-05-15 14:30:19.0");
        practiceInfoList.add(myPracticeInfo4);

        MyPracticeInfo myPracticeInfo5 = new MyPracticeInfo();
        myPracticeInfo5.setId("5");
        myPracticeInfo5.setTitle("瑜伽");
        myPracticeInfo5.setParticipants(20);
        myPracticeInfo5.setType("2");
        myPracticeInfo5.setPracticeDate("2016-05-14 14:30:19.0");
        practiceInfoList.add(myPracticeInfo5);

        MyPracticeInfo myPracticeInfo6 = new MyPracticeInfo();
        myPracticeInfo6.setId("6");
        myPracticeInfo6.setTitle("国术");
        myPracticeInfo6.setParticipants(20);
        myPracticeInfo6.setType("2");
        myPracticeInfo6.setPracticeDate("2016-05-13 14:30:19.0");
        practiceInfoList.add(myPracticeInfo6);
        refreshPracticeRecylerView(practiceInfoList);
        refreshCareReyclerView(practiceInfoList);
    }

    public void recieveMsg(Message msg)
    {
        if (!isCreateView) {
            return;
        }
    }

    @Override
    public void handleStateMessage(Message msg) {
        if (!isCreateView) {
            return;
        }
        Object obj = msg.obj;
        switch (msg.what) {
            case BussinessConstants.HomePageMsgID.GET_MY_HEALTH_SUCCESS_MSG_ID:
                if (obj != null) {
                    MyHealthInfo myHealthInfo = (MyHealthInfo) obj;
                    refreshMyHealthInfo(myHealthInfo);
                }
                loadNetWorkMyHealth =true;
                break;
            case BussinessConstants.HomePageMsgID.GET_MY_PRACTICE_SUCCESS_MSG_ID:
                if (obj != null) {
                    List<MyPracticeInfo> practiceInfoList = (List<MyPracticeInfo>) obj;
                    refreshPracticeRecylerView(practiceInfoList);
                }
                loadNetWorkMyPractice =true;
                break;
            case BussinessConstants.HomePageMsgID.GET_MY_CARE_SUCCESS_MSG_ID:
                if (obj != null) {
                    List<MyPracticeInfo> careInfoList = (List<MyPracticeInfo>) obj;
                    refreshCareReyclerView(careInfoList);
                }
                loadNetWorkMycare = true;
                break;
            case BussinessConstants.HomePageMsgID.GET_INDEX_LECTURE_SUCCESS_MSG_ID:
                if (obj != null) {
                    List<HomeForumInfo> homeForumInfoList = (List<HomeForumInfo>) obj;

                    //TODO TEST增加数据量
//                    homeForumInfoList.addAll(homeForumInfoList);

                    homePageForumAapter.refreshData(homeForumInfoList);
                }
                loadNetWorkIndexLecture =true;
                break;
            case BussinessConstants.PersonMsgID.GET_PERSON_INFO_SUCCESS_MSG_ID:
                if (msg.obj != null) {
                    PersonInfo personInfo = (PersonInfo) msg.obj;
                    if (personInfo != null) {
                        Picasso.with(this.getContext()).load(personInfo.getAvatar()).placeholder(R.mipmap.default_avatar).into(headerImageView);
                    };
                }
                break;
            case BussinessConstants.PersonMsgID.UPLOAD_USER_AVATAR_IMAGE_SUCCESS_MSG_ID:
                if (msg.obj != null) {
                    String avatarPath = (String)msg.obj;
                    Picasso.with(this.getContext()).invalidate(avatarPath);
                    Picasso.with(this.getContext()).load(avatarPath).placeholder(R.mipmap.default_avatar).into(headerImageView);
                }
                break;

        }
    }

    private void refreshPracticeRecylerView(List<MyPracticeInfo> practiceInfoList) {
        for(MyPracticeInfo info:practiceInfoList)
        {
            if(BussinessConstants.DictInfo.PRACTICE_TYPE_MY.equals(info.getType()))
            {
                isMyPractice =true;
                break;
            }
        }
        if(isMyPractice)
        {
            homePracticeTitleTv.setText(R.string.home_practice_my_title);
        }
        else
        {
            homePracticeTitleTv.setText(R.string.home_practice_recommend_title);
        }
        //TODO TEST增加数据量
//        practiceInfoList.addAll(practiceInfoList);

        homePagePracticeAapter.refreshData(practiceInfoList);
        //刷新布局高度
        ViewGroup.LayoutParams layoutParams = myPracticeRecyclerView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        myPracticeRecyclerView.setLayoutParams(layoutParams);
    }

    private void refreshCareReyclerView(List<MyPracticeInfo> careInfoList) {
        for(MyPracticeInfo info:careInfoList)
        {
            if(BussinessConstants.DictInfo.PRACTICE_TYPE_MY.equals(info.getType()))
            {
                isMyCare =true;
                break;
            }
        }
        if(isMyCare)
        {
            homeCareTitleTv.setText(R.string.home_care_my_title);
        }
        else
        {
            homeCareTitleTv.setText(R.string.home_care_recommend_title);
        }
        //TODO TEST增加数据量
//        careInfoList.addAll(careInfoList);

        homePageCareAapter.refreshData(careInfoList);
        //刷新布局高度
        ViewGroup.LayoutParams layoutParams = myCareRecyclerView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        myCareRecyclerView.setLayoutParams(layoutParams);
    }

    private void refreshMyHealthInfo(MyHealthInfo myHealthInfo) {
        String ranking = myHealthInfo.getRanking() != null ? myHealthInfo.getRanking() : "";
        StringBuffer str = new StringBuffer();
        str.append(getString(R.string.health_growup_ranking_desc_one));
        str.append(myHealthInfo.getGrowup());
        str.append(getString(R.string.health_growup_ranking_desc_two));
        str.append(ranking);
        str.append(getString(R.string.health_growup_ranking_desc_three));
        healthGrowupRankingTv.setText(str.toString());
        str = new StringBuffer();
        str.append(getString(R.string.health_exec_num_desc_one));
        str.append(myHealthInfo.getWeeknum());
        str.append(getString(R.string.health_exec_num_desc_two));
        str.append(myHealthInfo.getNownum());
        str.append(getString(R.string.health_exec_num_desc_three));
        healthExecNumDescTv.setText(str.toString());
        str =null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_practice_more_image:
                if(isMyPractice)
                {
                    //TODO 后续修改为我的
                    mListener.onAction(BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_PRACTICE_ID, null);
                }
                else
                {
                    mListener.onAction(BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_PRACTICE_ID, null);
                }
                break;
            case R.id.home_care_more_image:
                if(isMyCare)
                {
                    //TODO 后续修改为我的
                    mListener.onAction(BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_CARE_ID, null);
                }
                else
                {
                    mListener.onAction(BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_CARE_ID, null);
                }
                break;
            case R.id.home_pull_line_layout:
            case R.id.home_pull_image:
                if (!isAll) {
                    boolean practiceDowmn = homePagePracticeAapter.pullDown();
                    boolean careDown = homePageCareAapter.pullDown();
                    if (practiceDowmn || careDown) {
                        homePullImage.setImageResource(R.mipmap.home_push_up_button);
                        isAll = true;
                    }
                } else {
                    boolean practiceUp = homePagePracticeAapter.pushUp();
                    boolean careUp = homePageCareAapter.pushUp();
                    if (practiceUp || careUp) {
                        homePullImage.setImageResource(R.mipmap.home_pull_down_button);
                        isAll = false;
                        //滑动到顶部
                        homePageScrollView.scrollTo(0, 0);
                    }
                }
                break;
            case R.id.home_forum_more_tv:
                mListener.onAction(BussinessConstants.FragmentActionId.COURSES_SWITCH_FORUM_ID, null);
                break;
            case R.id.home_forum_more_image:
                mListener.onAction(BussinessConstants.FragmentActionId.COURSES_SWITCH_FORUM_ID, null);
                break;
        }
    }

    public void doNetWorkConnect() {
        UserInfo info = CommonUtils.getLocalUserInfo();
        if (info != null) {
            Picasso.with(this.getContext()).load(info.getAvatar()).placeholder(R.mipmap.default_avatar).into(headerImageView);
        }
        if(!loadNetWorkMyHealth) {
            homePageLogic.getMyHealth();
        }
        if(!loadNetWorkMyPractice) {
            homePageLogic.getMyPractice();
        }
        if(!loadNetWorkMycare) {
            homePageLogic.getMycare();
        }
        if(!loadNetWorkIndexLecture) {
            homePageLogic.getIndexLecture();
        }
    }

}
