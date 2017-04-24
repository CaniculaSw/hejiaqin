package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class InputAcountActivityTest extends ActivityUnitTestCase<InputAcountActivity> {
    private InputAcountActivity mActivity;

    private HeaderView mHeaderView;
    private EditText mName;
    private EditText mNumber;
    private RelativeLayout progressLayout;
    private TextView progressTip;

    public InputAcountActivityTest() {
        super(InputAcountActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), InputAcountActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mHeaderView = (HeaderView) mActivity.findViewById(R.id.more_input_contact_title);
        mName = (EditText) mActivity.findViewById(R.id.more_input_contact_name_et);
        mNumber = (EditText) mActivity.findViewById(R.id.more_input_contact_number_et);
        progressLayout = (RelativeLayout) mActivity.findViewById(R.id.progress_tips);
        progressTip = (TextView) mActivity.findViewById(R.id.progress_text);
    }

    public void testPreconditons() {
        assertNotNull(mHeaderView);
        assertNotNull(mName);
        assertNotNull(mNumber);
        assertNotNull(progressLayout);
        assertNotNull(progressTip);
    }
}