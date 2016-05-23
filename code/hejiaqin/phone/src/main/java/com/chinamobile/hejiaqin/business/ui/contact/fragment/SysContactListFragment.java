package com.chinamobile.hejiaqin.business.ui.contact.fragment;

import android.os.Message;
import android.view.View;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;
import com.chinamobile.hejiaqin.business.ui.contact.adapter.AppContactAdapter;

/**
 *
 *
 */
public class SysContactListFragment extends BasicFragment {

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sys_contact_list;
    }

    @Override
    protected void initView(View view) {
        StickyListHeadersListView stickyList = (StickyListHeadersListView) view.findViewById(R.id.list);
        AppContactAdapter adapter = new AppContactAdapter(this.getContext());
        stickyList.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }
}
