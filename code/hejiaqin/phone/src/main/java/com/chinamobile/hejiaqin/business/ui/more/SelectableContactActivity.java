package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.SearchResultContacts;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.basic.view.SelectableSearchView;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;
import com.chinamobile.hejiaqin.business.ui.more.adapter.SelectContactAdapter;
import com.chinamobile.hejiaqin.business.ui.more.adapter.SelectableSearchContactAdapter;

import java.util.List;


/**
 * Created by eshaohu on 16/6/6.
 */
public class SelectableContactActivity extends BasicActivity implements View.OnClickListener {

    private static final String TAG = "SelectableContactActivity";

    private HeaderView mHeaderView;
    private Context mContext;
    private StickyListHeadersListView mContactListView;
    private SelectableSearchView searchView;
    private TextView searchText;
    private SelectContactAdapter adapter;
    private SelectableSearchContactAdapter searchAdapter;
    private IContactsLogic contactsLogic;
    private TextView mSelectAll;
    private TextView mSelectCount;
    private int mSelectedContactNum;

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID:
                List<ContactsInfo> contactsInfoList = (List<ContactsInfo>) msg.obj;
                adapter.setData(contactsInfoList);
                searchView.setHint(String.format(getString(R.string.contact_search_hint_text), contactsInfoList.size()));
                searchText.setText(String.format(getString(R.string.contact_search_hint_text), contactsInfoList.size()));
                break;
            case BussinessConstants.ContactMsgID.SEARCH_LOCAL_CONTACTS_SUCCESS_MSG_ID:
                SearchResultContacts resultContacts = (SearchResultContacts) msg.obj;
                if(TAG.equals(resultContacts.getInvoker())) {
                    searchView.setData(resultContacts.getContactsInfos());
                }
                break;
            case BussinessConstants.SettingMsgID.CONTACT_CHECKED_STATED_CHANGED:
                updateSelectedHint(msg.arg1);
                if (msg.arg1 == msg.arg2) {
                    mSelectAll.setText(getString(R.string.more_cancle_select_all));
                } else {
                    if (mSelectAll.getText().equals(getString(R.string.more_cancle_select_all))) {
                        mSelectAll.setText(getString(R.string.more_select_all));
                    }
                }
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_contact;
    }

    @Override
    protected void initView() {
        mContext = getApplicationContext();
        mHeaderView = (HeaderView) findViewById(R.id.more_select_contact_header);
        mHeaderView.title.setText(R.string.more_choose_contact);
        mHeaderView.backImageView.setImageResource(R.mipmap.title_icon_close);
        mHeaderView.rightBtn.setImageResource(R.mipmap.title_icon_check_nor);

        mContactListView = (StickyListHeadersListView) findViewById(R.id.list);

        // 添加搜索框
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View searchLayout = inflater.inflate(R.layout.layout_contact_search_view, null);
        mContactListView.addHeaderView(searchLayout);
        // 设置搜索显示的文字
        searchText = (TextView) searchLayout.findViewById(R.id.contact_search_text);
        // 添加点击事件
        searchLayout.findViewById(R.id.contact_search_layout).setOnClickListener(this);

        searchView = (SelectableSearchView) findViewById(R.id.more_search_view);
        searchView.setListener(new SelectableSearchView.ISearchListener() {
            @Override
            public void search(String input) {

                contactsLogic.searchLocalContactLst(input,TAG);
            }

            @Override
            public void cancel() {

                enterNormalView();
            }
        });
        adapter = new SelectContactAdapter(this, getHandler());
        mContactListView.setAdapter(adapter);
        searchAdapter = new SelectableSearchContactAdapter(this, getHandler());
        searchView.setAdapter(searchAdapter);
        mSelectCount = (TextView) findViewById(R.id.more_chosen);
        mSelectAll = (TextView) findViewById(R.id.more_select_all);
    }

    @Override
    protected void initDate() {
        contactsLogic.fetchAppContactLst();
        mSelectedContactNum = 0;
        updateSelectedHint(mSelectedContactNum);
    }

    @Override
    protected void initListener() {
        mHeaderView.backImageView.setClickable(true);
        mHeaderView.backImageView.setOnClickListener(this);
        mHeaderView.rightBtn.setClickable(true);
        mHeaderView.rightBtn.setOnClickListener(this);
        mSelectAll.setClickable(true);
        mSelectAll.setOnClickListener(this);
    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) this.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact_search_layout:
                // TODO
                enterSearchView();
                break;
            case R.id.more_select_all:
                adapter.selectAll(mSelectAll.getText().equals(getString(R.string.more_select_all)));
                break;
            default:
                break;
        }
    }

    private void enterSearchView() {
        // 显示searchView
        searchView.setVisibility(View.VISIBLE);
    }

    private void enterNormalView() {
        // 隐藏searchView
        searchView.setVisibility(View.GONE);
    }


    private void updateSelectedHint(int num) {
        mSelectCount.setText(getString(R.string.more_chosen, num));
    }
}
