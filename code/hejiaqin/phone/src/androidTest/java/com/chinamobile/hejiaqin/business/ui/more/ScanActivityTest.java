package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.component.qrcode.ZXingView;
import com.customer.framework.component.qrcode.core.QRCodeView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class ScanActivityTest extends ActivityUnitTestCase<ScanActivity> {
    private ScanActivity mActivity;

    private HeaderView mHeaderView;
    private QRCodeView mQRCodeView;

    public ScanActivityTest() {
        super(ScanActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), ScanActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mQRCodeView = (ZXingView) mActivity.findViewById(R.id.more_zxingview);
        mHeaderView = (HeaderView) mActivity.findViewById(R.id.more_scan_header);
    }

    public void testPreconditons() {
        assertNotNull(mQRCodeView);
        assertNotNull(mHeaderView);
    }
}
