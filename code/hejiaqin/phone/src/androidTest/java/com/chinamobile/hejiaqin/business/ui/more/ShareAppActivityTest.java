package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.widget.ImageView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

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

    public void testInitView() {
        assertNotNull(qrCodeIv);
        assertNotNull(headerView);
    }

    public void testOnClick() {
        headerView.backImageView.performClick();
    }

    public void testHandleStateMessage() {
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
