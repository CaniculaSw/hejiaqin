package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class SelectableContactActivityTest extends ActivityUnitTestCase<SelectableContactActivity> {
    private SelectableContactActivity mActivity;

    private HeaderView mHeaderView;
    private StickyListHeadersListView mContactListView;
    private TextView mSelectAll;
    private TextView mSelectCount;

    public SelectableContactActivityTest() {
        super(SelectableContactActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                SelectableContactActivity.class);
        intent.putExtra("tvAccount", "123456");
        intent.putExtra("tvName", "aaaaa");
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mHeaderView = (HeaderView) mActivity.findViewById(R.id.more_select_contact_header);
        mContactListView = (StickyListHeadersListView) mActivity.findViewById(R.id.list);
        mSelectCount = (TextView) mActivity.findViewById(R.id.more_chosen);
        mSelectAll = (TextView) mActivity.findViewById(R.id.more_select_all);
    }

    public void testInitView() {
        assertNotNull(mHeaderView);
        assertNotNull(mContactListView);
        assertNotNull(mSelectCount);
        assertNotNull(mSelectAll);
    }

    public void testOnClick() {
        mHeaderView.backImageView.performClick();
        mHeaderView.tvRight.performClick();
        mSelectAll.performClick();
        mActivity.findViewById(R.id.contact_search_layout).performClick();
    }

    public void testHandleStateMessage() {
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID, contactsInfoList));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.CONTACT_CHECKED_STATED_CHANGED, 10, 9));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.CONTACT_CHECKED_STATED_CHANGED, 10, 10));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.SEND_CONTACT_RESPOND_SUCCESS));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_SEND_FAILED));
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

    private Message generateMessage(int what, int arg1, int arg2) {
        Message message = Message.obtain();
        message.what = what;
        message.arg1 = arg1;
        message.arg2 = arg2;
        return message;
    }
}
