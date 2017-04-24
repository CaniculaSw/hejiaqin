package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.login.LoginActivity;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class BindTVBoxActivityTest extends ActivityUnitTestCase<BindTVBoxActivity> {
    private BindTVBoxActivity mActivity;

    private HeaderView mHeaderView;
    private RelativeLayout mBindTVBtn;
    private ListView mBindedTVList;

    public BindTVBoxActivityTest() {
        super(BindTVBoxActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), BindTVBoxActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mHeaderView = (HeaderView) mActivity.findViewById(R.id.more_bind_tv_header);
        mBindTVBtn = (RelativeLayout) mActivity.findViewById(R.id.choose_tv_rl);
        mBindedTVList = (ListView) mActivity.findViewById(R.id.binded_tv_list);
    }

    public void testPreconditons() {
        assertNotNull(mHeaderView);
        assertNotNull(mBindTVBtn);
        assertNotNull(mBindedTVList);
    }
}