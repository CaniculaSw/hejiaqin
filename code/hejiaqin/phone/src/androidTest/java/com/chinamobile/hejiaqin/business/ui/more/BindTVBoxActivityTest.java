package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

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

    public void testInitView() {
        assertNotNull(mHeaderView);
        assertNotNull(mBindTVBtn);
        assertNotNull(mBindedTVList);
    }

    public void testOnClick() {
        mHeaderView.backImageView.performClick();
    }

    public void testHandleStateMessage() {
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.GET_DEVICE_LIST_SUCCESSFUL));

        List<UserInfo> bindedList = new ArrayList<>();
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.GET_DEVICE_LIST_SUCCESSFUL, bindedList));


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
