package com.chinamobile.hejiaqin.business.ui.contact.fragment;

import android.content.Context;
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
import com.chinamobile.hejiaqin.business.ui.contact.adapter.AppContactAdapter;
import com.customer.framework.utils.LogUtil;

import java.util.List;

/**
 * 应用联系人列表界面
 */
public class AppContactListFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "AppContactListFragment";
    private IContactsLogic contactsLogic;
    private AppContactAdapter adapter;
    private SearchView searchView;
    private TextView searchText;

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID:
                List<ContactsInfo> contactsInfoList = (List<ContactsInfo>) msg.obj;
                adapter.setData(contactsInfoList);
                searchView.setHint(String.format(getContext().getString(R.string.contact_search_hint_text), contactsInfoList.size()));
                searchText.setText(String.format(getContext().getString(R.string.contact_search_hint_text), contactsInfoList.size()));
                break;
            case BussinessConstants.ContactMsgID.SEARCH_APP_CONTACTS_SUCCESS_MSG_ID:
                searchView.setData((List<ContactsInfo>) msg.obj);
                break;
        }
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
                
            }
        });

        // 添加adapter
        adapter = new AppContactAdapter(context);
        contactListView.setAdapter(adapter);
    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) this.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    protected void initData() {
        contactsLogic.fetchAppContactLst();
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
