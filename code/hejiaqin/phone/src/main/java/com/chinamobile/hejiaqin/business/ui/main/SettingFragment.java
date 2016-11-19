package com.chinamobile.hejiaqin.business.ui.main;


import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.MyActivityManager;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.login.LoginActivity;
import com.chinamobile.hejiaqin.business.ui.more.AboutActivity;
import com.chinamobile.hejiaqin.business.ui.more.BindTVBoxActivity;
import com.chinamobile.hejiaqin.business.ui.more.BindTVBoxFirstActivity;
import com.chinamobile.hejiaqin.business.ui.more.MessageActivity;
import com.chinamobile.hejiaqin.business.ui.more.MoreFunActivity;
import com.chinamobile.hejiaqin.business.ui.more.ShareAppActivity;
import com.chinamobile.hejiaqin.business.ui.more.UserInfoActivity;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.utils.StringUtil;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * desc: 设置
 * project:hejiaqin
 * version 001
 * author:
 * Created: 2016/4/22.
 */
public class SettingFragment extends BasicFragment implements View.OnClickListener {
    LinearLayout itemUserLl;
    CircleImageView userAvatarIv;
    TextView userAccountTv;
    LinearLayout itemSendToTvLl;
    LinearLayout itemMoreFunLl;
    LinearLayout itemShareAppLl;
    LinearLayout itemAboutLl;
    LinearLayout itemQuitLl;
    HeaderView moreHeader;
    private ILoginLogic loginLogic;
    private UserInfo userInfo;
    private IVoipLogic mVoipLogic;

    private static final String TAG = "SettingFragment";

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

        userAvatarIv = (CircleImageView) view.findViewById(R.id.more_user_avatar_ci);
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
        moreHeader.logoIv.setImageResource(R.mipmap.logo_small);
        moreHeader.title.setText("更多");
        moreHeader.rightBtn.setImageResource(R.mipmap.title_icon_message_nor);
        moreHeader.rightBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        userInfo = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
        if (null != userInfo) {
            userAccountTv.setText(userInfo.getUserName());
            Picasso.with(SettingFragment.this.getContext())
                    .load(BussinessConstants.ServerInfo.HTTP_ADDRESS + "/" + userInfo.getPhotoSm())
                    .placeholder(R.drawable.contact_photo_default)
                    .error(R.drawable.contact_photo_default).into(userAvatarIv);
        }
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
        mVoipLogic = (IVoipLogic) super.getLogicByInterfaceClass(IVoipLogic.class);
    }

    @Override
    public void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.LoginMsgID.UPDATE_PHOTO_SUCCESS_MSG_ID:
                userInfo = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
                if (null != userInfo && !StringUtil.isNullOrEmpty(userInfo.getPhotoSm())) {
                    Picasso.with(SettingFragment.this.getActivity().getApplicationContext())
                            .load(BussinessConstants.ServerInfo.HTTP_ADDRESS + "/" + userInfo.getPhotoSm()).into(userAvatarIv);
                }

                break;
            default:
                break;
        }
    }


    private void jumpToDetailUserProfile() {
        Intent intent = new Intent(getContext(), UserInfoActivity.class);
        //intent.putExtra("account", userAccountTv.getText().toString());
        this.startActivity(intent);
    }

    private void jumpToSendToTv() {
        Intent intent;
        if (UserInfoCacheManager.isBinded(getContext())) {
            intent = new Intent(getContext(), BindTVBoxActivity.class);
        }else {
            intent = new Intent(getContext(), BindTVBoxFirstActivity.class);
        }
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
        mVoipLogic.logout();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        this.startActivity(intent);
        MyActivityManager.getInstance().finishAllActivity(LoginActivity.class.getName());
    }

    private void jumpToMessage() {
        Intent intent = new Intent(getContext(), MessageActivity.class);
        this.startActivity(intent);
    }


}
