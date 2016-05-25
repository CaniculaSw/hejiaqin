package com.chinamobile.hejiaqin.business.ui.more.fragment;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;
import com.chinamobile.hejiaqin.business.ui.more.adapter.MoreMissCallAdapter;

/**
 * Created by eshaohu on 16/5/25.
 */
public class MissCallListFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "MissCallListFragment";
    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sys_message_miss_call_list;
    }

    @Override
    protected void initView(View view) {
        Context context = getContext();

        StickyListHeadersListView msgListView = (StickyListHeadersListView) view.findViewById(R.id.sys_message_list);

        // 添加搜索框
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        // 添加adapter
        MoreMissCallAdapter adapter = new MoreMissCallAdapter(context);
        msgListView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
