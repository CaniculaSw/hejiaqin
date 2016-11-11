package com.chinamobile.hejiaqin.business.ui.main;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.chinamobile.hejiaqin.business.ui.basic.FocusManager;
import com.chinamobile.hejiaqin.business.ui.basic.FragmentMgr;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.contact.fragment.ContactEditFragment;
import com.chinamobile.hejiaqin.business.ui.contact.fragment.ContactSearchFragment;
import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;
import com.chinamobile.hejiaqin.business.ui.contact.adapter.AppContactAdapter;
import com.customer.framework.utils.LogUtil;

import java.util.List;

/**
 * 应用联系人列表界面
 */
public class ContactListFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "AppContactListFragment";
    private IContactsLogic contactsLogic;
    private AppContactAdapter adapter;
    private HeaderView titleLayout;
    private View addLayout;
    private StickyListHeadersListView contactListView;

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID:
                List<ContactsInfo> contactsInfoList = (List<ContactsInfo>) msg.obj;
                adapter.setData(contactsInfoList);
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

        titleLayout = (HeaderView) view.findViewById(R.id.title);
        titleLayout.title.setText(R.string.contact_navigation_app_contacts);

        contactListView = (StickyListHeadersListView) view.findViewById(R.id.list);
        contactListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //当此选中的item的子控件需要获得焦点时
                parent.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            }
        });

        // 添加搜索框
        View operLayout = view.findViewById(R.id.oper);

        // 搜索联系人按钮
        View searchLayout = operLayout.findViewById(R.id.contact_search_layout);
        searchLayout.setOnClickListener(this);
        searchLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    contactListView.clearFocus();
                    contactListView.setFocusable(false);
                }
            }
        });
        // 添加联系人按钮
        addLayout = operLayout.findViewById(R.id.contact_add_layout);
        addLayout.setOnClickListener(this);
        addLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    contactListView.clearFocus();
                    contactListView.setFocusable(false);
                }
            }
        });

        // 添加adapter
        adapter = new AppContactAdapter(context);
        contactListView.setAdapter(adapter);
        contactListView.setItemsCanFocus(true);
        contactListView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);

        FocusManager.getInstance().addFocusViewInLeftFrag("1", addLayout);
    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) this.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    protected void initData() {
        contactsLogic.fetchAppContactLst();
    }

    @Override
    public void onResume() {
        super.onResume();
        FocusManager.getInstance().requestFocus(addLayout);
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
                enterSearchView();
                break;
            case R.id.contact_add_layout:
                enterAddView();
                break;
        }
    }

    private void enterSearchView() {
//        Intent intent = new Intent(getContext(), ContactSearchActivity.class);
//        intent.putExtra(ContactSearchActivity.Constant.INTENT_DATA_CONTACT_TYPE
//                , ContactSearchActivity.Constant.CONTACT_TYPE_APP);
//        startActivity(intent);
        ContactSearchFragment fragment = ContactSearchFragment.newInstance();
        FragmentMgr.getInstance().showContactFragment(fragment);
    }

    private void enterAddView() {
        ContactEditFragment fragment = ContactEditFragment.newInstance();
        FragmentMgr.getInstance().showContactFragment(fragment);
    }
}
