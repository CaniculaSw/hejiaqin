package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class BindTVBoxFirstActivityTest extends ActivityUnitTestCase<BindTVBoxFirstActivity> {
    private BindTVBoxFirstActivity mActivity;

    private HeaderView headerView;
    private Button bindTVBtn;

    public BindTVBoxFirstActivityTest() {
        super(BindTVBoxFirstActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), BindTVBoxFirstActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        headerView = (HeaderView) mActivity.findViewById(R.id.more_bind_tv_header);
        bindTVBtn = (Button) mActivity.findViewById(R.id.more_bind_tv_btn);
    }

    public void testPreconditons() {
        assertNotNull(headerView);
        assertNotNull(bindTVBtn);
    }
}