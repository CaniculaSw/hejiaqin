package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.FailResponse;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.login.ResetPasswordSecondStepActivity;

import org.junit.Test;

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

    @Test
    public void testInitView() {
        assertNotNull(headerView);
        assertNotNull(feedBackLL);
        assertNotNull(checkUpdateLL);
        assertNotNull(showContract);
        assertNotNull(updateTips);
    }

    public void testOnClick() {
        feedBackLL.performClick();
        checkUpdateLL.performClick();
        showContract.performClick();
    }

    public void testHandleStateMessage() {
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.NO_NEW_VERSION_AVAILABLE));

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
