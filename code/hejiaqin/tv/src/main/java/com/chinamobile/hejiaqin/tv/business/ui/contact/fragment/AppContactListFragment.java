package com.chinamobile.hejiaqin.tv.business.ui.contact.fragment;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.tv.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.tv.business.ui.basic.view.stickylistview.StickyListHeadersListView;
import com.chinamobile.hejiaqin.tv.business.ui.contact.adapter.AppContactAdapter;
import com.customer.framework.utils.LogUtil;

/**
 * 应用联系人列表界面
 */
public class AppContactListFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "AppContactListFragment";

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_app_contact_list;
    }

    @Override
    protected void initView(View view) {
        Context context = getContext();

        StickyListHeadersListView contactListView = (StickyListHeadersListView) view.findViewById(R.id.list);

        // 添加搜索框
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View searchView = inflater.inflate(R.layout.layout_contact_search_view, null);
        contactListView.addHeaderView(searchView);
        // 设置搜索显示的文字
        TextView searchText = (TextView) searchView.findViewById(R.id.contact_search_text);
        searchText.setText(String.format(context.getString(R.string.contact_search_hint_text), "100"));
        // 添加点击事件
        searchView.findViewById(R.id.contact_search_layout).setOnClickListener(this);

        // 添加adapter
        AppContactAdapter adapter = new AppContactAdapter(context);
        contactListView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact_search_layout:
                // TODO
                LogUtil.d(TAG, "start search");
                break;
        }
    }
}
