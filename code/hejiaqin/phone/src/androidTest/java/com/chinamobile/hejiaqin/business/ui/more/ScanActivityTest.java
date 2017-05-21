package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.SearchResultContacts;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.component.qrcode.ZXingView;
import com.customer.framework.component.qrcode.core.QRCodeView;

import java.util.ArrayList;
import java.util.List;

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

    public void testInitView() {
        assertNotNull(mQRCodeView);
        assertNotNull(mHeaderView);
    }

    public void testOnClick() {
        mHeaderView.backImageView.performClick();
        mHeaderView.tvRight.performClick();
    }

    public void testHandleStateMessage() {
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_DELIVERY_OK));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_DISPLAY_OK));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_SEND_FAILED));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.STATUS_UNDELIVERED));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.BIND_SUCCESS));
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.SENDING_BIND_REQUEST));

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
