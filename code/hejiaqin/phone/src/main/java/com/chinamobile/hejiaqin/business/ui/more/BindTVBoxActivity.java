package com.chinamobile.hejiaqin.business.ui.more;

import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.more.adapter.BindedTVListAdapter;
import com.chinamobile.hejiaqin.business.ui.more.dialog.BindTVDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/5/30.
 */
public class BindTVBoxActivity extends BasicActivity implements View.OnClickListener {
    private HeaderView mHeaderView;
    private RelativeLayout mBindTVBtn;
    private ListView mBindedTVList;
    private BindedTVListAdapter adapter;
    private ISettingLogic settingLogic;
    private List<UserInfo> bindedList;

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.SettingMsgID.GET_DEVICE_LIST_SUCCESSFUL:
                bindedList.addAll((List<UserInfo>) msg.obj);
                adapter.setData(bindedList);
                break;
            default:
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_tv;
    }

    @Override
    protected void initView() {
        mHeaderView = (HeaderView) findViewById(R.id.more_bind_tv_header);
        mBindTVBtn = (RelativeLayout) findViewById(R.id.choose_tv_rl);
        mBindedTVList = (ListView) findViewById(R.id.binded_tv_list);
        mHeaderView.title.setText(getString(R.string.more_bind_tv_btn_text));
        mHeaderView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);
        mHeaderView.backImageView.setClickable(true);
        adapter = new BindedTVListAdapter(getApplicationContext());
        mBindedTVList.setAdapter(adapter);
    }

    @Override
    protected void initDate() {
//        bindedList = new ArrayList<>();
//        UserInfo tv = new UserInfo();
//        tv.setName("包小美");
//        List<UserInfo> testList = new ArrayList<UserInfo>();
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(tv);
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(tv);
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(tv);
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(tv);
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(tv);
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(tv);
//        testList.add(UserInfoCacheManager.getUserInfo(this));
//        testList.add(tv);
//        adapter.setData(testList);
        settingLogic.getDeviceList();
    }

    @Override
    protected void initListener() {
        mBindTVBtn.setOnClickListener(this);
        mHeaderView.backImageView.setOnClickListener(this);
        mBindedTVList.setAdapter(adapter);
    }

    @Override
    protected void initLogics() {
        settingLogic = (ISettingLogic) getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.choose_tv_rl:
                showDialog();
                break;
            default:
                break;
        }
    }

    private void showDialog() {
        //创建一个AlertDialog对象
        final BindTVDialog dialog = new BindTVDialog(this, R.style.CalendarDialog);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.FILL_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        dialog.show();
    }
}
