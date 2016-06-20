package com.chinamobile.hejiaqin.business.ui.contact.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.SearchView;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;
import com.chinamobile.hejiaqin.business.ui.contact.adapter.SysContactAdapter;
import com.customer.framework.utils.LogUtil;

import java.util.List;

/**
 * 系统联系人列表界面
 */
public class SysContactListFragment extends BasicFragment implements View.OnClickListener {

    private static final String TAG = "SysContactListFragment";
    private IContactsLogic contactsLogic;
    private SysContactAdapter adapter;
    private SearchView searchView;
    private TextView searchText;

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.GET_LOCAL_CONTACTS_SUCCESS_MSG_ID:
                List<ContactsInfo> contactsInfoList = (List<ContactsInfo>) msg.obj;
                adapter.setData(contactsInfoList);
                searchView.setHint(String.format(getContext().getString(R.string.contact_search_hint_text), contactsInfoList.size()));
                searchText.setText(String.format(getContext().getString(R.string.contact_search_hint_text), contactsInfoList.size()));
                break;
            case BussinessConstants.ContactMsgID.SEARCH_LOCAL_CONTACTS_SUCCESS_MSG_ID:
                searchView.setData((List<ContactsInfo>) msg.obj);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_sys_contact_list;
    }

    @Override
    protected void initView(View view) {
        Context context = getContext();

        StickyListHeadersListView contactListView = (StickyListHeadersListView) view.findViewById(R.id.list);

        // 添加搜索框
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View searchLayout = inflater.inflate(R.layout.layout_contact_search_view, null);
        contactListView.addHeaderView(searchLayout);
        // 设置搜索显示的文字
        searchText = (TextView) searchLayout.findViewById(R.id.contact_search_text);
        // 添加点击事件
        searchLayout.findViewById(R.id.contact_search_layout).setOnClickListener(this);

        searchView = (SearchView) view.findViewById(R.id.search_view);
        searchView.setListener(new SearchView.ISearchListener() {
            @Override
            public void search(String input) {
                contactsLogic.searchLocalContactLst(input);
            }

            @Override
            public void cancel() {
                enterNormalView();
            }
        });

        adapter = new SysContactAdapter(this.getContext());
        contactListView.setAdapter(adapter);
    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) this.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    protected void initData() {
        contactsLogic.fetchLocalContactLst();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact_search_layout:
                // TODO
                LogUtil.d(TAG, "start search");
                enterSearchView();
                break;
        }
    }

    private void enterSearchView() {
        // 隐藏title
        this.mListener.onAction(BussinessConstants.ContactMsgID.UI_HIDE_CCONTACT_LIST_TITLE_ID, null);
        // 显示searchView
        searchView.setVisibility(View.VISIBLE);
    }

    private void enterNormalView() {
        // 显示title
        this.mListener.onAction(BussinessConstants.ContactMsgID.UI_SHOW_CCONTACT_LIST_TITLE_ID, null);
        // 隐藏searchView
        searchView.setVisibility(View.GONE);
    }
}
