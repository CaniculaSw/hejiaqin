package com.chinamobile.hejiaqin.tv.business.ui.login;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.model.login.req.RegisterSecondStepInfo;
import com.chinamobile.hejiaqin.tv.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.tv.business.ui.main.MainFragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiadong on 2016/4/27.
 */
public class RegisterInterestActivity extends BasicActivity implements View.OnClickListener{

    private ILoginLogic loginLogic;

    private CheckBox[] checkBoxes;

    private int[] checkBoxResId;

    private String[] interestId;

    private String[] interestDesc;

    private List<String> checkedInterestId = new ArrayList<String>();

    private RegisterSecondStepInfo info;

    private Button startButton;

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) this.getLogicByInterfaceClass(ILoginLogic.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_interest;
    }

    @Override
    protected void initView() {
        interestDesc = getResources().getStringArray(R.array.interestDesc);
        interestId = getResources().getStringArray(R.array.interestId);
        //标题栏
        TextView headerTitleTx = (TextView) findViewById(R.id.headertitle);
        headerTitleTx.setText(R.string.register_interest_title);
        checkBoxResId = new int[]{
                R.id.interest_checkBox1,
                R.id.interest_checkBox2,
                R.id.interest_checkBox3,
                R.id.interest_checkBox4,
                R.id.interest_checkBox5,
                R.id.interest_checkBox6,
                R.id.interest_checkBox7
        };
        checkBoxes = new CheckBox[]{(CheckBox) findViewById(R.id.interest_checkBox1),
                (CheckBox) findViewById(R.id.interest_checkBox2),
                (CheckBox) findViewById(R.id.interest_checkBox3),
                (CheckBox) findViewById(R.id.interest_checkBox4),
                (CheckBox) findViewById(R.id.interest_checkBox5),
                (CheckBox) findViewById(R.id.interest_checkBox6),
                (CheckBox) findViewById(R.id.interest_checkBox7)};
        for (int i = 0; i < interestDesc.length; i++) {
            checkBoxes[i].setText(interestDesc[i]);
        }

        startButton = (Button) findViewById(R.id.start_button);
    }

    @Override
    protected void initDate() {
        info = new RegisterSecondStepInfo();
        info.setLoginid(getIntent().getStringExtra(BussinessConstants.Login.INTENT_LOGIN_ID));
    }

    @Override
    protected void initListener() {
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                    for (int i = 0; i < checkBoxResId.length; i++) {
                        if (button.getId() == checkBoxResId[i]) {
                            if (isChecked) {
                                checkedInterestId.add(interestId[i]);
                            } else {
                                checkedInterestId.remove(interestId[i]);
                            }
                        }
                    }
                }
            });
        }

        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                start();
                break;
        }
    }

    private void start()
    {
        if (checkedInterestId.size() == 0) {

        }
        for(int i=0;i<checkedInterestId.size();i++)
        {
            if(i==0)
            {
                info.setPrefer(checkedInterestId.get(i));
            }else
            {
                info.setPrefer(info.getPrefer()+BussinessConstants.CommonInfo.SPLIT+checkedInterestId.get(i));
            }
        }
        super.showWaitDailog();
        loginLogic.registerSecondStep(info);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        //设置偏好成功或者失败都进入主页面
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.REGISTER_SECOND_STEP_SUCCESS_MSG_ID:
                super.dismissWaitDailog();
                Intent intent = new Intent(RegisterInterestActivity.this, MainFragmentActivity.class);
                this.startActivity(intent);
                this.finish();
                break;
            case BussinessConstants.LoginMsgID.REGISTER_SECOND_STEP_FAIL_MSG_ID:
                super.dismissWaitDailog();
                Intent intent2 = new Intent(RegisterInterestActivity.this, MainFragmentActivity.class);
                this.startActivity(intent2);
                this.finish();
                break;
            default:
                break;
        }
    }

    //回退也进入主页面
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterInterestActivity.this, MainFragmentActivity.class);
        this.startActivity(intent);
        super.onBackPressed();
    }


}
