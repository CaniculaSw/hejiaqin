package com.chinamobile.hejiaqin.business.ui.myMesage;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageDetailInfo;
import com.chinamobile.hejiaqin.business.model.setting.AppMessageDetailParame;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.utils.StringUtil;

/**
 * desc:消息详情
 *
 * @author wubg
 * @time 2016/5/6.
 */
public class MyMessageDetialActivity extends BasicActivity implements View.OnClickListener {

    private HeaderView headerView;

    private TextView tv_title;
    private TextView tv_content;
    private TextView headerTitleTx;

    private ISettingLogic settingLogic;
    private String mId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_message_detial;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView)findViewById(R.id.header_view);
        headerView.backImageView.setImageResource(R.mipmap.back);
        tv_content = (TextView) findViewById(R.id.tv_content);
    }

    private void getIntentData(){
        if(getIntent() != null) {
            mId = (getIntent().getStringExtra("id"));
        }
    }

    @Override
    protected void initDate() {
        getIntentData();
        AppMessageDetailParame parame = new AppMessageDetailParame();
        parame.setMessageid(mId);
        settingLogic.getMessageDetail(parame);
    }

    @Override
    protected void initListener() {
        headerView.backLayout.setOnClickListener(this);
    }

    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    @Override
    protected void initLogics() {
        settingLogic = (ISettingLogic) this.getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what){
            case BussinessConstants.SettingMsgID.GET_MESSAGE_DETAIL_SUCCESS_MSG_ID:
                AppMessageDetailInfo info = (AppMessageDetailInfo) msg.obj;
                setUpView(info);
                break;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:   //回退
                finish();
                break;
        }
    }

    private void setUpView(AppMessageDetailInfo info){
        if(info != null){
            headerView.title.setText((StringUtil.isNullOrEmpty(info.getTitle())?"":info.getTitle()));
            tv_content.setText((StringUtil.isNullOrEmpty(info.getContent())?"":info.getContent()));
        }
    }
}
