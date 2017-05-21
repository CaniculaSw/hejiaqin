package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

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

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                ShowContractActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mHeadView = (HeaderView) mActivity.findViewById(R.id.header);
        mContractContentTv = (TextView) mActivity.findViewById(R.id.contract_content);
    }

    public void testInitView() {
        assertNotNull(mHeadView);
        assertNotNull(mContractContentTv);
    }

    public void testOnClick() {
        mHeadView.backImageView.performClick();
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
