package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.SearchResultContacts;
import com.chinamobile.hejiaqin.business.ui.contact.ContactSearchActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class MoreSearchAndSendContactActivityTest extends
        ActivityUnitTestCase<MoreSearchAndSendContactActivity> {
    private MoreSearchAndSendContactActivity mActivity;

    private EditText searchInput;
    private View searchDelete;
    private View searchCancel;
    private ListView contactsListView;

    public MoreSearchAndSendContactActivityTest() {
        super(MoreSearchAndSendContactActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                MoreSearchAndSendContactActivity.class);
        intent.putExtra(ContactSearchActivity.Constant.INTENT_DATA_CONTACT_TYPE,
                ContactSearchActivity.Constant.CONTACT_TYPE_APP);
        Set<String> set = new HashSet<>();
        set.add("aaa");
        intent.putExtra("selected", (Serializable) set);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        searchInput = (EditText) mActivity.findViewById(R.id.search_input);
        searchDelete = mActivity.findViewById(R.id.search_del);
        searchCancel = mActivity.findViewById(R.id.search_cancel);
        contactsListView = (ListView) mActivity.findViewById(R.id.list);
    }

    public void testInitView() {
        assertNotNull(searchInput);
        assertNotNull(searchDelete);
        assertNotNull(searchCancel);
        assertNotNull(contactsListView);
    }

    public void testOnClick() {
        searchDelete.performClick();
        searchCancel.performClick();
    }

    public void testHandleStateMessage() {
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        SearchResultContacts resultContacts = new SearchResultContacts("MoreContactSearchActivity", contactsInfoList);
        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.SEARCH_LOCAL_CONTACTS_SUCCESS_MSG_ID, resultContacts));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.SEARCH_APP_CONTACTS_SUCCESS_MSG_ID, resultContacts));

    }

    private Message generateMessage(int what) {
        return generateMessage(what, null);
    }

    private Message generateMessage(int what, Object obj) {
        Message message = Message.obtain();
        message.what = what;
        message.obj = obj;
        return message;
    }
}
