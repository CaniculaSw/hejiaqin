package com.chinamobile.hejiaqin.business.ui.more;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragmentActivity;
import com.chinamobile.hejiaqin.business.ui.more.fragment.MessageFragment;

/**
 * Created by eshaohu on 16/5/25.
 */
public class MoreMessageActivity extends BasicFragmentActivity {
    FragmentManager mFm;
    BasicFragment messageFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_message;
    }

    @Override
    protected void initView() {
        mFm = getSupportFragmentManager();
        messageFragment = new MessageFragment();
        FragmentTransaction ft = mFm.beginTransaction();
        ft.add(R.id.more_message_content, messageFragment);
        ft.commit();
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initLogics() {

    }
}
