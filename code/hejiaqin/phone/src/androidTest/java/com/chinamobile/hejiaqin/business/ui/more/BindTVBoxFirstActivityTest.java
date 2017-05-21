package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.widget.Button;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

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

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                BindTVBoxFirstActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        headerView = (HeaderView) mActivity.findViewById(R.id.more_bind_tv_header);
        bindTVBtn = (Button) mActivity.findViewById(R.id.more_bind_tv_btn);
    }

    public void testInitView() {
        assertNotNull(headerView);
        assertNotNull(bindTVBtn);
    }


    public void testOnClick() {
        headerView.backImageView.performClick();
    }

    public void testHandleStateMessage() {
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.UPDATE_DEVICE_LIST_REQUEST));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.BIND_SUCCESS));
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
