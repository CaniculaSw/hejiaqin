package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.ImageView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class ShareAppActivityTest extends ActivityUnitTestCase<ShareAppActivity> {
    private ShareAppActivity mActivity;

    ImageView qrCodeIv;
    HeaderView headerView;

    public ShareAppActivityTest() {
        super(ShareAppActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), ShareAppActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        qrCodeIv = (ImageView) mActivity.findViewById(R.id.more_qr_iv);
        headerView = (HeaderView) mActivity.findViewById(R.id.more_share_app_header);
    }

    public void testPreconditons() {
        assertNotNull(qrCodeIv);
        assertNotNull(headerView);
    }
}