package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class FeedBackActivityTest extends ActivityUnitTestCase<FeedBackActivity> {
    private FeedBackActivity mActivity;

    private HeaderView headerView;
    private EditText feedBackContentEt;
    private TextView wordCountTv;
    private Button submitBtn;

    public FeedBackActivityTest() {
        super(FeedBackActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), FeedBackActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        headerView = (HeaderView) mActivity.findViewById(R.id.feedback_header);
        feedBackContentEt = (EditText) mActivity.findViewById(R.id.feeback_content);
        wordCountTv = (TextView) mActivity.findViewById(R.id.feedback_word_count);
        submitBtn = (Button) mActivity.findViewById(R.id.feedback_submit_btn);
    }

    public void testInitView() {
        assertNotNull(headerView);
        assertNotNull(feedBackContentEt);
        assertNotNull(wordCountTv);
        assertNotNull(submitBtn);
    }

    public void testOnClick() {
        headerView.backImageView.performClick();
        submitBtn.performClick();
    }

    public void testHandleStateMessage() {
        mActivity.handleStateMessage(generateMessage(BussinessConstants.SettingMsgID.SEND_FEED_BACK_SUCCESS));
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
