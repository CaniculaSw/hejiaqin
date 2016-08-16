package com.chinamobile.hejiaqin.business.ui.contact.fragment;

import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.SearchResultContacts;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.contact.adapter.SearchContactAdapter;
import com.chinamobile.hejiaqin.tv.R;

import java.util.ArrayList;
import java.util.List;

public class ContactSearchFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "ContactInfoFragment";

    private RelativeLayout contactInfoLayout;
    private HeaderView titleLayout;

    private EditText searchInput;
    private View searchDelete;
    private ListView contactsListView;
    private SearchContactAdapter adapter;

    private IContactsLogic contactsLogic;


    public static ContactSearchFragment newInstance() {
        ContactSearchFragment fragment = new ContactSearchFragment();
        return fragment;
    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) this.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.SEARCH_LOCAL_CONTACTS_SUCCESS_MSG_ID:
            case BussinessConstants.ContactMsgID.SEARCH_APP_CONTACTS_SUCCESS_MSG_ID:
                SearchResultContacts resultContacts = (SearchResultContacts) msg.obj;
                if (TAG.equals(resultContacts.getInvoker())) {
                    setData(resultContacts.getContactsInfos());
                }
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_contact_search;
    }

    @Override
    protected void initView(View view) {
        contactInfoLayout = (RelativeLayout) view.findViewById(R.id.contact_info_layout);

        // title
        titleLayout = (HeaderView) view.findViewById(R.id.title);
        titleLayout.title.setText("");
        titleLayout.backImageView.setImageResource(R.mipmap.title_icon_back_nor);

        // 搜索框
        searchInput = (EditText) view.findViewById(R.id.search_input);
        searchDelete = view.findViewById(R.id.search_del);

        // 搜索结果列表
        contactsListView = (ListView) view.findViewById(R.id.list);
        adapter = new SearchContactAdapter(getContext());
        contactsListView.setAdapter(adapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean hasInput = s.length() > 0;
                searchDelete.setVisibility(hasInput ? View.VISIBLE : View.INVISIBLE);
                if (hasInput) {
                    startSearch(s.toString().trim());
                } else {
                    setData(null);
                }
            }
        });

        searchDelete.setOnClickListener(this);
        titleLayout.backImageView.setOnClickListener(this);
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
            case R.id.back_iv:
                FragmentMgr.getInstance().finishContactFragment(this);
                break;
            case R.id.search_del:
                searchInput.setText("");
                break;
        }
    }

    private void startSearch(String input) {
        contactsLogic.searchAppContactLst(input, TAG);
    }

    public void setData(List<ContactsInfo> contactsInfoList) {
        List<ContactsInfo> tmpContactsInfoList = new ArrayList<>();
        if (null != contactsInfoList) {
            tmpContactsInfoList.addAll(contactsInfoList);
        }
        adapter.setData(tmpContactsInfoList);
    }

}
