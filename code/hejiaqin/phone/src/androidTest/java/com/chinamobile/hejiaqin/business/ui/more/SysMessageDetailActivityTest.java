package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class SysMessageDetailActivityTest extends ActivityUnitTestCase<SysMessageDetailActivity> {
    private SysMessageDetailActivity mActivity;

    private TextView mTitleTv;
    private TextView mDateTv;
    private TextView mBodyTv;
    private HeaderView headerView;

    public SysMessageDetailActivityTest() {
        super(SysMessageDetailActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                SysMessageDetailActivity.class);
        intent.putExtra("msgID", "1243");
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mTitleTv = (TextView) mActivity.findViewById(R.id.sys_message_detail_title);
        mDateTv = (TextView) mActivity.findViewById(R.id.sys_message_detail_date);
        mBodyTv = (TextView) mActivity.findViewById(R.id.sys_message_detail_body);
        headerView = (HeaderView) mActivity.findViewById(R.id.system_message_detail_header);
    }

    public void testInitView() {
        assertNotNull(mTitleTv);
        assertNotNull(mDateTv);
        assertNotNull(mBodyTv);
        assertNotNull(headerView);
    }

    public void testOnClick() {
        headerView.backImageView.performClick();
    }

    public void testHandleStateMessage() {
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
