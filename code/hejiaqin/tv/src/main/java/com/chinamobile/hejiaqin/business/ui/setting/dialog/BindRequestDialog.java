package com.chinamobile.hejiaqin.business.ui.setting.dialog;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.LogicBuilder;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.utils.CaaSUtil;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.XmlParseUtil;
import com.huawei.rcs.message.TextMessage;

/**
 * Created by eshaohu on 16/11/12.
 */
public class BindRequestDialog extends BasicActivity implements View.OnClickListener {
    public TextMessage message;
    private String fromNumber;
    private LinearLayout agreeButton;
    private LinearLayout deniedButton;
    private TextView tips;
    private ISettingLogic settingLogic;
    @Override
    protected void initLogics() {
        settingLogic = (ISettingLogic) LogicBuilder.getInstance(this).getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popwindow_bind_request_incoming;
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what){
            case BussinessConstants.SettingMsgID.SAVE_BIND_REQUEST_SUCCESS:
                settingLogic.sendBindResult(message.getPeer().getNumber(), CaaSUtil.OpCode.BIND_SUCCESS);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        message = (TextMessage) intent.getSerializableExtra("message");
//        fromNumber = message.getPeer().getNumber();
        fromNumber = XmlParseUtil.getElemString(message.getContent(),"Phone");
        agreeButton = (LinearLayout) findViewById(R.id.btn_agree);
        deniedButton = (LinearLayout) findViewById(R.id.btn_denied);
        tips = (TextView) findViewById(R.id.hint);
        tips.setText(getString(R.string.prompt_bind_hint, fromNumber));
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        agreeButton.setOnClickListener(this);
        deniedButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_agree:
                settingLogic.saveBindRequest(message);
                break;
            case R.id.btn_denied:
                settingLogic.sendBindResult(message.getPeer().getNumber(), CaaSUtil.OpCode.BIND_DENIED);
                finish();
                break;
            default:
                break;
        }
    }
}
