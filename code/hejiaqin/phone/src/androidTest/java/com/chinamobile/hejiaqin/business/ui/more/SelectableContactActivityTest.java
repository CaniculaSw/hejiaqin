package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;

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

    public void testPreconditons() {
        assertNotNull(mHeaderView);
        assertNotNull(mContactListView);
        assertNotNull(mSelectCount);
        assertNotNull(mSelectAll);
    }
}
