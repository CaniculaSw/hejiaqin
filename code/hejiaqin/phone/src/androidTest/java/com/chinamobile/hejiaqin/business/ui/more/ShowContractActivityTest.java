package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class ShowContractActivityTest extends ActivityUnitTestCase<ShowContractActivity> {
    private ShowContractActivity mActivity;

    private HeaderView mHeadView;
    private TextView mContractContentTv;

    public ShowContractActivityTest() {
        super(ShowContractActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), ShowContractActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mHeadView = (HeaderView) mActivity.findViewById(R.id.header);
        mContractContentTv = (TextView) mActivity.findViewById(R.id.contract_content);
    }

    public void testPreconditons() {
        assertNotNull(mHeadView);
        assertNotNull(mContractContentTv);
    }
}