package com.chinamobile.hejiaqin.business.ui.main;


import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.login.LoginActivity;
import com.chinamobile.hejiaqin.business.ui.more.AboutActivity;
import com.chinamobile.hejiaqin.business.ui.more.BindTVBoxActivity;
import com.chinamobile.hejiaqin.business.ui.more.MessageActivity;
import com.chinamobile.hejiaqin.business.ui.more.MoreFunActivity;
import com.chinamobile.hejiaqin.business.ui.more.ShareAppActivity;
import com.chinamobile.hejiaqin.business.ui.more.UserInfoActivity;


/**
 * desc: 设置
 * project:hejiaqin
 * version 001
 * author: zhanggj
 * Created: 2016/4/22.
 */
public class SettingFragment extends BasicFragment implements View.OnClickListener {
    LinearLayout itemUserLl;
    ImageView userAvatarIv;
    TextView userAccountTv;
    LinearLayout itemSendToTvLl;
    LinearLayout itemMoreFunLl;
    LinearLayout itemShareAppLl;
    LinearLayout itemAboutLl;
    LinearLayout itemQuitLl;
    HeaderView moreHeader;
    private ILoginLogic loginLogic;

    @Override
    protected void handleFragmentMsg(Message msg) {
    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_more;
    }

    @Override
    protected void initView(View view) {
        itemUserLl = (LinearLayout) view.findViewById(R.id.more_item_user);
        itemUserLl.setClickable(true);
        itemUserLl.setOnClickListener(this);

        userAvatarIv = (ImageView) view.findViewById(R.id.more_user_avatar_iv);
        userAccountTv = (TextView) view.findViewById(R.id.more_user_phone_num_tv);

        itemSendToTvLl = (LinearLayout) view.findViewById(R.id.more_item_send_to_tv);
        itemSendToTvLl.setClickable(true);
        itemSendToTvLl.setOnClickListener(this);

        itemMoreFunLl = (LinearLayout) view.findViewById(R.id.more_item_more_fun);
        itemMoreFunLl.setClickable(true);
        itemMoreFunLl.setOnClickListener(this);

        itemShareAppLl = (LinearLayout) view.findViewById(R.id.more_item_share_app);
        itemShareAppLl.setClickable(true);
        itemShareAppLl.setOnClickListener(this);

        itemAboutLl = (LinearLayout) view.findViewById(R.id.more_item_about);
        itemAboutLl.setClickable(true);
        itemAboutLl.setOnClickListener(this);

        itemQuitLl = (LinearLayout) view.findViewById(R.id.more_item_quit);
        itemQuitLl.setClickable(true);
        itemQuitLl.setOnClickListener(this);

        moreHeader = (HeaderView) view.findViewById(R.id.more_tab_header);
        moreHeader.title.setText("更多");
        moreHeader.rightBtn.setImageResource(R.mipmap.title_icon_message_nor);
        moreHeader.rightBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        userAccountTv.setText("13776570335");
        userAvatarIv.setImageResource(R.drawable.contact_photo_default);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_item_user:
                jumpToDetailUserProfile();
                break;
            case R.id.more_item_send_to_tv:
                jumpToSendToTv();
                break;
            case R.id.more_item_more_fun:
                jumpToMoreFun();
                break;
            case R.id.more_item_share_app:
                jumpToShareApp();
                break;
            case R.id.more_item_about:
                jumpToAbout();
                break;
            case R.id.more_item_quit:
                doQuit();
                break;
            case R.id.right_btn:
                jumpToMessage();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) super.getLogicByInterfaceClass(ILoginLogic.class);
    }

    private void jumpToDetailUserProfile() {
        Intent intent = new Intent(getContext(), UserInfoActivity.class);
        intent.putExtra("account", userAccountTv.getText().toString());
        this.startActivity(intent);
    }

    private void jumpToSendToTv() {
        Intent intent = new Intent(getContext(), BindTVBoxActivity.class);
        this.startActivity(intent);
    }

    private void jumpToMoreFun() {
        Intent intent = new Intent(getContext(), MoreFunActivity.class);
        this.startActivity(intent);
    }

    private void jumpToShareApp() {
        Intent intent = new Intent(getContext(), ShareAppActivity.class);
        this.startActivity(intent);
    }

    private void jumpToAbout() {
        Intent intent = new Intent(getContext(), AboutActivity.class);
        this.startActivity(intent);
    }

    private void doQuit() {
        loginLogic.logout();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        this.startActivity(intent);
        getActivity().finish();
    }

    private void jumpToMessage() {
        Intent intent = new Intent(getContext(), MessageActivity.class);
        this.startActivity(intent);
    }


}
