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
import com.chinamobile.hejiaqin.business.ui.basic.view.sidebar.SideBarView;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;
import com.chinamobile.hejiaqin.business.ui.contact.ContactSearchActivity;
import com.chinamobile.hejiaqin.business.ui.contact.adapter.SysContactAdapter;
import com.customer.framework.component.log.Logger;
import com.customer.framework.utils.LogUtil;

import java.util.List;

/**
 * 系统联系人列表界面
 */
public class SysContactListFragment extends BasicFragment implements View.OnClickListener {

    private static final String TAG = "SysContactListFragment";
    private IContactsLogic contactsLogic;
    private SysContactAdapter adapter;
    private TextView searchText;
    private StickyListHeadersListView contactListView;
    private View empty_view;

    private SideBarView sideBarView;
    private TextView tipText;

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.GET_LOCAL_CONTACTS_SUCCESS_MSG_ID:
                List<ContactsInfo> contactsInfoList = (List<ContactsInfo>) msg.obj;
                adapter.setData(contactsInfoList);
                searchText.setText(String.format(getContext().getString(R.string.contact_search_hint_text), contactsInfoList.size()));
                showView();
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

        contactListView = (StickyListHeadersListView) view.findViewById(R.id.list);

        // 添加搜索框
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View searchLayout = inflater.inflate(R.layout.layout_contact_search_view, null);
        contactListView.addHeaderView(searchLayout);
        // 设置搜索显示的文字
        searchText = (TextView) searchLayout.findViewById(R.id.contact_search_text);
        // 添加点击事件
        searchLayout.findViewById(R.id.contact_search_layout).setOnClickListener(this);

        adapter = new SysContactAdapter(this.getContext());
        contactListView.setAdapter(adapter);

        empty_view = view.findViewById(R.id.empty_view);

        tipText = (TextView) view.findViewById(R.id.tip);
        sideBarView = (SideBarView) view.findViewById(R.id.sidebar);
        sideBarView.setOnLetterSelectListen(new SideBarView.LetterSelectListener() {
            @Override
            public void onLetterSelected(String letter) {
                int position = adapter.getPositionByLetter(letter);
                Logger.i(TAG, "onLetterSelected: " + letter + "; position: "
                        + position);
                tipText.setText(letter);
                tipText.setVisibility(View.VISIBLE);
                if (position >= 0) {
                    contactListView.setSelection(position);
                }
            }

            @Override
            public void onLetterChanged(String letter) {
                int position = adapter.getPositionByLetter(letter);
                Logger.i(TAG, "onLetterChanged: " + letter + "; position: "
                        + position);
                tipText.setText(letter);
                tipText.setVisibility(View.VISIBLE);
                if (position >= 0) {
                    contactListView.setSelection(position);
                }
            }

            @Override
            public void onLetterReleased(String letter) {
                tipText.setVisibility(View.GONE);
            }
        });
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
        Intent intent = new Intent(getContext(), ContactSearchActivity.class);
        intent.putExtra(ContactSearchActivity.Constant.INTENT_DATA_CONTACT_TYPE
                , ContactSearchActivity.Constant.CONTACT_TYPE_SYS);
        startActivity(intent);
    }

    private void showView() {
        if (adapter.isEmpty()) {
            empty_view.setVisibility(View.VISIBLE);
            contactListView.setVisibility(View.GONE);
        } else {
            empty_view.setVisibility(View.GONE);
            contactListView.setVisibility(View.VISIBLE);
        }
    }

}
