package com.chinamobile.hejiaqin.business.ui.setting.fragment;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.more.TvSettingInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.setting.dialog.AutoAnswerNumberSettingDialog;
import com.chinamobile.hejiaqin.tv.R;
import com.customer.framework.utils.StringUtil;

/**
 * Created by eshaohu on 16/8/24.
 */
public class AutoAnswerSettingFragment extends BasicFragment implements View.OnClickListener {
//    ISettingLogic settingLogic;
    HeaderView headerView;
    LinearLayout yes;
    LinearLayout no;
    ImageView yesIv;
    ImageView noIv;
    LinearLayout numberSettingLayout;
    LinearLayout numberOneBtn;
    LinearLayout numberTwoBtn;
    LinearLayout numberThreeBtn;
    LinearLayout numberFourBtn;
    TextView numberOne;
    TextView numberTwo;
    TextView numberThree;
    TextView numberFour;

    @Override
    protected void initLogics() {
        super.initLogics();
//        settingLogic = (SettingLogic) getLogicBuilder().getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {
        switch (msg.what) {
            case BussinessConstants.SettingMsgID.AUTO_ANSWER_SETTING_COMMIT:
                updateSettingUI();
                break;
            default:
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_auto_answer_setting;
    }

    @Override
    protected void initView(View view) {
        headerView = (HeaderView) view.findViewById(R.id.title);
        headerView.title.setText(getString(R.string.auto_answer));
        yes = (LinearLayout) view.findViewById(R.id.btn_yes);
        no = (LinearLayout) view.findViewById(R.id.btn_no);
        yesIv = (ImageView) view.findViewById(R.id.btn_yes_iv);
        noIv = (ImageView) view.findViewById(R.id.btn_no_iv);

        numberSettingLayout = (LinearLayout) view.findViewById(R.id.setting_number_ll);
        numberOneBtn = (LinearLayout) view.findViewById(R.id.btn_number_one);
        numberTwoBtn = (LinearLayout) view.findViewById(R.id.btn_number_two);
        numberThreeBtn = (LinearLayout) view.findViewById(R.id.btn_number_three);
        numberFourBtn = (LinearLayout) view.findViewById(R.id.btn_number_four);

        numberOne = (TextView) view.findViewById(R.id.number_one_tv);
        numberTwo = (TextView) view.findViewById(R.id.number_two_tv);
        numberThree = (TextView) view.findViewById(R.id.number_three_tv);
        numberFour = (TextView) view.findViewById(R.id.number_four_tv);

        yes.setClickable(true);
        no.setClickable(true);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        numberOneBtn.setOnClickListener(this);
        numberTwoBtn.setOnClickListener(this);
        numberThreeBtn.setOnClickListener(this);
        numberFourBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        TvSettingInfo settingInfo = UserInfoCacheManager.getUserSettingInfo(getContext());
        if (settingInfo == null) {
            showNumberSettingBtn(false);
        } else {
            showNumberSettingBtn(settingInfo.isAutoAnswer());
        }
    }

    private void updateSettingUI() {
        TvSettingInfo settingInfo = UserInfoCacheManager.getUserSettingInfo(getContext());
        numberOne.setText(StringUtil.isNullOrEmpty(settingInfo.getNumberOne()) ? getString(R.string.number_one) : settingInfo.getNumberOne());
        numberTwo.setText(StringUtil.isNullOrEmpty(settingInfo.getNumberTwo()) ? getString(R.string.number_two) : settingInfo.getNumberTwo());
        numberThree.setText(StringUtil.isNullOrEmpty(settingInfo.getNumberThree()) ? getString(R.string.number_three) : settingInfo.getNumberThree());
        numberFour.setText(StringUtil.isNullOrEmpty(settingInfo.getNumberFour()) ? getString(R.string.number_four) : settingInfo.getNumberFour());
    }

    private void showNumberSettingBtn(boolean isYes) {
        TvSettingInfo settingInfo = UserInfoCacheManager.getUserSettingInfo(getContext());
        if (settingInfo == null) {
            settingInfo = new TvSettingInfo();
        }
        if (isYes != settingInfo.isAutoAnswer()) {
            settingInfo.setAutoAnswer(isYes);
            UserInfoCacheManager.updateUserSetting(getContext(), settingInfo);
        }
        if (isYes) {
            yes.requestFocus();
            noIv.setVisibility(View.GONE);
            yesIv.setVisibility(View.VISIBLE);
            numberSettingLayout.setVisibility(View.VISIBLE);

            yes.setNextFocusDownId(R.id.btn_number_one);
            no.setNextFocusDownId(R.id.btn_number_two);
            updateSettingUI();
        } else {
            no.requestFocus();
            yesIv.setVisibility(View.GONE);
            noIv.setVisibility(View.VISIBLE);
            numberSettingLayout.setVisibility(View.GONE);
            yes.setNextFocusDownId(R.id.btn_yes);
            no.setNextFocusDownId(R.id.btn_no);
        }
    }

    @Override
    public void onClick(View view) {
        TvSettingInfo settingInfo = UserInfoCacheManager.getUserSettingInfo(getContext());
        switch (view.getId()) {
            case R.id.btn_no:
                showNumberSettingBtn(false);
                break;
            case R.id.btn_yes:
                showNumberSettingBtn(true);
                break;
            case R.id.btn_number_one:
                inputNumberSetting(settingInfo.getNumberOne(), "numberOne");
                break;
            case R.id.btn_number_two:
                inputNumberSetting(settingInfo.getNumberTwo(), "numberTwo");
                break;
            case R.id.btn_number_three:
                inputNumberSetting(settingInfo.getNumberThree(), "numberThree");
                break;
            case R.id.btn_number_four:
                inputNumberSetting(settingInfo.getNumberFour(), "numberFour");
                break;
            default:
                break;
        }
    }


    private void inputNumberSetting(String number,String id) {
        Intent intent = new Intent(getActivity(), AutoAnswerNumberSettingDialog.class);
        intent.putExtra("number", number);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public View getFirstFouseView() {
        return yes;
    }
}
