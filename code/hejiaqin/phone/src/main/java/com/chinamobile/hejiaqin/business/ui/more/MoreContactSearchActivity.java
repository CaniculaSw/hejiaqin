package com.chinamobile.hejiaqin.business.ui.more;

import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.SearchResultContacts;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.more.adapter.MoreSearchContactAdapter;
import com.customer.framework.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/11/14.
 */
public class MoreContactSearchActivity extends BasicActivity implements View.OnClickListener {

    private static final String TAG = "MoreContactSearchActivity";

    private MoreSearchContactAdapter adapter;
    private EditText searchInput;
    private View searchDelete;
    private View searchCancel;
    private ListView contactsListView;

    private IContactsLogic contactsLogic;
    private int contactType = Constant.CONTACT_TYPE_APP;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_search;
    }

    @Override
    protected void initView() {

        searchInput = (EditText) findViewById(R.id.search_input);

        searchDelete = findViewById(R.id.search_del);

        searchCancel = findViewById(R.id.search_cancel);

        contactsListView = (ListView) findViewById(R.id.list);
        View headView = getLayoutInflater().inflate(R.layout.layout_contact_search_contact_head_view, null);
        contactsListView.addHeaderView(headView);

        adapter = new MoreSearchContactAdapter(this);
        contactsListView.setAdapter(adapter);
    }

    @Override
    protected void initDate() {
        contactType = getIntent().getIntExtra(MoreContactSearchActivity.Constant.INTENT_DATA_CONTACT_TYPE
                , MoreContactSearchActivity.Constant.CONTACT_TYPE_APP);
    }

    @Override
    protected void initListener() {
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

        searchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput.setText("");
            }
        });

        searchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack();
            }
        });
    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) this.getLogicByInterfaceClass(IContactsLogic.class);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.SEARCH_LOCAL_CONTACTS_SUCCESS_MSG_ID:
            case BussinessConstants.ContactMsgID.SEARCH_APP_CONTACTS_SUCCESS_MSG_ID:
                SearchResultContacts resultContacts = (SearchResultContacts) msg.obj;
                LogUtil.i(TAG, "receive");
                if (TAG.equals(resultContacts.getInvoker())) {
                    setData(resultContacts.getContactsInfos());
                }
                break;
            default:
                break;
        }
    }

    private void startSearch(String input) {
        if (isAppContact()) {
            contactsLogic.searchAppContactLst(input, TAG);
            return;
        }
        contactsLogic.searchLocalContactLst(input, TAG);
    }

    public void setData(List<ContactsInfo> contactsInfoList) {
        List<ContactsInfo> tmpContactsInfoList = new ArrayList<>();
        if (null != contactsInfoList) {
            tmpContactsInfoList.addAll(contactsInfoList);
        }
        adapter.setData(tmpContactsInfoList);
    }

    private boolean isAppContact() {
        return contactType == Constant.CONTACT_TYPE_APP;
    }

    /***/
   static public final class Constant {
        public static final String INTENT_DATA_CONTACT_TYPE = "intent_data_contact_type";

        public static final int CONTACT_TYPE_APP = 0;

        public static final int CONTACT_TYPE_SYS = 1;
    }
}
