package com.chinamobile.hejiaqin.business.ui.myMessageFragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageInfo;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageParame;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageResutl;
import com.chinamobile.hejiaqin.business.model.setting.PageInfo;
import com.chinamobile.hejiaqin.business.ui.adapter.MessageAapter;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.customer.framework.utils.StringUtil;

import java.util.List;

/**
 * Created by wubg on 2016/5/4.
 */
public class MessageFragmentAll extends BasicFragment {

    private int mPage = 1;                              //当前页数
    private RecyclerView recycler_view;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MessageAapter mAdapter;
    private PageInfo mPageInfo;                         //保存分页信息

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_message_all, null);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new MessageAapter(getActivity());
        recycler_view.setAdapter(mAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.setHasFixedSize(true);
        recycler_view.addOnScrollListener(new LoadMoreListener());  // 下拉加载
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mPage = 1;                  //设置起始于为1
                loadMessageData(mPage,true);     //加载数据
            }
        });

            loadMessageData(mPage,true);     //加载数据
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
                    loadMessageData(mPage,false);     //加载数据
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
     * 接收父类(FragmentActivity或者BaseFragment)的消息
     * 刷新我的消息数据
     * @param msg
     */
    @Override
    public void recieveMsg(Message msg) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false); //关闭加载圈
        }
        if (msg != null && mAdapter != null) {
            if(msg.what == BussinessConstants.SettingMsgID.DEL_MESSAGE_SUCCESS_MSG_ID){
                mAdapter.deleteData(msg.obj.toString());
                return;
            }
            AppMessageResutl resutl = (AppMessageResutl) msg.obj;
            mPageInfo = resutl.getPageInfo();
            AppMessageParame parame = resutl.getParame();
            //请求得到的数据
            List<AppMessageInfo> infoData = resutl.getMsgData();
            if (parame != null && infoData != null) {
                //根据当前页数判断刷新还是加载数据
                if (parame.getCurrent_page() > 1) {
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

    private void loadMessageData(int page,boolean isRefresh) {
        if (mListener != null) {
            AppMessageParame parame = new AppMessageParame();
            parame.setCurrent_page(page);
            parame.setQueryType(2);       // 全部类型
            if(mAdapter != null && !isRefresh){
                if(mPageInfo != null){
                    //检查是否最后一页
                    if(!StringUtil.isNullOrEmpty(mPageInfo.getCurrentPageno()) && !StringUtil.isNullOrEmpty(mPageInfo.getTotalPages())){
                        try {
                            int currentPageno = Integer.parseInt(mPageInfo.getCurrentPageno());
                            int totalPages = Integer.parseInt(mPageInfo.getTotalPages());
                            if (currentPageno >= totalPages) {
                                //最后一页，不需要加载
                                mListener.onAction(BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_LAST_PAGE_ACTION_ID, null);
                                return;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                //当前最后一页最后一条记录的ID值(刷新的时候不传)
                parame.setLast_record_id(mAdapter.getLastId());
                mListener.onAction(BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_ALL_SHOW_LOADING_ACTION_ID, parame);
            }else {
                mListener.onAction(BussinessConstants.SettingHttpErrorCode.MESSAGE_FRAGMENT_ALL_ACTION_ID, parame);
            }
        }
    }


    public int getDataCount(){
        int count = 0;
        if(mAdapter != null){
            count = mAdapter.getItemCount();
        }
        return count;
    }
}
