package com.chinamobile.hejiaqin.business.ui.person;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wbg
 * @desc prefer to choose
 * @time 2016/5/13.
 */
public class ChoosePreferActivity extends BasicActivity implements View.OnClickListener {


    private CheckBox[] checkBoxes;

    private int[] checkBoxResId;

    private String[] interestId;

    private String[] interestDesc;

    private List<Integer> checkedInterestSeq = new ArrayList<Integer>();

    private Button startButton;
    private HeaderView headerView;

    private String checkedPreferIds="";
    private String checkedPrefers="";

    @Override
    protected void initLogics() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_prefer_choose;
    }

    @Override
    protected void initView() {
        interestDesc = getResources().getStringArray(R.array.interestDesc);
        interestId = getResources().getStringArray(R.array.interestId);
        headerView = (HeaderView) findViewById(R.id.header_view);
        headerView.title.setText(R.string.person_information_prefer);
        headerView.backImageView.setImageResource(R.mipmap.back);
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
        String preferIds = getIntent().getStringExtra(BussinessConstants.Person.INTENT_CHECKED_PREFER_IDS);
        //回显
        if (!StringUtil.isNullOrEmpty(preferIds)) {
            String[] preferIdArray = preferIds.split(BussinessConstants.CommonInfo.SPLIT);
            for (int i = 0; i < preferIdArray.length; i++) {
                for (int j = 0; j < interestId.length; j++) {
                    if (preferIdArray[i].equals(interestId[j])) {
                        checkBoxes[j].setChecked(true);
                    }
                }
            }
        }
    }

    @Override
    protected void initListener() {
        headerView.backLayout.setOnClickListener(this);
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                    for (int i = 0; i < checkBoxResId.length; i++) {
                        if (button.getId() == checkBoxResId[i]) {
                            if (isChecked) {
                                checkedInterestSeq.add(new Integer(i));
                            } else {
                                checkedInterestSeq.remove(new Integer(i));
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
            case R.id.backLayout:
                finish();   //回退
                break;
        }
    }

    private void start() {
        for (int i = 0; i < checkedInterestSeq.size(); i++) {
            if (i == 0) {
                checkedPreferIds =interestId[checkedInterestSeq.get(i)];
                checkedPrefers= interestDesc[checkedInterestSeq.get(i)];
            } else {
                checkedPreferIds =checkedPreferIds + BussinessConstants.CommonInfo.SPLIT + interestId[checkedInterestSeq.get(i)];
                checkedPrefers =checkedPrefers + BussinessConstants.CommonInfo.SPLIT + interestDesc[checkedInterestSeq.get(i)];
            }
        }
        Intent it = new Intent();
        it.putExtra(BussinessConstants.Person.INTENT_CHECKED_PREFER_IDS,checkedPreferIds );
        it.putExtra(BussinessConstants.Person.INTENT_CHECKED_PREFERS,checkedPrefers );
        setResult(Activity.RESULT_OK,it);
        finish();
    }


}
