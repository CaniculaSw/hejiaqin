package com.chinamobile.hejiaqin.business.ui.setting.fragment;

import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.tv.R;
import com.huawei.rcs.call.CallApi;

/***/
public class CheckStatusFragment extends BasicFragment {
    LinearLayout normalLayout;
    LinearLayout abnormalLayout;

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_check_status;
    }

    @Override
    protected void initView(View view) {
        normalLayout = (LinearLayout) view.findViewById(R.id.check_status_normal_ll);
        abnormalLayout = (LinearLayout) view.findViewById(R.id.check_status_abnormal_ll);
        //TODO: 根据摄像头检测结果决定显示那个Layout
        setStatusLayout(CallApi.getCameraCount() > 0 ? true : false);
    }

    private void setStatusLayout(boolean isNormal) {
        if (isNormal) {
            normalLayout.setVisibility(View.VISIBLE);
            abnormalLayout.setVisibility(View.GONE);
        } else {
            normalLayout.setVisibility(View.GONE);
            abnormalLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {

    }

    public View getFirstFouseView() {
        return null;
    }
}
