package com.chinamobile.hejiaqin.business.ui.main;


import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.more.MoreFunActivity;
import com.chinamobile.hejiaqin.business.ui.more.ShareAppActivity;


/**
 * desc: 设置
 * project:hejiaqin
 * version 001
 * author: zhanggj
 * Created: 2016/4/22.
 */
public class SettingFragment extends BasicFragment implements View.OnClickListener{
    LinearLayout itemUserLl;
    ImageView    userAvatarIv;
    TextView     userPhoneNumTv;
    LinearLayout itemSendToTvLl;
    LinearLayout itemMoreFunLl;
    LinearLayout itemShareAppLl;
    LinearLayout itemAboutLl;
    LinearLayout itemQuitLl;
    HeaderView   moreHeader;
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

        userAvatarIv = (ImageView)  view.findViewById(R.id.more_user_avatar_iv);
        userPhoneNumTv = (TextView) view.findViewById(R.id.more_user_phone_num_tv);

        itemSendToTvLl = (LinearLayout) view.findViewById(R.id.more_item_send_to_tv);
        itemSendToTvLl.setClickable(true);
        itemSendToTvLl.setOnClickListener(this);

        itemMoreFunLl = (LinearLayout)  view.findViewById(R.id.more_item_more_fun);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
                jumpToQuit();
                break;
            default:
                break;
        }
    }

    private void jumpToDetailUserProfile(){
    }
    private void jumpToSendToTv(){
    }
    private void jumpToMoreFun(){
        Intent intent = new Intent(getContext(),MoreFunActivity.class);
        this.startActivity(intent);
    }
    private void jumpToShareApp(){
        Intent intent = new Intent(getContext(),ShareAppActivity.class);
        this.startActivity(intent);
    }
    private void jumpToAbout(){
    }
    private void jumpToQuit(){
    }
}
