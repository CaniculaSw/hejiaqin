package com.chinamobile.hejiaqin.business.ui.courses;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.otto.Subscribe;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.criteria.CourseCriteria;
import com.chinamobile.hejiaqin.business.model.PageableInfo;
import com.chinamobile.hejiaqin.business.model.RequestResult;
import com.chinamobile.hejiaqin.business.model.courses.LectureInfo;
import com.chinamobile.hejiaqin.business.model.courses.ResultMessageAction;
import com.chinamobile.hejiaqin.business.model.event.CoursesLoadDataEvent;
import com.chinamobile.hejiaqin.business.ui.adapter.CourseForumAapter;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.utils.BusProvider;

import java.util.List;

/**
 * desc:康兮讲堂
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/26.
 */
public class ForumFragment extends BasicFragment {

    private CourseForumAapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mPage = 1;
    private PageableInfo mPageableInfo;                         //保存分页信息

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses_forum, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        initData();
        return view;
    }

    private void initData(){
        mAdapter = new CourseForumAapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new LoadMoreListener());  // 下拉加载
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mPage = 1;                  //设置起始于为1
                loadPracticeData(mPage,true);     //加载数据
            }
        });

        loadPracticeData(mPage, true);     //加载数据
    }

    @Override
    public void recieveMsg(Message msg) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getUIBusInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getUIBusInstance().unregister(this);
    }

    /**
     * 加载数据
     */
    private void loadPracticeData(int page,boolean isRefresh){
        CourseCriteria criteria = new CourseCriteria();
        criteria.setCurrentPageno(page);
        CoursesLoadDataEvent event = new CoursesLoadDataEvent();

        if(mAdapter != null && !isRefresh){
            if(mPageableInfo != null){
                //检查是否最后一页
                if (mPageableInfo.getCurrentPageno() >= mPageableInfo.getTotalPages()) {
                    //最后一页，不需要加载
                    event.setActionId(BussinessConstants.CoursesHttpErrorCode.COURSE_LAST_PAGE_ACTION_ID);
                    BusProvider.getUIBusInstance().post(event);
                    return;
                }

            }

            //当前最后一页最后一条记录的ID值(刷新的时候不传)
            criteria.setLastRecordId(mAdapter.getLastId());

            event.setActionId(BussinessConstants.CoursesHttpErrorCode.COURSE_FORUM_SHOW_LOADING_ACTION_ID);
            event.setObj(criteria);
            BusProvider.getUIBusInstance().post(event);
        }else {
            event.setActionId(BussinessConstants.CoursesHttpErrorCode.COURSE_FORUM_ACTION_ID);
            event.setObj(criteria);
            BusProvider.getUIBusInstance().post(event);
        }
    }

    /**
     * 加载更多
     */
    private class LoadMoreListener extends RecyclerView.OnScrollListener {
        //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
        boolean isSlidingToLast = true;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                int lastVisibleItem = manager.findLastVisibleItemPosition();
                int totalItemCount = manager.getItemCount();

                if (lastVisibleItem == (totalItemCount - 1) && totalItemCount > 1 && !isSlidingToLast) {
                    loadPracticeData(mPage,false);     //加载数据
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
            if (dy < 0) {
                //正在向上滚动
                isSlidingToLast = true;
            } else {
                //正在向下滚动
                isSlidingToLast = false;
            }
        }
    }

    /**
     * 监听课程练习加载返回的数据
     */
    @Subscribe
    public void resultCoursesData(ResultMessageAction action) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false); //关闭加载圈
        }

        if (action != null && mAdapter != null) {
            if(action.getActionId() == BussinessConstants.CoursesHttpErrorCode.COURSE_FORUM_ACTION_ID
                    || action.getActionId() == BussinessConstants.CoursesHttpErrorCode.COURSE_FORUM_SHOW_LOADING_ACTION_ID){
                RequestResult result = (RequestResult) action.getResultData();
                mPageableInfo = result.getPageableInfo();

                CourseCriteria criteria = (CourseCriteria) action.getCriteria();
                //请求得到的数据
                List<LectureInfo> infoData = (List<LectureInfo>) result.getResult();
                if (criteria != null && infoData != null) {
                    //根据当前页数判断刷新还是加载数据
                    if (criteria.getCurrentPageno() > 1) {
                        if (infoData.size() > 0) {
                            mPage++;
                            //加载更多
                            mAdapter.loadMoreData(infoData);
                        }
                    } else {
                        //刷新
                        mAdapter.refreshData(infoData);
                        mPage++;
                    }
                }
            }
        }
    }
}
