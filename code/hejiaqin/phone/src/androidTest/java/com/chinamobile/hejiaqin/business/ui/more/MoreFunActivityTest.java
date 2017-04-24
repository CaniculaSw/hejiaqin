package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class MoreFunActivityTest extends ActivityUnitTestCase<MoreFunActivity> {
    private MoreFunActivity mActivity;

    private HeaderView headerView;

    public MoreFunActivityTest() {
        super(MoreFunActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), MoreFunActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        headerView = (HeaderView) mActivity.findViewById(R.id.more_fun_header);
    }

    public void testPreconditons() {
        assertNotNull(headerView);
    }
}
