package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class SelectContactAndBindActivityTest extends ActivityUnitTestCase<SelectContactAndBindActivity> {
    private SelectContactAndBindActivity mActivity;

    private StickyListHeadersListView contactListView;
    private TextView searchText;
    private TextView tipText;
    private HeaderView headerView;
    private View progressLayout;
    private TextView progressTips;
    private View searchLayout;

    public SelectContactAndBindActivityTest() {
        super(SelectContactAndBindActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), SelectContactAndBindActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        contactListView = (StickyListHeadersListView) mActivity.findViewById(R.id.list);

        // 添加搜索框
        LayoutInflater inflater = (LayoutInflater) getInstrumentation().getTargetContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        searchLayout = inflater.inflate(R.layout.layout_contact_search_view, null);
        contactListView.addHeaderView(searchLayout);
        // 设置搜索显示的文字
        searchText = (TextView) searchLayout.findViewById(R.id.contact_search_text);
        tipText = (TextView) mActivity.findViewById(R.id.tip);
        headerView = (HeaderView) mActivity.findViewById(R.id.header);
        progressLayout = inflater.inflate(R.layout.layout_progress_tips, null);
        progressTips = (TextView) progressLayout.findViewById(R.id.progress_text);
    }

    public void testPreconditons() {
        assertNotNull(searchText);
        assertNotNull(tipText);
        assertNotNull(headerView);
        assertNotNull(progressLayout);
        assertNotNull(progressTips);
        assertNotNull(contactListView);
    }
}