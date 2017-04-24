package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.chinamobile.hejiaqin.R;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class MoreContactSearchActivityTest extends ActivityUnitTestCase<MoreContactSearchActivity> {
    private MoreContactSearchActivity mActivity;

    private EditText searchInput;
    private View searchDelete;
    private View searchCancel;
    private ListView contactsListView;

    public MoreContactSearchActivityTest() {
        super(MoreContactSearchActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                MoreContactSearchActivity.class);
        intent.putExtra(MoreContactSearchActivity.Constant.INTENT_DATA_CONTACT_TYPE,
                MoreContactSearchActivity.Constant.CONTACT_TYPE_APP);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        searchInput = (EditText) mActivity.findViewById(R.id.search_input);
        searchDelete = mActivity.findViewById(R.id.search_del);
        searchCancel = mActivity.findViewById(R.id.search_cancel);
        contactsListView = (ListView) mActivity.findViewById(R.id.list);
    }

    public void testPreconditons() {
        assertNotNull(searchInput);
        assertNotNull(searchDelete);
        assertNotNull(searchCancel);
        assertNotNull(contactsListView);
    }
}
