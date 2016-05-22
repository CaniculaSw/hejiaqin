package com.chinamobile.hejiaqin.business.ui.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.model.setting.PageInfo;
import com.chinamobile.hejiaqin.business.model.setting.PageInfoObject;
import com.chinamobile.hejiaqin.business.model.setting.UserQuestionInfo;
import com.chinamobile.hejiaqin.business.ui.adapter.QuestionAdapter;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.utils.StringUtil;

import java.util.List;

/**
 * 帮助与反馈页面
 * Created by wubg on 2016/5/5.
 */
public class HelpActivity extends BasicActivity implements View.OnClickListener {

    private ISettingLogic settingLogic;

    private HeaderView headerView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private QuestionAdapter mAdapter;
    private PageInfo mPageInfo;

    private int mPage = 1;                              //当前页数

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView)findViewById(R.id.header_view);
        headerView.title.setText(R.string.help_title);
        headerView.backImageView.setImageResource(R.mipmap.back);
        headerView.rightBtn.setText(R.string.help_feedback_button);
        headerView.rightBtn.setTextColor(Color.WHITE);
        mRecyclerView = (RecyclerView) findViewById(R.id.question_list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addOnScrollListener(new LoadMoreListener());  // 下拉加载
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mPage = 1;                  //设置起始于为1
                loadHelpData(mPage,true);     //加载数据
            }
        });

        mAdapter = new QuestionAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        loadHelpData(mPage,true);
    }

    @Override
    protected void initDate() {
    }

    @Override
    protected void initListener() {
        headerView.backLayout.setOnClickListener(this);
        headerView.rightBtn.setOnClickListener(this);
    }

    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    @Override
    protected void initLogics() {
        settingLogic = (ISettingLogic) this.getLogicByInterfaceClass(ISettingLogic.class);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                //回退
                finish();
                break;
            case R.id.right_btn:
                startActivity(new Intent(HelpActivity.this, FeedbackActivity.class));
                break;
        }
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false); //关闭加载圈
        }
        dismissWaitDailog();

        switch (msg.what) {
            case BussinessConstants.SettingMsgID.HELP_QUESTION_LIST_SUCCESS_MSG_ID:
                PageInfoObject pio = (PageInfoObject)msg.obj;
                List<UserQuestionInfo> list = (List) pio.getPageData();
                mPageInfo = pio.getPageInfo();
                setUpData(list);
                break;
            case BussinessConstants.SettingMsgID.HELP_QUESTION_LIST_FAIL_MSG_ID:
                break;
            default:
                break;
        }
    }

    private void setUpData(List<UserQuestionInfo> list){
        if(mAdapter != null){
            //根据当前页数判断刷新还是加载数据
            if (mPage > 1) {
                if (list != null && list.size() > 0) {
                    mPage++;
                    //加载更多
                    mAdapter.loadMoreData(list);
                }
            } else {
                //刷新
                mAdapter.refreshData(list);
                mPage++;
            }
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
                    loadHelpData(mPage,false);     //加载数据
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

    private void loadHelpData(int page,boolean isRefresh) {
        String lastId = "";
        if(mAdapter != null && !isRefresh) {
            if(mPageInfo != null){
                //检查是否最后一页
                if(!StringUtil.isNullOrEmpty(mPageInfo.getCurrentPageno()) && !StringUtil.isNullOrEmpty(mPageInfo.getTotalPages())){
                    try {
                        int currentPageno = Integer.parseInt(mPageInfo.getCurrentPageno());
                        int totalPages = Integer.parseInt(mPageInfo.getTotalPages());
                        if (currentPageno >= totalPages) {
                            //最后一页，不需要加载
                            showToast(R.string.last_page, 1, null);
                            return;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            //当前最后一页最后一条记录的ID值(刷新的时候不传)
            lastId = mAdapter.getLastId();
            showWaitDailog();
        }
        settingLogic.helpQuestionList(BussinessConstants.CommonInfo.DEFAULT_PAGE_SIZE, page, lastId);
    }
}
