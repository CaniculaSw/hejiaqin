package com.chinamobile.hejiaqin.business.ui.more;

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
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;
import com.chinamobile.hejiaqin.business.ui.more.adapter.SelectContactAndBindAdapter;

import java.util.List;

/**
 * Created by eshaohu on 16/11/14.
 */
public class SelectContactAndBindActivity extends BasicActivity implements View.OnClickListener {
    private static final String TAG = "SelectContactAndBindActivity";
    private IContactsLogic contactsLogic;
    private StickyListHeadersListView contactListView;
    private SelectContactAndBindAdapter adapter;
    private TextView searchText;
    private TextView tipText;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_contact_and_bind;
    }

    @Override
    protected void initView() {
        Context context = this;

        contactListView = (StickyListHeadersListView) findViewById(R.id.list);

        // 添加搜索框
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View searchLayout = inflater.inflate(R.layout.layout_contact_search_view, null);
        contactListView.addHeaderView(searchLayout);
        // 设置搜索显示的文字
        searchText = (TextView) searchLayout.findViewById(R.id.contact_search_text);
        // 添加点击事件
        searchLayout.findViewById(R.id.contact_search_layout).setOnClickListener(this);

        // 添加adapter
        adapter = new SelectContactAndBindAdapter(context);
        contactListView.setAdapter(adapter);

        tipText = (TextView) findViewById(R.id.tip);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) this.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID:
                List<ContactsInfo> contactsInfoList = (List<ContactsInfo>) msg.obj;
                adapter.setData(contactsInfoList);
                searchText.setText(String.format(this.getString(R.string.contact_search_hint_text), contactsInfoList.size()));
                break;
        }
    }

    @Override
    protected void initDate() {
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
                enterSearchView();
                break;
        }
    }

    private void enterSearchView() {
        Intent intent = new Intent(this, MoreContactSearchActivity.class);
        intent.putExtra(MoreContactSearchActivity.Constant.INTENT_DATA_CONTACT_TYPE
                , MoreContactSearchActivity.Constant.CONTACT_TYPE_APP);
        startActivity(intent);
    }


}
