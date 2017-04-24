package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

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

        Intent intent = new Intent(getInstrumentation().getTargetContext(), SysMessageDetailActivity.class);
        intent.putExtra("msgID", "1243");
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mTitleTv = (TextView) mActivity.findViewById(R.id.sys_message_detail_title);
        mDateTv = (TextView) mActivity.findViewById(R.id.sys_message_detail_date);
        mBodyTv = (TextView) mActivity.findViewById(R.id.sys_message_detail_body);
        headerView = (HeaderView) mActivity.findViewById(R.id.system_message_detail_header);
    }

    public void testPreconditons() {
        assertNotNull(mTitleTv);
        assertNotNull(mDateTv);
        assertNotNull(mBodyTv);
        assertNotNull(headerView);
    }
}