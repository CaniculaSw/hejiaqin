package com.chinamobile.hejiaqin.business.ui.person;

import android.graphics.Color;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.person.IPersonLogic;
import com.chinamobile.hejiaqin.business.model.person.PhysiologyInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.utils.StringUtil;

/**
 * Created by wubg on 2016/4/28.
 */
public class PhysiologyActivity extends BasicActivity {

    private IPersonLogic personLogic;

    private HeaderView headerView;
    private EditText tv_height;
    private EditText tv_danguc;
    private EditText tv_xietang;
    private EditText tv_weight;
    private Button right_btn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_physiological_data;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView)findViewById(R.id.header_view);
        headerView.title.setText(R.string.physical_data);
        headerView.backImageView.setImageResource(R.mipmap.back);
        tv_height = (EditText) findViewById(R.id.tv_height);
        tv_height.setCursorVisible(false);
        tv_danguc = (EditText) findViewById(R.id.tv_danguc);
        tv_danguc.setCursorVisible(false);
        tv_xietang = (EditText) findViewById(R.id.tv_xietang);
        tv_xietang.setCursorVisible(false);
        tv_weight = (EditText) findViewById(R.id.tv_weight);
        tv_weight.setCursorVisible(false);
        right_btn = (Button) findViewById(R.id.right_btn);
        right_btn.setTextColor(Color.WHITE);
        right_btn.setText(R.string.physical_data_tizhi_confirm_to_modify);
    }

    @Override
    protected void initDate() {
        personLogic.loadPhysiologyInfo();
    }

    @Override
    protected void initListener() {
        headerView.backLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();   //回退
            }
        });

        right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhysiologyInfo physiologyInfo = new PhysiologyInfo();
                physiologyInfo.setHeight(StringUtil.isNullOrEmpty(tv_height.getText().toString().trim()) ? 0 : Long.parseLong(tv_height.getText().toString().trim()));
                physiologyInfo.setWeight(StringUtil.isNullOrEmpty(tv_weight.getText().toString().trim())?0:Long.parseLong(tv_weight.getText().toString().trim()));
                physiologyInfo.setCholesterol(StringUtil.isNullOrEmpty(tv_danguc.getText().toString().trim())?0:Long.parseLong(tv_danguc.getText().toString()));
                physiologyInfo.setGi(StringUtil.isNullOrEmpty(tv_xietang.getText().toString().trim())?0:Long.parseLong(tv_xietang.getText().toString()));
                personLogic.changePhysiologyInfo(physiologyInfo);
            }
        });
    }

    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    @Override
    protected void initLogics() {
        personLogic = (IPersonLogic) this.getLogicByInterfaceClass(IPersonLogic.class);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.PersonMsgID.GET_PHYSIOLOGY_INFO_SUCCESS_MSG_ID:
                if(msg.obj!=null)
                {
                    PhysiologyInfo physiologyInfo = (PhysiologyInfo)msg.obj;
                    setUpView(physiologyInfo);
                }
                break;
            case BussinessConstants.PersonMsgID.GET_PHYSIOLOGY_INFO_FAIL_MSG_ID:
                break;
            case BussinessConstants.PersonMsgID.CHANGE_PHYSIOLOGICAL_INFO_SUCCESS_MSG_ID:
                showToast(R.string.physical_data_change_success, Toast.LENGTH_SHORT, null);
                break;
            case BussinessConstants.PersonMsgID.CHANGE_PHYSIOLOGICAL_INFO_FAIL_MSG_ID:
                showToast(R.string.physical_data_change_fail, Toast.LENGTH_SHORT, null);
                break;
            default:
                break;
        }
    }

    private void setUpView(PhysiologyInfo physiologyInfo) {
        if (physiologyInfo != null) {
            tv_height.setText(String.valueOf(physiologyInfo.getHeight()));
            tv_weight.setText(String.valueOf(physiologyInfo.getWeight()));
            tv_danguc.setText(String.valueOf(physiologyInfo.getCholesterol()));
            tv_xietang.setText(String.valueOf(physiologyInfo.getGi()));
        }
    }
}
