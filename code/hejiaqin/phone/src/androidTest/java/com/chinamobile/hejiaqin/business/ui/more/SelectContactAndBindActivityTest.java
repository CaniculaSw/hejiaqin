package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.view.LayoutInflater;
import android.view.View;
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
public class SelectContactAndBindActivityTest extends
        ActivityUnitTestCase<SelectContactAndBindActivity> {
    private SelectContactAndBindActivity mActivity;

    private StickyListHeadersListView contactListView;
    private TextView searchText;
    private TextView tipText;
    private HeaderView headerView;
    private View progressLayout;
    private TextView progressTips;
    private View searchLayout;

    public SelectContactAndBindActivityTest() {
        super(SelectContactAndBindActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                SelectContactAndBindActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        contactListView = (StickyListHeadersListView) mActivity.findViewById(R.id.list);

        // 添加搜索框
        LayoutInflater inflater = (LayoutInflater) getInstrumentation().getTargetContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        searchLayout = inflater.inflate(R.layout.layout_contact_search_view, null);
        contactListView.addHeaderView(searchLayout);
        // 设置搜索显示的文字
        searchText = (TextView) searchLayout.findViewById(R.id.contact_search_text);
        tipText = (TextView) mActivity.findViewById(R.id.tip);
        headerView = (HeaderView) mActivity.findViewById(R.id.header);
        progressLayout = inflater.inflate(R.layout.layout_progress_tips, null);
        progressTips = (TextView) progressLayout.findViewById(R.id.progress_text);
    }

    public void testInitView() {
        assertNotNull(searchText);
        assertNotNull(tipText);
        assertNotNull(headerView);
        assertNotNull(progressLayout);
        assertNotNull(progressTips);
        assertNotNull(contactListView);
    }

    public void testOnClick() {
        headerView.backImageView.performClick();
        searchLayout.findViewById(R.id.contact_search_layout).performClick();
    }

    public void testHandleStateMessage() {
        List<ContactsInfo> contactsInfoList = new ArrayList<>();
        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID, contactsInfoList));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.SENDING_BIND_REQUEST));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_DELIVERY_OK));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_DISPLAY_OK));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_SEND_FAILED));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_UNDELIVERED));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.BIND_SUCCESS));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.BIND_DENIED));
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
