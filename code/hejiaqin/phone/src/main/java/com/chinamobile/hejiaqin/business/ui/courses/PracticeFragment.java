package com.chinamobile.hejiaqin.business.ui.courses;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.criteria.CourseCriteria;
import com.chinamobile.hejiaqin.business.manager.CourseCacheManager;
import com.chinamobile.hejiaqin.business.model.PageableInfo;
import com.chinamobile.hejiaqin.business.model.RequestResult;
import com.chinamobile.hejiaqin.business.model.courses.ResultMessageAction;
import com.chinamobile.hejiaqin.business.model.courses.PracticeInfo;
import com.chinamobile.hejiaqin.business.model.event.CoursesLoadDataEvent;
import com.chinamobile.hejiaqin.business.ui.adapter.CoursePracticeAapter;
import com.chinamobile.hejiaqin.business.ui.adapter.CoursePracticeSelectAdapter;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.utils.BusProvider;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.utils.StringUtil;

import java.util.List;

/**
 * desc: 练习课程
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/26.
 */
public class PracticeFragment extends BasicFragment {

    private static final int PRACTICE_SELECT_RECOMMEND_INDEX = 1;
    private LinearLayout layout_div_select;
    private TextView tv_text_select;
    private ImageView iv_icon_select;
    private String[] mCourseList;
    private PopupWindow pop;
    private RecyclerView mPopRecyclerView;
    private CoursePracticeSelectAdapter mPopAdapter;
    private View view_line;
    private ClickListener mClickListener;
    private CoursePracticeAapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mPage = 1;
    private PageableInfo mPageableInfo;                         //保存分页信息
    private boolean isCreateView;
    private Message switchSelectMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses_practice, container, false);
        layout_div_select = (LinearLayout) view.findViewById(R.id.layout_div_courses_select);
        tv_text_select = (TextView) view.findViewById(R.id.tv_courses_practice_select);
        iv_icon_select = (ImageView) view.findViewById(R.id.iv_courses_practice_select);
        view_line = (View) view.findViewById(R.id.view_line_course_practiice);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        initData();


        String text = StorageMgr.getInstance().getSharedPStorage(getActivity().getApplicationContext()).getString(BussinessConstants.Courses.COURSE_PRACTICE_SELECT_TEXT);
        tv_text_select.setText(StringUtil.isNullOrEmpty(text) ? getResources().getString(R.string.all_practice) : text);

        mCourseList = getActivity().getResources().getStringArray(R.array.course_practice_list);

        mClickListener = new ClickListener();
        layout_div_select.setOnClickListener(mClickListener);
        mPopAdapter = new CoursePracticeSelectAdapter(getActivity(), mCourseList, mClickListener);
        initPopupWindow();
        if(switchSelectMsg!=null)
        {
            switchSelectFromHomePage(switchSelectMsg);
        }
        isCreateView=true;
        return view;
    }


    private void initData() {
        mAdapter = new CoursePracticeAapter(getActivity());
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
        if (!isCreateView) {
            //没有创建前先缓存消息
            switch (msg.what) {
                //首页查看推荐操作
                case BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_PRACTICE_ID:
                    switchSelectMsg = msg;
                    break;
            }
            return;
        }
        switchSelectFromHomePage(msg);
    }


    //首页查看切换通知
    private void switchSelectFromHomePage(Message msg) {
        switch (msg.what)
        {
            case BussinessConstants.FragmentActionId.COURSES_SWITCH_RECOMMEND_PRACTICE_ID:
                int index = StorageMgr.getInstance().getSharedPStorage(getActivity().getApplicationContext()).getInt(BussinessConstants.Courses.COURSE_PRACTICE_SELECT_INDEX);
                //如果不是推荐的就切换到推荐上面
                if(index != PRACTICE_SELECT_RECOMMEND_INDEX)
                {
                    CourseCacheManager.saveCoursePracticeToLoacl(getActivity().getApplicationContext(), PRACTICE_SELECT_RECOMMEND_INDEX, mCourseList[PRACTICE_SELECT_RECOMMEND_INDEX]);
                    mPopAdapter.notifyDataSetChanged();
                    tv_text_select.setText(mCourseList[PRACTICE_SELECT_RECOMMEND_INDEX]);
                    mPage = 1;                  //设置起始于为1
                    loadPracticeData(mPage, true);     //加载数据
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
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getUIBusInstance().unregister(this);
    }

    /**
     * 加载数据
     */
    private void loadPracticeData(int page,boolean isRefresh) {
        CourseCriteria criteria = new CourseCriteria();
        criteria.setCurrentPageno(page);

        int index = StorageMgr.getInstance().getSharedPStorage(getActivity().getApplicationContext()).getInt(BussinessConstants.Courses.COURSE_PRACTICE_SELECT_INDEX);

        switch (index) {
            case PRACTICE_SELECT_RECOMMEND_INDEX:
                criteria.setMovementType("-1");       //推荐练习
                break;
            case 2:
                criteria.setMovementType("1");       // 有氧练习
                break;
            case 3:
                criteria.setMovementType("3");       // 瑜伽
                break;
            case 4:
                criteria.setMovementType("2");       // 形体练习
                break;
            case 5:
                criteria.setMovementType("4");       // 中华国术
                break;
        }

        CoursesLoadDataEvent event = new CoursesLoadDataEvent();
        if (mAdapter != null && !isRefresh) {
            if (mPageableInfo != null) {
                //检查是否最后一页, 如果是最后一页则不需要加载
                if (mPageableInfo.getCurrentPageno() >= mPageableInfo.getTotalPages()) {
                    event.setActionId(BussinessConstants.CoursesHttpErrorCode.COURSE_LAST_PAGE_ACTION_ID);
                    BusProvider.getUIBusInstance().post(event);
                    return;
                }
                //当前最后一页最后一条记录的ID值(刷新的时候不传)
                criteria.setLastRecordId(mAdapter.getLastId());
                event.setActionId(BussinessConstants.CoursesHttpErrorCode.COURSE_PRACTICE_SHOW_LOADING_ACTION_ID);
                event.setObj(criteria);
            }
        } else {
            event.setActionId(BussinessConstants.CoursesHttpErrorCode.COURSE_PRACTICE_ACTION_ID);
            event.setObj(criteria);
        }
        BusProvider.getUIBusInstance().post(event);
    }

    class ClickListener implements View.OnClickListener, CoursePracticeSelectAdapter.OnCoursePracticeChangeListener {

        @Override
        public void onClick(View v) {
            switchPop();
        }

        @Override
        public void onSelectChange(int position, String text) {
            switchPop();
            //刷新数据
            tv_text_select.setText(text);
            mPage = 1;                  //设置起始于为1
            loadPracticeData(mPage,true);     //加载数据
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


    private void switchPop() {
        if (pop.isShowing()) {
            // 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏
            pop.dismiss();
            iv_icon_select.setImageResource(R.mipmap.ic_down);
        } else {
            // 显示窗口
            pop.showAsDropDown(view_line);
            iv_icon_select.setImageResource(R.mipmap.ic_up);
        }
    }

    /**
     * 初始化课程选择窗口
     */
    private void initPopupWindow() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        // 引入窗口配置文件
        View view = inflater.inflate(R.layout.recycler_list, null);
        mPopRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mPopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPopRecyclerView.setAdapter(mPopAdapter);
        // 创建PopupWindow对象
        pop = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, false);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iv_icon_select.setImageResource(R.mipmap.ic_down);
            }
        });
    }


    /**
     * 监听课程练习加载返回的数据
     */
    @Subscribe
    public void courseLoaidngMonitor(ResultMessageAction action) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false); //关闭加载圈
        }

        if (action != null && mAdapter != null) {
            if (action.getActionId() == BussinessConstants.CoursesHttpErrorCode.COURSE_PRACTICE_ACTION_ID ||
                action.getActionId() == BussinessConstants.CoursesHttpErrorCode.COURSE_PRACTICE_SHOW_LOADING_ACTION_ID) {
                RequestResult requestResult = (RequestResult) action.getResultData();
                mPageableInfo = requestResult.getPageableInfo();

                //查询参数
                CourseCriteria criteria = (CourseCriteria) action.getCriteria();

                //请求得到的数据
                List<PracticeInfo> infoData = (List<PracticeInfo>) requestResult.getResult();
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
