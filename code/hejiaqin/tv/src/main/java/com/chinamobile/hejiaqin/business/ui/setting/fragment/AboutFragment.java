package com.chinamobile.hejiaqin.business.ui.setting.fragment;

import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.SystemUtil;

import org.w3c.dom.Text;

/**
 * Created by eshaohu on 16/8/30.
 */
public class AboutFragment extends BasicFragment {
    TextView releaseVersion;
    TextView aboutHejiaqin;

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initView(View view) {
        releaseVersion = (TextView) view.findViewById(R.id.release_version);
        aboutHejiaqin = (TextView) view.findViewById(R.id.about_hejiaqin);
        releaseVersion.setText(SystemUtil.getPackageVersionName(getActivity()));
        aboutHejiaqin.setText(getString(R.string.about_hejiaqin));
    }

    @Override
    protected void initData() {

    }

    public View getFirstFouseView()
    {
        return null;
    }
}
