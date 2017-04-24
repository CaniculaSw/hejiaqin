package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class AboutActivityTest extends ActivityUnitTestCase<AboutActivity> {
    private AboutActivity mActivity;

    private HeaderView headerView;
    private LinearLayout feedBackLL;
    private RelativeLayout checkUpdateLL;
    private ImageView updateTips;
    private LinearLayout showContract;

    public AboutActivityTest() {
        super(AboutActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), AboutActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        headerView = (HeaderView) mActivity.findViewById(R.id.about_header);
        feedBackLL = (LinearLayout) mActivity.findViewById(R.id.about_feedback);
        checkUpdateLL = (RelativeLayout) mActivity.findViewById(R.id.about_check_update);
        showContract = (LinearLayout) mActivity.findViewById(R.id.about_contract);
        updateTips = (ImageView) mActivity.findViewById(R.id.about_new_version_tips_iv);
    }

    public void testPreconditons() {
        assertNotNull(headerView);
        assertNotNull(feedBackLL);
        assertNotNull(checkUpdateLL);
        assertNotNull(showContract);
        assertNotNull(updateTips);
    }
}
