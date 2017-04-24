package com.chinamobile.hejiaqin.business.ui.contact;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.EditText;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class InputInfoActivityTest extends ActivityUnitTestCase<InputInfoActivity> {
    private InputInfoActivity mActivity;

    private HeaderView titleLayout;
    private EditText input;
    private View cancel;

    public InputInfoActivityTest() {
        super(InputInfoActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), InputInfoActivity.class);
        startActivity(intent, null, null);


        mActivity = getActivity();
        assertNotNull(mActivity);

        titleLayout = (HeaderView) mActivity.findViewById(R.id.title);
        input = (EditText) mActivity.findViewById(R.id.input);
        cancel = mActivity.findViewById(R.id.delete);
    }

    public void testPreconditons() {
        assertNotNull(titleLayout);
        assertNotNull(input);
        assertNotNull(cancel);
    }
}